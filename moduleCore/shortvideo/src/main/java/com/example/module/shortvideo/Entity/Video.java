package com.example.module.shortvideo.Entity;

/**
 * @ClassName Vedio
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-31
 * @Version 1.0
 */

public class Video {
    public String id;
    public String name;
    public String url;
    public String intro;
    public boolean isLike;
    public boolean isCollect;

    public Video() {
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", intro='" + intro + '\'' +
                ", isLike=" + isLike +
                ", isCollect=" + isCollect +
                '}';
    }
}