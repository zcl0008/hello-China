package com.example.module.mine;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Article {
    private String title;
    private String source;

    public Article(String title, String source) {
        this.title = title;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }
    public String getSource() {
        return source;
    }

    public RequestBody toRequestBody() {
        // 创建FormBody.Builder来构建请求体
        FormBody.Builder builder = new FormBody.Builder();
        // 将文章标题和来源添加到请求体中
        builder.add("title", title);
        builder.add("source", source);
        // 构建请求体并返回
        return builder.build();
    }
}
