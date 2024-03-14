package com.example.module.store.tool;

public class Product {
    private String name;
    private String url;
    private int picture;
    private String introduce;

    public Product(String name, String url, int picture, String introduce) {
        this.name = name;
        this.url = url;
        this.picture = picture;
        this.introduce = introduce;
    }

    public Product(String name, String url, int picture) {
        this.name = name;
        this.url = url;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
