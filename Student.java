public class Student {
    private String name;
    private int id;
    private int timesLate;
    private int timesAbsent;
    private int timesTooLongOutClass;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
        timesLate = 0;
        timesAbsent = 0;
        timesTooLongOutClass = 0;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public int getTimesLate() { return timesLate; }
    public int getTimesAbsent() { return timesAbsent; }
    public int getTimesTooLongOutClass() { return timesTooLongOutClass; }
    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
    public void incrementTimesLate() { timesLate++; }
    public void incrementTimesAbsent() { timesAbsent++; }
    public void incrementTimesTooLongOutClass() { timesTooLongOutClass++; }
    public void resetTimesLate() { timesLate = 0; }
    public void resetTimesAbsent() { timesAbsent = 0; }
    public void resetTimesTooLongOutClass() { timesTooLongOutClass = 0; }
}