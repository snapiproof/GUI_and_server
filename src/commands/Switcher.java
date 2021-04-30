package commands;

public class Switcher {
    private  AbstractCommand command;
    public void setCommand(AbstractCommand command) {
        this.command = command;
    }
    public String doCommand(){
        return command.execute();
    }
}