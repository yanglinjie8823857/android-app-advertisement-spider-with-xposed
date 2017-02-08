package cn.zingfront.creativemonitor.handlers;


//import cn.zingfront.util.UCResult;
import cn.zingfront.util.UCCAfterResult;
import cn.zingfront.util.UCMobileResult;
import cn.zingfront.util.UCResult;
import cn.zingfront.util.UCTencentResult;
import cn.zingfront.util.UCWebResult;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by yuhaiqiang on 16/12/1.
 */

public class UCHookHandler  extends BaseHookHandler{


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        //hookTest(new UCWebResult(),loadPackageParam,"com.ucweb");
        //hookTest(new UCMobileResult(),loadPackageParam,"com.UCMobile");
        // hookTest(new UCResult(),loadPackageParam,"com.uc.application");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.application.search");
        //hookTest(new UCResult(),loadPackageParam,new String[]{"com.uc.base.util"});
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.e");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.service");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.b.c");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.b.d");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.b.e");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.a");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.wa");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.net");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.d");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.e");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.h");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.d");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.base.data.e");
        //hookTest(new UCCAfterResult(),loadPackageParam,"com.uc.framework.ui");
        //hookTest(new UCCAfterResult(),loadPackageParam,"com.uc.framework.animation");
        //hookTest(new UCTencentResult(),loadPackageParam,"com.tencent");
        //hookTest(new UCResult(),loadPackageParam,"com.uc.application.desktopwidget");
        hookTest(new UCResult(),loadPackageParam,new String[]{"com.uc.browser"});



    }
}
