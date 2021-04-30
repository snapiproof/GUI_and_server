package commands;

import database.SpaceMarineCollection;

import java.net.InetAddress;

public class CommandHelp extends  AbstractCommand {

    public CommandHelp(SpaceMarineCollection myCollection, InetAddress address, int port) {
        super(myCollection, address, port);
    }

    @Override
    public String execute() {
        return super.getCollection().help();
    }
}
