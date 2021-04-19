package server;
import java.util.Scanner;
import database.*;

public class ServerConsole extends Thread{
    private SpaceMarineCollection collection;
    private boolean exit;
    public ServerConsole(SpaceMarineCollection collection, boolean f){
        this.collection = collection;
        exit = f;
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            if (command.equals("save")) {
                collection.writeToFIle("collection.csv");
            }else if(command.equals("exit")){
                exit = true;
            } else {
                    System.out.println("This console can only save collection");
            }
        }
    }
}
