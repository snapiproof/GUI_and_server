package database;

import server.User;

import java.io.Serializable;
import java.util.Objects;

public class SpaceMarine implements Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Double health; //Поле может быть null, Значение поля должно быть больше 0
    private final AstartesCategory category; //Поле может быть null
    private final Weapon weaponType; //Поле не может быть null
    private final MeleeWeapon meleeWeapon; //Поле может быть null
    private final Chapter chapter; //Поле не может быть null
    public static final Double MinHealth = 0.0;
    private long y;
    private double xfs;
    private String chapterName;
    private String chapterLegion;
    private int chapterCount;
    private long key;
    private String owner;

    public void setId(long id) {
        this.id = id;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public long getKey() {
        return key;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Chapter getChapter() {
        return chapter;
    }


    public SpaceMarine(String name, Coordinates coordinates, String creationDate, Double health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.name = name;
        this.category = category;
        this.chapter = chapter;
        this.creationDate = creationDate;
        this.meleeWeapon = meleeWeapon;
        this.coordinates = coordinates;
        this.health = health;
        this.weaponType = weaponType;
        this.xfs = coordinates.getX();
        this.y = coordinates.getY();
        this.chapterCount = chapter.getMarinesCount();
        this.chapterLegion = chapter.getParentLegion();
        this.chapterName = chapter.getName();
    }

    public void setXfs(double xfs) {
        this.xfs = xfs;
    }

    public long getY() {
        return y;
    }

    public double getXfs() {
        return xfs;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public String getChapterLegion() {
        return chapterLegion;
    }


    public String getChapterName() {
        return chapterName;
    }

    public Double getMinHealth() {
        return MinHealth;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "" + name +
                "," + xfs +
                "," + y +
                "," + health +
                "," + category +
                "," + weaponType +
                "," + meleeWeapon +
                "," + chapterName +
                "," + chapterLegion +
                "," + chapterCount +
                "," + id +
                "," + creationDate +
                "," + owner +
                "," + key
                ;
    }

    public Long getId() {
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