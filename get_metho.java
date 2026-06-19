package jun.map;
import java.util.HashMap;

public class get_metho {
    public static void main(String[] args) {
        HashMap<String, String> phoneBook = new HashMap<String, String>();
        phoneBook.put("Doctor", "111 222 3333");
        phoneBook.put("Mechanic", "777 777 7777");
        phoneBook.put("Manager", "444 444 4444");
        phoneBook.put("Master", "555 555 5555");

        String number = phoneBook.get("Mechanic");
        int size = phoneBook.size(); 

        System.out.println(size);
    }
    
}
