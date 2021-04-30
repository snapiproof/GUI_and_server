package commands;

import java.io.Serializable;
import java.net.InetAddress;

import database.*;
abstract public class AbstractCommand<T> implements Serializable {
    private SpaceMarineCollection collection;
    private T parameter;
    private InetAddress address;
    private int port;


    public SpaceMarineCollection getCollection() {
        return collection;
    }

    public T getParameter() {
        return parameter;
    }

    public AbstractCommand(SpaceMarineCollection collection, InetAddress address, int port) {
        this.collection = collection;
        this.address = address;
        this.port = port;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }
    public abstract String execute();
}

