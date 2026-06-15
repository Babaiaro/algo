package jun.oop;

public class this_keyword {
    int modelYear;
    String modelName;

    public this_keyword(String modelName){
        this(2025, modelName);

    }

    public this_keyword(int modelYear, String modelName){
        this.modelName = modelName;
        this.modelYear = modelYear;
    }
    public void printInfo() {
        System.out.println(modelName + " is " + modelYear);
    }

    public static void main(String[] args) {
        this_keyword myCar1 = new this_keyword("Carvette");
        this_keyword myCar2 = new this_keyword( 2020 ,"Range Rover");

        myCar1.printInfo();
        myCar2.printInfo();
    }

    
    
}
