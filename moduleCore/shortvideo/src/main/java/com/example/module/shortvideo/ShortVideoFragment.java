package com.example.module.shortvideo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.example.module.shortvideo.Entity.Vedio;
import com.example.module.shortvideo.OkHttpUtils.OkHttpsUtils;
import com.example.module.shortvideo.Tool.IJKVideoPlayerAdapter;
import com.example.module.shortvideo.Tool.OnViewPagerListener;
import com.example.module.shortvideo.Tool.PageLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

    private String Server_IP = "http://192.168.0.83:8080";
    private String Server_Apply_Video = "";
    private List<Vedio> vedioList = new ArrayList<>();
    private RecyclerView recyclerView;
    View view;
    View viewRecycle;
    //private VideoAdapter adapter;
    IjkVideoView ijkVideoView;
    private IJKVideoPlayerAdapter adapter;
    private PageLayoutManager pageLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shortvideo_gragment,container,false);

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
            initVedio();
            initPlayer();
        }
    };

    private void initVedio(){
//        urlList.add("https://v-cdn.zjol.com.cn/277001.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/280443.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276982.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276984.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276985.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277004.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277003.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277002.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276996.mp4");
    }

    public void initPlayer(){
        recyclerView = view.findViewById(R.id.video_recycle);
        //LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        pageLayoutManager = new PageLayoutManager(getContext(), OrientationHelper.VERTICAL);

        adapter = new IJKVideoPlayerAdapter(getContext(),vedioList);
        recyclerView.setLayoutManager(pageLayoutManager);
        recyclerView.setAdapter(adapter);

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
    public void ApplyVideo(){
        OkHttpsUtils.sendApplyVideoRequest(Server_IP + Server_Apply_Video, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    if (jsonObject.getInt("code") == 0){
                        JSONObject object;
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            object = array.getJSONObject(0);
                            Vedio vedio = new Vedio();
                            vedio.name = object.getString("name");
                            vedio.url = object.getString("url");
                            vedio.intro = object.getString("intro");
                            vedio.isLike = object.getBoolean("isLike");
                            vedio.isCollect = object.getBoolean("isCollect");

                        }
                        Log.d("LoginActivity", "onResponse: yes");
                        //handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
