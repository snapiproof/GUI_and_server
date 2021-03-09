import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        String command;
        String line;
        String[] commands;
        SpaceMarineCollection collection = new SpaceMarineCollection();
        long key;

        while (!exit) {
            System.out.println("Enter a command: ");
            System.out.println("All commands : " + Commands.show());
            try {
                line = scanner.nextLine().trim();
                commands = line.split(" ");
                command = commands[0];
            } catch (NoSuchElementException e) {
                System.out.println("You crashed scanner :( \n Bye-bye \n See you latter");
                break;
            }
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
                    collection.insert(Console.inputKey(scanner), Console.getElement(scanner));
                    break;
                case "update":
                    collection.update(Console.inputKey(scanner), Console.getElement(scanner));
                    break;
                case "remove":
                    collection.remove(Console.inputKey(scanner));
                    break;
                case "clear":
                    collection.clear();
                    break;
                case "save":
                    System.out.println("It's not done yet");
                    break;
                case "exit":
                    exit = true;
                    System.out.println("You closed this program");
                    break;
                default:
                    System.out.println("There is no command: " + command + "\nUse 'help' to see all commands");
                    break;
            }
        }
    }
}

