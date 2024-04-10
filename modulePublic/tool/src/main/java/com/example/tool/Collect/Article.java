package com.example.tool.Collect;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}