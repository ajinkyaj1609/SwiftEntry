public class Teacher{
    private static String password;

    public Teacher(){
        password = "000000";
    }

    
    public Teacher(String p){
        password = p;
    }

    public boolean checkPassword(String p){
        if (p.equals(password)){
            return true;
        }
        else{
            return false;
        }
    }

    public String getPassword(){
        return password;
    }

}