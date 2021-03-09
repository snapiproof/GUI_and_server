public class SpaceMarine {
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Double health; //Поле может быть null, Значение поля должно быть больше 0
    private final AstartesCategory category; //Поле может быть null
    private final Weapon weaponType; //Поле не может быть null
    private final MeleeWeapon meleeWeapon; //Поле может быть null
    private final Chapter chapter; //Поле не может быть null

    public SpaceMarine(String name, Coordinates coordinates, java.time.ZonedDateTime creationDate, Double health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.name = name;
        this.category = category;
        this.chapter = chapter;
        this.creationDate = creationDate;
        this.meleeWeapon = meleeWeapon;
        this.coordinates = coordinates;
        this.health = health;
        this.weaponType = weaponType;
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", health=" + health +
                ", category=" + category +
                ", weaponType=" + weaponType +
                ", meleeWeapon=" + meleeWeapon +
                ", chapter=" + chapter.toString() +
                '}';
    }
}