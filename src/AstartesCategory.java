public enum AstartesCategory {
    AGGRESSOR("AGGRESSOR"),
    ASSAULT("ASSAULT"),
    TACTICAL("TACTICAL"),
    CHAPLAIN("CHAPLAIN");
    private final String name;

    AstartesCategory(String name) {
        this.name = name;
    }

    public static String show(){
        StringBuilder s = new StringBuilder("");
        for (AstartesCategory category : AstartesCategory.values()) {
            s.append(category.getName()).append(" ");
        }
        return s.toString();
    }

    private String getName() {
        return name;
    }
}

