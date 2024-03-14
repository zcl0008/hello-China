package com.example.tool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class LoadingAnimation {
    ImageView imageView;
    TextView textView;
    ObjectAnimator animator1;
    ObjectAnimator animator2;
    ObjectAnimator animator3;


    public LoadingAnimation(ImageView imageView) {
        this.imageView = imageView;
        init();
    }
    public void init(){
        int color1 = Color.parseColor("#d81e06");
        int color2 = Color.parseColor("#f4ea2a");
        int color3 = Color.parseColor("#1296db");

        animator1 = ObjectAnimator.ofInt(imageView,"fillColor", color1,color2);
        animator1.setDuration(120);

        animator2 = ObjectAnimator.ofInt(imageView,"fillColor", color2,color3);
        animator2.setDuration(120);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int color = (int) valueAnimator.getAnimatedValue();
                imageView.setColorFilter(color);
            }
        });
        animator3 = ObjectAnimator.ofInt(imageView,"fillColor", color3,color1);
        animator3.setDuration(120);
        animator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int color = (int) valueAnimator.getAnimatedValue();
                imageView.setColorFilter(color);
            }
        });

        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator2.start();
            }
        });
        animator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator3.start();
            }
        });
        animator3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator1.start();
            }
        });

        animator1.start();
    }

    public void ReleaseAnimation(){
        animator1.cancel();
        animator2.cancel();
        animator3.cancel();
    }
}
