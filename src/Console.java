import java.io.FileReader;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
    private static long idCounter = 1;

    private static synchronized long createID()
    {
        return (idCounter++);
    }

    public static SpaceMarineCollection startCollection(String nameFile, SpaceMarineCollection collection) throws Exception{
        FileReader fr = new FileReader(nameFile);
        Scanner file = new Scanner(fr);
        String line;
        while (file.hasNextLine()) {
            try {
                line = file.nextLine().trim();
                if (!line.equals("")){
                    try {
                        String[] params = line.split(",");
                        String stringKey = params[0];
                        String name = params[1];
                        double x = Double.parseDouble(params[2]);
                        long y = Long.parseLong(params[3]);
                        Coordinates coordinates = new Coordinates(x, y);
                        double health = Double.parseDouble(params[4]);
                        AstartesCategory category = AstartesCategory.valueOf(params[5]);
                        Weapon weaponType = Weapon.valueOf(params[6]);
                        MeleeWeapon melleWeapon = MeleeWeapon.valueOf(params[7]);
                        String chapterName = params[8];
                        String chapterParentLegion = params[9];
                        int chapterMarinesCount = Integer.parseInt(params[10]);
                        Chapter chapter = new Chapter(chapterName, chapterParentLegion, chapterMarinesCount);
                        long id = Console.createID();
                        java.time.ZonedDateTime creationDate = ZonedDateTime.now();
                        SpaceMarine spaceMarine = new SpaceMarine(id, name, coordinates, creationDate, health, category, weaponType, melleWeapon, chapter);
                        collection.insert(stringKey, spaceMarine);
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Incorrect input. Collection is empty");
                        break;
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println("Collection has been downloading");
                break;
            }
        }
        return collection;
    }

    public static boolean executeFile(String nameFile, SpaceMarineCollection collection) throws Exception{
        boolean executeExit = false;
        FileReader fr = new FileReader(nameFile);
        Scanner file = new Scanner(fr);
        String command;
        String line;
        String[] commands;
        while (!executeExit) {
            try {
                line = file.nextLine().trim();
                commands = line.split(" ");
                command = commands[0];
            } catch (NoSuchElementException e) {
                System.out.println("File has been executing");
                break;
            }
            try {
                switch (command) {
                    case "help":
                        System.out.println("All commands : " + Commands.show());
                        break;
                    case "info":
                        collection.info();
                        break;
                    case "show":
                        collection.show();
                        break;
                    case "insert":
                        collection.insert(commands[1], Console.getElementFromFile(commands[2]));
                        break;
                    case "update":
                        collection.update(commands[1], Console.getElementFromFile(commands[2]));
                        break;
                    case "remove":
                        collection.remove(commands[1]);
                        break;
                    case "clear":
                        collection.clear();
                        break;
                    case "save":
                        collection.writeToFIle(commands[1]);
                        break;
                    case "execute_script":
                        String nextExecuteFile = commands[1];
                        if (nameFile.equals(nextExecuteFile)) throw new FileCycleException();
                        executeFile(nextExecuteFile, collection);
                        executeExit = Console.executeFile(commands[1], collection);
                        break;
                    case "exit":
                        executeExit = true;
                        System.out.println("You closed this program");
                        break;
                    case "replace_if_lowe null":
                        collection.replace_if_lowe(commands[1], getElementFromFile(commands[2]));
                        break;
                    case "remove_greater_key":
                        collection.remove_greater_key(commands[1]);
                        break;
                    case "remove_lower_key":
                        collection.remove_lower_key(commands[1]);
                        break;
                    case "remove_any_by_health":
                        collection.remove_any_by_health(commands[1]);
                        break;
                    case "group_counting_by_health":
                        collection.group_counting_by_health();
                        break;
                    case "count_less_than_health":
                        collection.count_less_than_health(commands[1]);
                        break;
                    default:
                        System.out.println("There is no command: " + command + "\nUse 'help' to see all commands");
                        break;
                }
            }catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect input. Try again.");
            }
        }
        fr.close();
        return executeExit;
    }

    public static String inputFile(Scanner scanner){
        String file;
        while (true) {
            System.out.println("Enter a name of file: ");
                file = scanner.nextLine().trim();
                break;
        }
        return file;
    }

    public static SpaceMarine getElementFromFile(String element) {
        try {
            String[] params = element.split(",");
            String name = params[0];
            double x = Double.parseDouble(params[1]);
            long y = Long.parseLong(params[2]);
            Coordinates coordinates = new Coordinates(x, y);
            double health = Double.parseDouble(params[3]);
            AstartesCategory category = AstartesCategory.valueOf(params[4]);
            Weapon weaponType = Weapon.valueOf(params[5]);
            MeleeWeapon melleWeapon = MeleeWeapon.valueOf(params[6]);
            String chapterName = params[7];
            String chapterParentLegion = params[8];
            int chapterMarinesCount = Integer.parseInt(params[9]);
            Chapter chapter = new Chapter(chapterName, chapterParentLegion, chapterMarinesCount);
            long id = Console.createID();
            java.time.ZonedDateTime creationDate = ZonedDateTime.now();
            SpaceMarine spaceMarine = new SpaceMarine(id, name, coordinates, creationDate, health, category, weaponType, melleWeapon, chapter);
            return spaceMarine;
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("incorrect element");
            return null;
        }
    }

    /**
     *
     * @return element SpaceMarine
     */
    public static SpaceMarine getElement(Scanner scanner) {
        System.out.println("New element");

        // initialisation a String name of element
        String name;
        while (true) {
            System.out.println("Enter the name:");
            name = scanner.nextLine();
            if (!name.equals("")) {
                break;
            }
            System.out.println("You failed. Try again!");
        }
        name = '"' + name + '"';


         //initialisation a Coordinates coordinates of element
        Double x = null;
        while (x == null) {
            System.out.println("Enter X coordinate (X is double and less than 131): ");
            try {
                x = Double.parseDouble(scanner.nextLine());
                if (x > Coordinates.MaxX){
                    x = null;
                }else break;
            }catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }
        long y;
        while (true) {
            System.out.println("Enter Y coordinate (Y is long and more than -448): ");
            try {
                y = Long.parseLong(scanner.nextLine());
                if (y > Coordinates.MinY){
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input! Try again.");
            }
        }
        Coordinates coordinates = new Coordinates(x, y);

        // initialisation a Double health of element
        Double health = 0.0;
        while (true) {
            System.out.println("Enter health (Health is double and more than 0): ");
            try {
                health = Double.parseDouble(scanner.nextLine());
                if (health > 0) {
                    break;
                } else{
                    System.out.println("Health is more than 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input. Health = null");
                break;
            }
        }

        //initialisation AstartesCategory category of element
        System.out.println("Choose type of AstartesCategory: " + AstartesCategory.show());
        String tryCategory = scanner.nextLine();
        AstartesCategory category = null;
        while (category == null) {
            try {
                category = AstartesCategory.valueOf(tryCategory);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such category : " + tryCategory + "\n Try again \n" + AstartesCategory.show());
                tryCategory = scanner.nextLine();
            }
        }

        //initialisation Weapon weaponType of element
        System.out.println("Choose type of Weapon: " + Weapon.show());
        String tryWeaponType = scanner.nextLine();
        Weapon weaponType = null;
        while (weaponType == null) {
            try {
                weaponType = Weapon.valueOf(tryWeaponType);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such category : " + tryWeaponType + "\n Try again\n" + Weapon.show());
                tryWeaponType = scanner.nextLine();
            }
        }

        // initialisation MeleeWeapon meleeWeapon of element
        System.out.println("Choose type of MeleeWeapon: " + MeleeWeapon.show());
        String tryMeleeWeapon = scanner.nextLine();
        MeleeWeapon meleeWeapon = null;
        while (meleeWeapon == null) {
            try {
                meleeWeapon = MeleeWeapon.valueOf(tryMeleeWeapon);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such category : " + tryMeleeWeapon + "\n Try again\n" + MeleeWeapon.show());
                tryMeleeWeapon = scanner.nextLine();
            }
        }

        //initialisation a Chapter of element
        System.out.println("Chapter part");
        String chapterName;
        while (true) {
            System.out.println("Enter a name of Chapter: ");
            chapterName = scanner.nextLine();
            if (!chapterName.equals("")) {
                break;
            }
            System.out.println("You failed. Try again!");
        }
        chapterName = '"' + chapterName + '"';
        String parentLegion;
        while (true) {
            System.out.println("Enter a parent legion of Chapter: ");
            parentLegion = scanner.nextLine();
            if (!parentLegion.equals("")) {
                break;
            }
            System.out.println("You failed. Try again!");
        }
        parentLegion = '"' + parentLegion + '"';
        int marinesCount;
        while (true) {
            try {
                System.out.println("Enter a marines count of Chapter (int marinesCount MaxCount = 1000): ");
                marinesCount = Integer.parseInt(scanner.nextLine());
                if ((marinesCount > Chapter.MinMarinesCount) && (marinesCount < Chapter.MaxMarinesCount)) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input. Try again");
            }
        }
        Chapter chapter = new Chapter(chapterName, parentLegion, marinesCount);

        //initialisation ID of element
        long id = Console.createID();

        // initialisation java.time.ZonedDateTime creationDate of element
        java.time.ZonedDateTime creationDate = ZonedDateTime.now();

        // java.time.ZonedDateTime of element
        SpaceMarine spaceMarine = new SpaceMarine(id, name, coordinates, creationDate, health, category, weaponType, meleeWeapon, chapter);
        return spaceMarine;
    }
}
