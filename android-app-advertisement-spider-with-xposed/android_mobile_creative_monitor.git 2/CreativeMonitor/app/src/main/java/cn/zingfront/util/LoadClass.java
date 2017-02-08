package cn.zingfront.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by yuhaiqiang on 16/11/21.
 */

public class LoadClass {


    public LoadClass(){

    }


    public static void main(String[] args){
        File file = new File("list.txt");

        if(file.exists()){
            System.out.println("ls");
            try{
                file.createNewFile();
                //System.out.print(file.getAbsolutePath());
                System.out.println(file.getAbsolutePath());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.print("cd");
        }
        //load(file);
    }



    public static String[] load(String fileName){
        try{
            File file = new File(fileName);
            //Resources.getSystem().getAssets()

            // FileReader fileReader = new FileReader( file);

            //Resources.getSystem().getAssets().
            //XposedBridge.log("打印：");
            //XposedBridge.log("资源位置：");
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

            //BufferedReader reader = new BufferedReader(new InputStreamReader(Resources.getSystem().getAssets().open(fileName)));
            //Resource resource =  new Resource();
            //BufferedReader reader = resource.getBuffer(fileName);
            String line = "";
            List<String> list = new ArrayList<String>(200);
            while((line = reader.readLine()) != null){
                line = line.substring(line.indexOf("jar/")+4);
                if(line.indexOf(".class")>-1) {
                    line = line.substring(0, line.indexOf(".class"));
                    line = line.replace(File.separator, ".");
                    list.add(line);
                    //System.out.println(line);

                }
            }
            String[] result = list.toArray(new String[list.size()]);
            return  result;
        }
        catch (IOException e){
            e.printStackTrace();
            //XposedBridge.log(e.getLocalizedMessage());
            //XposedBridge.log("异常："+e.getMessage());
        }

        return null;
    }

    public static String[] loadByPackage(Result results,String[] prePackage){
        String[] result = results.getClazzs();
        XposedBridge.log(String.valueOf(result.length));
        List<String> targetList = new ArrayList<>(200);
        if(result != null){
            for(String str:result){
                if(prePackage==null){
                    return result;
                }
                if(str.indexOf(prePackage[0])>-1){
                    boolean success = true;
                    for(int i = 1;i<prePackage.length;i++){
                        if(str.indexOf(prePackage[i])>-1){
                            success = false;
                        }
                    }

                    if(success){
                        targetList.add(str);
                    }
                }
            }
            String[] targetArray = targetList.toArray(new String[targetList.size()]);
            return targetArray;
        }else{

            return null;
        }

    }

    public static void write(String[] clazzs){
        File file = new File("Result.java");
        if(!file.exists()){
            try {
                file.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                StringBuffer result = new StringBuffer();
                result.append("\r\n public class Result{ " +
                        "public String[] getClazzs(){\n" +
                        "   return this.clazz;\n" +
                        "  }  " +
                        "public static String[] clazz = {");
                for(String c: clazzs){
                    result.append("\""+c+"\"\r\n"+",");
                }
                result.deleteCharAt(result.length()-1);
                result.append("};}");
                writer.write(result.toString());
                writer.close();
            }
            catch(Exception e){

                e.printStackTrace();
            }
        }

    }

}
class Resource extends Activity{

    public   BufferedReader getBuffer(String fileName){
        try{

            //InputStream inputStream =
            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().getAssets().open(fileName)));
            return reader;
        }catch (Exception e){
            e.printStackTrace();
            //XposedBridge.
            //XposedHelpers.assetAsByteArray()
        }
        return null;
    }

    public void inputstreamtofile(InputStream ins,File file){
        try{
            if(!file.exists())
            {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}