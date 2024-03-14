package com.example.module.shortvideo;

import android.os.Bundle;
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
import com.example.module.shortvideo.Tool.IJKVideoPlayerAdapter;
import com.example.module.shortvideo.Tool.OnViewPagerListener;
import com.example.module.shortvideo.Tool.PageLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.example.module.shortvideo.Tool.VideoAdapter;
//import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/module/shortvideo/ShortVideoFragment")
public class ShortVideoFragment extends Fragment{
    private List<String> urlList;
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

        initUrl();
        initPlayer();


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

    private void initUrl(){
        urlList = new ArrayList<>();
        urlList.add("https://v-cdn.zjol.com.cn/277001.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/280443.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/276982.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/276984.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/276985.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/277004.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/277003.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/277002.mp4");
        urlList.add("https://v-cdn.zjol.com.cn/276996.mp4");
    }

    public void initPlayer(){
        recyclerView = view.findViewById(R.id.video_recycle);
        //LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        pageLayoutManager = new PageLayoutManager(getContext(), OrientationHelper.VERTICAL);

        adapter = new IJKVideoPlayerAdapter(getContext(),urlList);
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
}
