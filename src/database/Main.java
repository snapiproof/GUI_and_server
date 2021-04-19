package database;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        String[] commands;
        SpaceMarineCollection collection = new SpaceMarineCollection();

        try {
            collection = Console.startCollection(System.getenv("START_FILE"), collection);
        }catch(FileNotFoundException e){
            System.out.println("File not found. Collection is empty.");
        }

        while (!exit) {

             //check correct input
            System.out.println("Enter a command: ");
            try {
                commands = scanner.nextLine().trim().split(" ");
            } catch (NoSuchElementException e) {
                System.out.println("You crashed scanner :( \n Bye-bye \n See you latter");
                break;
            }

            //commands:
            try {
                switch (commands[0]) {
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
//                        exit = Console.executeFile(commands[1], collection);
                        break;
                    case "exit":
                        exit = true;
                        System.out.println("You closed this program");
                        break;
                    case "replace_if_lowe":
                        collection.replace_if_lowe(commands[1], Console.getElement(scanner));
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
                        System.out.println("There is no command: " + commands[0] + "\nUse 'help' to see all commands");
                        break;
                }
            }catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect input. Try again.");
            }
        }
    }
}

