import java.util.ArrayList;

//with fork test
public class Main {
    //arraylist of all students acceible everyehere
    public static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args){
        //moved everything to ScannerTesting.java
        Teacher prof = new Teacher();
        prof.startDay();
    }    
}