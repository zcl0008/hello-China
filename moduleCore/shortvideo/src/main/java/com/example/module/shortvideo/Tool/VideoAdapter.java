//package com.example.module.shortvideo.Tool;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.module.shortvideo.R;
////import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
//
//import java.util.List;
//
//public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
//
//    private List<String> urlList;
//    Context context;
//
//    public VideoAdapter(List<String> urlList, Context context) {
//        this.urlList = urlList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String url = urlList.get(position);
//        holder.player.setUp(url,true,"你好中国");
//        holder.player.getTitleTextView().setVisibility(View.GONE);
//        holder.player.getBackButton().setVisibility(View.GONE);
//        // 防止错位设置
//        holder.player.setPlayPosition(position);
//        // 是否根据视频尺寸，自动选择横竖屏全屏
//        holder.player.setAutoFullWithSize(true);
//        // 音频焦点冲突时是否释放
//        holder.player.setReleaseWhenLossAudio(true);
//        // 全屏动画
//        holder.player.setShowFullAnimation(true);
//        // 不能触摸滑动
//        holder.player.setIsTouchWiget(false);
//    }
//
//    @Override
//    public int getItemCount() {
//        return urlList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        StandardGSYVideoPlayer player;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            player = itemView.findViewById(R.id.video_player);
//        }
//    }
//}
