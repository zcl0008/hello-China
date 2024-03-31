package com.example.introductionpage;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

@Route(path = "/introductionpage/EnterPageActivity_Spring")
public class EnterPageActivity_Spring extends AppCompatActivity {

    ImageView gif;
    ImageView logo;
    ImageView bamboo_forest;
    ImageView panda;
    ImageView bamboo__;
    TextView title;

    Animator logo_animator1;
    Animator logo_animator2;
    Animator logo_animator3;
    Animator logo_scaleX;
    Animator logo_scaleY;
    Animator logo_alpha;
    Animator panda_animator;
    Animator panda_alpha;
    Animator bamboo_forest_animator;
    Animator forest_alpha;
    Animator bamboo_scaleX;
    Animator bamboo_scaleY;
    Animator bamboo_translation;
    AnimatorSet logo_animatorSet;
    AnimatorSet bamboo_animatorSet;

    CharSequence charSequence ;
    Handler handler = new Handler();
    int index;
    long delay = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_enter_page_spring);

        initWidget();
        setAnimation();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance()
                        .build("/app/MainActivity")
                        .withTransition(R.transition.explode,R.transition.fade)
                        .navigation();
            }
        }, 5000); // 延迟时间应该是以毫秒为单位，这里表示延迟4秒
    }

    public void initWidget(){
        gif = findViewById(R.id.gifImage);
        logo = findViewById(R.id.logo);
        bamboo_forest = findViewById(R.id.bamboo_group);
        panda = findViewById(R.id.panda);
        bamboo__ = findViewById(R.id.bamboo__);
        title = findViewById(R.id.title);

        Glide.with(this)
                .asGif()
                .load(R.drawable.bamboo)
                .into(gif);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/app/MainActivity")
                        .withTransition(R.transition.explode,R.transition.fade)
                        .navigation();
            }
        });

    }
    public void setAnimation(){
        logo_animatorSet = new AnimatorSet();
        bamboo_animatorSet = new AnimatorSet();

        logo_animator1 = ObjectAnimator.ofFloat(logo,"rotation",0,405);
        logo_animator2 = ObjectAnimator.ofFloat(logo,"rotation",45,-45);
        logo_animator3 = ObjectAnimator.ofFloat(logo,"rotation",-45,0);
        logo_scaleX = ObjectAnimator.ofFloat(logo,"scaleX",0,1);
        logo_scaleY = ObjectAnimator.ofFloat(logo,"scaleY",0,1);
        logo_alpha = ObjectAnimator.ofFloat(logo,"alpha",0.5f,1f);
        logo_animator1.setDuration(1750);
        logo_animator2.setDuration(600);
        logo_animator3.setDuration(600);
        logo_scaleX.setDuration(1750);
        logo_scaleY.setDuration(1750);
        logo_alpha.setDuration(1500);
        logo_animatorSet.play(logo_animator1)
                .with(logo_scaleX)
                .with(logo_scaleY)
                .with(logo_alpha)
                .before(logo_animator2)
                .before(logo_animator3);

        float panda_TranslationY = panda.getTranslationY();
        float forest_TranslationY = panda.getTranslationY();
        float bamboo_TranslationY = bamboo__.getTranslationY();
        float height = getResources().getDisplayMetrics().heightPixels;
        panda_animator = ObjectAnimator.ofFloat(panda,"translationY",height,panda_TranslationY);
        bamboo_forest_animator = ObjectAnimator.ofFloat(bamboo_forest,"translationY",height,forest_TranslationY);
        bamboo_scaleX = ObjectAnimator.ofFloat(bamboo__,"scaleX",0,1f);
        bamboo_scaleY = ObjectAnimator.ofFloat(bamboo__,"scaleY",0,1f);
        panda_alpha = ObjectAnimator.ofFloat(logo,"alpha",0.5f,1f);
        forest_alpha = ObjectAnimator.ofFloat(logo,"alpha",0.5f,1f);
        bamboo_translation = ObjectAnimator.ofFloat(bamboo__,"translationY",height,bamboo_TranslationY);
        panda_animator.setDuration(4000);
        bamboo_forest_animator.setDuration(4000);
        bamboo_scaleX.setDuration(4500);
        bamboo_scaleY.setDuration(4500);
        panda_alpha.setDuration(3000);
        forest_alpha.setDuration(3000);
        bamboo_translation.setDuration(4500);

        bamboo_animatorSet.play(panda_animator)
                .with(bamboo_forest_animator)
                .with(bamboo_scaleX)
                .with(bamboo_scaleY)
                .with(bamboo_translation)
                .with(panda_alpha)
                .with(forest_alpha);

        logo_animatorSet.start();
        bamboo_animatorSet.start();

        animationText("你 好 中 国");
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(index <= charSequence.length()){
                title.setText(charSequence.subSequence(0,index++));
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animationText(CharSequence cs){
        charSequence = cs;
        index = 0;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
        finish();
    }
}