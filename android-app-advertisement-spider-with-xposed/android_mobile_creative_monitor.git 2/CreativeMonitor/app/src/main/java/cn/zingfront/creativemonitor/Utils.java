package cn.zingfront.creativemonitor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by johnny on 16/10/10.
 */

public class Utils {

    /**
     * 按属性名获取对象的属性值。
     *
     * @param obj 指定对象
     * @param fieldName 属性名
     * @return 属性名对应的值,若属性不存在,返回null
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        for (Field field: obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals(fieldName)) {
                Object value = null;
                try {
                    value = field.get(obj);
                    return value;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 通过指定owner对象的函数名为methodName,参数列表为args的函数。
     *
     * @param owner
     * @param methodName
     * @param args
     * @return 函数返回值
     * @throws Exception
     */
    public static Object invokeMethod(ClassLoader classLoader,Object owner, String methodName,Class[] argsClass, Object[] args) throws Exception {
        Class ownerClass = classLoader.loadClass(owner.getClass().getName());
       /* Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }*/
        Method method = ownerClass.getDeclaredMethod(methodName,argsClass);
        return method.invoke(owner, args);
    }

    /**
     * 使用XposedBridge.log打印任意Object的所有已声明属性名、属性值。
     *
     * @param obj
     */
    public static void printObjectFields(Object obj) {

        if(obj == null){
            return ;
        }
        XposedBridge.log("=====do print one object ClassName:["+obj.getClass()+"]");
        if(obj.getClass().isPrimitive()){

            XposedBridge.log("value:"+obj.toString());
            XposedBridge.log("==== done print one object ====");
            return;
        }

        if(obj.getClass().equals(String.class)){
            XposedBridge.log("value:"+obj.toString());
            XposedBridge.log("==== done print one object ====");
            return;
        }

        for (Field field: obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(obj);

                Class valueClass = null;
                if (value != null) {
                    valueClass = value.getClass();
                }
                XposedBridge.log("field: " + field.getName() +
                        ", value: <" +  valueClass + "> " + value);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        XposedBridge.log("==== done print one object ====");
    }

    public static void setValue(Object obj,String fieldName,Object value){
        try{
            Field field = obj.getClass().getField(fieldName);
            field.setAccessible(true);
            field.set(obj,value);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
