public enum MeleeWeapon {
    POWER_SWORD("POWER_SWORD"),
    CHAIN_AXE("CHAIN_AXE"),
    MANREAPER("MANREAPER"),
    LIGHTING_CLAW("LIGHTING_CLAW"),
    POWER_FIST("POWER_FIST");

    private final String name;

    private String getName() {
        return name;
    }

    MeleeWeapon(String name) {
        this.name = name;
    }
    public static String show(){
        StringBuilder s = new StringBuilder("");
        for (MeleeWeapon meleeWeapon : MeleeWeapon.values()) {
            s.append(meleeWeapon.getName()).append(" ");
        }
        return s.toString();
    }
}
