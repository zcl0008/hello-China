package com.example.module.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.home.OKHttp.OkHttpsUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/moduleCore/home/HomeFragment")
public class HomeFragment extends Fragment implements View.OnClickListener{
    private String Server_IP = "http://192.168.0.101:8080/";
    private String Server_Collect = "article/get/list";
    private  RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private TextView first;
    private TextView second;
    private TextView third;
    private TextView new_one;
    private TextView new_two;
    private TextView new_three;
    private TextView new_four;
    private ImageView collect_one;
    private ImageView collect_two;
    private ImageView collect_three;
    private ImageView collect_four;
    private ImageView collect_six;
    private ImageView collect_seven;
    private ImageView collect_eight;
    private  ImageView zhuanfa_one;
    private  ImageView zhuanfa_two;
    private  ImageView zhuanfa_three;
    private  ImageView zhuanfa_four;
    private  ImageView zhuanfa_six;
    private  ImageView zhuanfa_seven;
    private  ImageView zhuanfa_eight;
    private  ImageView like_one;
    private  ImageView like_two;
    private  ImageView like_three;
    private  ImageView like_four;
    private  ImageView like_six;
    private  ImageView like_seven;
    private  ImageView like_eight;

    SharedPreferences sp;
    String email;
    private MZBannerView galleryRecycle;
    private List<Integer> galleries = Arrays.asList(new Integer[]{R.drawable.begin, R.drawable.first,
            R.drawable.second,R.drawable.third,R.drawable.last});
    private List<Fruit> fruitList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        sp = getContext().getSharedPreferences("Information",Context.MODE_PRIVATE);
        email = sp.getString("email",null);

        ARouter.getInstance().inject(this);
        initFruits();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        initWidget(view);
        galleryRecycle = (MZBannerView) view.findViewById(R.id.Gallery);

