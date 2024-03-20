package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

@Route(path = "/login/LoginByCodeActivity")
public class LoginByCodeActivity extends AppCompatActivity implements View.OnClickListener{

    private String Server_IP = "http://192.168.0.83:8080";
    private String Server_Login = "/user/findUserByPhoneAndPwd";
    private User user;
    private String returnCode;

    private SharedPreferences sp;

    private EditText email;
    private EditText code;
    private TextView email_format;
    private TextView email_code_format;
    private TextView get_code;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_code);
        initWidget();
        Log.d("LonginBy", "onCreate");
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Login();
            finish();
        }
    };

    public void initWidget(){
        email = (EditText) findViewById(R.id.email);
        code = (EditText) findViewById(R.id.security_code);
        email_format = (TextView) findViewById(R.id.email_format);
        email_code_format = (TextView) findViewById(R.id.email_code_format);
        get_code = (TextView) findViewById(R.id.get_security_code);
        login = (Button) findViewById(R.id.login);

        get_code.setOnClickListener(this);
        login.setOnClickListener(this);

        email_format.setVisibility(View.GONE);
        email_code_format.setVisibility(View.GONE);
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

    public void sendLoginRequire(Context context, String url, String email){
        OkHttpUtil.sendPostLoginByCodeRequest(url,email,new Callback() {
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
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void GetCode(){
        OkHttpUtil.sendPostCodeRequest(Server_IP + Server_Login, email.getText().toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String respondDate = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(respondDate);
                    if (jsonObject.getInt("code") == 0){
                        returnCode = jsonObject.getString("returnCode");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                get_code.setText("已成功发送");
                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.get_security_code){
            GetCode();
        }
        if (view.getId() == R.id.login){
            if (email.getText().toString().trim().equals("") || code.getText().toString().trim().equals("")){

            }else{
                if (returnCode.equals(code.getText().toString())){
                    email_code_format.setVisibility(View.VISIBLE);
                }else {
                    sendLoginRequire(this,Server_IP + Server_Login,email.getText().toString());
                }
            }
        }
    }
}