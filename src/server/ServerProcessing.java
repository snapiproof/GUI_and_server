package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Exchanger;
import database.*;
import database.MessageForClient;

public class ServerProcessing implements Runnable {
    private DatagramSocket socket;

    private SpaceMarineCollection collection;
    private Exchanger<CommandForServer> exchangerCommand;
    private Exchanger<MessageForClient> exchangerMessage;
    private InetAddress address;
    private int port;
    private MessageForClient message;
    private DataBase dataBase;

    public ServerProcessing(DatagramSocket socket, SpaceMarineCollection collection, Exchanger<CommandForServer> exchangerCommand, Exchanger<MessageForClient> exchangerMessage, DataBase dataBase) {
        this.socket = socket;
        this.collection = collection;
        this.exchangerCommand = exchangerCommand;
        this.exchangerMessage = exchangerMessage;
        this.dataBase = dataBase;
    }

    @Override
    public void run() {
        while(true){
            try {
                CommandForServer command = exchangerCommand.exchange(null);
                address = command.getAddress();
                port = command.getPort();
                switch (command.getCommandName().split(" ")[0]) {
                    case "help":
                        message = new MessageForClient("All commands : " + Commands.show(), address, port);
                        break;
                    case "info":
                        message = new MessageForClient(collection.info(), address, port);
                        break;
                    case "show":
                        message = new MessageForClient(collection.show(), address, port);
                        break;
                    case "insert":
                        message = new MessageForClient(collection.insert(command.getCommandName().split(" ")[1], (SpaceMarine)command.getArgument()), address, port);
                        break;
                    case "update":
                        message = new MessageForClient(collection.update(command.getCommandName().split(" ")[1], (SpaceMarine) command.getArgument()), address, port);
                        break;
                    case "remove":
                        message = new MessageForClient(collection.remove(command.getCommandName().split(" ")[1]), address, port);
                        break;
                    case "clear":
                        message = new MessageForClient(collection.clear());
                        break;
                    case "execute_script":
                        message = new MessageForClient(Console.executeFile(command.getCommandName().split(" ")[1], collection), address, port);
                        break;
                    case "replace_if_lowe":
                        message = new MessageForClient(collection.replace_if_lowe(command.getCommandName().split(" ")[1], (SpaceMarine) command.getArgument()), address, port);
                        break;
                    case "remove_greater_key":
                        message = new MessageForClient(collection.remove_greater_key(command.getCommandName().split(" ")[1]), address, port);
                        break;
                    case "remove_lower_key":
                        message = new MessageForClient(collection.remove_lower_key(command.getCommandName().split(" ")[1]), address, port);
                        break;
                    case "remove_any_by_health":
                        message = new MessageForClient(collection.remove_any_by_health(command.getCommandName().split(" ")[1]), address, port);
                        break;
                    case "group_counting_by_health":
                        message = new MessageForClient(collection.group_counting_by_health());
                        break;
                    case "count_less_than_health":
                        message = new MessageForClient(collection.count_less_than_health(command.getCommandName().split(" ")[1]), address, port);
                        break;
                }
                exchangerMessage.exchange(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    private void send(MessageForClient message) throws IOException {
        byte[] sendBuffer = serialize(message);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(sendPacket);
    }
}
