import java.util.ArrayList; 
import java.time.LocalTime;
import java.time.ZoneId; 
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.*;

public class Scans {
    //these are an individual student's scans, not scans overall 
    ArrayList<Scan> scans = new ArrayList<>();
    private String id;
    private int timeSpentOutOfClass = 0;
    private boolean late = false;

    public Scans(String id){
        this.id = id;
    }

    public void Scan(){
        if (scans.size() == 0){
            Scan x = new Scan(id);
            x.setCheckType("First check-in"); //first check in, scan size will be 1 
            scans.add(x);
        }
        else if (scans.size() % 2 == 0){
            Scan x = new Scan(id);
            x.setCheckType("Check-in");
            scans.add(x);
            LocalTime prevTime = scans.get(scans.size() - 2).getTime();
            LocalTime nowTime = scans.get(scans.size() - 1).getTime();
            Duration timeBetween = Duration.between(prevTime, nowTime);
            int numHours = (int) timeBetween.toHours();
            int numMinutes = (int) timeBetween.toMinutes();
            int minutesOut = (numHours * 60) + numMinutes;
            timeSpentOutOfClass += minutesOut;
            //check ins
        }
        else{
            Scan x = new Scan(id);
            x.setCheckType("Check-out");
            scans.add(x);
            //check outs
        }
        System.out.println("Scanned successfully");
        System.out.println(scans.get(scans.size()-1).getId());
        System.out.println(scans.get(scans.size()-1).getTime());
        System.out.println(scans.get(scans.size()-1).getDate());
        System.out.println(scans.get(scans.size()-1).getCheckType());
    }
                                
    public int returnTimeOut(){
        return timeSpentOutOfClass;
    }
}
