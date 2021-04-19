package database;

public enum Weapon {
    PLASMA_GUN("PLASMA_GUN"),
    FLAMER("FLAMER"),
    GRAV_GUN("GRAV_GUN"),
    GRENADE_LAUNCHER("GRENADE_LAUNCHER"),
    MULTI_MELTA("MULTI_MELTA");

    private final String name;

    Weapon(String name) {
        this.name = name;
    }

    public static String show(){
        StringBuilder s = new StringBuilder("");
        for (Weapon weapon : Weapon.values()) {
            s.append(weapon.getName()).append(" ");
        }
        return s.toString();
    }

    private String getName() {
        return name;
    }
}
