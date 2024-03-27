package com.example.nihaochina;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.introductionpage.IntroPageActivity;

@Route(path = "/app/StartActivity")
public class StartActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d("isFirst", "onCreate: ");
        initView();
    }

    public void initView(){
        sp = getSharedPreferences("isFirst",MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("first",true);
        Log.d("isFirst", "initView: " + isFirst);
        if (isFirst){
            ARouter.getInstance()
                    .build("/introductionpage/IntroPageActivity")
                    //.withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .navigation();
            finish();
        }else {
            ARouter.getInstance()
                    .build("/introductionpage/EnterPageActivity_Spring")
                    .navigation();
            finish();
        }
        Log.d("start", "initView: finish");

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
        Log.d("StartActivity", "onDestroy: ");
    }
}