package jun.error;

import java.io.FileReader;
import java.io.IOException;

public class file_error {
    public static void main(String[] args) {
        try(FileReader fr = new FileReader ("/home/bob/Downloads/wordle/resources/words.txt")){
            char [] a = new char[50];
            fr.read(a);
            for(char c : a)
            System.out.print(c);
        }catch(IOException e){
            e.printStackTrace();

        }
    }
    
}
