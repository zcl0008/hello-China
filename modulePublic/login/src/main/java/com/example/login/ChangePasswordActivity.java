package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

@Route(path = "/login/ChangePasswordActivity")
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private String Server_IP = "http://192.168.0.83:8080";
    private String Server_ChangePassword = "/user/updateNewPassword";
    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private Button change;
    private TextView old_password_format;
    private TextView new_password_format;
    private TextView confirm_password_format;
    private ImageView old_eye;
    private ImageView new_eye;
    private ImageView confirm_eye;
    private boolean is_old_password;
    private boolean is_new_password;
    private boolean is_confirm_password;
    private boolean isHide_old_eye;
    private boolean isHide_new_eye;
    private boolean isHide_confirm_eye;
    private SharedPreferences sp;
    private HideReturnsTransformationMethod show;
    private PasswordTransformationMethod hide;

    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initWidget();
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            exitAccount();
            Login();
            finish();
        }
    };

    public void initWidget(){
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);

        old_password_format = (TextView) findViewById(R.id.old_password_format);
        new_password_format = (TextView) findViewById(R.id.new_password_format);
        confirm_password_format = (TextView) findViewById(R.id.confirm_password_format);

        old_eye = (ImageView) findViewById(R.id.old_eye);
        new_eye = (ImageView) findViewById(R.id.new_eye);
        confirm_eye = (ImageView) findViewById(R.id.confirm_eye);

        change = (Button) findViewById(R.id.change_button);

        old_eye.setOnClickListener(this);
        new_eye.setOnClickListener(this);
        confirm_eye.setOnClickListener(this);
        change.setOnClickListener(this);

        old_password_format.setVisibility(View.GONE);
        new_password_format.setVisibility(View.GONE);
        confirm_password_format.setVisibility(View.GONE);

        isHide_old_eye = true;
        isHide_new_eye = true;
        isHide_confirm_eye = true;
        hide = PasswordTransformationMethod.getInstance();
        show = HideReturnsTransformationMethod.getInstance();

        old_password.setTransformationMethod(hide);
        new_password.setTransformationMethod(hide);
        confirm_password.setTransformationMethod(hide);

        sp = getSharedPreferences("Login_State",MODE_PRIVATE);
        phone = sp.getString("phone",null);

        setEditFocus();
    }

    public void setEditFocus(){
        new_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!new_password.hasFocus()){
                    if (new_password.getText().toString().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_.]{8,16}$")){
                        is_new_password = true;
                        new_password_format.setVisibility(View.GONE);
                    }else {
                        is_new_password = false;
                        new_password_format.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(ChangePasswordActivity.this,new_password.getText().toString().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_.]{8,16}$") + "",Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!confirm_password.hasFocus()){
                    if (confirm_password.getText().toString().equals(new_password.getText().toString())){
                        is_confirm_password = true;
                        confirm_password_format.setVisibility(View.GONE);
                        Toast.makeText(ChangePasswordActivity.this,confirm_password.getText().toString().equals(new_password.getText().toString()) + "",Toast.LENGTH_SHORT).show();
                    }else {
                        is_confirm_password = false;
                        confirm_password_format.setVisibility(View.VISIBLE);
                    }

                }
            }

        });
    }

    public void sendChangePasswordRequire(){
        OkHttpUtil.sendPostChangePasswordRequest(Server_IP + Server_ChangePassword, phone,
                old_password.getText().toString(),new_password.getText().toString(), new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try {
                            String respondData = response.body().string();
                            JSONObject jsonObject = new JSONObject(respondData);
                            Log.d("ChangeActivity", "onResponse:  " + jsonObject.getInt("code"));
                            if (jsonObject.getInt("code") == 0){
                                Log.d("ChangeActivity", "onResponse: yes");
                                old_password_format.setVisibility(View.GONE);
                                handler.sendEmptyMessage(1);
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        old_password_format.setVisibility(View.VISIBLE);
                                        Log.d("ChangeActivity", "onResponse: no");
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

    public void Login(){
        ARouter.getInstance()
                .build("/login/LoginActivity")
                .navigation();
    }
    public void exitAccount(){
        sp = getSharedPreferences("Login_State",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("name",null);
        editor.putString("phone",null);
        editor.putString("email",null);
        editor.putString("photo",null);
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.old_eye){
            if (isHide_old_eye){
                isHide_old_eye = false;
                old_password.setTransformationMethod(show);
                old_eye.setImageResource(R.drawable.able);
            }else {
                isHide_old_eye = true;
                old_password.setTransformationMethod(hide);
                old_eye.setImageResource(R.drawable.disable);
            }
            if (!old_password.getText().toString().trim().equals("")) {
                old_password.setSelection(old_password.getText().toString().trim().length());//将光标移至文字末尾
            }
        }
        if (view.getId() == R.id.new_eye){
            if (isHide_new_eye){
                isHide_new_eye = false;
                new_password.setTransformationMethod(show);
                new_eye.setImageResource(R.drawable.able);
            }else {
                isHide_new_eye = true;
                new_password.setTransformationMethod(hide);
                new_eye.setImageResource(R.drawable.disable);
            }
            if (!new_password.getText().toString().trim().equals("")) {
                new_password.setSelection(new_password.getText().toString().trim().length());//将光标移至文字末尾
            }
        }
        if (view.getId() == R.id.confirm_eye){
            if (isHide_confirm_eye){
                isHide_confirm_eye = false;
                confirm_password.setTransformationMethod(show);
                confirm_eye.setImageResource(R.drawable.able);
            }else {
                isHide_confirm_eye = true;
                confirm_password.setTransformationMethod(hide);
                confirm_eye.setImageResource(R.drawable.disable);
            }
            if (!confirm_password.getText().toString().trim().equals("")) {
                confirm_password.setSelection(confirm_password.getText().toString().trim().length());//将光标移至文字末尾
            }
        }
        if (view.getId() == R.id.change_button){
            confirm_password.clearFocus();
            if (is_new_password && is_confirm_password && !old_password.getText().toString().trim().equals("")){
//                Toast.makeText(ChangePasswordActivity.this,"HHH",Toast.LENGTH_SHORT).show();
                Log.d("ChangeActivity", "onClick: changeButton " + phone);
                sendChangePasswordRequire();
            }
        }
    }
}