import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        String text = ""; // строка для записи
        try(FileOutputStream out=new FileOutputStream("note11s.txt");
            BufferedOutputStream bos = new BufferedOutputStream(out))
        {
            // перевод строки в байты
            byte[] buffer = text.getBytes();
            bos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}