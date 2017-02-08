package cn.zingfront.creativemonitor.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by johnny on 16/10/13.
 */

public class BaseAdInfo {

    @SerializedName("src")
    public String sourcePackage;
    @SerializedName("title")
    public String title;
    @SerializedName("logo")
    public String logoUrl;
    @SerializedName("desc")
    public String description;
    @SerializedName("images")
    public ArrayList<String> imageUrlList;
    @SerializedName("v_post")
    public String videoPostImageUrl;
    @SerializedName("video")
    public String videoUrl;
    @SerializedName("stars")
    public int recommendStars;
    @SerializedName("action_btn")
    public String actionButtonText;
    @SerializedName("landing")
    public String landingPageUrl;
    @SerializedName("player_count")
    public String playerCount;
    @SerializedName("app_download_url")
    public String appDownloadUrl;
    @SerializedName("ads_type")
    public String adsType;
    @SerializedName("account")
    public String account;

    @SerializedName("previewurl")
    public String previewUrl;

    @SerializedName("store_id")
    public String store_id;

    @SerializedName("app_name")
    public String app_name;

    @SerializedName("video_id")
    public String video_id;


    public String toJson() {
        final Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static BaseAdInfo fromJson(String jsonData) {
        final Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonData, BaseAdInfo.class);
    }

    @Override
    public String toString() {
        return "BaseAdInfo{" +
                "sourcePackage='" + sourcePackage + '\'' +
                ", title='" + title + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", description='" + description + '\'' +
                ", imageUrlList=" + imageUrlList +
                ", videoPostImageUrl='" + videoPostImageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", recommendStars=" + recommendStars +
                ", actionButtonText='" + actionButtonText + '\'' +
                ", landingPageUrl='" + landingPageUrl + '\'' +
                ", playerCount='" + playerCount + '\'' +
                ", appDownloadUrl='" + appDownloadUrl + '\'' +
                ", adsType='" + adsType + '\'' +
                ", account='" + account + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", store_id='" + store_id + '\'' +
                ", app_name='" + app_name + '\'' +
                ", video_id='" + video_id + '\'' +
                '}';
    }

    // main for test
    public static void main(String[] args) {
        BaseAdInfo srcAdInfo = new BaseAdInfo();
        srcAdInfo.sourcePackage = "com.qzone";
        srcAdInfo.title = "罗马帝国时代HD";
        srcAdInfo.logoUrl = "http://host/logo.png";
        srcAdInfo.description = "这款游戏太烧脑!";
        srcAdInfo.imageUrlList = new ArrayList<String>(
                Arrays.asList("http://host/image1.png", "http://host/image1.png"));
        srcAdInfo.videoPostImageUrl = "http://host/post.png";
        srcAdInfo.videoUrl = "http://host/video.mp4";
        srcAdInfo.recommendStars = 450;
        srcAdInfo.actionButtonText = "Download";
        srcAdInfo.landingPageUrl = "http://host/landing.html";
        srcAdInfo.playerCount = "1000万人正在玩";
        srcAdInfo.appDownloadUrl = "http://www.baidu.com/youxi";
        srcAdInfo.adsType = "qzone";

        final String jsonStr = srcAdInfo.toJson();
        System.out.println("json: " + jsonStr);

        BaseAdInfo destAdInfo = BaseAdInfo.fromJson(jsonStr);
        System.out.println("obj: ");
        for (Field field : destAdInfo.getClass().getDeclaredFields()) {
            try {
                System.out.println(field.getName() + ": " + field.get(destAdInfo));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
