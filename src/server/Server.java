package server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import database.*;
import database.Console;

public class Server {

    public final static int SERVICE_PORT=5000;
    private DatagramSocket socket;
    private InetAddress address;
    byte[] receivingDataBuffer = new byte[1024];
    byte[] sendingDataBuffer = new byte[1024];
    private int port;
    private SpaceMarineCollection collection;

    public Server(SpaceMarineCollection collection){
        this.collection = collection;
    }

    public void run(){
        try {

                try {
                    collection = Console.startCollection("collection.csv", collection);
                    System.out.println("Collection was uploaded");
                }catch(FileNotFoundException e){
                    System.out.println("File not found. Collection is empty.");
                }
                boolean exit = false;
                ServerConsole console = new ServerConsole(collection, exit);
                console.start();
                socket = new DatagramSocket(SERVICE_PORT);
                System.out.println("Server was open");

                while (!exit){
                    CommandForServer command = getCommand();
                    switch (command.getCommandName().split(" ")[0]){
                        case "askID":
                            send(new MessageForClient(String.valueOf(Console.getID())));
                            break;
                        case "help":
                            send(new MessageForClient("All commands : " + Commands.show()));
                            break;
                        case "info":
                            send(new MessageForClient(collection.info()));
                            break;
                        case "show":
                            send(new MessageForClient(collection.show()));
                            break;
                        case "insert":
                            send(new MessageForClient(collection.insert(command.getCommandName().split(" ")[1], (SpaceMarine)command.getArgument())));
                            break;
                        case "update":
                            send(new MessageForClient(collection.update(command.getCommandName().split(" ")[1], (SpaceMarine) command.getArgument())));
                            break;
                        case "remove":
                            send(new MessageForClient(collection.remove(command.getCommandName().split(" ")[1])));
                            break;
                        case "clear":
                            send(new MessageForClient(collection.clear()));
                            break;
                        case "execute_script":
                            send(new MessageForClient(Console.executeFile(command.getCommandName().split(" ")[1], collection)));
                            break;
                        case "replace_if_lowe":
                            send(new MessageForClient(collection.replace_if_lowe(command.getCommandName().split(" ")[1], (SpaceMarine) command.getArgument())));
                            break;
                        case "remove_greater_key":
                            send(new MessageForClient(collection.remove_greater_key(command.getCommandName().split(" ")[1])));
                            break;
                        case "remove_lower_key":
                            send(new MessageForClient(collection.remove_lower_key(command.getCommandName().split(" ")[1])));
                            break;
                        case "remove_any_by_health":
                            send(new MessageForClient(collection.remove_any_by_health(command.getCommandName().split(" ")[1])));
                            break;
                        case "group_counting_by_health":
                            send(new MessageForClient(collection.group_counting_by_health()));
                            break;
                        case "count_less_than_health":
                            send(new MessageForClient(collection.count_less_than_health(command.getCommandName().split(" ")[1])));
                            break;
                    }
                }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private CommandForServer getCommand() throws IOException, ClassNotFoundException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        socket.receive(getPacket);
        address = getPacket.getAddress();
        port = getPacket.getPort();
        return deserialize(getPacket, getBuffer);
    }

    private void send(MessageForClient message) throws IOException {
        byte[] sendBuffer = serialize(message);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(sendPacket);
    }

    private CommandForServer deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        CommandForServer command = (CommandForServer) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return command;
    }

    private byte[] serialize(MessageForClient message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    public static void main(String[] args) {
        SpaceMarineCollection collection = new SpaceMarineCollection();
        Server server = new Server(collection);
        server.run();
    }
}