        galleryRecycle.setPages(galleries, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


        return view;
    }
    public  class  BannerViewHolder implements MZViewHolder<Integer>{
        ImageView imageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.gallery_item,null);
            imageView = (ImageView) view.findViewById(R.id.gallery_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    private void initFruits() {
        Fruit one = new Fruit(("你好,江西"), R.drawable.one);
        fruitList.add(one);
        Fruit two = new Fruit(("你好,内蒙古"), R.drawable.two);
        fruitList.add(two);
        Fruit three = new Fruit(("你好,黑龙江"), R.drawable.three);
        fruitList.add(three);
        Fruit four = new Fruit(("你好,北京"), R.drawable.four);
        fruitList.add(one);
        Fruit five = new Fruit(("你好,西安"), R.drawable.five);
        fruitList.add(five);
    }
    private void initWidget(View view){
        first = view.findViewById(R.id.first_name);
        first.setOnClickListener(this);
        second = view.findViewById(R.id.second_name);
        second.setOnClickListener(this);
        third = view.findViewById(R.id.third_name);
        third.setOnClickListener(this);
        new_one = view.findViewById(R.id.new_one);
        new_one.setOnClickListener(this);
        new_two = view.findViewById(R.id.new_two);
        new_two.setOnClickListener(this);
        new_three = view.findViewById(R.id.new_three);
        new_three.setOnClickListener(this);
        new_four = view.findViewById(R.id.new_four);
        new_four.setOnClickListener(this);
        collect_one = view.findViewById(R.id.home_collect_one);
        collect_one.setOnClickListener(this);
        collect_two = view.findViewById(R.id.home_collect_two);
        collect_two.setOnClickListener(this);
        collect_three = view.findViewById(R.id.home_collect_three);
        collect_three.setOnClickListener(this);
        collect_four = view.findViewById(R.id.home_collect_four);
        collect_four.setOnClickListener(this);
        collect_six = view.findViewById(R.id.home_collect_six);
        collect_six.setOnClickListener(this);
        collect_seven = view.findViewById(R.id.home_collect_seven);
        collect_seven.setOnClickListener(this);
        collect_eight = view.findViewById(R.id.home_collect_eight);
        collect_eight.setOnClickListener(this);
        zhuanfa_one = view.findViewById(R.id.img_zhuanfa_one);
        zhuanfa_one.setOnClickListener(this);
        zhuanfa_two = view.findViewById(R.id.img_zhuanfa_two);
        zhuanfa_two.setOnClickListener(this);
        zhuanfa_three = view.findViewById(R.id.img_zhuanfa_three);
        zhuanfa_three.setOnClickListener(this);
        zhuanfa_four = view.findViewById(R.id.img_zhuanfa_four);
        zhuanfa_four.setOnClickListener(this);
        zhuanfa_six = view.findViewById(R.id.img_zhuanfa_six);
        zhuanfa_six.setOnClickListener(this);
        zhuanfa_seven = view.findViewById(R.id.img_zhuanfa_seven);
        zhuanfa_seven.setOnClickListener(this);
        zhuanfa_eight= view.findViewById(R.id.img_zhuanfa_eight);
        zhuanfa_eight.setOnClickListener(this);
        like_one = view.findViewById(R.id.img_like_one);
        like_one.setOnClickListener(this);
        like_two = view.findViewById(R.id.img_like_two);
        like_two.setOnClickListener(this);
        like_three = view.findViewById(R.id.img_like_three);
        like_three.setOnClickListener(this);
        like_four= view.findViewById(R.id.img_like_four);
        like_four.setOnClickListener(this);
        like_six= view.findViewById(R.id.img_like_six);
        like_six.setOnClickListener(this);
        like_seven = view.findViewById(R.id.img_like_seven);
        like_seven.setOnClickListener(this);
        like_eight = view.findViewById(R.id.img_like_eight);
        like_eight.setOnClickListener(this);

    }
    public void onClick(View view){
        if(view.getId() == R.id.first_name){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml")
                    .navigation();
        }
        if(view.getId() == R.id.second_name){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," http://www.china.com.cn/lianghui/news/2024-03/11/content_117054183.shtml")
                    .navigation();
        }
        if(view.getId() == R.id.third_name){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," http://photo.china.com.cn/2024-03/11/content_117053662.shtml")
                    .navigation();
        }
        if(view.getId() == R.id.new_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/life/2024/03-13/10179323.shtml")
                    .navigation();
        }
        if(view.getId() == R.id.new_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/life/2024/03-13/10179345.shtml")
                    .navigation();
        }
        if(view.getId() == R.id.new_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/sh/2024/03-13/10179610.shtml")
                    .navigation();
        }
        if(view.getId() == R.id.new_four){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/life/2024/03-13/10179204.shtml")
                    .navigation();
        }
        if(view.getId() == R.id.home_collect_one){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_two){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_one){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_two){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_one){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_two){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_three){
            Log.d("Server_Collect", "onClick: ");
            String article_url = " http://photo.china.com.cn/2024-03/11/content_117053662.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_four){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "https://www.chinanews.com.cn/life/2024/03-13/10179323.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_six){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "https://www.chinanews.com.cn/life/2024/03-13/10179345.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_seven){
            Log.d("Server_Collect", "onClick: ");
            String article_url = " https://www.chinanews.com.cn/sh/2024/03-13/10179610.shtml";
            setServer_Collect(article_url);
        }
        if(view.getId() == R.id.home_collect_eight){
            Log.d("Server_Collect", "onClick: ");
            String article_url = "https://www.chinanews.com.cn/life/2024/03-13/10179204.shtml";
            setServer_Collect(article_url);
        }

        if(view.getId() == R.id.img_zhuanfa_one){
            Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_zhuanfa_two){
            Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_zhuanfa_three){
            Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_zhuanfa_four){
            Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_zhuanfa_six){
            Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_zhuanfa_seven){
            Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_zhuanfa_eight){
            Toast.makeText(getContext(), "转发成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_like_one){
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_like_two){
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_like_three){
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_like_four){
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_like_six){
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_like_seven){
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        }
        if(view.getId() == R.id.img_like_eight){
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        galleryRecycle.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        galleryRecycle.start();
    }
    public void setServer_Collect(String article_url){
        Log.d("Server_Collect", "setServer_Collect: "+ email);
        Log.d("Server_Collect", "setServer_Collect: "+ Server_IP + Server_Collect);
        OkHttpsUtils.sendArticle(Server_IP + Server_Collect,email,article_url, new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                try {
                    String responseBody = response.body().string();
                    JSONObject responseData = new JSONObject(responseBody);
                    int statusCode = responseData.getInt("code");
                    String message = responseData.getString("msg");
                    Log.d("CollectRespond", "onResponse: " + responseData);
                    Log.d("CollectRespond", "onResponse: " + statusCode);
                    // 检查响应状态码
                    if (statusCode == 200) {
                        // 文章收藏成功，请根据需要更新用户界面
                        showSuccessMessage(message);

                    } else {
                        // 文章收藏失败，显示错误信息
                        showErrorMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }
    private void showErrorMessage(String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showSuccessMessage(String message) {
        // 显示成功消息，例如使用Toast
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
