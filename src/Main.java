
import java.util.regex.Pattern;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        String line;
        String[] command;
        SpaceMarineCollection collection = new SpaceMarineCollection();



        while (!exit) {
            System.out.println("Enter a command: ");
            line = null;
            while (line == null) {
                try {
                    line = scanner.nextLine().trim();
                    break;
                } catch (NoSuchElementException e) {
                    line = null;
                    System.out.println("Command should contains letters!");
                }
            }
            command = line.split(" ");

            switch (command[0]) {
                case "help":
                    System.out.println("");
                    break;
                case "insert":
                    System.out.println("Please enter an element");
                    SpaceMarine spaceMarine = MyReader.getElementFromConsole(scanner);
                        System.out.println("Enter id (long): ");
                        long id = Long.parseLong(scanner.nextLine().trim());
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

