public class Coordinates {
    private final Double x; //Максимальное значение поля: 131, Поле не может быть null
    private final long y; //Значение поля должно быть больше -448
    public static final Double MaxX = 131.0;
    public static final long MinY = -448;

    public Coordinates (Double m, long n){
        x = m;
        y = n;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public String toCsv(){
        return  x +
                ", " + y;
    }
}