import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class Log {

    public static String PATH = "/Users/xujin/Desktop/log/";
    public static void main(String args[]){
        File file = Log.createFile();
    }
    public static File createFile() {
        String newPath = PATH + File.pathSeparator + "Qzone_" + System.currentTimeMillis();
        File f = new File(newPath);
            try {
                f.createNewFile();// 不存在则创建
            } catch (IOException e) {
                e.printStackTrace();
            }

        return f;
    }
}