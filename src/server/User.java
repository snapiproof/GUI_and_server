package server;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    private String username;
    private String password;
    private InetAddress address;
    private int port;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}