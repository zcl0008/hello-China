package com.example.module.mine.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.mine.Article;
import com.example.module.mine.FirstFragment;
import com.example.module.mine.R;
import com.example.module.mine.SecondFragment;
import com.google.android.material.tabs.TabLayout;

@Route(path = "/mine/CollegeActivity")
public class CollegeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // 设置TabLayout显示的选项文本
        tabLayout.getTabAt(0).setText("文章");
        tabLayout.getTabAt(1).setText("视频");

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