package oop;

public class Enum_constructor {
    enum Level{
        LOW("LOW LEVEL"), 
        MEDIUM("MEDIUM LEVEL"),
        HIGH("HIGH LEVELl");

        private String description; 

        private Level(String description){
         this.description = description;    
        }
        public String getDescription(){
            return description;
        }
    }

    public class Main{
        public static void main(String[] args){
            Level myVar = Level.HIGH;
            System.out.println(myVar.description);
        }
    }       
}
