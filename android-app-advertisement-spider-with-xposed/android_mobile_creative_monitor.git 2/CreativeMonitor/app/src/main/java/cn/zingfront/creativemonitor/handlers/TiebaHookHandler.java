package cn.zingfront.creativemonitor.handlers;

import android.app.Application;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.zingfront.creativemonitor.Utils;
import cn.zingfront.creativemonitor.models.BaseAdInfo;
import cn.zingfront.creativemonitor.net.AdUploader;
import cn.zingfront.util.ClassUtil;
import cn.zingfront.util.LoadClass;
//import cn.zingfront.util.Result;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import java.io.File;
/**
 * Created by xujin on 16/10/27.
 */

public class TiebaHookHandler extends BaseHookHandler {




    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {



        //hookTest(new Result(),loadPackageParam,"com.baidu.tieba.tbadk.view");

        //loadPackageParam.classLoader.loadClass()
        final String[] forum_names = {"",""};
        try{


            findAndHookMethod("com.baidu.tbadk.core.view.ClickableHeaderImageView",
                    loadPackageParam.classLoader ,"setData",loadPackageParam.classLoader.loadClass("com.baidu.tbadk.core.data.bg")
                    , new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //XposedBridge.log("搜索开始啦");
                            //Utils.printObjectFields(param.args[0]);
                            forum_names[0] =  forum_names[1];
                            forum_names[1] = (String)Utils.getFieldValue(param.args[0] , "forum_name");
                            //XposedBridge.log("吧名：" + forum_names[1]);
                            //XposedBridge.log(param.args[0]);
                            //Utils.printObjectFields(param.args[1]);
                            //Utils.printObjectFields(param.thisObject);
                            //XposedBridge.log("搜索结束了");

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }



        findAndHookMethod("com.baidu.tieba.recapp.report.a",
                loadPackageParam.classLoader, "qt", String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        //XposedBridge.log("after hook ");
                        Object obj = param.thisObject;
                        //XposedBridge.log("check value");
                        //Utils.printObjectFields(obj);
                        /////////////////////
                        //ClassUtil.getClasses("com.baidu.")
                        //this.

                        /////////////////
                        findAndHookMethod("com.baidu.tieba.tbadkCore.data.p",
                                loadPackageParam.classLoader, "biV", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        Object obj = param.getResult();
                                        //Utils.printObjectFields(obj);
                                        //XposedBridge.log("广告开始啦");
                                        Object cellOperationInfoValue = Utils.getFieldValue(obj, "Pn");
                                        //Utils.printObjectFields(cellOperationInfoValue);

                                        String title = (String) Utils.getFieldValue(cellOperationInfoValue, "userName");

                                        String logoUrl = (String) Utils.getFieldValue(cellOperationInfoValue, "Pr");

                                        String contentText = (String) Utils.getFieldValue(cellOperationInfoValue, "Ps");

                                        String packageName = (String) Utils.getFieldValue(obj, "Pe");

                                        String appDownloadUrl = (String) Utils.getFieldValue(obj, "Pc");

                                        ArrayList imageList = null;
                                        if (Utils.getFieldValue(cellOperationInfoValue, "PA") != null) {
                                            imageList = (ArrayList) Utils.getFieldValue(cellOperationInfoValue, "PA");
                                        }
                                        if (Utils.getFieldValue(cellOperationInfoValue, "Pu") != null) {
                                            imageList.add(Utils.getFieldValue(cellOperationInfoValue, "Pu"));
                                        }
                                       // XposedBridge.log("请看广告内容：" + forum_names[0] + title + "!!!" + logoUrl + "!!!!" + contentText + "!!!" + packageName + "!!!!" + appDownloadUrl + "!!!");
                                        for (Object imageUrl : imageList) {
                                           // XposedBridge.log("imageUrl: !" + (String) imageUrl);
                                        }

                                        BaseAdInfo baseAdInfo = new BaseAdInfo();

                                        baseAdInfo.title = title;
                                        baseAdInfo.logoUrl = logoUrl;
                                        baseAdInfo.description = contentText;
                                        baseAdInfo.imageUrlList = imageList;
                                        baseAdInfo.sourcePackage = packageName;
                                        baseAdInfo.landingPageUrl = appDownloadUrl;
                                        baseAdInfo.adsType = "Tieba";
                                        baseAdInfo.account = forum_names[0];

                                        List<BaseAdInfo> baseAdInfos = new ArrayList<BaseAdInfo>();
                                        baseAdInfos.add(baseAdInfo);
                                        XposedBridge.log(baseAdInfo.toString());

                                        AdUploader.getInstance().upload(baseAdInfos);
                                    }
                                });
                    }

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        //XposedBridge.log("before hook");
                    }
                });
    }
}
