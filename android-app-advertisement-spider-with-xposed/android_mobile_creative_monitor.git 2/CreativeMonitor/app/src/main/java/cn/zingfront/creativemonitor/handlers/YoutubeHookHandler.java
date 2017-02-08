package cn.zingfront.creativemonitor.handlers;

import cn.zingfront.util.AllYouTuOAfterResult;
import cn.zingfront.util.YouTuResult;
import cn.zingfront.util.YoutubeResult;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import android.app.Application;
import android.graphics.YuvImage;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
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
/**
 * Created by yuhaiqiang on 16/12/8.
 */

public class YoutubeHookHandler  extends BaseHookHandler {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {

        XposedBridge.log("hook youtube");
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.apps.youtube.app.ui"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.apps.youtube.app.common"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.youtube.api"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.libraries.youtube.ads.player.ui"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.libraries.youtube"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.libraries"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.libraries"});

        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.apps"});

        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.gms.ads"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.gms"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.vr"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"clq"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"xag"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"xia"});/**可以获取视频id,文案，第一张图片*/
        //ßhookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"rfi"});  /*获取视频URL**/
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam, new String[]{"o"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam, new String[]{"ywo"});
         //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"uwk"});
         // hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"xan"});

        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.gms.ad"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"b"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"c"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"e"});
        //hookTest(new YoutuResult(),loadPackageParam,new String[]{"e"});
        //hookTest(new YoutuResult(),loadPackageParam,new String[]{"f"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"hsk"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"hsf"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iva"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iuu"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"htp"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"htt"});

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iun"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"omf"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iuu"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iun"});

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iux"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"zof"});


        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"hqt"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"hur"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"hvb"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"itl"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"itl"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ium"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"jdw"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"kwt"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"kvt"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"kww"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"kzk"}); 9 找到了video_id,appid共存的地方
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"fns"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"fnq"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"fcf"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"enw"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"enr"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"dlo"});

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"enx"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"dlp"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"bwv"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"dfx"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"fdv"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"gjv"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"gny"});
       //hookTest(new YouTuResult(),loadPackageParam,new String[]{"odb"});

        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"stv"});

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ckj"});
        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"com.google.android.apps.youtube.app.common.ads"});


        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"VideoInfoFragment"});



        //hookTest(new YoutubeResult(),loadPackageParam,new String[]{"dlo"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"kzu"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lfa"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lib"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lic"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llj"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"osy"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"pbt"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"pey"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"omf"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lmd"});

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ejs"}); 0
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"om"});
        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"omd"});

        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"ooq"});

        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"opk"});0
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lfy"});  2

        //hookTest(new AllYouTuOAfterResult(),loadPackageParam,new String[]{"wld"}); //每进入一个视频都会触发
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lmf"}); 0
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lmd"});

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"hso"}); 0

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iog"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"iok"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ium"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lga"});0
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llj"});0
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lgp"});0

        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"eii"});
        //Annotation a = YoutubeResult.class.getAnnotation();


        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"h"});
        //hookTest(new YouTusult(),loadPackageParam,new String[]{"i"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"htt"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"htp"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"f"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"eyg"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ezk"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"fcf"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"fnq"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"fns"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"eoa"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"ejs"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ejv"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llq"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llg"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llk"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llo"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llm"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"kzk"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ldu"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"csa"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"kvt"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"las"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lat"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lbn"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"lbu"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lft"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lhk"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"lik"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"llj"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"llm"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"llz"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"lmb"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"kwd"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"omb"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"ome"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"omf"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"qpk"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"sbq"});
        //hookTest(new YouTuResult(),loadPackageParam,new String[]{"shb"});
        hookTest(new YouTuResult(),loadPackageParam,new String[]{"kxo"});


        try{
            findAndHookMethod("kwd", loadPackageParam.classLoader, "a", loadPackageParam.classLoader.loadClass("kwo"),
                    java.util.Map.class,
                    loadPackageParam.classLoader.loadClass("muq"),
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                           //Utilsparam.args[0];
                            //android.net.Uri uri ;

                            Object object = param.args[1];
                            java.util.Map map = (java.util.Map)param.args[1];
                            String string = (String)map.entrySet().toArray()[0];
                            Object targetOpk =  map.get(string);
                            Object gObject = Utils.getFieldValue(Utils.getFieldValue(targetOpk,"a"),"g");
                            Utils.setValue(gObject,"a","oSh25PUh5gw");
                            map.clear();
                            map.put("oSh25PUh5gw",targetOpk);
                            param.args[1] = map;
                            //Utils.setValue(object,"c",Uri.parse("https://www.youtube.com/watch?v=j0gc_cf-VxE"));
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }



        //String[]  clazz = YoutubeResult.clazz;
        //for()

        class Ad{
            String appName;
            String videoID;
            String app_id;

            @Override
            public String toString() {
                return "Ad{" +
                        "appName='" + appName + '\'' +
                        ", videoID='" + videoID + '\'' +
                        ", app_id='" + app_id + '\'' +
                        '}';
            }

            boolean isAd = false;
            public void setNull(){
                this.videoID = null;
                this.app_id = null;
                this.appName = null;
            }
            public void setAppName(String appName) {

                    this.appName = new String(appName);
                //XposedBridge.log("what");
            }

            public void setVideoID(String videoID) {
                //setNull();
                this.videoID = new String(videoID);
                if(this.appName!=null&&this.app_id!=null){
                    //XposedBridge.log(this.toString());
                    //setNull();
                }
            }

            public void setApp_id(String app_id) {
                this.app_id = new String(app_id);
                if(this.appName!=null&&this.videoID!=null){
                    //XposedBridge.log(this.appName+":"+this.app_id+":"+this.videoID);

                    //XposedBridge.log(this.toString());
                    BaseAdInfo info = new BaseAdInfo();
                    info.app_name = this.appName;
                    info.video_id = this.videoID;
                    info.store_id = this.app_id;
                    info.adsType = "youtube";
                    List<BaseAdInfo> baseAdInfos = new ArrayList<BaseAdInfo>();
                    baseAdInfos.add(info);
                    //XposedBridge.log(info.toString());


                    AdUploader.getInstance().upload(baseAdInfos);
                }else{
                }
                setNull();
            }
        }
        final Ad ad = new Ad();

        //拦截APP名称,成功

        try{

            findAndHookMethod("ywo", loadPackageParam.classLoader, "a", loadPackageParam.classLoader.loadClass("ywx"),
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if(param.args[0].getClass().equals(loadPackageParam.classLoader.loadClass("vwd"))){
                                //XposedBridge.log("搜索：ywo"+"::"+"a"+"::"+"ywx");
                                //Utils.printObjectFields(param.args[0]);
                                String b = (String)Utils.getFieldValue(param.args[0], "a");
                                try{
                                    b = b.substring(b.indexOf("docid="));
                                    b = b.substring(b.indexOf("=")+1,b.indexOf("&"));
                                }
                               catch (Exception e){
                                   return;
                               }
                                //String[] ids = b.split("/");
                                ad.setVideoID(b);
                                //XposedBridge.log("视频ID"+ b);

                                //Utils.printObjectFields(param.thisObject);
                                //Utils.printObjectFields(param.args[0]);
                                //Utils.printObjectFields(a);
                                //XposedBridge.log("搜索：ywo"+"::"+"a"+"::"+"ywx"+ "结束");
                            }

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


        final String[] values = {"","",""};
        final String value[] ={""};
        try{
            findAndHookMethod("xan", loadPackageParam.classLoader, "a", loadPackageParam.classLoader.loadClass("ywo"),
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if(param.args[0].getClass().equals(loadPackageParam.classLoader.loadClass("ywo"))){
                                String install =(String) Utils.getFieldValue(param.thisObject, "a");
                                values[0] = values[1];
                                values[1] = values[2];
                                values[2] = install;
                                if(values[1].equals("FREE")&&values[2].equals("INSTALL")){
                                    value[0] = values[0];
                                    ad.setAppName(value[0]);
                                    //XposedBridge.log("AppName:"+value[0]);

                                    //Utils.printObjectFields(param.thisObject);
                                    //Utils.printObjectFields(param.args[0]);
                                    //XposedBridge.log("AppName结束");
                                }

                            }


                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            findAndHookMethod("uwk", loadPackageParam.classLoader, "a", loadPackageParam.classLoader.loadClass("ywo"),
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if(param.args[0].getClass().equals(loadPackageParam.classLoader.loadClass("ywo"))){
                                Object o = Utils.getFieldValue(param.thisObject, "o");
                                if(o==null)
                                    return;
                                String packageName = (String)Utils.getFieldValue(o, "a");
                                //Utils.printObjectFields(param.thisObject);
                                //Utils.printObjectFields(param.args[0]);
                                ad.setApp_id(packageName);
                                XposedBridge.log("包名："+packageName);

                            }


                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
/*
        try{
            findAndHookMethod("ywu", loadPackageParam.classLoader, "a", loadPackageParam.classLoader.loadClass("ywu"),loadPackageParam.classLoader.loadClass("ywu"),
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //Utils.printObjectFields(param.args[0]);
                            //Utils.printObjectFields(param.args[1]);
                            //Utils.printObjectFields(param.thisObject);

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            findAndHookMethod("opn", loadPackageParam.classLoader, "a", Integer.class,loadPackageParam.classLoader.loadClass("android.os.Parcel"),
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //Utils.printObjectFields(param.args[0]);
                            //Utils.printObjectFields(param.args[1]);
                            //Utils.printObjectFields(param.thisObject);

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

*/
        /*findAndHookMethod("defpackage.cfw", loadPackageParam.classLoader, "cfw",int.class,boolean.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("hook cfw");
            }
        });*/
    }
}
