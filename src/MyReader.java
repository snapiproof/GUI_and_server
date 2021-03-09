

import java.time.ZonedDateTime;
import java.util.Scanner;

public class MyReader {



    //
    // create element
    //
    public static SpaceMarine getElementFromConsole(Scanner scanner) {
        String name;
        while (true) {
            System.out.println("Enter the name:");
            name = scanner.nextLine();
            if (!name.equals("")) {
                break;
            }
        }

        System.out.println("Enter X coordinate (X is double and less than 131): ");
        Double x = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter Y coordinate (Y is long and more than -448): ");
        long y = Long.parseLong(scanner.nextLine());
        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Enter health (Health is double and >0): ");
        Double health = Double.parseDouble(scanner.nextLine());

        System.out.println("Choose type of AstartesCategory: " + AstartesCategory.show());
        String tryCategory = scanner.nextLine();
        AstartesCategory category = null;
        while (category == null) {
            try {
                category = AstartesCategory.valueOf(tryCategory);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such category : " + tryCategory + "\n Try again");
                tryCategory = scanner.nextLine();
            }
        }


        System.out.println("Choose type of Weapon: " + Weapon.show());
        String tryWeaponType = scanner.nextLine();
        Weapon weaponType = null;
        while (weaponType == null) {
            try {
                weaponType = Weapon.valueOf(tryWeaponType);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such category : " + tryWeaponType + "\n Try again");
                tryWeaponType = scanner.nextLine();
            }
        }

        System.out.println("Choose type of MeleeWeapon: " + MeleeWeapon.show());
        String tryMeleeWeapon = scanner.nextLine();
        MeleeWeapon meleeWeapon = null;
        while (tryMeleeWeapon == null) {
            try {
                meleeWeapon = MeleeWeapon.valueOf(tryMeleeWeapon);
            } catch (IllegalArgumentException e) {
                System.out.println("There is no such category : " + tryMeleeWeapon + "\n Try again");
                tryMeleeWeapon = scanner.nextLine();
            }
        }

        System.out.println("Chapter part");
        System.out.println("Enter a name of Chapter: ");
        String chapterName = scanner.nextLine();
        System.out.println("Enter a parent legion of Chapter: ");
        String parentLegion = scanner.nextLine();
        System.out.println("Enter a marines count of Chapter (int marinesCount MaxCount = 1000): ");
        int marinesCount = Integer.parseInt(scanner.nextLine());
        Chapter chapter = new Chapter(chapterName, parentLegion, marinesCount);

        java.time.ZonedDateTime creationDate = ZonedDateTime.now();

        SpaceMarine spaceMarine = new SpaceMarine(name, coordinates, creationDate, health, category, weaponType, meleeWeapon, chapter);
        return spaceMarine;
    }
}
