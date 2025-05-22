import java.util.ArrayList;
//with fork test
public class Main {
    //arraylist of all students acceible everyehere
    public static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args){
        // String[] names = {"Alice", "Bob", "Charlie"};
        // int[] ids = {101, 102, 103};
        // for (int i = 0; i < ids.length; i++) {
        //     Student s = new Student(names[i], Integer.toString(ids[i]));
        //     students.add(s);
        // }
        // for (Student s : students) {
        //     System.out.println(s);
        // }
        Teacher prof = new Teacher();
        prof.startDay();
       
    }    
}