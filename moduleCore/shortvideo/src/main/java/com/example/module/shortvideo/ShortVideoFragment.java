package com.example.module.shortvideo;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.module.shortvideo.Entity.Video;
import com.example.module.shortvideo.OkHttpUtils.OkHttpsUtils;
import com.example.module.shortvideo.Tool.IJKVideoPlayerAdapter;
import com.example.module.shortvideo.Tool.OnViewPagerListener;
import com.example.module.shortvideo.Tool.PageLayoutManager;
//import com.example.module.shortvideo.Tool.VideoAdapter;
//import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/module/shortvideo/ShortVideoFragment")
public class ShortVideoFragment extends Fragment{

    private String Server_IP = "http://192.168.0.101:8080";
    private String Server_Apply_Video = "/vedio/login/list";
    private List<Video> videoList;
    private RecyclerView recyclerView;
    View view;
    View viewRecycle;
    //private VideoAdapter adapter;
    IjkVideoView ijkVideoView;
    private IJKVideoPlayerAdapter adapter;
    private PageLayoutManager pageLayoutManager;

    private SharedPreferences sp;
    private boolean isLogin;
    private String email;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shortvideo_gragment,container,false);

        initVideo();
        initPlayer();

        //ARouter.getInstance().inject(this);


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int firstPosition = manager.findFirstVisibleItemPosition();
//                int lastPosition = manager.findLastVisibleItemPosition();
//                int playPosition = GSYVideoManager.instance().getPlayPosition();
//                if (playPosition >= 0){
//                    if (playPosition < firstPosition || playPosition > lastPosition){
//                        if (GSYVideoManager.isFullState(getActivity())){
//                            return;
//                        }
//                        GSYVideoManager.releaseAllVideos();
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
        return view;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //initVedio();
            changeVideo();
        }
    };

//        urlList.add("https://v-cdn.zjol.com.cn/277001.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/280443.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276982.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276984.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276985.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277004.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277003.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277002.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276996.mp4");

    public void changeVideo(){
        Log.d("changeVideo", "changeVideo: " + videoList.size());
        adapter.notifyDataSetChanged();
    }

    public void initVideo(){
        sp = getContext().getSharedPreferences("Information", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin",false);
        email = sp.getString("email",null);
        Log.d("initVideo", "onResume: " + isLogin);
        videoList = new ArrayList<>();
        if (isLogin){
            Log.d("initVideo", "initVideo: 111");
            Log.d("initVideo", "initVideo: " + Server_IP + Server_Apply_Video);
            ApplyLoginVideo();
        }else {
            Log.d("initVideo", "initVideo: 222");
            ApplyUnLoginVideo();
        }
    }

    public void initPlayer(){
        recyclerView = view.findViewById(R.id.video_recycle);
        //LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        pageLayoutManager = new PageLayoutManager(getContext(), OrientationHelper.VERTICAL);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)){
                    if (isLogin){
                        Log.d("changeVideo", "onScrolled: isLogin");
                        ApplyLoginVideo();
                    }else {
                        Log.d("changeVideo", "onScrolled: is not Login");
                        ApplyUnLoginVideo();
                    }
                }
            }
        });

        pageLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                playVideo(0,view);
            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                releaseVideo(view);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                playVideo(position,view);
            }
        });

        recyclerView.setLayoutManager(pageLayoutManager);
        adapter = new IJKVideoPlayerAdapter(getContext(),videoList,isLogin, getActivity().getSupportFragmentManager(), getActivity());
        recyclerView.setAdapter(adapter);
    }
    private void playVideo(int position,View view){//播放视频\
        viewRecycle = view;
        ijkVideoView = view.findViewById(R.id.ijkVideoPlayer);
        //ijkVideoView = adapter.getViewHolder(view,recyclerView).ijkVideoView;
        StandardVideoController standardVideoController = new StandardVideoController(getContext());
        ijkVideoView.setVideoController(standardVideoController);
        //ijkVideoView.setUrl(urlList.get(position));
        ijkVideoView.start();
    }
    private void releaseVideo(View view){
        ijkVideoView = view.findViewById(R.id.ijkVideoPlayer);
//        //ijkVideoView = adapter.getViewHolder(view,recyclerView).ijkVideoView;
        StandardVideoController standardVideoController = new StandardVideoController(getContext());
        ijkVideoView.setVideoController(standardVideoController);
        ijkVideoView.stopPlayback();
        ijkVideoView.release();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("ShortVideo", "onPause: 111");
        if(viewRecycle != null){
            ijkVideoView = viewRecycle.findViewById(R.id.ijkVideoPlayer);
//        ijkVideoView = adapter.getViewHolder(viewRecycle,recyclerView).ijkVideoView;
            //ijkVideoView.pause();
            ijkVideoView.stopPlayback();
        }

        //ijkVideoView.release();
        //recyclerView.stopScroll();
        //GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ShortVideo", "onResume: 444");
        sp = getContext().getSharedPreferences("Information", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin",false);
        //GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ShortVideo", "onDestroy: 333");
//        VideoViewManager.instance().releaseVideoPlayer();
        //GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ShortVideo", "onStop: 222");

//        recyclerView.setAdapter(null);
//        adapter = null;
    }

    public void stopPlayVideo(){
        if(viewRecycle != null){
            ijkVideoView = viewRecycle.findViewById(R.id.ijkVideoPlayer);
//        ijkVideoView = adapter.getViewHolder(viewRecycle,recyclerView).ijkVideoView;
            ijkVideoView.pause();
            //ijkVideoView.stopPlayback();
        }
    }
    public void reStartPlayVideo(){
        if (viewRecycle != null){
//            ijkVideoView = adapter.getViewHolder(viewRecycle,recyclerView).ijkVideoView;
            ijkVideoView = viewRecycle.findViewById(R.id.ijkVideoPlayer);
            ijkVideoView.resume();
        }
    }
    public void ApplyLoginVideo(){
        OkHttpsUtils.sendApplyLoginVideoRequest(Server_IP + Server_Apply_Video,email, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("ApplyLoginVideo", "onResponse: " + respondData);
                    Log.d("ApplyLoginVideo", "onResponse: " + jsonObject.getInt("code"));
                    if (jsonObject.getInt("code") == 200){
                        JSONObject object;
                        JSONArray array = jsonObject.getJSONArray("data");
                        Log.d("ApplyLoginVideo", "onResponse: " + array.length());
                        for (int i = 1; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            Video video = new Video();
                            video.id = object.getString("vedioId");
                            video.name = object.getString("name");
                            video.url = object.getString("url");
                            video.intro = object.getString("title");
                            video.isLike = object.getBoolean("like");
                            video.isCollect = object.getBoolean("collect");
                            videoList.add(video);
                        }
                        Log.d("ApplyLoginVideo", "onResponse: yes + " + videoList.size());
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void ApplyUnLoginVideo(){
        OkHttpsUtils.sendApplyUnLoginVideoRequest(Server_IP + Server_Apply_Video, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    if (jsonObject.getInt("code") == 200){
                        JSONObject object;
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 1; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            Video vedio = new Video();
                            vedio.id = object.getString("video_id");
                            vedio.name = object.getString("name");
                            vedio.url = object.getString("url");
                            vedio.intro = object.getString("intro");
                            vedio.isLike = object.getBoolean("isLike");
                            vedio.isCollect = object.getBoolean("isCollect");
                            videoList.add(vedio);
                        }
                        Log.d("LoginActivity", "onResponse: yes");
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
