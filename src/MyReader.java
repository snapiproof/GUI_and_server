import sun.awt.geom.AreaOp;

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
        Double x = scanner.nextDouble();
        System.out.println("Enter Y coordinate (Y is long and more than -448): ");
        long y = scanner.nextLong();
        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Enter health (Health is double and >0): ");
        Double health = scanner.nextDouble();

        System.out.println("Choose type of vehicle: " + AstartesCategory.values());
        AstartesCategory category = scanner.nextLine();

        Weapon weaponType = scanner.nextLine();

        MeleeWeapon meleeWeapon = scanner.nextLine();

        Chapter chapter = scanner.nextLine();

        java.time.ZonedDateTime creationDate = ZonedDateTime.now();

        SpaceMarine spaceMarine = new SpaceMarine(name, coordinates, creationDate, health, category, weaponType, meleeWeapon, chapter);
        return spaceMarine;
    }
}
