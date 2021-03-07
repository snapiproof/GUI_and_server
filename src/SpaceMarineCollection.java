import java.util.*;

public class SpaceMarineCollection {


    private Map<Long, SpaceMarine> linkedHashMap = new LinkedHashMap<Long, SpaceMarine>();

    public Map<Long, SpaceMarine> getLinkedHashMap() {
        return linkedHashMap;
    }

//    public void show() {
//        Map<Integer, String> testMap = new PriorityQueue<>(linkedHashMap);
//        while (!testQueue.isEmpty()) {
//            System.out.println(testQueue.poll().toString());
//        }
//    }

    public void info() {
        System.out.println("Type of collection is " + linkedHashMap.getClass().toString() + ". Size of collection is " + linkedHashMap.size());
    }

    public void add(Long id, SpaceMarine spaceMarine) {
        linkedHashMap.put(id, spaceMarine);
    }

}
