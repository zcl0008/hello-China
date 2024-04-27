package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.login.R;
import com.example.tool.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/login/FindPasswordVerityActivity")
public class ForgetPasswordVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private String Server_IP = "http://192.168.0.101:8080";
    private String Server_FindPassword = "/user/updatePasswordByPhone";
    private EditText email;
    private EditText code;
    private Button confirm;
    private TextView email_format;
    private TextView code_format;
    private TextView get_code;
    private boolean is_email;
    private boolean is_code = true;
    private String info_phone;
    private String info_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_verity);

        initWidget();
    }
    public void initWidget(){
        email = (EditText) findViewById(R.id.email);
        code = (EditText) findViewById(R.id.security_code);
        confirm = (Button) findViewById(R.id.confirm_button);

        email_format = (TextView) findViewById(R.id.email_format);
        code_format = (TextView) findViewById(R.id.code_format);
        get_code = (TextView) findViewById(R.id.get_security_code);

        get_code.setOnClickListener(this);
        confirm.setOnClickListener(this);

        email_format.setVisibility(View.GONE);
        code_format.setVisibility(View.GONE);

        setEditFocus();
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            FindPassword();
            finish();
        }
    };

    public void FindPassword(){
        ARouter.getInstance()
                .build("/login/ForgetPasswordActivity")
                .withString("phone",info_phone)
                .withString("email",info_email)
                .navigation();
    }

    public void setEditFocus(){
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
                    //Toast.makeText(RegisterActivity.this,email.getText().toString().matches("^[0-9A-Za-z_]+([-+.][0-9A-Za-z_]+)*@[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*\\.[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*$") + "",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void judgeCode(){

    }

    public void sendFindPasswordRequire(){
        OkHttpUtil.sendPostFindPasswordRequest(Server_IP + Server_FindPassword, email.getText().toString(),
                code.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try {
                            String respondData = response.body().string();
                            JSONObject jsonObject = new JSONObject(respondData);
                            if (jsonObject.getInt("code") == 0){
                                JSONObject object = jsonObject.getJSONObject("data");
                                info_phone = (String) object.get("phone");
                                info_email = (String) object.get("email");
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
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirm_button){
            if (is_email && is_code){
                sendFindPasswordRequire();
            }
        }
    }
}