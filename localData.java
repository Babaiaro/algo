package oop;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime; 
import java.time.format.DateTimeFormatter;

public class localData {
    public static void main(String[] args) {
        LocalDate myObj = LocalDate.now(); 
        System.out.println(myObj);
        LocalTime myTime = LocalTime.now();
        System.out.println(myTime);
        LocalDateTime myLocDate = LocalDateTime.now();
        System.out.println("This is before formatter " + myLocDate);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
        String formattedDate = myLocDate.format(myFormatObj);
        System.out.println("This is after formatter " + formattedDate);
    }

    
}
