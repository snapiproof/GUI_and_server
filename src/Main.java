import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        String command;
        String line;
        String[] commands;
        SpaceMarineCollection collection = new SpaceMarineCollection();
        collection = Console.startCollection(Console.inputFile(scanner), collection);

        while (!exit) {

            /**
             * check correct input
             * */
            System.out.println("Enter a command: ");
            try {
                line = scanner.nextLine().trim();
                commands = line.split(" ");
                command = commands[0];
            } catch (NoSuchElementException e) {
                System.out.println("You crashed scanner :( \n Bye-bye \n See you latter");
                break;
            }

            /**
             * commands:
                 * */
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
                        collection.insert(commands[1], Console.getElement(scanner));
                        break;
                    case "update":
                        collection.update(commands[1], Console.getElement(scanner));
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
                        exit = Console.executeFile(commands[1], collection);
                        break;
                    case "exit":
                        exit = true;
                        System.out.println("You closed this program");
                        break;
                    case "replace_if_lowe null":
                        System.out.println("It's not done yet");
                        break;
                    case "remove_greater_key":
                        System.out.println("It's not done yet");
                        break;
                    case "remove_lower_key":
                        System.out.println("It's not done yet");
                        break;
                    case "remove_any_by_health":
                        System.out.println("It's not done yet");
                        break;
                    case "group_counting_by_health":
                        System.out.println("It's not done yet");
                        break;
                    case "count_less_than_health":
                        System.out.println("It's not done yet");
                        break;
                    default:
                        System.out.println("There is no command: " + command + "\nUse 'help' to see all commands");
                        break;
                }
            }catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect input. Try again.");
            }
        }
    }
}

