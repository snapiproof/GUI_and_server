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
import server.ServerConsole;
import server.User;

public class Client {
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
    }

    public void run() {
        try {
            clientChannel = DatagramChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            clientChannel.connect(address);
            clientChannel.configureBlocking(false);
            selector = Selector.open();
            clientChannel.register(selector, SelectionKey.OP_WRITE);

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

    public static void main(String[] args) {
        Client client = new Client("localhost", 5000);
        client.run();
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

}