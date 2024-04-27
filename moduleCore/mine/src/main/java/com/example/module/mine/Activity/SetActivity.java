package com.example.module.mine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.mine.R;
@Route(path = "/mine/SetActivity")
public class SetActivity extends AppCompatActivity implements View.OnClickListener{
    Button banned;
    Button exit;
    Button modify_password;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        banned = (Button) findViewById(R.id.banned);
        exit = (Button) findViewById(R.id.exit);
        modify_password = (Button) findViewById(R.id.modify_password);

        banned.setOnClickListener(this);
        exit.setOnClickListener(this);
        modify_password.setOnClickListener(this);
    }
    public void exitAccount(){
        sp = getSharedPreferences("Information",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("name",null);
        editor.putString("phone",null);
        editor.putString("email",null);
        editor.putString("photo_url",null);
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.banned){

        }
        if (view.getId() == R.id.exit){
            exitAccount();
            finish();
        }
        if ((view.getId() == R.id.modify_password)){
            ARouter.getInstance()
                    .build("/login/ChangePasswordActivity")
                    .navigation();
        }
    }
}