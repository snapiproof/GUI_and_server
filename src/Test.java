import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        System.out.println((scanner.equals(null)));
        System.out.println((scanner.equals(scanner)));

        scanner.close();
        System.out.println((scanner.equals(null)));
        System.out.println((scanner.equals(scanner)));






    }
}