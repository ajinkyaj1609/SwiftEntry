public class Teacher{
    private String password;

    public Teacher(){
        password = "000000";
    }

    public Teacher(String p){
        password = p;
    }
    
    public static String getPassword(){
        return password;
    }

}