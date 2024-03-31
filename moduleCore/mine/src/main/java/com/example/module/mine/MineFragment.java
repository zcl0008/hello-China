package com.example.module.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.TaskExecutor;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.module.mine.Activity.CollegeActivity;
import com.example.module.mine.Activity.MessageActivity;
import com.example.module.mine.Activity.PersonalActivity;
import com.example.module.mine.Activity.SetActivity;
import com.example.tool.GlideUtil;

@Route(path = "/moduleCore/MineFragment")
public class MineFragment extends Fragment implements View.OnClickListener,ChangeProfilePhotoListener{
    View view;
    ChangeProfilePhotoFragment fragment;
//    Button register;
//    Button change;
    TextView name;
    ImageView profile_photo;
    LinearLayout college;
    LinearLayout message;
    LinearLayout personal;
    LinearLayout set;
    SharedPreferences sp;
    boolean isLogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment,container,false);
        initWidget();
        return view;
    }

    public void initWidget(){
//        register = view.findViewById(R.id.register);
//        change = view.findViewById(R.id.changePassword);
        fragment = new ChangeProfilePhotoFragment(this,getContext());

        name = view.findViewById(R.id.name);
        profile_photo = view.findViewById(R.id.profile_photo);

        college = view.findViewById(R.id.college);
        message = view.findViewById(R.id.message);
        personal = view.findViewById(R.id.personal);
        set = view.findViewById(R.id.set);

//        register.setOnClickListener(this);
//        change.setOnClickListener(this);
        name.setOnClickListener(this);
        profile_photo.setOnClickListener(this);

        college.setOnClickListener(this);
        message.setOnClickListener(this);
        personal.setOnClickListener(this);
        set.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.name){
            if (isLogin){

            }else {
                ARouter.getInstance()
                        .build("/login/LoginActivity")
                        .navigation();
            }
        }
        if (view.getId() == R.id.profile_photo){
            if (isLogin){
                fragment.show(getChildFragmentManager(),"fragment");
            }else {
                ARouter.getInstance()
                        .build("/login/LoginActivity")
                        .navigation();
            }
        }
        if (view.getId() == R.id.college){
            if (isLogin){
                Intent intent = new Intent(getContext(), CollegeActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(),"请先登陆账号！",Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.message){
            if (isLogin){
                Log.d("Mine", "click" + isLogin);
//                ARouter.getInstance()
//                        .build("/mine/MessageActivity")
//                        .navigation();
                Intent intent = new Intent(getContext(), MessageActivity.class);
                startActivity(intent);

            }else {
                Toast.makeText(getContext(),"请先登陆账号！",Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.personal){
            if (isLogin){
                Intent intent = new Intent(getContext(), PersonalActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(),"请先登陆账号！",Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.set){
            if (isLogin){
                Intent intent = new Intent(getContext(), SetActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(),"请先登陆账号！",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onPause() {
        Log.d("Mine", "onPause: ");
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoginStatus();
    }

    @Override
    public void changeProfilePhoto(Bitmap bitmap) {
        Log.d("MinePhoto", "changeProfilePhoto: yes");
        profile_photo.setImageBitmap(bitmap);
    }
    public void getLoginStatus(){
        sp = getContext().getSharedPreferences("Information", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin",false);
        if (isLogin){
            String Name = sp.getString("name",null);
            String photo_url = sp.getString("photo_url",null);
            name.setText(Name);
            GlideUtil.loadImage(getContext(),profile_photo,photo_url);
        }
        Log.d("Mine", "initWidget:  isLogin " + isLogin);
    }
}
