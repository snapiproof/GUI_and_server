import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SpaceMarineCollection {

    private final Map<Long, SpaceMarine> linkedHashMap = new LinkedHashMap<>();

    public void info() {
        System.out.println("Type of collection is " + linkedHashMap.getClass().toString() + ". Size of collection is " + linkedHashMap.size());
    }

    public void insert(String stringKey, SpaceMarine spaceMarine) {
        long key;
        try {
            key = Long.parseLong(stringKey);
            if (!linkedHashMap.containsKey(key)) {
                linkedHashMap.put(key, spaceMarine);
                System.out.println("Element is inserted");
            } else System.out.println("Collection already has element with this ID");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect enter! Try again.");
        }
    }

    public void remove(String stringKey){
        long key;
        try {
            key = Long.parseLong(stringKey);
            linkedHashMap.remove(key);
            System.out.println("Element was removed");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect enter! Try again.");
        }
    }

    public void update(String stringKey, SpaceMarine spaceMarine){
        long key;
        try {
            key = Long.parseLong(stringKey);
            linkedHashMap.replace(key, spaceMarine);
            System.out.println("Element is updated");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect enter! Try again.");
        }
    }

    public void clear(){
        linkedHashMap.clear();
        System.out.println("Collection is empty");
    }

    public void show(){
        System.out.println("LinkedHashMap initial content:");
        Set set = linkedHashMap.entrySet();
        for (Object element: set) {
            Map.Entry mapEntry = (Map.Entry) element;
            System.out.println("ID: " + mapEntry.getKey() + ", " + mapEntry.getValue().toString());
        }
    }

    public void writeToFIle(String nameFile){

        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nameFile))) {
            Map<Long, SpaceMarine> map = linkedHashMap;
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String csv = "" + pair.getKey() + linkedHashMap.get(pair.getKey()).toCsv() + "";
                byte[] buffer = csv.getBytes();
                bos.write(buffer, 0, buffer.length);
                System.out.println("Collection is saved in " + nameFile);

            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
