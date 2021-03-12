import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        System.out.println((scanner.equals(null)));
        System.out.println((scanner.equals(scanner)));

        scanner.close();
        System.out.println((scanner.equals(null)));
        System.out.println((scanner.equals(scanner)));


//    public static long inputKey(Scanner scanner){
//        long key;
//        while (true) {
//            System.out.println("Enter key: ");
//            try {
//                key = Long.parseLong(scanner.nextLine());
//                break;
//            } catch (NumberFormatException e) {
//                System.out.println("Incorrect enter! Try again.");
//            }
//        }
//        return key;
//    }
//        private static Long getKeyByID(Map<Long, SpaceMarine> map, String stringID) {
//            long id;
//            long key = 0;
//            try {
//                id = Long.parseLong(stringID);
//                for (Map.Entry<Long, SpaceMarine> entry : map.entrySet()) {
//                    if (Objects.equals(id, map.get(entry.getKey()).getId())) {
//                        key = entry.getKey();
//                    }
//                }
//            }catch(NumberFormatException e){
//                System.out.println("Incorrect enter! Try again.");
//            }
//            return key;
//        }
//
//        private static <Long, SpaceMarine> Long getKeyByValue(Map<Long, SpaceMarine> map, SpaceMarine value) {
//            for (Map.Entry<Long, SpaceMarine> entry : map.entrySet()) {
//                if (Objects.equals(value, entry.getValue())) {
//                    return entry.getKey();
//                }
//            }
//            return null;
//        }


    }
}