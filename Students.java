import java.util.ArrayList; 

public class Students{

    ArrayList<Student> students;

    public Students(ArrayList<Student> x){
        students = x;
    }

    public void addStudent(String id){
        Student x = new Student(id);
        students.add(x);
    }

    public void addStudent(String id, String name){
        Student x = new Student(name, id);
        students.add(x);
    }

    public void removeStudents(int index){
        students.remove(index);
    }

    public int findIndex(String id){
        for (int i = 0; i < students.size(); i++){
            if (students.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    public void accessScanner(int index){
        students.get(index).takeScan();
    }

}