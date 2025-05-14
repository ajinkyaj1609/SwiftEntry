import java.time.LocalTime;
import java.time.LocalDate;

public class Scan {
    
    private int id; 
    private LocalTime time;
    private LocalDate date;
    
    public Scan(int id){
        this.id = id;
        time = LocalTime.now();
        date = LocalDate.now();
    }

}
