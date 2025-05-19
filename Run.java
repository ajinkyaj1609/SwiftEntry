import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;

//just used to write code, move all this to the main branch eventually 

public class Run {

    private LocalTime startTime = LocalTime.of(12,20,0); //starttime - 1 min so that it actually starts at starttime
    private LocalTime endTime = LocalTime.of(14, 19, 9); //starttime + 1 min so that it doesn't end before endtime
    //no constructor, no private variables, just runs the program that takes in an id
    private Scanner input = new Scanner(System.in);

    public void run(){
        Students list = new Students(new ArrayList<Student>());
        list.addStudent(123);

        //while loop
        while(LocalTime.now().isBefore(endTime) && LocalTime.now().isAfter(startTime)){
            System.out.println("Type in student's id for scanning");
            int id = input.nextInt();
            int index = list.findIndex(id); 
            list.accessScanner(index);

        }

    }

    //after you run, at the end of the day, the program has a method that checks check ins and check outs to figure out how much time out of class 
    //it saves this daily information(time out of class, daily scans) about the student to a text file

}
