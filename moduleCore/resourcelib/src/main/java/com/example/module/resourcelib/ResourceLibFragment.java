package com.example.module.resourcelib;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Random;

public class ResourceLibFragment extends Fragment implements View.OnClickListener{
    private Banner banner;
    private TextView picture;
    private TextView back_one;
    private TextView back_two;
    private TextView back_three;
    private ImageView pic_first;
    private ImageView pic_second;
    private ImageView pic_third;
    private ImageView new_one;
    private ImageView new_two;
    private ImageView new_three;
    private ImageView new_four;
    private ImageView story_one;
    private ImageView story_two;
    private ImageView story_three;
    private ImageView story_four;
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

        picture = view.findViewById(R.id.picture_more);
        picture.setOnClickListener(this);
        back_one = view.findViewById(R.id.back_one);
        back_one.setOnClickListener(this);
        back_two = view.findViewById(R.id.back_two);
        back_two.setOnClickListener(this);
        back_three = view.findViewById(R.id.back_three);
        back_three.setOnClickListener(this);
        pic_first = view.findViewById(R.id.pic_first);
        pic_first.setOnClickListener(this);
        pic_second = view.findViewById(R.id.pic_second);
        pic_second.setOnClickListener(this);
        pic_third = view.findViewById(R.id.pic_fourth);
        pic_third.setOnClickListener(this);
        new_one = view.findViewById(R.id.new_one);
        new_one.setOnClickListener(this);
        new_two = view.findViewById(R.id.new_two);
        new_two.setOnClickListener(this);
        new_three= view.findViewById(R.id.new_three);
        new_three.setOnClickListener(this);
        new_four = view.findViewById(R.id.new_four);
        new_four.setOnClickListener(this);
        story_one = view.findViewById(R.id.story_one);
        story_one.setOnClickListener(this);
        story_two = view.findViewById(R.id.story_two);
        story_two.setOnClickListener(this);
        story_three= view.findViewById(R.id.story_three);
        story_three.setOnClickListener(this);
        story_four = view.findViewById(R.id.story_four);
        story_four.setOnClickListener(this);
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
        if (view.getId() == R.id.picture_more){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","http://art.china.cn/huodong/node_1014517.html")
                    .navigation();
        }
        if (view.getId() == R.id.back_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://culture.gmw.cn/2022-05/10/content_35723213.htm")
                    .navigation();
        }
        if (view.getId() == R.id.back_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.thepaper.cn/newsDetail_forward_26731471")
                    .navigation();
        }
        if (view.getId() == R.id.back_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","http://ent.people.com.cn/n1/2022/0412/c1012-32396579.html")
                    .navigation();
        }
        if (view.getId() == R.id.pic_first){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","http://cygx.china.com.cn/2024-02/04/content_42692445.html")
                    .navigation();
        }
        if (view.getId() == R.id.pic_second){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/character_detail/24537.html")
                    .navigation();
        }
        if (view.getId() == R.id.pic_second){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/character_detail/24537.html")
                    .navigation();
        }
        if (view.getId() == R.id.pic_fourth){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/character_detail/27602.html")
                    .navigation();
        }
        if (view.getId() == R.id.new_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","http://120.25.237.190/hanfu/fushiwenwu/images/index.html")
                    .navigation();
        }
        if (view.getId() == R.id.new_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.meishij.net/china-food/")
                    .navigation();
        }
        if (view.getId() == R.id.new_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.archdaily.cn/cn")
                    .navigation();
        }
        if (view.getId() == R.id.new_four){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://chiculture.org.hk/sc/china-five-thousand-years/2588")
                    .navigation();
        }
        if (view.getId() == R.id.story_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","http://www.shenhuagushi.net/zhongguoshenhua/shenhua278.html")
                    .navigation();
        }
        if (view.getId() == R.id.story_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","http://www.shenhuagushi.net/zhongguoshenhua/shenhua281.html")
                    .navigation();
        }
        if (view.getId() == R.id.story_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.qigushi.com/zgsh/5.html")
                    .navigation();
        }
        if (view.getId() == R.id.story_four){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://so.gushiwen.cn/shiwenv_1104052ed0fc.aspx")
                    .navigation();
        }
    }
}
