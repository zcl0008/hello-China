package com.example.module.shortvideo.Tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.example.module.shortvideo.R;
import com.example.module.shortvideo.ShortVideoFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class IJKVideoPlayerAdapter extends RecyclerView.Adapter<IJKVideoPlayerAdapter.ViewHolder> {

    private Context context;
    private List<String> urlList;
    private final PlayerConfig playerConfig;

    public IJKVideoPlayerAdapter(Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
        this.playerConfig = new com.dueeeke.videoplayer.player.PlayerConfig.Builder()
                .enableCache()//启用缓存功能，可以提高视频加载速度和播放流畅性。
                .usingSurfaceView()//使用SurfaceView来渲染视频，SurfaceView通常用于更高效地显示视频
                .savingProgress()//保存视频播放进度，下次播放时可以从上次停止的位置继续
                .disableAudioFocus()//禁用音频焦点管理，避免在播放视频时被其他应用打断
                .setLooping()//设置视频循环播放，即播放完毕后自动重新播放
                .addToPlayerManager()//将配置应用到PlayerManager播放器管理器中
                .build();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public IjkVideoView ijkVideoView;
        public ImageView like;
        public ImageView comment;
        public ImageView collect;
        public ImageView transmit;
        public BottomSheetDialog dialog;
        public View buttonSheetView;
        public boolean like_isCheck = false;
        public boolean collect_isCheck = false;
        public ViewHolder(@NonNull View view) {
            super(view);
            ijkVideoView = view.findViewById(R.id.ijkVideoPlayer);
            like = (ImageView) view.findViewById(R.id.like);
            comment = (ImageView) view.findViewById(R.id.comment);
            collect = (ImageView) view.findViewById(R.id.collect);
            transmit = (ImageView) view.findViewById(R.id.transmit);
            dialog = new BottomSheetDialog(context);
            buttonSheetView = LayoutInflater.from(context).inflate(R.layout.bottomdialog,null);
            dialog.setContentView(buttonSheetView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ijk_video_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = urlList.get(position);
        holder.ijkVideoView.setUrl(url);
        holder.ijkVideoView.setPlayerConfig(playerConfig);

        holder.ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);//设置视频缩放模式

        holder.like.setOnClickListener(new View.OnClickListener() {//点赞
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.like){
                    if (holder.like_isCheck){
                        holder.like_isCheck = false;
                        holder.like.setImageResource(R.drawable.dislike);
                    }else {
                        holder.like_isCheck = true;
                        holder.like.setImageResource(R.drawable.like);
                    }
                }
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {//评论
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"该功能正在开发中，敬请期待吧！",Toast.LENGTH_LONG).show();
            }
        });

        holder.collect.setOnClickListener(new View.OnClickListener() {//收藏
            @Override
            public void onClick(View view) {
                if (holder.collect_isCheck){
                    holder.collect_isCheck = false;
                    holder.collect.setImageResource(R.drawable.dis_collect);
                }else {
                    holder.collect_isCheck = true;
                    holder.collect.setImageResource(R.drawable.collect);
                }
            }
        });

        holder.transmit.setOnClickListener(new View.OnClickListener() {//转发
            @Override
            public void onClick(View view) {
                holder.dialog.show();
                ImageView weChat = holder.buttonSheetView.findViewById(R.id.wechat);
                ImageView circle_friends = holder.buttonSheetView.findViewById(R.id.circle_friends);
                ImageView qq = holder.buttonSheetView.findViewById(R.id.qq);
                ImageView qq_room = holder.buttonSheetView.findViewById(R.id.qq_room);
                ImageView link = holder.buttonSheetView.findViewById(R.id.link);

                weChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"已转发至微信",Toast.LENGTH_SHORT).show();
                        holder.dialog.dismiss();
                    }
                });
                circle_friends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"已转发至微信朋友圈",Toast.LENGTH_SHORT).show();
                        holder.dialog.dismiss();
                    }
                });
                qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"已转发至QQ",Toast.LENGTH_SHORT).show();
                        holder.dialog.dismiss();
                    }
                });
                qq_room.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"已转发至QQ空间",Toast.LENGTH_SHORT).show();
                        holder.dialog.dismiss();
                    }
                });
                link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context,"链接已复制到剪切板",Toast.LENGTH_SHORT).show();
                        holder.dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    public ViewHolder getViewHolder(View view,RecyclerView recyclerView){
        ViewHolder viewHolder = (ViewHolder) recyclerView.getChildViewHolder(view);
        return viewHolder;
    }
}

