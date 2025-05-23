import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

public class Student {
    //static var for all student objects
    private static ZoneId location = ZoneId.of("America/Los_Angeles"); 
    //un-changing attributes of student 
    private String name;
    private String id;
    private String email;
    //date specific vars 
    private boolean absentToday;
    private Scans studentScans;
    //incrementing vars 
    private int timesLate;
    private int timesAbsent;
    private int timesTooLongOutClass;
    private int timeSpentOutOfClass;
    private LocalDate date = LocalDate.now();

    public Student(String name, String id, String email, int timesLate, int timesAbsent, int timesTooLongOutClass, int timeSpentOutOfClass) {
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

    public void emailPlan() {
        String msg = Teacher.getLessonPlan();
        if (msg == null) {
            msg = "No lesson plan available. You should check in with your teacher.";
            return;
        }
        TLSEmail.sendEmail(email, msg);       
    }

    // public Boolean isFlagged(){
    //     if(timesLate > 5 || timesAbsent > 10 || timesTooLongOutClass > 5){
    //         return true;
    //     }
    //     else{
    //         return false;
    //     }
    // }
    
    public String toString() {
        return "\nStudent{" +
                "\nName: " + name +
                "\nID: " + id +
                "\nTimes Late: " + timesLate +
                "\nTimes Absent: " + timesAbsent +
                "\nTimes Too Long Outside Class: " + timesTooLongOutClass +
                "\n}";
    }

    public void takeScan(){
        studentScans.Scan();
    }

    public void GetData(java.nio.file.Path filePath) {try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
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
                if (data.length == 7) { // ID,Name,Email,TimesLate,TimesAbsent,TimesTooLongOutClass,TimeSpentOutOfClass
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
                        System.out.println("Read student: " + student);

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

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.err.println("Please ensure 'studentList.csv' exists in the same directory as the compiled Java code.");
        }}

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
