package cn.zingfront.creativemonitor.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.UUID;

import cn.zingfront.creativemonitor.Utils;
import cn.zingfront.util.LoadClass;
import cn.zingfront.util.Result;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by johnny on 16/10/10.
 */

public abstract class BaseHookHandler {

    Context appContext = null;
    String version = null;

    public abstract void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam);

    protected boolean hasHook(XC_MethodHook.MethodHookParam param, String pkg) {
        if (appContext != null) {
            return true;
        }

        XposedBridge.log("ready to hook");
        appContext = ((Activity)param.thisObject).getApplicationContext();
        PackageInfo pInfo = null;
        try {
            pInfo = appContext.getPackageManager().getPackageInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pInfo != null) {
            version = pInfo.versionName;
        }
        XposedBridge.log(pkg + " version: " + version);

        return false;
    }

    public String getArray(String[] arrays){
        String res = "";
        if(arrays!=null){
            for(String str:arrays){
                res += str;
            }
        }
        return res;

    }

    /**
     * hook测试某一个包，或者一个类
     * @param loadPackageParam
     * @param classPrefix  类的前缀，例如com.baidu.tieba.a.b（hook 类） com.baidu.tieba.a (hook 这个包下的所有类的所有方法，非构造方法)
     * @param xc
     */
    protected void hookTest(Result result,XC_LoadPackage.LoadPackageParam loadPackageParam,String[] classPrefix,XC_MethodHook... xc){

        final boolean isHook = true;

        String[] clazzs = LoadClass.loadByPackage(result,classPrefix);
        if(clazzs==null){

            XposedBridge.log("没有找到");
        }

        XposedBridge.log(getArray(classPrefix)+"长度：" + String.valueOf(clazzs.length));


        if(isHook == true){
            for( String clazz:clazzs){
                //Class c = loadPackageParam.classLoader.loadClass(str);
                final String clazzName = clazz;
                try
                {
                    Class c = loadPackageParam.classLoader.loadClass(clazzName);
                    Method[] methods = c.getDeclaredMethods();
                    for(Method method:methods){
                        final String methodName = method.getName();

                         Class[] params1 = method.getParameterTypes();
                        if(params1 == null){
                            params1 = new Class[0];
                        }
                        final Class[] params = params1;
                            //XposedBridge.log(String.valueOf(params.length));
                            if(xc != null && xc.length>0){
                                findAndHook(clazz, loadPackageParam.classLoader, methodName,params,xc[0]);
                            }
                            else{
                                //long timestamp = System.currentTimeMillis();
                                //String uuid = UUID.randomUUID().toString();
                                findAndHook(clazz, loadPackageParam.classLoader, methodName,params,new XC_MethodHook(){
                                    String uuid = UUID.randomUUID().toString();
                                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                        XposedBridge.log("搜索开始啦"+"ClassName:["+clazzName+"] MethodName:["+methodName+"]"+"["+uuid+"]");
                                        String str = "";
                                        for(int i = 0;i<params.length;i++){
                                            str +=":"+params[i].getName();
                                        }
                                        XposedBridge.log("参数："+params.length+"-----"+str);
                                        for(int i = 0;i< params.length;i++){

                                            Utils.printObjectFields(param.args[i]);
                                        }
                                        XposedBridge.log("参数结束"+clazzName+":"+methodName);
                                        if(param.getResult() != null){
                                            XposedBridge.log("返回值类型："+param.getResult().getClass()+" ---Value:");
                                            Utils.printObjectFields(param.getResult());
                                        }
                                        if(param.thisObject != null){
                                            XposedBridge.log("thisObject:"+param.thisObject.getClass());
                                        }
                                        Utils.printObjectFields(param.thisObject);

                                    }
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        XposedBridge.log("搜索结束了"+"ClassName:["+clazzName+"] MethodName:["+methodName+"]"+"["+uuid+"]");
                                    }

                                });
                            }

                    }
                /*
                Constructor[] constructors = c.getDeclaredConstructors();
                    for(Constructor constructor:constructors){
                        Class[] params1 = constructor.getParameterTypes();
                        if(params1==null){
                            params1 = new Class[0];
                        }
                        final Class[] params = params1;
                        if(xc != null && xc.length>0){
                            findAndHookCon(clazz, loadPackageParam.classLoader,params,xc[0]);
                        }
                        else{
                            findAndHookCon(clazz, loadPackageParam.classLoader,params,new XC_MethodHook(){
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    XposedBridge.log("搜索开始啦"+"ClassName:["+clazzName+"] MethodName:["+clazzName+"]");
                                    for(int i = 0;i< params.length;i++){
                                        Utils.printObjectFields(param.args[i]);
                                    }
                                    Utils.printObjectFields(param.thisObject);

                                    XposedBridge.log("搜索结束了");
                                }
                            });
                        }

                    }
                    */
                }catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

        }

    }

    private static void findAndHookCon(String className,ClassLoader classLoader, Object[] paramClass, XC_MethodHook xc){
        if(paramClass != null){
            switch (paramClass.length){

                case 0:
                    findAndHookConstructor(className,classLoader,xc);
                    break;
                case 1:
                    //XposedBridge.log("这是什么");
                    findAndHookConstructor(className, classLoader, paramClass[0], xc);
                    break;
                case 2:
                    findAndHookConstructor(className,classLoader,paramClass[0],paramClass[1],xc);
                    break;
                case 3:
                    findAndHookConstructor(className,classLoader,paramClass[0],paramClass[1],paramClass[2],xc);
                    break;
                case 4:
                    findAndHookConstructor(className,classLoader,paramClass[0],paramClass[1],paramClass[2],paramClass[3],xc);
                    break;
                case 5:
                    findAndHookConstructor(className,classLoader,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],xc);
                    break;
                case 6:
                    findAndHookConstructor(className,classLoader,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],paramClass[5],xc);
                    break;
                case 7:
                    findAndHookConstructor(className,classLoader,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],paramClass[5],paramClass[6],xc);
                    break;
                case 8:
                    findAndHookConstructor(className,classLoader,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],paramClass[5],paramClass[6],paramClass[7],xc);
                    break;
                default:
                    break;
            }

        }


    }


    private static void findAndHook(String className,ClassLoader classLoader,String methodName,Object[] paramClass,XC_MethodHook xc){

        if(paramClass != null){
            switch (paramClass.length){

                case 1:
                    //XposedBridge.log("这是什么");
                    findAndHookMethod(className, classLoader, methodName, paramClass[0], xc);
                    break;
                case 2:
                    findAndHookMethod(className,classLoader,methodName,paramClass[0],paramClass[1],xc);
                    break;
                case 3:
                    findAndHookMethod(className,classLoader,methodName,paramClass[0],paramClass[1],paramClass[2],xc);
                    break;
                case 4:
                    findAndHookMethod(className,classLoader,methodName,paramClass[0],paramClass[1],paramClass[2],paramClass[3],xc);
                    break;
                case 5:
                    findAndHookMethod(className,classLoader,methodName,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],xc);
                    break;
                case 6:
                    findAndHookMethod(className,classLoader,methodName,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],paramClass[5],xc);
                    break;
                case 7:
                    findAndHookMethod(className,classLoader,methodName,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],paramClass[5],paramClass[6],xc);
                    break;
                case 8:
                    findAndHookMethod(className,classLoader,methodName,paramClass[0],paramClass[1],paramClass[2],paramClass[3],paramClass[4],paramClass[5],paramClass[6],paramClass[7],xc);
                    break;
                default:
                    break;
            }

        }
    }

}
