package cn.zingfront.creativemonitor.handlers;

import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.os.Message;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.zingfront.creativemonitor.Utils;
import cn.zingfront.creativemonitor.models.BaseAdInfo;
import cn.zingfront.creativemonitor.net.AdUploader;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by johnny on 16/10/10.
 */

public class QZoneHookHandler extends BaseHookHandler {


    private static String account;
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {





                findAndHookMethod("com.tencent.sc.activity.SplashActivity", loadPackageParam.classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        //XposedBridge.log("before SplashActivity onCreate");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (hasHook(param, loadPackageParam.packageName)) {
                            return;
                        }



                        findAndHookMethod("com.qzonex.component.wns.login.LoginManager", loadPackageParam.classLoader,
                                "login",loadPackageParam.classLoader.loadClass("com.qzonex.component.wns.login.LoginRequest")
                                ,new XC_MethodHook(){

                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        super.afterHookedMethod(param);
                                       /*
                                        if (hasHook(param, loadPackageParam.packageName)) {
                                            return;
                                        }
                                        */
                                        //XposedBridge.log("hook 登录,获取帐号");
                                        //String str = (LoginRequest)param.args[0];
                                        //Utils.printObjectFields(param.args[0]);
                                        QZoneHookHandler.account = (String)Utils.getFieldValue(param.args[0],"account");
                                        //XposedBridge.log("帐号："+ QZoneHookHandler.account);
                                        //Utils.printObjectFields(param.thisObject);
                                        //XposedBridge.log("hook  登录结束");
                                       /* findAndHookMethod("com.qzonex.component.wns.login.LoginManager",
                                                loadPackageParam.classLoader, "login", new XC_MethodHook() {
                                                    @Override
                                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                                                        Object req = param.thisObject;
                                                        Object result = param.getResult();
                                                        XposedBridge.log("请求");
                                                        Utils.printObjectFields(req);
                                                        XposedBridge.log("结果");
                                                        Utils.printObjectFields(result);
                                                    }
                                                });
                                        */
                                    }

                                });




