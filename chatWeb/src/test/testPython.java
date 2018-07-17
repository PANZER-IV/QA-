package test;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class testPython {

    public static void main(String[] args) {

        try {
            int number1 = 10;
            int number2 = 32;
            //D:\pycham\pre_deal\Src
            ProcessBuilder processBuilder = new ProcessBuilder("python", "D:/pycham/pre_deal/Src/.py");
            Process progress = processBuilder.start();

//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(progress.getInputStream()));
//            int result = new Integer(bufferedReader.readLine()).intValue();
//            System.out.println("value is : " + result);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}