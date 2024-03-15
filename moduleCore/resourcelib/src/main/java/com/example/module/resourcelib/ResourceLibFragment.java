package com.example.module.resourcelib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.resourcelibrary.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class ResourceLibFragment extends Fragment implements View.OnClickListener{
    private Banner banner;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private List<Integer> bannerList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resourcelib_fragment,container,false);
        ARouter.getInstance().inject(this);
        initBannerList();
        initWidget(view);
        banner = (Banner) view.findViewById(R.id.banner);

        banner.setAdapter(new BannerImageAdapter<Integer>(bannerList) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        });

        banner.setIndicator(new CircleIndicator(getContext()));//想要有指示器这一步必须有
        banner.start();

        return view;
    }

    private void initWidget(View view) {
        one = view.findViewById(R.id.button_1);
        two = view.findViewById(R.id.button_2);
        three = view.findViewById(R.id.button_3);
        four = view.findViewById(R.id.button_4);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

    }

    public void initBannerList(){
        bannerList = new ArrayList<>();
        bannerList.add(R.drawable.tu);
        bannerList.add(R.drawable.wan);
        bannerList.add(R.drawable.shoushi);
        bannerList.add(R.drawable.fushi);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_1){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," https://www.shidianguji.com/")
                    .navigation();
        }
        if (view.getId() == R.id.button_2){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/library ")
                    .navigation();
        }
        if (view.getId() == R.id.button_3){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/ ")
                    .navigation();
        }
        if (view.getId() == R.id.button_4){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.qigushi.com/zgsh/ ")
                    .navigation();
        }
    }
}
