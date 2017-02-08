package cn.zingfront.creativemonitor.net;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cn.zingfront.creativemonitor.models.BaseAdInfo;
import de.robv.android.xposed.XposedBridge;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static okhttp3.MediaType.parse;

/**
 * Created by johnny on 16/10/13.
 */

public class AdUploader {
    public static final MediaType JSON = parse("application/json; charset=utf-8");
    private volatile static AdUploader uploader;

    private AdUploader() {
    }

    public static AdUploader getInstance() {
        if (uploader == null) {
            synchronized (AdUploader.class) {
                if (uploader == null) {
                    uploader = new AdUploader();
                }
            }
        }
        return uploader;
    }

    public void upload(List<BaseAdInfo> baseAdInfos) {
        Gson gson = new Gson();
        String adsArray = gson.toJson(baseAdInfos);

        FormBody formBody = null;
        try {
            formBody = new FormBody.Builder()
                    .add("ads", URLEncoder.encode(adsArray, "UTF-8"))
                    .add("client_ts=", String.valueOf(System.currentTimeMillis()))
                    .add("imei=", "imei")
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://60.205.141.163:33333/creative/receive")
                .post(formBody)
                .build();
        XposedBridge.log(String.valueOf(formBody));

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                XposedBridge.log("FAIL TO UPLAOD");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                XposedBridge.log("response:" + response.body().string());
            }
        });


    }

}
