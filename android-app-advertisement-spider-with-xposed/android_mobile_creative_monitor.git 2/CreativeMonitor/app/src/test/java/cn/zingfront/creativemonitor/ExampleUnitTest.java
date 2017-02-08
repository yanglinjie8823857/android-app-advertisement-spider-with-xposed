package cn.zingfront.creativemonitor;

import android.view.MotionEvent;

import org.junit.Test;
import java.io.File;
import java.lang.reflect.Method;

import cn.zingfront.util.LoadClass;
import static org.junit.Assert.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import java.lang.reflect.Method;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public  void testLoadClass(){


        File file = new File("list.txt");
        if(!file.exists()){
            System.out.println("ls");
            try{
                file.createNewFile();
                //System.out.print(file.getAbsolutePath());
                System.out.println(file.getAbsolutePath());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("cd");
        }
        //String[] result = LoadClass.loadByPackage("com.baidu.tieba");
        ///System.out.println(result.length);
    }

    @Test
    public void test(){
        String[] result = LoadClass.load("allyoutubelist.txt");
        LoadClass.write(result);
    }

    @Test
    public void testHook(){
        File file = new File("list.txt");
        //String[] clazzs = LoadClass.loadByPackage("com.baidu.tieba");
        String[] clazzs={};

        for(String clazz:clazzs){
            //Class c = loadPackageParam.classLoader.loadClass(str);

            try
            {
                Class c = this.getClass().getClassLoader().loadClass("cn.zingfront.util.LoadClass");
                Method[] methods = c.getDeclaredMethods();
                for(Method method:methods){
                    String methodName = method.getName();
                    System.out.println(method.getName());
                    Class[] params = method.getParameterTypes();
                    if(params != null){
                        for(Class paramClass: params){
                            System.out.println(paramClass.getName());
                        }
                        System.out.println();
                    }
                }
                break;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}