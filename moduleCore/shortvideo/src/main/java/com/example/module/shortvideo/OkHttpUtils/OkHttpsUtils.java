package com.example.module.shortvideo.OkHttpUtils;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @ClassName OkHttpsUtils
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-29
 * @Version 1.0
 */

public class OkHttpsUtils {
    public static void sendApplyLoginVideoRequest(String url,String email ,okhttp3.Callback callback) {
        OkHttpClient client  = new OkHttpClient();
        Log.d("okhttp", "sendApplyLoginVideoRequest: " + url);
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        builder.addQueryParameter("email",email);
        String newUrl = builder.build().toString();

        Request request = new Request.Builder()
                .url(newUrl)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void sendApplyUnLoginVideoRequest(String url,okhttp3.Callback callback) {
        OkHttpClient client  = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void sendApplyCommentRequire(String url,String video_id,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("vedio_id",video_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendCommentRequire(String url,String video_id,String email,String content,String time,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("vedioId",video_id)
                .add("email",email)
                .add("content",content)
                .add("time",time)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendCollectVideo(String url,String email,String video_id,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("vedioId",video_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendCancelCollectVideo(String url,String email,String video_id,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("vedioId",video_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendLikeVideo(String url,String email,String video_id,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("vedioId",video_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendDislikeVideo(String url,String email,String video_id,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("vedioId",video_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
