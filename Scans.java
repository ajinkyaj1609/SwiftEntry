import java.util.ArrayList; 

public class Scans {
    //these are an individual student's scans, not scans overall 
    ArrayList<Scan> scans = new ArrayList<>();
    private String id;

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

}
