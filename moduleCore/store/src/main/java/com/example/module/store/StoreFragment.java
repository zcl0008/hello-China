package com.example.module.store;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.store.tool.BannerAdapter;
import com.example.module.store.tool.Product;
import com.example.module.store.tool.ProductAdapter;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;

@Route(path = "/module/store/StoreFragment")
public class StoreFragment extends Fragment implements View.OnClickListener {
    private Banner banner;
    private BannerAdapter bannerAdapter;
    private ArrayList<Product> bannerList;
    private CardView wenChuang;
    private CardView chayun;
    private CardView shuiguo;
    private CardView jianiang;
    private ImageView gu_pic;
    private ImageView cha_pic;
    private ImageView guo_pic;
    private ImageView jiu_pic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment,container,false);

        ARouter.getInstance().inject(this);

        initBannerList();
        initWidget(view);
        banner = view.findViewById(R.id.banner_brand);
        bannerAdapter = new BannerAdapter(getContext(),bannerList);
        banner.setAdapter(bannerAdapter);
        banner.setIndicator(new CircleIndicator(getContext()));
        banner.setIndicatorNormalColor(Color.parseColor("#C0C0C0"));
        banner.start();

        return view;
    }
    public void initBannerList(){
        bannerList = new ArrayList<>();
        bannerList.add(new Product("狗不理","http://www.chinagoubuli.com/",R.drawable.gou));
        bannerList.add(new Product("六必居","https://baike.baidu.com/item/%E5%85%AD%E5%BF%85%E5%B1%85/2302909",R.drawable.liu));
        bannerList.add(new Product("全聚德","https://www.quanjude.com.cn/",R.drawable.quan));
        bannerList.add(new Product("信远斋","http://www.xinyuanzhai.cn/brand.html",R.drawable.xin));
        bannerList.add(new Product("同春园","http://www.bjhuatian.cn/product/118.html",R.drawable.tongchun));
        bannerList.add(new Product("王致和","https://baike.baidu.com/item/%E7%8E%8B%E8%87%B4%E5%92%8C/80849",R.drawable.wang));
        bannerList.add(new Product("白云山","https://www.gybys.com.cn/",R.drawable.bai));
        bannerList.add(new Product("同仁堂","https://www.tongrentang.com/",R.drawable.tongren));
    }
    public void initWidget(View view){
        wenChuang = view.findViewById(R.id.wenchuang);
        chayun = view.findViewById(R.id.chayun  );
        shuiguo = view.findViewById(R.id.shuiguo);
        jianiang = view.findViewById(R.id.jianiang);

        gu_pic = view.findViewById(R.id.gugong_pic);
        cha_pic = view.findViewById(R.id.cha_pic);
        guo_pic = view.findViewById(R.id.gua_pic);
        jiu_pic = view.findViewById(R.id.jiu_pic);


        wenChuang.setOnClickListener(this);
        chayun.setOnClickListener(this);
        shuiguo.setOnClickListener(this);
        jianiang.setOnClickListener(this);

        gu_pic.setOnClickListener(this);
        cha_pic.setOnClickListener(this);
        guo_pic.setOnClickListener(this);
        jiu_pic.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.wenchuang || view.getId() == R.id.gugong_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://m.tb.cn/h.5tF0z7GqcwT5v0K") //https://www.shidianguji.com/  https://m.tb.cn/h.5tF0z7GqcwT5v0K
                    .navigation();
        }
        if (view.getId() == R.id.chayun || view.getId() == R.id.cha_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://m.tb.cn/h.5tucIvQJL07WcsZ ")
                    .navigation();
        }
        if (view.getId() == R.id.shuiguo || view.getId() == R.id.gua_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://m.tb.cn/h.5GvXsF1rFT7yn2H ")
                    .navigation();
        }
        if (view.getId() == R.id.jianiang || view.getId() == R.id.jiu_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://m.tb.cn/h.5uMzJRy1EootV8z")
                    .navigation();
        }
    }
}
