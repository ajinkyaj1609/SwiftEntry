import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.ZoneId; //stuff for starting day- scanner testing is moved into teacher
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import ArrayList

public class Teacher{

    private static String password;
    private static String lessonPlan = "";
    private List<Student> students = new ArrayList<>();
    private final Path studentFile;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private LocalTime startTime = LocalTime.of(11, 5);
    private LocalTime endTime = LocalTime.of(20, 18, 9);


    public Teacher() {
        this("000000", Paths.get("studentData.csv"));
    }

    public Teacher(String p) {
        this(p, Paths.get("studentData.csv"));
    }

    Teacher(String p, Path studentFile) {
        password = p;
        this.studentFile = studentFile;
        students = GetData(studentFile);
        updateScanStartTimes();
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
        while (true) {
            LocalTime proposedStart = readTime(scanner, "Enter the start time of the class (HH:mm:ss):");
            LocalTime proposedEnd = readTime(scanner, "Enter the end time of the class (HH:mm:ss):");
            if (proposedEnd.isAfter(proposedStart)) {
                startTime = proposedStart;
                endTime = proposedEnd;
                updateScanStartTimes();
                return;
            }
            System.out.println("End time must be after start time on the same day. Please try again.");
        }
    }

    private LocalTime readTime(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return LocalTime.parse(scanner.nextLine(), TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Use HH:mm:ss, for example 09:30:00.");
            }
        }
    }

    private void updateScanStartTimes() {
        for (Student student : students) {
            student.getStudentScans().setStartTime(startTime);
        }
    }

    public static String getLessonPlan() {
        return lessonPlan;
    }

    public void addStudent(String id, String name) {
        addStudent(id, name, "");
    }

    public void addStudent(String id, String name, String email) {
        if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID and name are required");
        }
        id = id.trim();
        if (findStudentIndex(id) >= 0) {
            throw new IllegalArgumentException("Student ID already exists: " + id);
        }
        Student student = new Student(id, name.trim(), email == null ? "" : email.trim(), 0, 0, 0, 0);
        student.getStudentScans().setStartTime(startTime);
        List<Student> updated = new ArrayList<>(students);
        updated.add(student);
        saveStudents(updated);
        students = updated;
    }

    public void removeStudent(int index) {
        if (index < 0 || index >= students.size()) {
            throw new IllegalArgumentException("Student not found");
        }
        List<Student> updated = new ArrayList<>(students);
        updated.remove(index);
        saveStudents(updated);
        students = updated;
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

    private void saveStudents(List<Student> data) {
        try {
            StudentCsv.write(studentFile, data);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save student data: " + e.getMessage(), e);
        }
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
                            }
                            currTime = LocalTime.now(location);
                        }

                        if (tooEarly == false){ //if program has actually run
                            System.out.println("Class has ended");
                            System.out.println("");
                            //saving student data gathered from scans at end of program
                            for(int i = 0; i < students.size(); i++){
                                Student student = students.get(i);
                                student.resetTimeSpentOutOfClass();
                                student.incrementTimeSpentOutOfClass(student.getStudentScans().returnTimeOut()); //updates time spent of out class and times too long outside of class 
                                if (student.getStudentScans().returnLate()){
                                    student.incrementTimesLate(); //updates num time lates
                                }
                                if (student.getStudentScans().getScansSize() == 0){
                                    student.incrementTimesAbsent(); //increments times absent if absent 
                                    student.setAbsentToday(true); //sets to absent
                                }
                                else{
                                    student.setAbsentToday(false); //sets to present
                                }
                            }
                            saveStudents(students);
                            for (Student student : students) {
                                if (student.isAbsentToday()) {
                                    if (student.getEmail() != null && !student.getEmail().isEmpty()) {
                                        try {
                                            student.emailPlan();
                                        } catch (Exception e) {
                                            System.out.println("Failed to email " + student.getEmail() + ": " + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("No email set for " + student.getName() + ". Cannot send lesson plan.");
                                    }
                                }
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

                        startProgramRun = !tooEarly;
                        break;
                    case 2:
                        System.out.println("Enter the Name of the student:");
                        String name = scanner.nextLine();
                        System.out.println("Enter the ID of the student:");
                        String id = scanner.nextLine();
                        System.out.println("Enter the Email of the student (optional):");
                        String email = scanner.nextLine();
                        try {
                            addStudent(id, name, email);
                        } catch (IllegalArgumentException | IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        for(int i = 0; i < students.size(); i++){
                            System.out.println(students.get(i));
                        }
                        System.out.println("Enter ID of student to remove:");
                        String ID = scanner.nextLine();
                        int index = findStudentIndex(ID);
                        if (index < 0) {
                            System.out.println("Student ID not found.");
                        } else {
                            try {
                                removeStudent(index);
                            } catch (IllegalStateException e) {
                                System.out.println(e.getMessage());
                            }
                        }
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

    public static List<Student> GetData(Path filePath) {
        try {
            return StudentCsv.read(filePath);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load student data: " + e.getMessage(), e);
        }
    }
}
