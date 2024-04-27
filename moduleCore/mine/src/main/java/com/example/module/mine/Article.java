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
}
