import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class Main
{
        public static void main(String[] args) {

                String data="data.txt";
                File file = new File(data);
                //read from data.txt
                try{
                        List<String> lines = Files.readAllLines(file.toPath());
                        lines.forEach(System.out::println);
                }
                catch (IOException e){
                        System.out.println("Error reading file: " + e.getMessage());
                }
                //write to data.txt
                try{
                        String content = "Hello, World!";
                        Files.write(file.toPath(), content.getBytes());
                }
                catch (IOException e){
                        System.out.println("Error writing to file: " + e.getMessage());
                }
                //temp arraylist for testing
                ArrayList<String> testingStudent = new ArrayList<>();
                testingStudent.add("John Doe");
                testingStudent.add("Jane Smith");
                testingStudent.add("Alice Johnson");
                testingStudent.add("Dow Jones");
                
                //add the arraylist to the file
                try{
                        Files.write(file.toPath(), testingStudent);
                }
                catch (IOException e){
                        System.out.println("Error writing to file: " + e.getMessage());
                }

        }
}