                        findAndHookMethod(TelephonyManager.class.getName(),
                                loadPackageParam.classLoader, "getDeviceId", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("imei修改");
                                        //将返回得imei值设置为我想要的值
                                        //param.setResult("998998");
                                        long res = System.currentTimeMillis() % 10000000 + 1;
                                        param.setResult(String.valueOf(res));
                                        //XposedBridge.log("修改成功了！" + param.getResult());
                                        //XposedBridge.log("手机名称：" + Build.MODEL);

                                    }
                                });



                        findAndHookMethod(WifiInfo.class.getName(),
                                loadPackageParam.classLoader, "getMacAddress", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("mac修改");
                                        //将返回得imei值设置为我想要的值
                                        param.setResult("987123");
                                        //XposedBridge.log("修改成功了！" + param.getResult());
                                    }
                                });
                        findAndHookMethod(LocationManager.class.getName(),
                                loadPackageParam.classLoader, "getLastKnownLocation", String.class, new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("gps修改");
                                        //将返回得imei值设置为我想要的值
                                        param.setResult("ylj123");
                                        //XposedBridge.log("修改成功了！" + param.getResult());
                                    }
                                });
                        findAndHookMethod("com.qzone.module.feedcomponent.ui.FeedContentView",
                                loadPackageParam.classLoader, "a", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        Object obj = param.thisObject;
                                        Utils.printObjectFields(obj);
                                    }
                                });
                        findAndHookMethod("com.qzone.module.feedcomponent.ui.FeedContent",
                                loadPackageParam.classLoader, "a", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        Object obj = param.thisObject;
                                        //XposedBridge.log("!!!!!!!");
                                        //Utils.printObjectFields(obj);
                                    }
                                });
                        findAndHookMethod("com.qzone.proxy.feedcomponent.model.FeedPictureInfo",
                                loadPackageParam.classLoader, "getImageType", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        Object obj = param.thisObject;
                                        //Utils.printObjectFields(obj);
                                    }
                                });
                        findAndHookMethod("com.qzone.module.feedcomponent.ui.FeedSinglePicArea",
                                loadPackageParam.classLoader, "a", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("FeedSinglePicArea!!!:");
                                        Object obj = param.thisObject;
                                        //Utils.printObjectFields(obj);
                                    }
                                });
                        findAndHookMethod("com.qzone.module.feedcomponent.ui.AvatarArea",
                                loadPackageParam.classLoader, "a", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("ylj!!!!avatar");
                                        Object obj = param.thisObject;
                                        //Utils.printObjectFields(obj);
                                    }
                                });
                        findAndHookMethod("com.qzone.module.feedcomponent.ui.LeftThumbView",
                                loadPackageParam.classLoader, "a", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("ylj!!!!leftthumbview");
                                        Object obj = param.thisObject;
                                        //Utils.printObjectFields(obj);
                                    }
                                });
                        findAndHookMethod("com.qzone.proxy.feedcomponent.model.PictureUrl",
                                loadPackageParam.classLoader, "toString", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("pictureUrl____________________:");
                                        Object obj = param.thisObject;
                                        //Utils.printObjectFields(obj);
                                    }
                                });
                        findAndHookMethod("com.qzone.proxy.feedcomponent.model.PictureUrl",
                                loadPackageParam.classLoader, "toString", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        //XposedBridge.log("picture____________________:");
                                        Object obj = param.thisObject;
                                        //Utils.printObjectFields(obj);
                                    }
                                });

                        findAndHookMethod("com.qzone.proxy.feedcomponent.model.BusinessFeedData",
                                loadPackageParam.classLoader, "getTitleInfoV2", new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        super.afterHookedMethod(param);
                                        //XposedBridge.log("after hook BusinessFeedData");
                                        Object obj = param.thisObject;

                                        Object targetObject = findAdsObject(obj);
                                        if (targetObject != null) {
                                            //Utils.printObjectFields(obj);
                                            Object cellOperationInfoValue = Utils.getFieldValue(obj, "cellOperationInfo");
                                            Object cellRecommActionValue = Utils.getFieldValue(obj, "cellRecommAction");
                                            Object sharedDataValue = Utils.getFieldValue(cellOperationInfoValue, "shareData");
                                            Object videoInfo = Utils.getFieldValue(obj, "cellVideoInfo");
                                            Object[] pics = (Object[]) Utils.getFieldValue(obj, "pics");

                                            //应用包名
                                            String appPackageName = (String) Utils.getFieldValue(cellOperationInfoValue, "appid");
                                            //应用名称
                                            String appName = (String) Utils.getFieldValue(sharedDataValue, "sTitle");
                                            //应用描述
                                            String appDescription = (String) Utils.getFieldValue(sharedDataValue, "sSummary");
                                            //应用图片url
                                            Map photoMap = (Map) Utils.getFieldValue(sharedDataValue, "mapPhotoUrl");
                                            Object picUrl = photoMap.get(5);
                                            String pictureUrl = (String) Utils.getFieldValue(picUrl, "url");
                                            //应用下载url
                                            String appUrl = (String) Utils.getFieldValue(cellOperationInfoValue, "qqUrl");
                                            //评价星星数（10为最高，即五颗星，9为四颗半）
                                            int starsCount = (Integer) Utils.getFieldValue(cellRecommActionValue, "rankCurrentWithHalfStar");
                                            //玩家数量
                                            String playersCount = (String) Utils.getFieldValue(cellRecommActionValue, "remark");
                                            //按钮文本
                                            String buttonText = (String) Utils.getFieldValue(cellRecommActionValue, "buttonText");
                                            //打包广告信息



                                            BaseAdInfo srcAdInfo = new BaseAdInfo();
                                            srcAdInfo.account = QZoneHookHandler.account;
                                            srcAdInfo.sourcePackage = appPackageName;
                                            srcAdInfo.title = appName;
                                            srcAdInfo.description = appDescription;
                                            srcAdInfo.imageUrlList = new ArrayList<String>(
                                                    Arrays.asList(pictureUrl));
                                            srcAdInfo.landingPageUrl = appUrl;
                                            srcAdInfo.recommendStars = starsCount;
                                            srcAdInfo.playerCount = playersCount;
                                            srcAdInfo.actionButtonText = buttonText;
                                            srcAdInfo.adsType = "Qzone";
                                            try {
                                                Object videoUrl1 = Utils.getFieldValue(videoInfo, "videoUrl");
                                                String videoUrl = (String) Utils.getFieldValue(videoUrl1, "url");

                                                Object previewUrl1 = Utils.getFieldValue(videoInfo, "bigUrl");
                                                String previewUrl = (String) Utils.getFieldValue(previewUrl1, "url");
                                                srcAdInfo.previewUrl = previewUrl;
                                                srcAdInfo.videoUrl = videoUrl;
                                            }catch(Exception e){
                                                e.printStackTrace();
                                            }
                                            List<BaseAdInfo> baseAdInfos = new ArrayList<BaseAdInfo>();
                                            baseAdInfos.add(srcAdInfo);
                                            XposedBridge.log(srcAdInfo.toString());


                                            AdUploader.getInstance().upload(baseAdInfos);

                                            //XposedBridge.log("value: 广告开始");
                                            /*
                                            XposedBridge.log("value: " + appPackageName);
                                            XposedBridge.log("value: " + appName);
                                            XposedBridge.log("value: " + appDescription);
                                            XposedBridge.log("value: " + pictureUrl);
                                            XposedBridge.log("value: " + appUrl);
                                            XposedBridge.log("value: " + starsCount);
                                            XposedBridge.log("value: " + playersCount);
                                            XposedBridge.log("value: " + buttonText);
                                            XposedBridge.log("value: 广告结束");*/
                                        }

                                    }

                                    @Override
                                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                        super.beforeHookedMethod(param);
                                        //XposedBridge.log("before hook BusinessFeedData");
                                    }
                                });

                    }
                });
    }

    public Object findAdsObject(Object obj) throws IllegalAccessException, NoSuchFieldException {
        Object object = null;
        Field field = obj.getClass().getDeclaredField("cellRecommHeader");
        field.setAccessible(true);
        Object value = field.get(obj);
        if (value != null) {
            String leftTitle = (String) Utils.getFieldValue(value, "leftTitle");
            if (leftTitle.equals("广告")) {
                object = obj;
            }
        }

        return object;
    }
}
