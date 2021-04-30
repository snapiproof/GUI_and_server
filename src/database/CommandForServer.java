package database;

import java.io.Serializable;
import java.net.InetAddress;

public class CommandForServer<T> implements Serializable {
    private final String commandName;
    private T argument;
    private InetAddress address;
    private int port;

    public CommandForServer(String commandName, T argument) {
        this.commandName = commandName;
        this.argument = argument;

    }
    public CommandForServer(String commandName, T argument, InetAddress address, int port) {
        this.commandName = commandName;
        this.argument = argument;
        this.port = port;
        this.address = address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getCommandName() {
        return commandName;
    }

    public T getArgument() {
        return argument;
    }

    public void setArgument(T argument) {
        this.argument = argument;
    }
}
