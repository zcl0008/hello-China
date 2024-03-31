package com.example.module.shortvideo.OkHttpUtils;

import okhttp3.FormBody;
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
    public static void sendApplyVideoRequest(String url, okhttp3.Callback callback) {
        OkHttpClient client  = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
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

    public static void sendCommentRequire(String url,String video_id,String photo_url,String name,String content,String time,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("vedio_id",video_id)
                .add("photo_url",photo_url)
                .add("name",name)
                .add("content",content)
                .add("time",time)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
