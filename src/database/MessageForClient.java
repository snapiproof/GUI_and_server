package database;

import java.io.Serializable;

public class MessageForClient implements Serializable {
    private String message;

    public MessageForClient(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
