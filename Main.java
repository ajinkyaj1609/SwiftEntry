import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
        public static void main(String[] args) {

                Student test = new Student("John Doe", 12345);
                System.out.println(test.toString());
                String data = "data.txt";
                System.out.println(data.getAbolutePath());
        }

      

}