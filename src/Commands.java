public enum Commands {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    INSERT("insert key"),
    UPDATE("update id"),
    REMOVE("remove key"),
    CLEAR("clear"),
    SAVE("save file"),
    EXECUTE("execute_script file"),
    EXIT("exit"),
    replace_if_lowe("replace_if_lowe key"),
    remove_greater_key("remove_greater_key key"),
    remove_lower_key("remove_lower_key key"),
    remove_any_by_health("remove_any_by_health health"),
    group_counting_by_health("group_counting_by_health"),
    count_less_than_health("count_less_than_health health");

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

