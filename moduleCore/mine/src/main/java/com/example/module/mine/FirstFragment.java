package com.example.module.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videoplayer.util.L;
import com.example.module.mine.Utils.OKhttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment {
    private String Server_IP = "http://192.168.0.101:8080/";
    private String Server_Collect = "article/collect";
    SharedPreferences sp;
    String email;
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private ArrayList<Article> articleList ;
    LinearLayoutManager layoutManager;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ArticleAdapter(articleList);
            articleList = new ArrayList<>();
            recyclerView.setAdapter(adapter);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first,container,false);
        sp = getContext().getSharedPreferences("Information", Context.MODE_PRIVATE);
        email = sp.getString("email",null);
        ApplyArticle();
        recyclerView =  view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        return view;
    }
    public void ApplyArticle(){
        OKhttpUtils.sendApplyArticleRequest(Server_IP + Server_Collect, email, new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    // 检查响应状态码
                    if (jsonObject.getInt("code") == 200){
                        JSONObject object;
                        JSONArray array = jsonObject.getJSONArray("data");
                        Log.d("ApplyLoginVideo", "onResponse: " + array.length());
                        for (int i = 1; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            Article article = new Article(
                                    object.getString("title"),
                                    object.getString("source")
                            );
                            articleList.add(article);
                            handler.sendEmptyMessage(1);

                        }
                        Log.d("ApplyLoginVideo", "onResponse: yes + " + articleList.size());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // 处理解析异常
                }

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }
//    public void setArticleList(List<Article> articleList) {
//        if (articleList != null) {
//            this.articleList = (ArrayList<Article>) articleList;
//            if (adapter != null) {
//                adapter.setArticleList(articleList);
//            }
//        }
//    }

     }



