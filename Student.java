import java.time.LocalDate;
import java.time.*;

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
    private LocalDate date = LocalDate.now();

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        absentToday = false;
        timesLate = 0;
        timesAbsent = 0;
        timesTooLongOutClass = 0;
        studentScans = new Scans(id);
    }

    public Student(String id) {
        this.id = id;
        absentToday = false;
        timesLate = 0;
        timesAbsent = 0;
        timesTooLongOutClass = 0;
        studentScans = new Scans(id);
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public int getTimesLate() { return timesLate; }
    public int getTimesAbsent() { return timesAbsent; }
    public int getTimesTooLongOutClass() { return timesTooLongOutClass; }

    public void setName(String name) { this.name = name; }
    public void setId(String id) { this.id = id; }
    public void incrementTimesLate() { timesLate++; }
    public void incrementTimesAbsent() { timesAbsent++; }
    public void incrementTimesTooLongOutClass() { timesTooLongOutClass++; }
    public void resetTimesLate() { timesLate = 0; }
    public void resetTimesAbsent() { timesAbsent = 0; }
    public void resetTimesTooLongOutClass() { timesTooLongOutClass = 0; }

    public void absent() { 
        absentToday = true; 
        this.incrementTimesAbsent();
    }

    public void tardy() { 
        absentToday = false; 
        this.incrementTimesLate();
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
}
