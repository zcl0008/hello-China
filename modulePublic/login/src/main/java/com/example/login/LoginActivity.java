package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.login.R;
import com.example.tool.Entity.User;
import com.example.tool.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private String Server_IP = "http://192.168.0.83:8080";
    private String Server_Login = "/user/findUserByPhoneAndPwd";
    private User user;

    private SharedPreferences sp;
    private boolean isHide;
    private Button login;
    private TextView register;
    private TextView find_password;
    private TextView login_by_code;
    private ImageView eye;
    private EditText account;
    private EditText password;
    private TextView password_hint;
    private HideReturnsTransformationMethod show;
    private PasswordTransformationMethod hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();

    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Login();
            finish();
        }
    };

    public void initWidget(){
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        find_password = findViewById(R.id.find_password);
        login_by_code = findViewById(R.id.login_by_code);
        eye = findViewById(R.id.eye);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        password_hint = findViewById(R.id.password_hint);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        find_password.setOnClickListener(this);
        login_by_code.setOnClickListener(this);
        eye.setOnClickListener(this);
        account.setOnClickListener(this);
        password.setOnClickListener(this);
        password_hint.setOnClickListener(this);

        password_hint.setVisibility(View.GONE);

        isHide = true;
        hide = PasswordTransformationMethod.getInstance();
        show = HideReturnsTransformationMethod.getInstance();
        password.setTransformationMethod(hide);
    }

    public void Login(){
        sp = getSharedPreferences("Login_State",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",true);
        editor.putString("name", user.getName());
        editor.putString("phone", user.getPhone());
        editor.putString("email", user.getEmail());
        editor.putString("photoUrl",user.getPhotoUrl());
        editor.apply();
        ARouter.getInstance()
                .build("/app/MainActivity")
                .navigation();
    }
    public void sandRequire(Context context, String url, String phone, String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtil.sendPostLoginRequest(url,phone,password, new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try {
                            String respondData = response.body().string();
                            JSONObject jsonObject = new JSONObject(respondData);
                            if (jsonObject.getInt("code") == 0){
                                JSONObject object = jsonObject.getJSONObject("data");
                                user = new User();
                                user.setName(object.getString("name"));
                                user.setPhone(object.getString("phone"));
                                user.setEmail(object.getString("email"));
                                user.setPhotoUrl(object.getString("photoUrl"));
                                Log.d("LoginActivity", "onResponse: yes");
                                handler.sendEmptyMessage(1);
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        Toast.makeText(context,"账号或者密码错误！",Toast.LENGTH_LONG).show();
                                        password_hint.setVisibility(View.VISIBLE);
                                        Log.d("LoginActivity", "onResponse: no");
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register){
            ARouter.getInstance()
                    .build("/login/RegisterActivity")
                    .navigation();
        }
        if (view.getId() == R.id.find_password){
            ARouter.getInstance()
                    .build("/login/ForgetPasswordActivity")
                    .navigation();
        }
        if (view.getId() == R.id.login_by_code){
            ARouter.getInstance()
                    .build("/login/LoginByCodeActivity")
                    .navigation();
            Log.d("LonginBy", "onClick: LoginActivity");
        }
        if (view.getId() == R.id.login){
            Log.d("LoginActivity", "onClick: ");
            sandRequire(this,Server_IP + Server_Login,account.getText().toString(),password.getText().toString());
        }
        if (view.getId() == R.id.eye){
            if (isHide){
                isHide = false;
                password.setTransformationMethod(show);
                eye.setImageResource(R.drawable.able);
            }else {
                isHide = true;
                password.setTransformationMethod(hide);
                eye.setImageResource(R.drawable.disable);
            }
            if (!password.getText().toString().trim().equals("")) {
                password.setSelection(password.getText().toString().trim().length());//将光标移至文字末尾
            }
        }
    }
}