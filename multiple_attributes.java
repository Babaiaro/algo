package jun.oop;

public class multiple_attributes {
    String namej = "Joshua";
    String namea = "Albert";
    int age = 35;

    public static void main(String[] args) {
        multiple_attributes myObj = new multiple_attributes();
        multiple_attributes myObj2 = new multiple_attributes();

        myObj2.age = 40;

        System.out.println("His name is " + myObj.namej + " and he is " + myObj.age + " years old");
        System.out.println("And his name is " + myObj2.namea + " and he is " + myObj2.age + " years old");
    }
    
}
