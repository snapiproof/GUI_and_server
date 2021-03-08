public class SpaceMarine {
//    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double health; //Поле может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле может быть null
    private Weapon weaponType; //Поле не может быть null
    private MeleeWeapon meleeWeapon; //Поле может быть null
    private Chapter chapter; //Поле не может быть null

    public SpaceMarine(String name, Coordinates coordinates, java.time.ZonedDateTime creationDate, Double health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
//        this.id = id;
        this.name = name;
        this.category = category;
        this.chapter = chapter;
        this.creationDate = creationDate;
        this.meleeWeapon = meleeWeapon;
        this.coordinates = coordinates;
        this.health = health;
        this.weaponType = weaponType;
    }
}