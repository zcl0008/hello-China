package com.example.module.shortvideo.Tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.example.module.shortvideo.CommentFragment;
import com.example.module.shortvideo.Entity.Comment;
import com.example.module.shortvideo.Entity.Vedio;
import com.example.module.shortvideo.OkHttpUtils.OkHttpsUtils;
import com.example.module.shortvideo.R;
import com.example.tool.Entity.User;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IJKVideoPlayerAdapter extends RecyclerView.Adapter<IJKVideoPlayerAdapter.ViewHolder> {

    private String Server_IP = "http://192.168.0.83:8080";
    private String Server_Apply_Video = "";
    private String Server_Apply_Comment = "";
    private String Server_Send_Comment = "";
    private Context context;
    private List<Vedio> vedioList;
    private final PlayerConfig playerConfig;
    private SharedPreferences sp;
    private String photo_url;
    private  String name;

    public IJKVideoPlayerAdapter(Context context, List<Vedio> vedioList) {
        this.context = context;
        this.vedioList = vedioList;
        this.playerConfig = new com.dueeeke.videoplayer.player.PlayerConfig.Builder()
                .enableCache()//启用缓存功能，可以提高视频加载速度和播放流畅性。
                .usingSurfaceView()//使用SurfaceView来渲染视频，SurfaceView通常用于更高效地显示视频
                .savingProgress()//保存视频播放进度，下次播放时可以从上次停止的位置继续
                .disableAudioFocus()//禁用音频焦点管理，避免在播放视频时被其他应用打断
                .setLooping()//设置视频循环播放，即播放完毕后自动重新播放
                .addToPlayerManager()//将配置应用到PlayerManager播放器管理器中
                .build();
        sp = context.getSharedPreferences("Information",context.MODE_PRIVATE);
        getInformation();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public IjkVideoView ijkVideoView;
        public ImageView like;
        public ImageView comment;
        public ImageView collect;
        public ImageView transmit;
        public BottomSheetDialog dialog;
        public BottomSheetBehavior behavior;
        public View bottomSheetDialogView;
        public View bottomSheetBehavior;
        public boolean like_isCheck;
        public boolean collect_isCheck;
        public ViewHolder(@NonNull View view) {
            super(view);
            ijkVideoView = view.findViewById(R.id.ijkVideoPlayer);
            like = (ImageView) view.findViewById(R.id.like);
            comment = (ImageView) view.findViewById(R.id.comment);
            collect = (ImageView) view.findViewById(R.id.collect);
            transmit = (ImageView) view.findViewById(R.id.transmit);
            dialog = new BottomSheetDialog(context);

            bottomSheetDialogView = LayoutInflater.from(context).inflate(R.layout.bottomdialog,null);
            bottomSheetBehavior = view.findViewById(R.id.bottom_sheet_behavior);

            dialog.setContentView(bottomSheetDialogView);
            behavior = BottomSheetBehavior.from(bottomSheetBehavior);
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
        Vedio vedio = vedioList.get(position);
        holder.ijkVideoView.setUrl(vedio.url);
        holder.like_isCheck = vedio.isLike;
        holder.collect_isCheck = vedio.isCollect;
        holder.ijkVideoView.setPlayerConfig(playerConfig);
        holder.ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);//设置视频缩放模式

        initIcon(holder);
        setTransitionOnClickListener(holder);
        setCommitBehavior(holder.bottomSheetBehavior,holder.behavior);
    }

    @Override
    public int getItemCount() {
        return vedioList.size();
    }

    public ViewHolder getViewHolder(View view,RecyclerView recyclerView){
        ViewHolder viewHolder = (ViewHolder) recyclerView.getChildViewHolder(view);
        return viewHolder;
    }

    public void initIcon(ViewHolder holder){
        if (holder.like_isCheck){
            holder.like.setImageResource(R.drawable.like);
        }else {
            holder.like.setImageResource(R.drawable.dislike);
        }
        if (holder.collect_isCheck){
            holder.collect.setImageResource(R.drawable._collect);
        }else {
            holder.collect.setImageResource(R.drawable.dis_collect);
        }
    }
    public void setTransitionOnClickListener(ViewHolder holder){
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
                //Toast.makeText(context,"该功能正在开发中，敬请期待吧！",Toast.LENGTH_LONG).show();
                holder.behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

        holder.collect.setOnClickListener(new View.OnClickListener() {//收藏
            @Override
            public void onClick(View view) {
                if (holder.collect_isCheck){
                    holder.collect_isCheck = false;
                    holder.collect.setImageResource(R.drawable.dis_collect);
//                    holder.collect.setColorFilter(Color.WHITE);
                }else {
                    holder.collect_isCheck = true;
                    holder.collect.setImageResource(R.drawable._collect);
//                    holder.collect.setColorFilter(Color.YELLOW);
                }
            }
        });

        holder.transmit.setOnClickListener(new View.OnClickListener() {//转发
            @Override
            public void onClick(View view) {
                holder.dialog.show();
                ImageView weChat = holder.bottomSheetDialogView.findViewById(R.id.wechat);
                ImageView circle_friends = holder.bottomSheetDialogView.findViewById(R.id.circle_friends);
                ImageView qq = holder.bottomSheetDialogView.findViewById(R.id.qq);
                ImageView qq_room = holder.bottomSheetDialogView.findViewById(R.id.qq_room);
                ImageView link = holder.bottomSheetDialogView.findViewById(R.id.link);

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

    public void setCommitBehavior(View bottomBehavior, BottomSheetBehavior behavior){
        final boolean[] isFill = {false};
        ImageView screen_state = bottomBehavior.findViewById(R.id.screen_state);
        ImageView cancel = bottomBehavior.findViewById(R.id.cancel);
        TextView commit_num = bottomBehavior.findViewById(R.id.comment_num);

        RecyclerView recyclerView = bottomBehavior.findViewById(R.id.comment_area);
        CommentAdapter adapter;
        LinearLayoutManager manager;
        List<Comment> comments;

        EditText editText = bottomBehavior.findViewById(R.id.comment);
        Button send = bottomBehavior.findViewById(R.id.send);

        comments = new ArrayList<>();
        adapter = new CommentAdapter(context,comments);
        manager = new LinearLayoutManager(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        screen_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFill[0]){
                    screen_state.setImageResource(R.drawable.shortvideo_fill_screen);
                    behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    isFill[0] = false;
                }else {
                    screen_state.setImageResource(R.drawable.shortvideo_shrink);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    isFill[0] = true;
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFill[0] = false;
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void getInformation(){
        sp = context.getSharedPreferences("Information",Context.MODE_PRIVATE);
        photo_url = sp.getString("photo_url",null);
        name = sp.getString("name",null);
    }


    public void ApplyComment(String video_id){
        OkHttpsUtils.sendApplyCommentRequire(Server_IP + Server_Apply_Comment, video_id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    if (jsonObject.getInt("code") == 0){
                        JSONObject object = jsonObject.getJSONObject("data");
                        //user.setPhotoUrl(object.getString("photoUrl"));
                        Log.d("LoginActivity", "onResponse: yes");
                        //handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void SendComment(String video_id,EditText editText,String time){
        String content = String.valueOf(editText.getText());
        OkHttpsUtils.sendCommentRequire(Server_IP + Server_Send_Comment, video_id, photo_url, name,
                content, time, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        try {
                            String respondData = response.body().string();
                            JSONObject jsonObject = new JSONObject(respondData);
                            if (jsonObject.getInt("code") == 0){
                                JSONObject object = jsonObject.getJSONObject("data");
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

