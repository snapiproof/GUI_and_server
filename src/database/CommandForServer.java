package database;

import java.io.Serializable;

public class CommandForServer<T> implements Serializable {
    private final String commandName;
    private T argument;

    public CommandForServer(String commandName, T argument) {
        this.commandName = commandName;
        this.argument = argument;
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
