package com.example.module.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment {

    private String articleUrl = "http://example.com/api/article";
    private String userToken = "your_user_token"; // 这里应该使用您实际的用户令牌

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        // 创建文章对象
        Article article = new Article("文章标题", "文章来源");

        // 发送文章到后台
        sendArticleToServer(article);

        return rootView;
    }

    private void sendArticleToServer(Article article) {
        OkHttpClient client = new OkHttpClient();

        // 构建请求体，将文章的标题和来源添加到请求中
        Request request = new Request.Builder()
                .url(articleUrl)
                .addHeader("Authorization", "Bearer " + userToken)
                .post(article.toRequestBody())
                .build();

        // 发送请求并设置回调
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 处理请求失败的情况
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 请求成功，可以在这里处理后台返回的数据
                    final String responseData = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 在UI线程更新UI
                            Toast.makeText(getActivity(), "文章发送成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // 请求失败，处理错误情况
                    final String error = response.message();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "发送文章失败: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}