package com.example.module.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(path = "/moduleCore/home/HomeFragment")
public class HomeFragment extends Fragment implements View.OnClickListener{
    private  RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private TextView first;
    private TextView second;
    private TextView third;
    private TextView new_one;
    private TextView new_two;
    private TextView new_three;
    private TextView new_four;

    private MZBannerView galleryRecycle;
    private List<Integer> galleries = Arrays.asList(new Integer[]{R.drawable.begin, R.drawable.first,
            R.drawable.second,R.drawable.third,R.drawable.last});
    private List<Fruit> fruitList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
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
}
