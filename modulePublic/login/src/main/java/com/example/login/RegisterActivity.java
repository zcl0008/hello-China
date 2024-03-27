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

@Route(path = "/login/RegisterActivity")
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private String Server_IP = "http://192.168.0.83:8080/";
    private String Server_Register = "user/createUser";
    private String Server_Login_byCode = "/user/code";
    private User user;
    private boolean isHide_eye;
    private boolean isHide_confirm_eye;

    private boolean is_phone;
    private boolean is_email;
    private boolean is_password;
    private boolean is_confirm_password;
    private boolean is_code;

    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText code;
    private EditText password;
    private EditText confirm_password;
    private TextView phone_format;
    private TextView email_format;
    private TextView email_code_confirm;
    private TextView password_format;
    private TextView password_confirm_format;
    private TextView get_code;
    private Button register;
    private ImageView eye;
    private ImageView confirm_eye;
    private SharedPreferences sp;
    private HideReturnsTransformationMethod show;
    private PasswordTransformationMethod hide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initWidget();
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Register();
            finish();
        }
    };

    public void initWidget(){
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone_number);
        email = (EditText) findViewById(R.id.email);
        code = (EditText) findViewById(R.id.security_code);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);


        phone_format = (TextView) findViewById(R.id.phone_number_format);
        email_format = (TextView) findViewById(R.id.email_format);
        email_code_confirm = (TextView) findViewById(R.id.email_code_format);
        password_format = (TextView) findViewById(R.id.password_format);
        password_confirm_format = (TextView) findViewById(R.id.confirm_password_format);
        get_code = (TextView) findViewById(R.id.get_security_code);

        register = (Button) findViewById(R.id.register_button);
        eye = (ImageView) findViewById(R.id.eye);
        confirm_eye = (ImageView) findViewById(R.id.confirm_eye);

        phone_format.setVisibility(View.GONE);
        email_format.setVisibility(View.GONE);
        email_code_confirm.setVisibility(View.GONE);
        password_format.setVisibility(View.GONE);
        password_confirm_format.setVisibility(View.GONE);

        register.setOnClickListener(this);
        eye.setOnClickListener(this);
        confirm_eye.setOnClickListener(this);
        get_code.setOnClickListener(this);

        isHide_eye = true;
        isHide_confirm_eye = true;
        hide = PasswordTransformationMethod.getInstance();
        show = HideReturnsTransformationMethod.getInstance();

        password.setTransformationMethod(hide);
        confirm_password.setTransformationMethod(hide);

        setEditFocus();
    }

    public void setEditFocus(){
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!phone.hasFocus()){
                    if (phone.getText().toString().matches("^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\\d{8}$")){
                        is_phone = true;
                        phone_format.setVisibility(View.GONE);
                    }else {
                        is_phone = false;
                        phone_format.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(RegisterActivity.this,phone.getText().toString().matches("^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\\d{8}$") + "",Toast.LENGTH_SHORT).show();
                    //Log.d("phone", "onFocusChange: " + phone.getText().toString());
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!email.hasFocus()){
                    if (email.getText().toString().matches("^[0-9A-Za-z_]+([-+.][0-9A-Za-z_]+)*@[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*\\.[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*$")){
                        is_email = true;
                        email_format.setVisibility(View.GONE);
                    }else {
                        is_email = false;
                        email_format.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(RegisterActivity.this,email.getText().toString().matches("^[0-9A-Za-z_]+([-+.][0-9A-Za-z_]+)*@[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*\\.[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*$") + "",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    Toast.makeText(RegisterActivity.this,password.getText().toString().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_.]{8,16}$") + "",Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!confirm_password.hasFocus()){
                    if (confirm_password.getText().toString().equals(password.getText().toString())){
                        is_confirm_password = true;
                        password_confirm_format.setVisibility(View.GONE);
                        //Toast.makeText(RegisterActivity.this,"密码相同",Toast.LENGTH_SHORT).show();

                    }else {
                        is_confirm_password = false;
                        password_confirm_format.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(RegisterActivity.this,confirm_password.getText().toString().equals(password.getText().toString()) + "",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(RegisterActivity.this,confirm_password.getText().toString().equals(password.getText().toString()) + "",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    public void sandRegisterRequire(Context context,String url,User user){
        OkHttpUtil.sendPostRegisterRequest(url, user, code.getText().toString(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {

                        String respondData = response.body().string();
                        Log.d("dfgdfgdfgdfgdfgdfgdfgdf", "onResponse: " + respondData);
                        JSONObject jsonObject = new JSONObject(respondData);
                        Log.d("Register", "onResponse: ");
                        if (jsonObject.getInt("code") == 200) {
                            Log.d("Register", "code: ");
                            handler.sendEmptyMessage(1);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
                        }
                } catch (Exception e) {
                    Log.d("dfgdfgdfgdfgdfgdfgdfgdf",e.toString());
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void Register(){
//        sp = getSharedPreferences("Login_State",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("name", user.getName());
//        editor.putString("phone", user.getPhone());
//        editor.putString("email", user.getEmail());
//        editor.apply();
        ARouter.getInstance()
                .build("/login/LoginActivity")
                .navigation();
    }
    public void saveUser(){
        user = new User();
        user.setName(name.getText().toString());
        //Log.d("1", "saveUser: "+ name.getText().toString());
        user.setPhone(phone.getText().toString());
        //Log.d("2", "saveUser: " + );
        user.setEmail(email.getText().toString());
        //Log.d("3", "saveUser: ");
        user.setPassword(password.getText().toString());
    }

    @Override
    public void onClick(View view) {
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
        if (view.getId() == R.id.get_security_code){

        }
        if (view.getId() == R.id.register_button){
            confirm_password.clearFocus();
            Log.d("Register_Button", "onClick: " + is_phone + is_password + is_confirm_password);
            if (is_phone && is_email&& is_password && is_confirm_password){
                Log.d("Register_Button", "Register: ");
                saveUser();
                Log.d("Register_Button", "Register: " + Server_IP + Server_Register);
                sandRegisterRequire(this,Server_IP + Server_Register,user);
            }
        }
    }
}