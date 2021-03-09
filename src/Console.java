import java.time.ZonedDateTime;
import java.util.Scanner;

public class Console {
    public static long inputKey(Scanner scanner){
        long key;
        while (true) {
            System.out.println("Enter key: ");
            try {
                key = Long.parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect enter! Try again.");
            }
        }
        return key;
    }

    /**
     * @param scanner
     * @return element SpaceMarine
     */
    public static SpaceMarine getElement(Scanner scanner) {
        System.out.println("New element");

        /**
         *
         * @param String name
         * */
        String name;
        while (true) {
            System.out.println("Enter the name:");
            name = scanner.nextLine();
            if (!name.equals("")) {
                break;
            }
            System.out.println("You failed. Try again!");
        }

        /**
         *
         * @param Coordinates coordinates
         * */
        Double x;
        while (true) {
            System.out.println("Enter X coordinate (X is double and less than 131): ");
            try {
                x = Double.parseDouble(scanner.nextLine());
                break;
            }catch (NumberFormatException e) {
                System.out.println("Incorrect enter! Try again.");
            }
        }
        long y;
        while (true) {
            System.out.println("Enter Y coordinate (Y is long and more than -448): ");
            try {
                y = Long.parseLong(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect enter! Try again.");
            }
        }
        Coordinates coordinates = new Coordinates(x, y);

        /**
         *
         * @param Double health
         * */
        Double health;
        while (true) {
            System.out.println("Enter health (Health is double and more than 0): ");
            try {
                health = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect enter! Try again.");
            }
        }

        /**
         *
         * @param AstartesCategory category
         * */
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

        /**
         *
         * @param Weapon weapon
         * */
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

        /**
         *
         * @param MeleeWeapon meleeWeapon
         * */
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


        /**
         *
         * @param Chapter chapter
         * */
        System.out.println("Chapter part");
        System.out.println("Enter a name of Chapter: ");
        String chapterName = scanner.nextLine();
        System.out.println("Enter a parent legion of Chapter: ");
        String parentLegion = scanner.nextLine();
        System.out.println("Enter a marines count of Chapter (int marinesCount MaxCount = 1000): ");
        int marinesCount = Integer.parseInt(scanner.nextLine());
        Chapter chapter = new Chapter(chapterName, parentLegion, marinesCount);

        /**
         *
         * @param creationDate
         * */
        java.time.ZonedDateTime creationDate = ZonedDateTime.now();

        SpaceMarine spaceMarine = new SpaceMarine(name, coordinates, creationDate, health, category, weaponType, meleeWeapon, chapter);
        return spaceMarine;
    }
}
