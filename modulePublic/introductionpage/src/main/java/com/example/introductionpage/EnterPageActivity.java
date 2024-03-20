package com.example.introductionpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

@Route(path = "/introductionpage/EnterPageActivity")
public class EnterPageActivity extends AppCompatActivity implements View.OnClickListener {

    Button enter_skip;
    CountDownTimer countDownTimer;
    //Button enter_login;
    //ImageView imageView;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_enter_page);

        enter_skip = (Button) findViewById(R.id.enter_skip);

        initGif();

        enter_skip.setOnClickListener(this);

        initButton();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.enter_skip){
            countDownTimer.cancel();
            ARouter.getInstance()
                    .build("/app/MainActivity")
                    .withTransition(R.transition.explode,R.transition.fade)
                    .navigation();
            finish();
        }
    }

    public void initGif(){
        layout = (ConstraintLayout) findViewById(R.id.enter_background);
        Glide.with(this)
                .asGif()
                .load(R.drawable.enter_background)
                .into(new CustomTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        layout.setBackground(resource);
                        resource.start();
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    public void initButton(){
        countDownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                enter_skip.setText("跳过 " + (l / 1000 + 1));
            }

            @Override
            public void onFinish() {
                enter_skip.setText("跳过");
                ARouter.getInstance()
                        .build("/app/MainActivity")
                        .withTransition(R.transition.explode,R.transition.fade)
                        .navigation();
                finish();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Enter", "onDestroy: ");
    }
}