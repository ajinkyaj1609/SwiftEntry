import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.ZoneId; //stuff for starting day- scanner testing is moved into teacher
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class Teacher{

    private static String password;
    private static String lessonPlan;
    private ArrayList<Student> students = new ArrayList<>();

    public Teacher() {
        password = "000000";
    }

    public Teacher(String p) {
        password = p;
    }

    public boolean checkPassword(String p) {
        return p.equals(password);
    }

    public String getPassword() {
        return password;
    }

    public void writeLessonPlan() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the lesson plan details:");
        String plan = scanner.nextLine();
        lessonPlan = "SwiftEntry has detected that you were absent from class. Here is your teacher's lesson plan.\n";
        lessonPlan += plan;
    }

    public static String getLessonPlan() {
        return lessonPlan;
    }

    public void addStudent(String id, String name) {
        Student x = new Student(id, name, "", 0, 0, 0, 0);
        students.add(x);
        appendStudentToFile(x);
    }

    public void removeStudent(int index) {
        students.remove(index);
    }

    public int findStudentIndex(String id) {
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

    private void appendStudentToFile(Student student) {
        //this saves the list of students
        String id= student.getId();
        String name= student.getName();
        //should also update local arraylist when run
        Path filePath = Paths.get("studentList.csv");

        // Default values for the remaining fields
        String email = ""; // Empty string for email
        int timesLate = 0;
        int timesAbsent = 0;
        int timesTooLongOutClass = 0;
        int timeSpentOutOfClass = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            // If the file does not exist or is empty, write the header row
            if (Files.notExists(filePath) || Files.size(filePath) == 0) {
                writer.write("ID,Name,Email,TimesLate,TimesAbsent,TimesTooLongOutClass,TimeSpentOutOfClass");
                writer.newLine();
            }

            // Write the new student's data, including ID, Name, and the default values for other fields
            writer.write(id + "," +
                         name + "," +
                         email + "," +
                         timesLate + "," +
                         timesAbsent + "," +
                         timesTooLongOutClass + "," +
                         timeSpentOutOfClass);
            writer.newLine(); // Move to the next line for the next entry
            System.out.println("Student '" + name + "' (ID: " + id + ") appended to file successfully!");
        } catch (IOException e) {
            System.err.println("Error appending student '" + name + "' to file: " + e.getMessage());
        }
    }

    private void removeStudentFromFile(Student student){
        //this removes students
        //should also update local arraylist when run
    }

    private void updateAndReset(){
        //start of day method that updates all student objects with saved student data and clears saved student data since it will be replaced by today's data
    }

    private void saveStudentData(Student student){
        //this saves the information that we need to flagging 
        Path filePath = Paths.get("studentData.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            if (Files.notExists(filePath) || Files.size(filePath) == 0) {
                writer.write("ID,timesLate, timesAbsent, timesTooLongOutClass");
                writer.newLine();
            }
            writer.write(student.getId() + "," + student.getTimesLate() + "," + student.getTimesAbsent() + "," + student.getTimesTooLongOutClass());
            writer.newLine();
            System.out.println("Student data appended to file successfully!");
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    public void startDay() {
        Scanner scanner = new Scanner(System.in);
        ZoneId location = ZoneId.of("America/Los_Angeles");
        System.out.println("Please enter your password:");
        if (password.equals(scanner.nextLine())) {
            System.out.println("Authentication successful.");
            boolean quit = false; 
            while (quit == false){
                System.out.println("Press 1 to start the day \nPress 2 to add a student \nPress 3 to remove a student \nPress 4 to create a lesson plan. Type 5 to quit");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Starting the day...");

                        String sT = "17:25:00"; //class startTime/endTime
                        String eT = "18:18:09";

                        DateTimeFormatter theFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime startTime = LocalTime.parse(sT, theFormat);
                        LocalTime endTime = LocalTime.parse(eT, theFormat);
                        LocalTime currTime = LocalTime.now(location);
                        boolean tooEarly = false;

                        if (!currTime.isAfter(startTime) || !currTime.isBefore(endTime)){
                            System.out.println("Class has not started yet, come back when class starts.");
                            tooEarly = true;
                        }

                        while(currTime.isAfter(startTime) && currTime.isBefore(endTime)){
                            System.out.println("Type in student's id for scanning");
                            String id = scanner.nextLine();
                            int index = findStudentIndex(id); 
                            if (index == - 1){
                                System.out.println("Id not found");
                            }
                            else{
                                accessScanner(index);
                            }
                            currTime = LocalTime.now(location);
                        }
                    
                        if (tooEarly == false){
                            System.out.println("Class has ended");
                        }

                        break;
                    case 2:
                        System.out.println("Enter the Name of the student:");
                        String name = scanner.nextLine();
                        System.out.println("Enter the ID of the student:");
                        String id = scanner.nextLine();
                        addStudent(id, name);
                        break;
                    case 3:
                        for(int i = 0; i < students.size(); i++){
                            System.out.println(students.get(i));
                        }
                        System.out.println("Enter ID of student to remove:");
                        String ID = scanner.nextLine();
                        int index = findStudentIndex(ID);
                        
                        removeStudent(index);
                        break;
                    case 4:
                        writeLessonPlan();
                        break;
                    case 5: 
                        quit = true; 
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } 
        else {
            System.out.println("Authentication failed.");
        }
    }
} 