package oop;

public class Enums {
    enum Level{
        LOW,
        MEDIUM,
        HIGH,
    }

    public static void main(String[] args) {
        Level myVar = Level.HIGH;
        switch(myVar){
            case LOW: 
                System.out.println("Level is LOW");
            break;
            case MEDIUM:
                System.out.println("Level is MEDIUM");
            break; 
            case HIGH:
                System.out.println("Level is HIGH");
            break; 
        }
    }
    
}
