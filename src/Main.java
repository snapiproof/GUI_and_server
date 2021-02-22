import sun.awt.geom.AreaOp;
import java.util.regex.Pattern;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        String line;
        String[] command;

        while (true) {

            try {
                line = sc.nextLine();
                break;
            } catch (NoSuchElementException e) {
                System.out.println("A command might contains letters");
                sc.ioException();
            }

        }


        while (!exit) {
            System.out.println("Enter a command: ");
            line = sc.nextLine();
            command = line.split(" ");

            switch (command[0]) {
                case "help":
                    System.out.println("пока только help и exit");
                    break;
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

