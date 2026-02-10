package java_feb;
import java.util.Hashtable;

public class hash_bro1 {
    public static void main(String args[]){
        
        Hashtable<String, Integer> hashshi = new Hashtable<>();

        Integer n = hashshi.get("two"); 
        if (n != null){
            System.out.println("two"+n);
        }

        hashshi.put("UChicago", 2000);
        hashshi.put("Princeton", 1800);
        hashshi.put("Harvard", 1900);
        System.out.println("These are new"+ hashshi);
        System.out.println("here is the size " + hashshi.size());
    }

    
}
