public enum Commands {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    INSERT("insert"),
    UPDATE("update"),
    REMOVE("remove"),
    CLEAR("clear"),
    SAVE("save"),
    EXIT("exit");

    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public static String show(){
        StringBuilder s = new StringBuilder("");
        for (Commands commands : Commands.values()) {
            s.append(commands.getName()).append(", ");
        }
        return s.deleteCharAt(s.length() - 2).toString();
    }

    private String getName() {
        return name;
    }
}

