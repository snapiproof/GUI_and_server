public class Chapter {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private int marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000

    public Chapter(String chapterName, String legion, int x){
        name = chapterName;
        parentLegion = legion;
        marinesCount = x;
    }
}