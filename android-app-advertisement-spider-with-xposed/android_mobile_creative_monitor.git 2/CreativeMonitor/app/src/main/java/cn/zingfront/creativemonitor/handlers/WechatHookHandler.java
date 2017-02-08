package cn.zingfront.creativemonitor.handlers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by johnny on 16/10/10.
 */

public class WechatHookHandler extends BaseHookHandler {

    Object childA = null;
    boolean intervalStarted = false;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        findAndHookMethod("com.tencent.mm.plugin.sns.ui.SnsTimeLineUI",
                loadPackageParam.classLoader, "a", boolean.class, boolean.class, String.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("after hook SnsTimeLineUI.a");
                        Object currentObj = param.thisObject;
                        for (Field field : currentObj.getClass().getDeclaredFields()) {
                            field.setAccessible(true);
                            Object value = field.get(currentObj);
                            if (field.getName().equals("hki")) {
                                XposedBridge.log("Get 'hki' ");
                                childA = value;
                                if (!intervalStarted) {
                                    intervalStarted = true;
                                    new CheckTimelineInterval().start();
                                }
                            }
                        }
                    }

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("before hook");
                    }
                });
    }
    class CheckTimelineInterval extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    dealWithA();
                    Thread.sleep(3000);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }
    private void dealWithA() throws Throwable{
        if (childA == null) {
            return;
        }
        for (Field field : childA.getClass().getDeclaredFields()) { //遍历属性
            field.setAccessible(true);
            Object value = field.get(childA);
            if (field.getName().equals("gWB")) {  //取得了gyO
                ViewGroup vg = (ListView)value;
                for (int i = 0; i < vg.getChildCount(); i++) {  //遍历这个ListView的每一个子View
                    View child = vg.getChildAt(i);
                    getAllTextViews(child);
                }
            }
        }
    }

    private void getAllTextViews(final View v) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                getAllTextViews(child);
            }
        } else if (v instanceof TextView) {
            dealWithTextView((TextView)v); //dealWithTextView(TextView tv)方法：打印TextView中的显示文本
        }
    }

    private void dealWithTextView(TextView v) {
        String text = ((TextView)v).getText().toString().trim().replaceAll("\n", " ");
        XposedBridge.log("content: " + text);
    }
}
