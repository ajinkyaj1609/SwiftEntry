import java.util.Scanner;


public class Teacher{
    private static String password;
    private static String lessonPlan;

    public Teacher(){
        password = "000000";
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
}