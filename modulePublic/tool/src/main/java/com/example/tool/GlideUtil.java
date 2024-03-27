package com.example.tool;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tool.R;

/**
 * @ClassName GlideUtil
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-23
 * @Version 1.0
 */

public class GlideUtil {
    static public void loadImage(Context context, ImageView imageView, String url){
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_photo)
                .error(R.drawable.default_photo)
                .fallback(R.drawable.default_photo)
                .into(imageView);
    }
}