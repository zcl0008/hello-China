package com.example.module.shortvideo.Entity;

/**
 * @ClassName Commit
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-23
 * @Version 1.0
 */

public class Comment {
    public String photo_url;
    public String name;
    public String text;
    public String time;

    public Comment() {
    }

    public Comment(String photo_url, String name, String text, String time) {
        this.photo_url = photo_url;
        this.name = name;
        this.text = text;
        this.time = time;
    }
}