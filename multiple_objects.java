package jun.oop;

public class multiple_objects {
    int x = 20;
    public static void main(String[] args){
        multiple_objects myObj1 = new multiple_objects();
        multiple_objects myObj2 = new multiple_objects();

        myObj2.x = 45;
        System.out.println(myObj1.x);
        System.out.println(myObj2.x);
    }
    
}
