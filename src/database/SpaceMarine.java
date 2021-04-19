package database;

import java.io.Serializable;
import java.util.Objects;

public class SpaceMarine implements Serializable {
    private final long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Double health; //Поле может быть null, Значение поля должно быть больше 0
    private final AstartesCategory category; //Поле может быть null
    private final Weapon weaponType; //Поле не может быть null
    private final MeleeWeapon meleeWeapon; //Поле может быть null
    private final Chapter chapter; //Поле не может быть null
    public static final Double MinHealth = 0.0;

    public SpaceMarine(long id, String name, Coordinates coordinates, java.time.ZonedDateTime creationDate, Double health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.chapter = chapter;
        this.creationDate = creationDate;
        this.meleeWeapon = meleeWeapon;
        this.coordinates = coordinates;
        this.health = health;
        this.weaponType = weaponType;
    }

    public Double getMinHealth() {
        return MinHealth;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                ", ID='" + id + '\'' +
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

    public long getId() {
        return id;
    }

    public int getMarinesCount() {
        return chapter.getMarinesCount();
    }

    public String toCsv(){
        return name +
                "," + coordinates.toCsv() +
                "," + health +
                "," + category +
                "," + weaponType +
                "," + meleeWeapon +
                "," + chapter.toCsv();
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public Double getHealth() {
        return health;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine that = (SpaceMarine) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(health, that.health) && category == that.category && weaponType == that.weaponType && meleeWeapon == that.meleeWeapon && Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, health, category, weaponType, meleeWeapon, chapter);
    }
}