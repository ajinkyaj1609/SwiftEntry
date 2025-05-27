import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.time.LocalTime;
import java.time.ZoneId; //stuff for starting day- scanner testing is moved into teacher
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import ArrayList

public class Teacher{

    private static String password;
    private static String lessonPlan = "";
    private List<Student> students = new ArrayList<>();
    private String sT = "11:05:00"; //class startTime, change to whatever you want, just make sure to change start time in scans as well for accuracy
    private String eT = "20:18:09"; //class endTime, change to whatever you want, just make sure to change end time in scans as well for accuracy


    public Teacher() {
        password = "000000";
        Path path = Paths.get("studentData.csv");
        students = GetData(path);
    }

    

    public Teacher(String p) {
        password = p;
        Path path = Paths.get("studentData.csv");
        students = GetData(path);
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
        lessonPlan += plan;
        System.out.println("Lesson plan saved successfully.\n");
    }

    public void setStartendTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the start time of the class (HH:mm:ss):");
        sT = scanner.nextLine();            
        System.out.println("Enter the end time of the class (HH:mm:ss):");
        eT = scanner.nextLine();
    }

    public static String getLessonPlan() {
        return lessonPlan;
    }

    public void addStudent(String id, String name){
        Student x = new Student(id, name, "", 0, 0, 0, 0);
        students.add(x);
        appendStudentToFile(x);
    }

    public void removeStudent(int index) {
        students.remove(index); //removes student from local arraylist
        reset(); //clears file to get rid of all students including student to be removed
        for (int i = 0; i < students.size(); i++){
            updateStudentToFile(students.get(i)); //repopulates file with all the students aside from the removed one
        }
    }

    public int findStudentIndex(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
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
        Path filePath = Paths.get("studentData.csv");

        // Default values for the remaining fields
        String email = student.getEmail();; // Empty string for email
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

    private void updateStudentToFile(Student student) {
        //this saves the list of students
        String id= student.getId();
        String name= student.getName();
        //should also update local arraylist when run
        Path filePath = Paths.get("studentData.csv");

        // Default values for the remaining fields
        String email = student.getEmail(); // Empty string for email
        int timesLate = student.getTimesLate();
        int timesAbsent = student.getTimesAbsent();
        int timesTooLongOutClass = student.getTimesTooLongOutClass();
        int timeSpentOutOfClass = student.getTimeSpentOutOfClass();

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
            System.out.println("Student '" + name + "' (ID: " + id + ") updated to file successfully!");
        } catch (IOException e) {
            System.err.println("Error appending student '" + name + "' to file: " + e.getMessage());
        }
    }

    private void reset(){
        String filePath = "studentData.csv";
        try{
            FileWriter writer = new FileWriter(filePath, false);
            writer.write("");
            writer.close();
            // System.out.println("Existing csv data cleared");
        }
        catch(IOException e){
            System.err.println("Csv data clearance failed");
        }
        //start of day method that clears saved student data since it will be replaced by today's data, data will be updated when program runs 
    }

    public void startDay() {
        Scanner scanner = new Scanner(System.in);
        ZoneId location = ZoneId.of("America/Los_Angeles");
        System.out.println("Please enter your password:");
        if (password.equals(scanner.nextLine())) {
            System.out.println("Authentication successful.");
           
            boolean quit = false; 
            boolean startProgramRun = false;
            while (quit == false && startProgramRun == false){
                System.out.println("Press 1 to start the day \nPress 2 to add a student \nPress 3 to remove a student \nPress 4 to create a lesson plan \nPress 5 to quit");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Starting the day...");
                        //ask theacehr for start time and end time of class
                        // String sT = "11:05:00"; //class startTime & endTime, change to whatever you want, just make sure to change start time in scans as well for acccuracy 
                        // String eT = "20:18:09";
                        
                        //parse the start time and end time

                        DateTimeFormatter theFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime startTime = LocalTime.parse(sT,theFormat); // Assuming getStartTime() returns a String in "HH:mm:ss" format
                        LocalTime endTime = LocalTime.parse(eT, theFormat);
                        LocalTime currTime = LocalTime.now(location);
                        boolean tooEarly = false;
                        boolean endEarly = false;

                        //if teacher starts class before class starts
                        if (!currTime.isAfter(startTime) || !currTime.isBefore(endTime)){
                            System.out.println("Class has not started yet, come back when class starts.");
                            tooEarly = true;
                        }

                        //scanning system loop
                        while(currTime.isAfter(startTime) && currTime.isBefore(endTime) && endEarly == false){
                            System.out.println("Type in student's id for scanning. Type ! to quit.");
                            String id = scanner.nextLine();
                            if(id.equals("!")){
                                System.out.println("Enter password to confirm early end:");
                                String inputPass = scanner.nextLine();
                                    if (checkPassword(inputPass)) {
                                        System.out.println("Correct password. Ending class early...");
                                        endEarly = true;
                                    } else {
                                        System.out.println("Incorrect password. Continuing scan...");
                            }
                            }
                            else{
                                int index = findStudentIndex(id); 
                                if (index == - 1){
                                    System.out.println("Id not found");
                                }
                                else{
                                    accessScanner(index);
                                }
                                currTime = LocalTime.now(location);
                            }
                        }

                        if (tooEarly == false){ //if program has actually run
                            System.out.println("Class has ended");
                            System.out.println("");
                            reset();
                            //saving student data gathered from scans at end of program
                            for(int i = 0; i < students.size(); i++){
                                Student student = students.get(i);
                                student.incrementTimeSpentOutOfClass(student.getStudentScans().returnTimeOut()); //updates time spent of out class and times too long outside of class 
                                if (student.getStudentScans().returnLate()){ 
                                    student.incrementTimesLate(); //updates num time lates
                                }
                                if (student.getStudentScans().getScansSize() == 0){ 
                                    student.incrementTimesAbsent(); //increments times absent if absent 
                                    if (student.getEmail() != null && !student.getEmail().isEmpty()) {
                                        // Send email if email is set
                                        try {
                                            student.emailPlan();
                                            } catch (Exception e) {
                                            System.out.println("Failed to email " + student.getEmail() + ": " + e.getMessage());
    }
                                            //sends email with lesson plan
                                    } else {
                                        System.out.println("No email set for " + student.getName() + ". Cannot send lesson plan.");
                                    }
                                    student.setAbsentToday(true); //sets to absent
                                } 
                                else{
                                    student.setAbsentToday(false); //sets to present
                                }  
                                updateStudentToFile(student);
                            }
                            //attendance notifications
                            System.out.println("Here are the students you should mark absent:");
                            for(int i = 0; i < students.size(); i++){
                                Student student = students.get(i);
                                if (student.isAbsentToday()){
                                    System.out.println(student.getName() + ", id: " + student.getId() + ", was absent today");
                                }
                            }
                            //flagging notifications 
                            System.out.println("These are the students you should be checking in on: ");
                            for(int i = 0; i < students.size();i++){
                                Student student = students.get(i);
                                if (student.getTimesTooLongOutClass() > 3){ //change parameters based on what you want
                                    System.out.println(student.getName() + ", id: " + student.getId() + ", has been spending too much time outside of class");
                                }
                                if (student.getTimesAbsent() > 3){
                                    System.out.println(student.getName() + ", id: " + student.getId() + ", has been absent too many days");
                                }
                                if (student.getTimesLate() > 3){
                                    System.out.println(student.getName() + ", id: " + student.getId() + ", has been late too many times");
                                }
                            }
                        }

                        startProgramRun = true;
                        break;
                    case 2:
                        System.out.println("Enter the Name of the student:");
                        String name = scanner.nextLine();
                        System.out.println("Enter the ID of the student:");
                        String id = scanner.nextLine();
                        System.out.println("Enter the Email of the student (optional):");
                        String email = scanner.nextLine();
                        addStudent(id, name);
                        
                        if (!email.isEmpty()) {
                            // If email is provided, set it in the Student object
                            students.get(students.size() - 1).setEmail(email); // Set email for the last added student
                        } else {
                            // If no email is provided, just add the student with ID and name
                            System.out.println("No email provided for " + name + ". Email will not be set.");
                        }  
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

    /**
     * Reads student data from a CSV file and returns a List of Student objects.
     * This method is static as it operates on the file, not a specific Student instance.
     *
     * @param filePath The Path to the CSV file (e.g., Paths.get("studentList.csv")).
     * @return A List of Student objects read from the CSV. Returns an empty list if an error occurs.
     */
    public static List<Student> GetData(java.nio.file.Path filePath) {
        List<Student> students = new ArrayList<>(); // Initialize the list to store students

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            int lineNumber = 0;

            // Loop through each line of the CSV file
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                // Skip the header line (first line)
                if (lineNumber == 1) {
                    System.out.println("Header: " + line); // Print header for verification
                    continue; // Move to the next line
                }

                // Split the line by comma to get individual data fields
                String[] data = line.split(",");

                // Ensure the line has the expected number of fields before parsing
                if (data.length == 7) { // Expected fields: ID,Name,Email,TimesLate,TimesAbsent,TimesTooLongOutClass,TimeSpentOutOfClass
                    try {
                        String id = data[0].trim();
                        String name = data[1].trim();
                        String email = data[2].trim();
                        int timesLate = Integer.parseInt(data[3].trim());
                        int timesAbsent = Integer.parseInt(data[4].trim());
                        int timesTooLongOutClass = Integer.parseInt(data[5].trim());
                        int timeSpentOutOfClass = Integer.parseInt(data[6].trim());

                        // Create a Student object from the parsed data
                        Student student = new Student(id, name, email, timesLate, timesAbsent, timesTooLongOutClass, timeSpentOutOfClass);
                        
                        // Add the created Student object to the ArrayList
                        students.add(student);
                        
                        // System.out.println("Added student: " + student.getName() + " to list."); // Optional: print as students are added

                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing number on line " + lineNumber + ": " + line + " - " + e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Not enough data fields on line " + lineNumber + ": " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping malformed line " + lineNumber + " (incorrect number of fields): " + line);
                }
            }
            // System.out.println("\nFinished reading CSV file.");
            return students; // Return the populated list of students

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.err.println("Please ensure 'studentList.csv' exists in the same directory as the compiled Java code.");
            return new ArrayList<>(); // Return an empty list on error
        }
    }

} 