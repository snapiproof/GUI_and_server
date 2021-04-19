package database;

import java.io.Serializable;

public class Chapter implements Serializable {
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final String parentLegion;
    private final int marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    public static final int MaxMarinesCount = 1001;
    public static final int MinMarinesCount = 0;

    public Chapter(String chapterName, String legion, int x){
        name = chapterName;
        parentLegion = legion;
        marinesCount = x;
    }

    public int getMarinesCount() {
        return marinesCount;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", parentLegion='" + parentLegion + '\'' +
                ", marinesCount=" + marinesCount +
                '}';
    }
    public String toCsv(){
        return name +
                "," + parentLegion +
                "," + marinesCount;
    }
}