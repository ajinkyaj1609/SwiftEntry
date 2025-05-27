import java.time.Duration; 
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Scans {
    //these are an individual student's scans, not scans overall 
    ArrayList<Scan> scans = new ArrayList<>();
    private String id;
    //timestuff
    private String sT = "17:25:00"; //class startTime/endTime
    private DateTimeFormatter theFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private LocalTime startTime = LocalTime.parse(sT, theFormat);
    //stuff needed to be captured here and moved to teacher to avoid excess code 
    private int timeSpentOutOfClass = 0;
    private boolean late = false;

    public Scans(String id){
        this.id = id;
    }

    public String getStartTime(){

        return sT;
    }
    public String getEndTIme(){
        return sT; //assuming end time is same as start time for simplicity, can be changed later
    }

    public void Scan(){
        if (scans.size() == 0){
            Scan x = new Scan(id);
            x.setCheckType("First check-in"); //first check in, scan size will be 1 
            scans.add(x);
            if (scans.get(scans.size() - 1).getTime().isAfter(startTime)){
                late = true;
            }
        }
        else if (scans.size() % 2 == 0){
            Scan x = new Scan(id);
            x.setCheckType("Check-in");
            scans.add(x);
            LocalTime prevTime = scans.get(scans.size() - 2).getTime();
            LocalTime nowTime = scans.get(scans.size() - 1).getTime();
            Duration timeBetween = Duration.between(prevTime, nowTime);
            int minutesOut = (int) timeBetween.toMinutes();
            System.out.println(minutesOut);
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

    public boolean returnLate(){
        return late;
    }

    public int getScansSize(){ //used to check whether student was absent or not because if they didn't check in at all scans size == 0
        return scans.size();
    }
}
