import sun.awt.geom.AreaOp;
import java.util.regex.Pattern;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        String line;
        String[] command;
        SpaceMarineCollection collection = new SpaceMarineCollection();

//        while (true) {
//            try {
//                scanner.hasNextLine();
//                break;
//            } catch (NoSuchElementException e) {
//                System.out.println("A command might contains letters");
//            }
//        }


        while (!exit) {
            System.out.println("Enter a command: ");
            line = scanner.nextLine().trim();
            command = line.split(" ");

            switch (command[0]) {
                case "help":
                    System.out.println("пока только help и exit");
                    break;
                case "insert":
                    System.out.println("Please enter an element");
                    SpaceMarine spaceMarine = MyReader.getElementFromConsole(scanner);
                    long id = 2;
                    collection.add(id, spaceMarine);
                    break;
                case "info":
                    collection.info();
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("There is no command " + command[0] + "\n");
                    break;
            }
        }
    }
}

