import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main
{
        public static void main(String[] args) {

                String data="data.txt";
                File file = new File(data);
                try{
                        List<String> lines = Files.readAllLines(file.toPath());
                        lines.forEach(System.out::println);
                }
                catch (IOException e){
                        System.out.println("Error reading file: " + e.getMessage());
                }
        }
}