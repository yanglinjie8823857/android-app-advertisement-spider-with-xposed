package cn.zingfront.creativemonitor;

import java.util.HashMap;

import cn.zingfront.creativemonitor.handlers.BaseHookHandler;
import cn.zingfront.creativemonitor.handlers.QZoneHookHandler;
import cn.zingfront.creativemonitor.handlers.TiebaHookHandler;
import cn.zingfront.creativemonitor.handlers.ToutiaoHookHandler;
import cn.zingfront.creativemonitor.handlers.UCHookHandler;
import cn.zingfront.creativemonitor.handlers.WechatHookHandler;
import cn.zingfront.creativemonitor.handlers.YoutubeHookHandler;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by johnny on 16/10/10.
 */

public class Main implements IXposedHookLoadPackage {

    private static final HashMap handlerMapping = new HashMap<String, Class>() {
        {
            put("com.qzone", QZoneHookHandler.class);
            put("com.tencent.mm", WechatHookHandler.class);
            put("com.ss.android.article.news", ToutiaoHookHandler.class);
            put("com.baidu.tieba", TiebaHookHandler.class);
            //put("com.UCMobile.x86", UCHookHandler.class);

            //put("com.UCMobile", UCHookHandler.class);
            put("com.google.android.youtube", YoutubeHookHandler.class);
            //put("com.google.android.gms", YoutubeHookHandler.class);
            //put("com.google.android.gm",YoutubeHookHandler.class);
            //put("com.google.android.googlequicksearchbox",YoutubeHookHandler.class);
            //put("com.android.defcontainer",YoutubeHookHandler.class);
            //put("com.google.android.youtube", YoutubeHookHandler.class);
            //put("com.google.android.youtube", YoutubeHookHandler.class);
            //put("com.google.android.youtube", YoutubeHookHandler.class);
            //put("com.google.android.youtube", YoutubeHookHandler.class);

        }
    };

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("creative monitor handleLoadPackage:" + loadPackageParam.packageName);
        Class handlerClass = (Class) handlerMapping.get(loadPackageParam.packageName);
        if (handlerClass != null) {
            BaseHookHandler handler = (BaseHookHandler) handlerClass.newInstance();
            XposedBridge.log("=================== " + loadPackageParam.packageName + " ===================");
            handler.handleLoadPackage(loadPackageParam);


        }
        return;
    }
}
