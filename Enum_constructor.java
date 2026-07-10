package oop;

public class Enum_constructor {
    enum Level{
        LOW("Low Level"), 
        MEDIUM("Medium Level"),
        HIGH("High Level");

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
