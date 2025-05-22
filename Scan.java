import java.time.LocalTime;
import java.time.LocalDate;
import java.time.*;

public class Scan {
    
    private String id; 
    private LocalTime time;
    private LocalDate date;
    private String checkType = null;
    private ZoneId location = ZoneId.of("America/Los_Angeles");
    
    public Scan(String id){
        this.id = id;
        time = LocalTime.now(location);
        date = LocalDate.now(location);
    }

    public String getId(){
        return id;
    }

    public LocalTime getTime(){
        return time;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setCheckType(String x){
        checkType = x;
    }

    public String getCheckType(){
        return checkType;
    }

}
