package com.example.introductionpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/introductionpage/IntroPageActivity")
public class IntroPageActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager viewPager;
    private List<View> viewList;
    private IntroAdapter adapter;
    private Button intro_enter;
    private Button intro_login;
    private Button button1;
    private Button button2;
    private Button button3;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_intro_page);

        initView();
        initStart();

    }

    public  void initView(){
        viewPager = findViewById(R.id.introduce_viewpage);
        viewList = new ArrayList<>();
        viewList.add(getView(R.layout.intro1));
        viewList.add(getView(R.layout.intro2));
        viewList.add(getView(R.layout.intro3));
        viewList.add(getView(R.layout.intro4));

        adapter = new IntroAdapter(viewList);
        viewPager.setAdapter(adapter);
        Log.d("IntroActivity", "initView: ");
    }

    public View getView(int resId){
        return LayoutInflater.from(this).inflate(resId,null);
    }

    public void initStart(){
        sp = getSharedPreferences("isFirst",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first",false);
        editor.apply();
        intro_enter = (Button) viewList.get(3).findViewById(R.id.intro_enter);
        intro_login = (Button) viewList.get(3).findViewById(R.id.intro_login);
        button1 = (Button) viewList.get(0).findViewById(R.id.next1);
        button2 = (Button) viewList.get(1).findViewById(R.id.next2);
        button3 = (Button) viewList.get(2).findViewById(R.id.next3);

        intro_login.setOnClickListener(this);
        intro_enter.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        Log.d("Intro", "onPause: ");
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Intro", "onStop: ");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Intro", "onDestroy: ");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.intro_login){
            ARouter.getInstance()
                    .build("/login/LoginActivity")
                    .navigation();
            Log.d("intro", "onClick: ");
            finish();
        }
        if (view.getId() == R.id.intro_enter){
            ARouter.getInstance()
                    .build("/app/MainActivity")
                    .navigation();
            Log.d("intro", "onClick: ");
            finish();
        }
        if (view.getId() == R.id.next1){
            viewPager.setCurrentItem(1);
        }
        if (view.getId() == R.id.next2){
            viewPager.setCurrentItem(2);
        }
        if (view.getId() == R.id.next3){
            viewPager.setCurrentItem(3);
        }
    }
}
