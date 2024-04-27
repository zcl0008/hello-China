package com.example.module.mine.Activity;

import static android.app.Activity.RESULT_OK;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.mine.Article;
import com.example.module.mine.Utils.OKhttpUtils;
import com.example.tool.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.example.module.mine.FirstFragment;
import com.example.module.mine.R;
import com.example.module.mine.SecondFragment;
import com.google.android.material.tabs.TabLayout;


@Route(path = "/mine/CollegeActivity")
public class CollegeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private String Server_IP = "http://192.168.0.101:8080/";
    private String Server_Collect = "article/collect";
    private Handler handler;
    private TextView titleTextView; // 用于显示标题的TextView
    private TextView contentTextView; // 用于显示内容的TextView
    private ArrayList<Article> articleList = new ArrayList<>();
    Context context;
    SharedPreferences sp;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);
        sp = getSharedPreferences("Information",MODE_PRIVATE);
        email = sp.getString("email",null);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // 设置TabLayout显示的选项文本
        tabLayout.getTabAt(0).setText("文章");
        tabLayout.getTabAt(1).setText("视频");
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Register();
                finish();
            }
        };

    }

    private void Register() {
        ARouter.getInstance()
                .build("/mine/Activity/CollegeActivity")
                .navigation();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FirstFragment(); // 第一个Fragment
                case 1:
                    return new SecondFragment(); // 第二个Fragment
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
