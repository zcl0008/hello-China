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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
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

@Route(path = "/login/ForgetPasswordActivity")
public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    @Autowired(name = "phone")
    public String info_phone;

    @Autowired(name = "email")
    public String info_email;

    private String Server_IP = "169.254.225.195";
    private String Server_ModifyPassword = "/user/updatePasswordByPhone";
    private EditText password;
    private EditText confirm_password;
    private Button modify;
    private TextView password_format;
    private TextView confirm_password_format;
    private ImageView eye;
    private ImageView confirm_eye;
    private boolean is_password;
    private boolean is_confirm_password;
    private boolean isHide_eye;
    private boolean isHide_confirm_eye;
    private SharedPreferences sp;
    private User user;
    private HideReturnsTransformationMethod show;
    private PasswordTransformationMethod hide;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ARouter.getInstance().inject(this);

        initWidget();
    }
    public void initWidget(){
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        modify = (Button) findViewById(R.id.modify_button);

        password_format = (TextView) findViewById(R.id.password_format);
        confirm_password_format = (TextView) findViewById(R.id.confirm_password_format);
        eye = (ImageView) findViewById(R.id.eye);
        confirm_eye = (ImageView) findViewById(R.id.confirm_eye);

        eye.setOnClickListener(this);
        confirm_eye.setOnClickListener(this);
        modify.setOnClickListener(this);

        password_format.setVisibility(View.GONE);
        confirm_password_format.setVisibility(View.GONE);

        isHide_eye = true;
        isHide_confirm_eye = true;
        hide = PasswordTransformationMethod.getInstance();
        show = HideReturnsTransformationMethod.getInstance();

        password.setTransformationMethod(hide);
        confirm_password.setTransformationMethod(hide);

        setEditFocus();
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            reLogin();
            finish();
        }
    };

    public void setEditFocus(){
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!password.hasFocus()){
                    if (password.getText().toString().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_.]{8,16}$")){
                        is_password = true;
                        password_format.setVisibility(View.GONE);
                    }else {
                        is_password = false;
                        password_format.setVisibility(View.VISIBLE);
                    }
                    //Toast.makeText(RegisterActivity.this,password.getText().toString().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_.]{8,16}$") + "",Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!confirm_password.hasFocus()){
                    if (confirm_password.getText().toString().equals(password.getText().toString())){
                        is_confirm_password = true;
                        confirm_password_format.setVisibility(View.GONE);
                    }else {
                        is_confirm_password = false;
                        confirm_password_format.setVisibility(View.VISIBLE);
                    }
                    //Toast.makeText(RegisterActivity.this,confirm_password.getText().toString().equals(password.getText().toString()) + "",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    public void sendModifyRequire(){
        OkHttpUtil.sendPostModifyPasswordRequest(Server_IP + Server_ModifyPassword, password.getText().toString(), new Callback() {
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
                        handler.sendEmptyMessage(1);
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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

    public void reLogin(){
        sp = getSharedPreferences("Login_State",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("name", user.getName());
        editor.putString("phone", user.getPhone());
        editor.putString("email", user.getEmail());
        editor.apply();
        ARouter.getInstance()
                .build("/login/ModifyPasswordActivity")
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
        if (view.getId() == R.id.modify_button){
            if (is_password && is_confirm_password){
                sendModifyRequire();
            }
        }
        if (view.getId() == R.id.eye){
            if (isHide_eye){
                isHide_eye = false;
                password.setTransformationMethod(show);
                eye.setImageResource(R.drawable.able);
            }else {
                isHide_eye = true;
                password.setTransformationMethod(hide);
                eye.setImageResource(R.drawable.disable);
            }
            if (!password.getText().toString().trim().equals("")) {
                password.setSelection(password.getText().toString().trim().length());//将光标移至文字末尾
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
    }
}