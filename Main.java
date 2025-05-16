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
                //read the data from the file
                try{
                        List<String> lines = Files.readAllLines(file.toPath());
                        lines.forEach(System.out::println);
                }
                catch (IOException e){
                        System.out.println("Error reading file: " + e.getMessage());
                }
                /*  
                public static int firstScan(){ // checks if studet has already check in for the day. Used to distinguish attendence check ins from brief during class check outs.
                        for(int i = 0; i<testingStudents.length; i++){
                                if(student.getName() != testingStudent[i]){
                                        return 1; // if 1 is return if is the first check in of the day, and therefore the attendence check in
                                }
                                else{
                                        System.out.println("The student has already checked in for attendence")
                                        return 0
                                }
                        }
                }
                public static int scanNumber(){
                        if(student.get)
                }
                */
        }
}