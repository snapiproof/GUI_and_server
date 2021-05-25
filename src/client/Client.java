package client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Scanner;
import java.util.Set;
import database.*;
import database.Console;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ServerConsole;
import server.User;

public class Client {
    private String textField;
    private String host;
    private int port;
    private DatagramChannel clientChannel;
    private SocketAddress address;
    private Selector selector;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);
    private static Scanner scanner = new Scanner(System.in, "UTF-8");

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            clientChannel = DatagramChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            clientChannel.connect(address);
            clientChannel.configureBlocking(false);
            selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_WRITE);
        }catch (Exception e){

        }
    }
    public boolean checkLogin(String login, String password){
        try {
            User user = new User(login, password);
            send(new CommandForServer<User>("add_user", user));
            String check = receive().getMessage();
            System.out.println(check);
            if ((check.equals("User signed in successfully")) || (check.equals("User was signed"))) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", clientChannel=" + clientChannel +
                ", address=" + address +
                ", selector=" + selector +
                ", byteBuffer=" + byteBuffer +
                '}';
    }

    public static SpaceMarineCollection AskCollection(String host, int port){
        Client client = new Client(host, port);
        return client.askCollection();
    }

    public SpaceMarineCollection askCollection(){
        SpaceMarineCollection spaceMarineCollection = new SpaceMarineCollection();
        try {
            send(new CommandForServer<>("show", ""));
            String all = receive().getMessage();
            String[] q = all.split("\n");
            int i = q.length;
            int f;
            for (f = 1; f<i; f++) {
                String collection = all.split("\n")[f];
                String key = collection.split(" ")[1];
                String element = collection.split(" ")[3];
                SpaceMarine e = Console.getElementFromLine(element);
                e.setKey(Long.parseLong(key));
                spaceMarineCollection.insert(key, e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return spaceMarineCollection;

    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 5001);
        client.run();
//        System.out.println(client.askCollection().show());

    }


    public void run() {
        try {

            String login;
            String password;
            User user;
            System.out.println("You need log in");
            while(true) {
                System.out.println("Enter login");
                login = scanner.nextLine();
                System.out.println("Enter password");
                password = scanner.nextLine();
                user = new User(login, password);
                send(new CommandForServer<User>("add_user ", user));
                String check = receive().getMessage();
                System.out.println(check);
                if ((check.equals("User signed in successfully")) || (check.equals("User was signed"))) break;
                else {
                    System.out.println("Try again");
                }
            }

                boolean exit = false;
                    String[] commands;
                    while(!exit){
                        try {

                        System.out.println("Enter a command");
                        commands = scanner.nextLine().trim().split(" ");

                        switch(commands[0]) {
                            case "askCollection":
                                askCollection();
                                break;
                            case "help":
                                send(new CommandForServer<>(commands[0], ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "info":
                                send(new CommandForServer<>(commands[0], ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "show":
                                send(new CommandForServer<>(commands[0], ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "insert":
                                send(new CommandForServer<SpaceMarine>(commands[0] + " " + commands[1] + " " + login, Console.getElement(scanner)));
                                System.out.println(receive().getMessage());
                                break;
                            case "update":
                                send(new CommandForServer<SpaceMarine>(commands[0] + " " + commands[1] + " " + login, Console.getElement(scanner)));
                                System.out.println(receive().getMessage());
                                break;
                            case "remove":
                                send(new CommandForServer<>(commands[0] + " " + commands[1] + " " + login, ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "clear":
                                send(new CommandForServer<>(commands[0] + " " + login, ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "execute_script":
                                send(new CommandForServer<>(commands[0] + " " + commands[1] + " " + login, ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "exit":
                                exit = true;
                                System.out.println("You disconnected from server");
                                break;
                            case "replace_if_lowe":
                                send(new CommandForServer<SpaceMarine>(commands[0] + " " + commands[1] + " " + login, Console.getElement(scanner)));
                                System.out.println(receive().getMessage());
                                break;
                            case "remove_greater_key":
                                send(new CommandForServer<>(commands[0] + " " + commands[1] + " " + login, ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "remove_lower_key":
                                send(new CommandForServer<>(commands[0] + " " + commands[1] + " " + login, ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "remove_any_by_health":
                                send(new CommandForServer<>(commands[0] + " " + commands[1] + " " + login, ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "group_counting_by_health":
                                send(new CommandForServer<>(commands[0] + " " + commands[1] + " " + login, ""));
                                System.out.println(receive().getMessage());
                                break;
                            case "count_less_than_health":
                                send(new CommandForServer<>(commands[0] + " " + commands[1], ""));
                                System.out.println(receive().getMessage());
                                break;
                            default:
                                System.out.println("There is no command: " + commands[0] + "\nUse 'help' to see all commands");
                                break;
                        }
                    }catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("Incorrect input. Try again.");
                    }
                }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public String replace_if_lowe(String key, SpaceMarine element, String login){
        try {
            send(new CommandForServer<SpaceMarine>("replace_if_lowe" + " " + key + " " + login, element));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }
    public String count_less_than_health(String health){
        try {
            send(new CommandForServer<>("count_less_than_health" + " " + health, ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }
    public String remove_any_by_health(String health, String login){
        try {
            send(new CommandForServer<>("remove_any_by_health" + " " + health + " " + login, ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }
    public String remove_lower_key(String key, String login){
        try {
            send(new CommandForServer<>("remove_lower_key" + " " + key + " " + login, ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }
    public String remove_greater_key(String key, String login){
        try {
            send(new CommandForServer<>("remove_greater_key" + " " + key + " " + login, ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }


    public String executeScript(String file, String login){
        try {
            send(new CommandForServer<>("execute_script" + " " + file + " " + login, ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }
    public String remove(String key, String login){
        try {
            send(new CommandForServer<>("remove" + " " + key + " " + login, ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }

    public String show(){
        try {
            send(new CommandForServer<>("show", ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }

    public String insert(String key, SpaceMarine spaceMarine, String login){
        try {
            send(new CommandForServer<SpaceMarine>("insert" + " " + key + " " + login, spaceMarine));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }

    public String update(String key, SpaceMarine spaceMarine, String login){
        try {
            send(new CommandForServer<SpaceMarine>("update" + " " + key + " " + login, spaceMarine));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }

    public String clear(String login){
        try {
            send(new CommandForServer<>("clear" + " " + login, ""));
            return receive().getMessage();
        }catch(Exception e){
            return "Exception";
        }
    }

    private void send(CommandForServer<?> command) throws IOException {
        byteBuffer.put(serialize(command));
        byteBuffer.flip();
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isWritable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.write(byteBuffer);
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }

    private MessageForClient receive() throws IOException, ClassNotFoundException {
        DatagramChannel channel = null;
        while (channel == null) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                if (key.isReadable()) {
                    channel = (DatagramChannel) key.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        }
        return deserialize();
    }

    private byte[] serialize(CommandForServer<?> command) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(command);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private MessageForClient deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        MessageForClient message = (MessageForClient) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return message;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public String getTextField() {
        return textField;
    }
}