package cn.zingfront.creativemonitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ylj on 16/10/31.
 */

public class Log {

    public static String PATH = "";
    public static File createFile() {
        String newPath = PATH  + "Qzone_" + System.currentTimeMillis();
        File f = new File(newPath);
        try {
            f.createNewFile();// 不存在则创建
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }

    public static void contentToTxt(File f, String content) {
        String str = new String();
        str += content;
        BufferedWriter output;
        try {
            output = new BufferedWriter(new FileWriter(f));
            output.write(str);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
