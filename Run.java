import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Run {

    private LocalTime startTime = LocalTime.of(12,20,0); //starttime - 1 min so that it actually starts at starttime
    private LocalTime endTime = LocalTime.of(14, 19, 9); //starttime + 1 min so that it doesn't end before endtime
    //no constructor, no private variables, just runs the program that takes in an id
    private Scanner x = new Scanner(System.in);

    public void run(){
        //ArrayList x = new ArrayList<Student>; 
        //Students list = new Students(x);

        //while loop
        while(LocalTime.now().isBefore(endTime) && LocalTime.now().isAfter(startTime)){
            int num = x.nextInt();
            //for(int i = 0; i < )
        }

    }

    //after you run, at the end of the day, the program has a method that checks check ins and check outs to figure out how much time out of class 
    //it saves this daily information(time out of class, daily scans) about the student to a text file

}
