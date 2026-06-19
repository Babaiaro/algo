package jun.map;
import java.util.HashMap;

public class hashmap_addItem {
    public class Main{
        public static void main(String[] args) {
            HashMap<String, String> city = new HashMap<String, String>(); 
            city.put("England", "London");
            city.put("Illinois", "Springfield");
            city.put("The USA", "Washington");
            city.put("German", "Berlin"); 

            city.get("Illinois"); 

            city.remove("German");
            System.out.println(city);

        }
    }
    
}
