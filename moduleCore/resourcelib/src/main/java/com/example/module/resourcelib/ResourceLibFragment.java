package com.example.module.resourcelib;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    private ImageView new_five;
    private ImageView story_one;
    private ImageView story_two;
    private ImageView story_three;
    private ImageView story_four;
    private ImageView story_five;
    private List<Integer> bannerList;
    private ImageView resource_one;
    private ImageView resource_two;
    private ImageView resource_three;
    private ImageView resource_four;
    private ImageView resource_five;
    private ImageView resource_six;
    private ImageView old_book_one;
    private ImageView old_book_two;
    private ImageView old_book_three;
    private ImageView old_book_four;
    private ImageView old_book_five;
    private ImageView old_book_six;
    private ImageView old_book_seven;
    private ImageView old_book_eight;
   private TextView old_more;
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
        new_five = view.findViewById(R.id.new_five);
        new_five.setOnClickListener(this);
        story_one= view.findViewById(R.id.story_one);
        story_one.setOnClickListener(this);
        story_two = view.findViewById(R.id.story_two);
        story_two.setOnClickListener(this);
        story_three = view.findViewById(R.id.story_three);
        story_three.setOnClickListener(this);
        story_four = view.findViewById(R.id.story_four);
        story_four.setOnClickListener(this);
        story_five = view.findViewById(R.id.story_five);
        story_five.setOnClickListener(this);
        resource_one = view.findViewById(R.id.resource_one);
        resource_one.setOnClickListener(this);
        resource_two = view.findViewById(R.id.resource_two);
        resource_two.setOnClickListener(this);
        resource_three = view.findViewById(R.id.resource_three);
        resource_three.setOnClickListener(this);
        resource_four = view.findViewById(R.id.resource_four);
        resource_four.setOnClickListener(this);
        resource_five = view.findViewById(R.id.resource_five);
        resource_five.setOnClickListener(this);
        resource_six = view.findViewById(R.id.resource_six);
        resource_six.setOnClickListener(this);
        old_book_one = view.findViewById(R.id.old_book_one);
        old_book_one.setOnClickListener(this);
        old_book_two = view.findViewById(R.id.old_book_two);
        old_book_two.setOnClickListener(this);
        old_book_three = view.findViewById(R.id.old_book_three);
        old_book_three.setOnClickListener(this);
        old_book_four= view.findViewById(R.id.old_book_four);
        old_book_four.setOnClickListener(this);
        old_book_five= view.findViewById(R.id.old_book_five);
        old_book_five.setOnClickListener(this);
        old_book_six = view.findViewById(R.id.old_book_six);
        old_book_six.setOnClickListener(this);
        old_book_seven= view.findViewById(R.id.old_book_seven);
        old_book_seven.setOnClickListener(this);
        old_book_eight= view.findViewById(R.id.old_book_eight);
        old_book_eight.setOnClickListener(this);
        old_more = view.findViewById(R.id.old_more);
        old_more.setOnClickListener(this);

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
                    .withString("url","http://museum.jift.edu.cn/dz/gdfs.htm")
                    .navigation();
        }
        if (view.getId() == R.id.new_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.xiangha.com/")
                    .navigation();
        }
        if (view.getId() == R.id.new_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.dpm.org.cn/explore/buildings.html")
                    .navigation();
        }


        if (view.getId() == R.id.new_four){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.dpm.org.cn/collection/jewelrys.html")
                    .navigation();
        }
        if (view.getId() == R.id.new_five){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.dpm.org.cn/explore/collections.html")
                    .navigation();
        }
        if (view.getId() == R.id.story_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.qigushi.com/zgsh/64.html")
                    .navigation();
        }
        if (view.getId() == R.id.story_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","http://www.shenhuagushi.net/houyisheri.html")
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
        if (view.getId() == R.id.story_five){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.qigushi.com/zgsh/")
                    .navigation();
        }
        if (view.getId() == R.id.resource_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/project.html?tid=1#sy_target1")
                    .navigation();
        }
        if (view.getId() == R.id.resource_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/project.html?tid=2#sy_target1")
                    .navigation();
        }
        if (view.getId() == R.id.resource_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/project.html?tid=3#sy_target1")
                    .navigation();
        }
        if (view.getId() == R.id.resource_four){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/project.html?tid=4#sy_target1")
                    .navigation();
        }
        if (view.getId() == R.id.resource_five){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/project.html?tid=6#sy_target1")
                    .navigation();
        }
        if (view.getId() == R.id.resource_six){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.ihchina.cn/project.html?tid=7#sy_target1")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/SBCK011?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/SBCK051?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/SBCK001?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_four){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/SBCK012?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_five){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/JS0010?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_six){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/JS1524?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_seven){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/SBCK111?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_book_eight){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/book/SBCK317?page_from=home_page")
                    .navigation();
        }
        if (view.getId() == R.id.old_more){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.shidianguji.com/")
                    .navigation();
        }

    }
}
