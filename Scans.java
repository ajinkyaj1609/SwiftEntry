import java.time.LocalTime; 
import java.time.LocalDate; 
import java.util.ArrayList; 

public class Scans {
    //these are an individual student's scans, not scans overall 
    ArrayList<Scan> scans = new ArrayList<>();
    private int id;

    public Scans(int id){
        this.id = id;
    }

    public void Scan(){
        if (scans.size() == 0){
            Scan x = new Scan(id);
            x.setCheckType("Check-in"); //first check in, scan size will be 1 
            scans.add(x);
        }
        else if (scans.size() % 2 == 0){
            //check ins
        }
        else{
            //check outs
        }
    }

}
