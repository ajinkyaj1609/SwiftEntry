public class Teacher{
    private String password;

    public Teacher(){
        password = "000000";
    }

    public Teacher(String p){
        password = p;
    }

    public String getPassword(){
        return password;
    }

}