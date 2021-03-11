import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if ((int)pair.getValue() < 500) it.remove();
        }


    }
}