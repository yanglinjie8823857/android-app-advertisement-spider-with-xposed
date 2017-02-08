package cn.zingfront.creativemonitor.handlers;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Field;

import cn.zingfront.creativemonitor.Utils;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by johnny on 16/10/14.
 */

public class ToutiaoHookHandler extends BaseHookHandler {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {

        XposedHelpers.findAndHookMethod("com.ss.android.article.news.activity.MainActivity",
                loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        if (hasHook(param, loadPackageParam.packageName)) {
                            return;
                        }

                        XposedHelpers.findAndHookMethod("com.ss.android.common.ui.view.InScrollViewPager",
                                loadPackageParam.classLoader, "onTouchEvent", MotionEvent.class, new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        super.afterHookedMethod(param);
                                        XposedBridge.log("InScrollViewPager.onTouchEvent");
                                    }
                                });
                        XposedHelpers.findAndHookMethod("com.ss.android.article.base.ui.DragSortGridView.j",
                                loadPackageParam.classLoader, "getItem", int.class, new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        super.afterHookedMethod(param);
                                        XposedBridge.log("check value !!!");
                                        Object obj = param.thisObject;
                                        //Object targetObj = findAdsObject(obj);
                                        Utils.printObjectFields(obj);
                                    }
                                });
//                        XposedHelpers.findAndHookMethod("com.ss.android.article.base.feature.detail2.h.a",
//                                loadPackageParam.classLoader, "O",  new XC_MethodHook() {
//                                    @Override
//                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                        super.afterHookedMethod(param);
//                                        Object obj = param.thisObject;
//                                        Utils.printObjectFields(obj);
//                                    }
//                                });
                    }
                });

//        XposedHelpers.findAndHookMethod("com.ss.android.concern.model.response.ConcernHomePageBrowResponse",
//                loadPackageParam.classLoader, "getMinCursor", new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        Object obj = param.thisObject;
//                        Utils.printObjectFields(obj);
//                    }
//                });
//        XposedHelpers.findAndHookConstructor("com.ss.android.concern.model.response.ConcernHomePageBrowResponse",
//                loadPackageParam.classLoader, Parcel.class, new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        Object obj = param.thisObject;
//                        Utils.printObjectFields(obj);
//                    }
//                });
    }
    public Object findAdsObject(Object obj) throws IllegalAccessException, NoSuchFieldException {
        Object object = null;
        Field field = obj.getClass().getDeclaredField("g");
        field.setAccessible(true);
        Object value = field.get(obj);

        return value;
    }
}
