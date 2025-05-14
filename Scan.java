import java.time.LocalTime;
import java.time.LocalDate;

public class Scan {
    
    private int id; 
    private LocalTime time;
    private LocalDate date;
    private String checkType = null;
    
    public Scan(int id){
        this.id = id;
        time = LocalTime.now();
        date = LocalDate.now();
    }

    public int getId(){
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

}
