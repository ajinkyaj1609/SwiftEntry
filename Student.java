import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List; // Import ArrayList

public class Student {
    //static var for all student objects
    private static ZoneId location = ZoneId.of("America/Los_Angeles"); 
    //un-changing attributes of student 
    private String name;
    private String id;
    private String email;
    //date specific vars 
    private boolean absentToday;
    private Scans studentScans; // Assuming Scans class exists
    //incrementing vars 
    private int timesLate;
    private int timesAbsent;
    private int timesTooLongOutClass;
    private int timeSpentOutOfClass;
    private LocalDate date = LocalDate.now();

    // Constructor to create a Student object from parsed CSV data
    public Student(String id, String name, String email, int timesLate, int timesAbsent, int timesTooLongOutClass, int timeSpentOutOfClass) {
        this.id = id; // Note: Constructor parameter order changed to match CSV (ID, Name, Email...)
        this.name = name;
        this.email = email;
        this.timesLate = timesLate;
        this.timesAbsent = timesAbsent;
        this.timesTooLongOutClass =timesTooLongOutClass ;
        this.timeSpentOutOfClass = timeSpentOutOfClass;
        
        // Initialize other fields with default values or based on logic
        this.absentToday = true; // Default to absent, can be changed later
        this.studentScans = new Scans(id); // Initialize Scans for this student
    }

    // Constructor for creating a new student with minimal info (rest set to zero/empty)
    public Student(String id, String name) {
        this(id, name, "", 0, 0, 0, 0); // Call the main constructor with default values
    }


    public String getName() { return name; }
    public String getId() { return id; }
    public int getTimesLate() { return timesLate; }
    public int getTimesAbsent() { return timesAbsent; }
    public int getTimesTooLongOutClass() { return timesTooLongOutClass; }
    public int getTimeSpentOutOfClass() { return timeSpentOutOfClass; }
    public boolean isAbsentToday() { return absentToday; }
    public String getEmail() { return email; }
    public Scans getStudentScans() { return studentScans; }
    public void setEmail(String email) { this.email = email; }
    public void setAbsentToday(boolean absentToday) { this.absentToday = absentToday; }
    public void setName(String name) { this.name = name; }
    public void setId(String id) { this.id = id; }
    public void incrementTimesLate() { timesLate++; }
    public void incrementTimesAbsent() { timesAbsent++; }
    public void incrementTimesTooLongOutClass() { timesTooLongOutClass++; }
    public void resetTimesLate() { timesLate = 0; }
    public void resetTimesAbsent() { timesAbsent = 0; }
    public void resetTimesTooLongOutClass() { timesTooLongOutClass = 0; }
    public void incrementTimeSpentOutOfClass(int time) { 
        timeSpentOutOfClass += time; 
        if (timeSpentOutOfClass > 15) {
            this.incrementTimesTooLongOutClass();
        }
    }
    public void resetTimeSpentOutOfClass() { timeSpentOutOfClass = 0; }
    public void setStudentScans(Scans studentScans) { this.studentScans = studentScans; }

    public void absent() { 
        absentToday = true; 
        this.incrementTimesAbsent();
    }
    public void tardy() { 
        absentToday = false; 
        this.incrementTimesLate(); 
    }
    public void tooLongOutOfClass() { 
        absentToday = false; 
        this.incrementTimesTooLongOutClass(); 
    }
    

    public void excused() { 
        absentToday = false; 
    }

    // Assuming Teacher and TLSEmail classes exist
    public void emailPlan() {
        String msg = Teacher.getLessonPlan(); // Assuming Teacher.getLessonPlan() is static
        if (msg == null || msg.isEmpty()) { // Check for null or empty message
            msg = "No lesson plan available. You should check in with your teacher.";
            // No return here, proceed to send email with default message
        }
        TLSEmail.sendEmail(email, msg); // Assuming TLSEmail.sendEmail is static
    }

    // public Boolean isFlagged(){
    //     if(timesLate > 5 || timesAbsent > 10 || timesTooLongOutClass > 5){
    //         return true;
    //     }
    //     else{
    //         return false;
    //     }
    // }
    
    @Override
    public String toString() {
    return "Student [ID=" + id + ", Name=" + name + ", Email=" + email +
           ", TimesLate=" + timesLate + ", TimesAbsent=" + timesAbsent +
           ", TimesTooLongOutClass=" + timesTooLongOutClass +
           ", TimeSpentOutOfClass=" + timeSpentOutOfClass + "]";
    }

    public void takeScan(){
        studentScans.Scan();
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
            System.out.println("\nFinished reading CSV file.");
            return students; // Return the populated list of students

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.err.println("Please ensure 'studentList.csv' exists in the same directory as the compiled Java code.");
            return new ArrayList<>(); // Return an empty list on error
        }
    }

    // This method seems to be for initializing an *existing* Student object's fields,
    // not for importing multiple students from a CSV. Keeping as is per previous context.
    public void importStudents(String name, String id, String email, int timesLate, int timesAbsent, int timesTooLongOutClass, int timeSpentOutOfClass){
        this.name = name;
        this.id = id;
        absentToday = true;
        this.timesLate = timesLate;
        this.timesAbsent = timesAbsent;
        this.timesTooLongOutClass =timesTooLongOutClass ;
        studentScans = new Scans(id);
        this.email = email;
        this.timeSpentOutOfClass = timeSpentOutOfClass;
    }
}
