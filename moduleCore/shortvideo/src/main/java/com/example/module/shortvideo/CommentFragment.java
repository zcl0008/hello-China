package com.example.module.shortvideo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.shortvideo.Entity.Comment;
import com.example.module.shortvideo.OkHttpUtils.OkHttpsUtils;
import com.example.module.shortvideo.Tool.CommentAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @ClassName CommentFragment
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-04-04
 * @Version 1.0
 */

public class CommentFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    private String Server_IP = "http://192.168.0.101:8080";
    private String Server_Send_Comment = "/comment/add";
    private String Server_Apply_Comment = "/comment/get/list";
    View view;
    String video_id;
    String email;
    String phone_url;
    EditText editText;
    ImageView cancel;
    Button send;
    RecyclerView recyclerView;
    CommentAdapter adapter;
    LinearLayoutManager manager;
    List<Comment> comments;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                changeComments();
            }
            if (msg.what == 2){
                adapter.notifyItemInserted(comments.size() - 1);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.comment_area_fragment,container,false);
        initWidget();
        ApplyComment();
        return view;
    }

    public CommentFragment(String video_id,String email,String phone_url) {
        this.video_id = video_id;
        this.email = email;
        this.phone_url = phone_url;
        comments = new ArrayList<>();
    }

    public void initWidget(){
        editText = view.findViewById(R.id.commit);
        send = view.findViewById(R.id.send);
        cancel = view.findViewById(R.id.cancel);
        recyclerView = view.findViewById(R.id.comment_area);

        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        send.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    public String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public void changeComments(){
        editText.setText("");
        adapter = new CommentAdapter(getContext(),comments);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void ApplyComment(){
        Log.d("ApplyComment", "ApplyComment: " + Server_IP + Server_Apply_Comment);
        OkHttpsUtils.sendApplyCommentRequire(Server_IP + Server_Apply_Comment, video_id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("ApplyComment", "onResponse: " + respondData);
                    Log.d("ApplyComment", "onResponse: " + jsonObject.getInt("code"));
                    if (jsonObject.getInt("code") == 200){
                        JSONArray array = jsonObject.getJSONArray("data");
                        comments.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            Comment comment = new Comment();
                            comment.name = object.getString("name");
                            comment.photo_url = object.getString("url");
                            comment.text = object.getString("text");
                            comment.time = object.getString("time");
                            comments.add(comment);
                        }
                        Log.d("LoginActivity", "onResponse: yes");
                        handler.sendEmptyMessage(1);
                    }
                    if (jsonObject.getInt("code") == 10016){
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void sendComment(){
        String content = String.valueOf(editText.getText());
        String time = getCurrentTime();
        OkHttpsUtils.sendCommentRequire(Server_IP + Server_Send_Comment, video_id, email,
                content, time, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try {
                            String respondData = response.body().string();
                            JSONObject jsonObject = new JSONObject(respondData);
                            if (jsonObject.getInt("code") == 200){
                                Comment comment = new Comment();
                                comment.text = content;
                                comment.time = time;
                                comment.photo_url = phone_url;
                                comments.add(comment);
                                Log.d("LoginActivity", "onResponse: yes");
                                handler.sendEmptyMessage(2);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send){
            sendComment();
        }
        if (view.getId() == R.id.cancel){
            this.dismiss();
        }
    }
}