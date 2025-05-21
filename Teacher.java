import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Teacher{
    private static String password;
    private static String lessonPlan;

    public Teacher(){
        password = "000000";
        // TODO: implement method logic here
        // return students;
        String pass="000000";
    }

    
    public Teacher(String p){
        password = p;
    }

    public boolean checkPassword(String p){
        if (p.equals(password)){
            return true;
        }
        else{
            return false;
        }
    }

    public String getPassword(){
        return password;
    }


    public void writeLessonPlan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the lesson plan details:");
        String plan = scanner.nextLine(); // Consume the newline character
        lessonPlan = "SwiftEntry has detected that you were absent from class. Here is your teacher's lesson plan.\n";
        lessonPlan += plan;
    }

    public static String getLessonPlan() {
        return lessonPlan;
    }
   

public class Students {

    ArrayList<Student> students;

    public Students(ArrayList<Student> x) {
        students = x;
    }

    public void addStudent(int id) {
        Student x = new Student(id);
        students.add(x);
    }

    public void addStudent(int id, String name) {
        Student x = new Student(name, id);
        students.add(x);
    }

    public void removeStudents(int index) {
        students.remove(index);
    }

    public int findIndex(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void accessScanner(int index) {
        students.get(index).takeScan();
    }
      
    public void writeStudentsToFile(Path filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
             //Write the header if the file is new
             if(Files.notExists(filePath) || Files.size(filePath) ==0){
                writer.write("ID,Name");
                writer.newLine();
            }
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName());
                writer.newLine();
            }
             System.out.println("Student data written to file successfully!");
        } catch (IOException e) {
              System.err.println("Error writing to file: " + e.getMessage());
        }
    }

   public void startDay() {

        System.out.println("Please enter your password");
        Scanner scanner = new Scanner(System.in);
        String p = scanner.nextLine();
        String pass = "000000";
        if (pass.equals(p)) {

            System.out.println("Authentication successful.");
            System.out.println("Press 1 to start the day \n Press 2 to add a student \n Press 3 to remove a student \n Press 4 to create a lesson plan");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Starting the day...");
                    break;
                case 2:
                    System.out.println("Enter the Name of the student");
                    String name = scanner.next();
                    System.out.println("Enter the ID of the student");
                    int id = scanner.nextInt();
                    addStudent(id, name);

                break;
                case 3:
                    System.out.println("Removing a student...");
                    break;
                case 4:
                     System.out.println("Creating a lesson plan...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } else {
            System.out.println("Authentication failed.");
        }
           Path filePath = Paths.get("studentData.csv");
           writeStudentsToFile(filePath);
    }
}

}