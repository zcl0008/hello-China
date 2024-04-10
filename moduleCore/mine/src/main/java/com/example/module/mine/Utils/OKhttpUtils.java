package com.example.module.mine.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @ClassName OKhttpUtils
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-21
 * @Version 1.0
 */

public class OKhttpUtils {
    public static void sendUpLoadPhoto(String url,String email,File file,okhttp3.Callback callback){

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("iconFile","uploadFile",
                        RequestBody.create(MediaType.parse("*/*"),file))
                .addFormDataPart("email",email)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}