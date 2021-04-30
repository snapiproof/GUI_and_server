package server;

import database.CommandForServer;
import database.MessageForClient;
import database.SpaceMarineCollection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.concurrent.Exchanger;
import java.util.concurrent.RecursiveTask;


public class ServerWriting extends RecursiveTask<String> {
    private Exchanger<MessageForClient> exchangerMessage;
    private MessageForClient message;
    private InetAddress address;
    private int port;
    private DatagramSocket socket;

    public ServerWriting(DatagramSocket socket, Exchanger<MessageForClient> exchangerMessage){
        this.socket = socket;
        this.exchangerMessage = exchangerMessage;
    }

    @Override
    public String compute(){
        while(true) {
            try {
                MessageForClient message = exchangerMessage.exchange(null);
                send(message);
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
        address = message.getAddress();
        port = message.getPort();
        byte[] sendBuffer = serialize(new MessageForClient(message.getMessage()));
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(sendPacket);
    }
}
