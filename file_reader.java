package jun.error;
import java.io.File;
import java.io.FileReader;
import java.io.PushbackInputStream;
import java.nio.channels.Pipe;

// public class file_reader {
//     public static void main(String[] args) {
//         File words = new File("/home/bob/Downloads/wordle/resources/words.txt");
//         FileReader fr = new FileReader(words);
//     }
    
// }




public class file_reader {
    public static void main(String[] args) throws FileNotFoundException {
        File words = new File("/home/bob/Downloads/wordle/resources/words.txt");
        FileReader fr = new FileReader(words);
    }
}