import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
            System.out.println("Incorrect input! Try again.");
        }
    }

    public void remove(String stringKey){
        long key;
        try {
            key = Long.parseLong(stringKey);
            linkedHashMap.remove(key);
            System.out.println("Element was removed");
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input! Try again.");
        }
    }

    public void update(String stringID, SpaceMarine spaceMarine){
        long id;
        long key = 0;
        boolean check = false;
        try {
            id = Long.parseLong(stringID);
            Map<Long, SpaceMarine> map = linkedHashMap;
            for (Map.Entry<Long, SpaceMarine> entry : map.entrySet()) {
                if (Objects.equals(id, map.get(entry.getKey()).getId())) {
                    key = entry.getKey();
                    check = true;
                }
            }

            if (check) {
                linkedHashMap.replace(key, spaceMarine);
                System.out.println("Element is updated");
            }else {
                System.out.println("There is no element with ID: " + stringID);
            }
        }catch(NumberFormatException e){
            System.out.println("Incorrect input! Try again.");
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
            System.out.println(mapEntry.getValue().toString());
        }
    }

    public void remove_greater_key(String stringKey){
        long key;
        try {
            key = Long.parseLong(stringKey);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if ((Long)mapEntry.getKey() > key){
                    linkedHashMap.remove(key);
                }
            }
            System.out.println("Elements with key more than " + key + " were deleted.");
        }catch (NumberFormatException e){
            System.out.println("Incorrect input! Try again.");
        }
    }

    public void remove_lower_key(String stringKey){
        long key;
        try {
            key = Long.parseLong(stringKey);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if ((Long)mapEntry.getKey() < key){
                    linkedHashMap.remove(key);
                }
            }
            System.out.println("Elements with key more than " + key + " were deleted.");
        }catch (NumberFormatException e){
            System.out.println("Incorrect input! Try again.");
        }
    }

    public void remove_any_by_health(String stringHealth){
        Double health;
        boolean check = false;
        try {
            health = Double.parseDouble(stringHealth);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if ( linkedHashMap.get(mapEntry.getKey()).getHealth() == health) {
                    System.out.println("Elements with key " + mapEntry.getKey() + " was deleted.");
                    linkedHashMap.remove(mapEntry.getKey());
                    check = true;
                    break;
                }
            }
            if (!check) System.out.println("There is no element with health " + health);
        }catch (NumberFormatException e){
            System.out.println("Incorrect input! Try again.");
        }
    }

    public void replace_if_lowe(String stringKey, SpaceMarine spaceMarine){
        long key;
        try {
            key = Long.parseLong(stringKey);
            if (spaceMarine.getMarinesCount() < linkedHashMap.get(key).getMarinesCount()){
                linkedHashMap.replace(key, spaceMarine);
                System.out.println("Element was replaced");
            }else {
                System.out.println("Element wasn't replaced");
            }
        }catch (NumberFormatException e){
            System.out.println("Incorrect input! Try again.");
        }
    }

    public void group_counting_by_health(){
        Map<Double, Integer> map = new TreeMap<>();
        double health;
        Integer count;
        Set set = linkedHashMap.entrySet();
        for (Object element : set) {
            Map.Entry mapEntry = (Map.Entry) element;
            health = linkedHashMap.get(mapEntry.getKey()).getHealth();
            count = map.get(health);
            if (count == null) {
                map.put(health, 1);
            } else {
                map.replace(health, ++count);
            }
        }
        System.out.println(map.toString());
    }

    public void count_less_than_health(String stringHealth){
        Double health;
        int check = 0;
        try {
            health = Double.parseDouble(stringHealth);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if ( linkedHashMap.get(mapEntry.getKey()).getHealth() < health) {
                    check++;
                }
            }
            System.out.println("There is " + check +  " elements with health less than " + health);
        }catch (NumberFormatException e){
            System.out.println("Incorrect input! Try again.");
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
