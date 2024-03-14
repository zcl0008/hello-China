package com.example.nihaochina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.home.HomeFragment;
import com.example.module.mine.MineFragment;
import com.example.module.resourcelib.ResourceLibFragment;
import com.example.module.shortvideo.ShortVideoFragment;
import com.example.module.store.StoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private boolean isLogin = false;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;
    private HomeFragment home;
    private ResourceLibFragment resourceLib;
    private ShortVideoFragment shortVideo;
    private StoreFragment store;
    private MineFragment mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("",MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        getLoginState();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        initUI();
    }

    private void initUI(){
        home = new HomeFragment();
        resourceLib = new ResourceLibFragment();
        shortVideo = new ShortVideoFragment();
        store = new StoreFragment();
        mine = new MineFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment,home);
        fragmentTransaction.add(R.id.fragment,resourceLib);
        fragmentTransaction.add(R.id.fragment,shortVideo);
        fragmentTransaction.add(R.id.fragment,store);
        fragmentTransaction.add(R.id.fragment,mine);

        fragmentTransaction.show(home);
        fragmentTransaction.hide(resourceLib);
        fragmentTransaction.hide(shortVideo);
        fragmentTransaction.hide(store);
        fragmentTransaction.hide(mine);
        fragmentTransaction.commit();
        currentFragment = home;

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home){
//                    if (currentFragment instanceof ShortVideoFragment){
//                        replaceFragment(home);
//                    }else {
//                        showFragment(home);
//                    }
                    showFragment(home);
                }
                if (item.getItemId() == R.id.resourceLib){
//                    if (currentFragment instanceof ShortVideoFragment){
//                        replaceFragment(resourceLib);
//                    }else {
//                        showFragment(resourceLib);
//                    }
                    showFragment(resourceLib);
                }
                if (item.getItemId() == R.id.shortVideo){
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    shortVideo = new ShortVideoFragment();
//                    fragmentTransaction.add(R.id.fragment,shortVideo);
//                    fragmentTransaction.commit();
                    showFragment(shortVideo);
                }
                if (item.getItemId() == R.id.store){
//                    if (currentFragment instanceof ShortVideoFragment){
//                        replaceFragment(store);
//                    }else {
//                        showFragment(store);
//                    }
                    showFragment(store);
                }
                if (item.getItemId() == R.id.mine){
//                    if (currentFragment instanceof ShortVideoFragment){
//                        replaceFragment(mine);
//                    }else {
//                        showFragment(mine);
//                    }
                    showFragment(mine);


                }
                return true;
            }
        });
    }

    private void showFragment(Fragment fragment){
        if (!currentFragment.equals(fragment)){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(currentFragment);
            if (currentFragment instanceof ShortVideoFragment){
                ((ShortVideoFragment) currentFragment).stopPlayVideo();
            }
            currentFragment = fragment;
            Log.d("ShortVideo", "showFragment:  " + currentFragment.getClass());
            if (currentFragment instanceof ShortVideoFragment){
                ((ShortVideoFragment) currentFragment).reStartPlayVideo();
            }
            fragmentTransaction.show(currentFragment);
            fragmentTransaction.commit();
        }
    }
//    public void replaceFragment(Fragment fragment){
//        if (!currentFragment.equals(fragment)){
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment,fragment);
//            currentFragment = fragment;
//            fragmentTransaction.commit();
//        }
//    }

    public void getLoginState(){
        sp = getSharedPreferences("LoginState",MODE_PRIVATE);
        isLogin = sp.getBoolean("LoginState",false);
        Log.d("MainActivity", "getLoginState: " + isLogin);
    }
}