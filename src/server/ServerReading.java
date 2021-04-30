package server;

import commands.AbstractCommand;
import commands.CommandHelp;
import commands.Switcher;
import database.CommandForServer;
import database.MessageForClient;
import database.SpaceMarineCollection;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Exchanger;

public class ServerReading implements Runnable {
    private DatagramSocket socket;
    private SpaceMarineCollection collection;
    private Exchanger<CommandForServer> exchangerCommand;
    private InetAddress address;
    private int port;

    public ServerReading(DatagramSocket socket, SpaceMarineCollection collection, Exchanger<CommandForServer> exchangerCommand) {
        this.collection = collection;
        this.exchangerCommand = exchangerCommand;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            Switcher switcher = new Switcher();
            MessageForClient message = new MessageForClient("");
            User user;
            ByteBuffer buffer = ByteBuffer.allocate(65536);


            try {
                CommandForServer command = getCommand();
                exchangerCommand.exchange(command);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private CommandForServer deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        CommandForServer command = (CommandForServer) objectInputStream.readObject();
        command.setAddress(getPacket.getAddress());
        command.setPort(getPacket.getPort());
        byteArrayInputStream.close();
        objectInputStream.close();
        return command;
    }

    private CommandForServer getCommand() throws IOException, ClassNotFoundException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        socket.receive(getPacket);
        address = getPacket.getAddress();
        port = getPacket.getPort();
        return deserialize(getPacket, getBuffer);
    }
}