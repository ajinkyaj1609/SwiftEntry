public class Teacher{
    private static String password;

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