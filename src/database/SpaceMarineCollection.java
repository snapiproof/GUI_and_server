package database;
import server.Postgre;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SpaceMarineCollection {

    private Postgre postgre;

    private final ConcurrentHashMap<Long, SpaceMarine> linkedHashMap = new ConcurrentHashMap<Long, SpaceMarine>();
    public SpaceMarineCollection(Postgre postgre){
        this.postgre = postgre;
    }
    public SpaceMarineCollection(){

    }

    public String help(){
        return "All commands : " + Commands.show();
    }

    public String info() {
        return ("Type of collection is " + linkedHashMap.getClass().toString() + ". Size of collection is " + linkedHashMap.size());
    }

    public String insert(String stringKey, SpaceMarine spaceMarine) {
        long key;
        try {
            key = Long.parseLong(stringKey);
            if (!linkedHashMap.containsKey(key)) {
                linkedHashMap.put(key, spaceMarine);
                return("Element is inserted");
            } else return("Collection already has element with this ID");
        } catch (NumberFormatException e) {
            return("Incorrect input! Try again.");
        }
    }

    public String remove(String stringKey, String login){
        long key;
        try {
            key = Long.parseLong(stringKey);
            if (!(linkedHashMap.get(key) == null)) {
                if (login.equals(linkedHashMap.get(key).getOwner())) {
                    linkedHashMap.remove(key);
                    postgre.remove(key);
                    return ("Element was removed");
                }else return "You can not remove this";
            } else return "Element is empty";
        } catch (NumberFormatException e) {
            return("Incorrect input! Try again.");
        }
    }

    public String update(String stringID, SpaceMarine spaceMarine, String login){
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
                if (login.equals(linkedHashMap.get(key).getOwner())){
                    postgre.update(key, spaceMarine);
                    return("Element is updated");
                }else return "You can not update this";
            }else {
                return("There is no element with ID: " + stringID);
            }
        }catch(NumberFormatException e){
            return("Incorrect input! Try again.");
        }
    }

    public String clear(String login){
        if (login.equals("postgre")) {
            linkedHashMap.clear();
            postgre.clear();
            return ("Collection is empty");
        }else return "You can not clear colelction";
    }

    public String show(){
        Map map = linkedHashMap;
        List<Map.Entry<Long, SpaceMarine>> list = new LinkedList<Map.Entry<Long, SpaceMarine>>(map.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<Long, SpaceMarine>>()
        {
            public int compare(Map.Entry<Long, SpaceMarine> o1,
                               Map.Entry<Long, SpaceMarine> o2)
            {
                return o1.getValue().getName().compareTo(o2.getValue().getName());
            }
        });

        Map<Long, SpaceMarine> sortedMap = new LinkedHashMap<Long, SpaceMarine>();
        for (Map.Entry<Long, SpaceMarine> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        String line = "";
        Set set = sortedMap.entrySet();
        for (Object element: set) {
            Map.Entry mapEntry = (Map.Entry) element;
            line += ("key: " + mapEntry.getKey() + " |" + mapEntry.getValue().toString()) + "\n";
        }
        return "LinkedHashMap initial content: \n" + line;
    }

    public String remove_greater_key(String stringKey, String login){
        long key;
        try {
            key = Long.parseLong(stringKey);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if (((Long)mapEntry.getKey() > key)&&(login.equals(linkedHashMap.get(key).getOwner()))){
                    linkedHashMap.remove(key);
                    postgre.remove(key);
                }
            }
            return("Elements with key more than " + key + " were deleted. If you can deleted it");
        }catch (NumberFormatException e){
            return("Incorrect input! Try again.");
        }
    }

    public String remove_lower_key(String stringKey, String login){
        long key;
        try {
            key = Long.parseLong(stringKey);
            Set set = linkedHashMap.entrySet();
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if (((Long)mapEntry.getKey() < key)&&(login.equals(linkedHashMap.get(key).getOwner()))){
                    linkedHashMap.remove(key);
                    postgre.remove(key);
                }
            }
            return("Elements with key less than " + key + " were deleted. If you can deleted it");
        }catch (NumberFormatException e){
            return ("Incorrect input! Try again.");
        }
    }

    public String remove_any_by_health(String stringHealth, String login){
        Double health;
        boolean check = false;
        try {
            health = Double.parseDouble(stringHealth);
            Set set = linkedHashMap.entrySet();
            String line = "";
            for (Object element : set) {
                Map.Entry mapEntry = (Map.Entry) element;
                if (( linkedHashMap.get(mapEntry.getKey()).getHealth() == health)&&(login.equals(linkedHashMap.get(mapEntry.getKey()).getOwner()))) {
                    line += mapEntry.getKey();
                    linkedHashMap.remove(mapEntry.getKey());
                    postgre.remove((Long)mapEntry.getKey());
                    check = true;
                    break;
                }
            }
            if (!check) {
                return("There is no element with health " + health);
            } else return "Elements with key " + line + " was deleted.";
        }catch (NumberFormatException e){
            return("Incorrect input! Try again.");
        }
    }

    public String replace_if_lowe(String stringKey, SpaceMarine spaceMarine, String login){
        long key;
        try {
            key = Long.parseLong(stringKey);
            if ((spaceMarine.getMarinesCount() < linkedHashMap.get(key).getMarinesCount())&&(login.equals(linkedHashMap.get(key).getOwner()))){
                linkedHashMap.replace(key, spaceMarine);
                postgre.update(key, spaceMarine);
                return("Element was replaced");
            }else {
                return("Element wasn't replaced");
            }
        }catch (NumberFormatException e){
            return("Incorrect input! Try again.");
        }
    }

    public String group_counting_by_health(){
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
        return (map.toString());
    }

    public String count_less_than_health(String stringHealth){
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
            return ("There is " + check +  " elements with health less than " + health);
        }catch (NumberFormatException e){
            return ("Incorrect input! Try again.");
        }
    }



    public void writeToFIle(String nameFile){
        File f = new File(nameFile);
        if(f.exists()) {
            System.out.println("File exists. File will reset. Are you sure you want do it? \n Enter '1' if you want or something else if you don't want.");
            Scanner Check = new Scanner(System.in);
            String check = Check.nextLine();
            int save;
            try{
                save = Integer.parseInt(check);
            }catch(NumberFormatException e){
                save = 0;
            }
            if (save == 1) {
                try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nameFile))) {
                    Map<Long, SpaceMarine> map = linkedHashMap;
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        String csv = "" + pair.getKey() + "," + linkedHashMap.get(pair.getKey()).toCsv() + "\n";
                        byte[] buffer = csv.getBytes("Windows-1251");
                        bos.write(buffer, 0, buffer.length);

                    }
                    System.out.println("Collection is saved in " + nameFile);
                } catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }else {
                System.out.println("Collection didn't save");
            }
        }
    }
}
