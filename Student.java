
public class Student {
    private String name;
    private boolean isAbsent;
    private String id;
    private int timesLate;
    private int timesAbsent;
    private int timesTooLongOutClass;
    private String email;
    private boolean isFlagged;
    private Scans studentScans;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        isAbsent = true;
        timesLate = 0;
        timesAbsent = 0;
        timesTooLongOutClass = 0;
        isFlagged = false;

        // Convert id to int for Scans if possible
        try {
            studentScans = new Scans(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            studentScans = new Scans(0); // fallback ID
        }
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public int getTimesLate() { return timesLate; }
    public int getTimesAbsent() { return timesAbsent; }
    public int getTimesTooLongOutClass() { return timesTooLongOutClass; }
    public boolean getIsFlagged() { return isFlagged; }

    public void setName(String name) { this.name = name; }
    public void setId(String id) { this.id = id; }

    public void incrementTimesLate() { timesLate++; }
    public void incrementTimesAbsent() { timesAbsent++; }
    public void incrementTimesTooLongOutClass() { timesTooLongOutClass++; }

    public void resetTimesLate() { timesLate = 0; }
    public void resetTimesAbsent() { timesAbsent = 0; }
    public void resetTimesTooLongOutClass() { timesTooLongOutClass = 0; }

    public void present() { isAbsent = false; }

    public void absent() { 
        isAbsent = true; 
        incrementTimesAbsent();
    }

    public void tardy() { 
        isAbsent = false; 
        incrementTimesLate();
    }

    public void emailPlan() {
        String msg = Teacher.getLessonPlan();
        if (msg == null) {
            msg = "No lesson plan available. You should check in with your teacher.";
            return;
        }
        TLSEmail.sendEmail(email, msg);       
    }

    public Boolean isFlagged(){
        return (timesLate > 5 || timesAbsent > 10 || timesTooLongOutClass > 5);
    }

    @Override
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
