import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class SpaceMarineCollection {

    private final Map<Long, SpaceMarine> linkedHashMap = new LinkedHashMap<>();

    public void info() {
        System.out.println("Type of collection is " + linkedHashMap.getClass().toString() + ". Size of collection is " + linkedHashMap.size());
    }

    public void insert(Long key, SpaceMarine spaceMarine) {
        linkedHashMap.put(key, spaceMarine);
    }

    public void remove(Long key){
        linkedHashMap.remove(key);
    }

    public void update(Long key, SpaceMarine spaceMarine){
        linkedHashMap.replace(key, spaceMarine);
    }

    public void clear(){
        linkedHashMap.clear();
    }

    public void show(){
        System.out.println("LinkedHashMap initial content:");
        Set set = linkedHashMap.entrySet();

        for (Object element : set) {
            Map.Entry mapEntry = (Map.Entry) element;
            System.out.println("ID: " + mapEntry.getKey() + ", " + mapEntry.getValue().toString());
        }
    }

    public void writeToFile(){
        //processing...
    }

}
