package com.example.module.shortvideo.Tool;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.example.module.shortvideo.CommentFragment;
import com.example.module.shortvideo.Entity.Comment;
import com.example.module.shortvideo.Entity.Video;
import com.example.module.shortvideo.OkHttpUtils.OkHttpsUtils;
import com.example.module.shortvideo.R;
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

    private String Server_IP = "http://192.168.0.101:8080";
    private String Server_Apply_Video = "/vedio/login/list";
    private String Server_Like_Video = "/vedio/like";
    private String Server_Dislike_Video = "/vedio/cancel/like";
    private String Server_Collect_Video = "/vedio/collect";
    private String Server_CancelCollect_Video = "/vedio/cancel/collect";
    private Context context;
    private List<Video> videoList;
    private final PlayerConfig playerConfig;
    private SharedPreferences sp;
    private String photo_url;
    private  String name;
    private String email;
    private boolean isLogin;
    private FragmentManager manager;
    private Activity activity;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ViewHolder holder = (ViewHolder) msg.obj;
            switch (msg.what){
                case 1://点赞失败
                    holder.like_isCheck = false;
                    holder.like.setImageResource(R.drawable.dislike);
                    break;
                case 2://取消点赞失败
                    holder.like_isCheck = true;
                    holder.like.setImageResource(R.drawable.like);
                    break;
                case 3://收藏失败
                    holder.collect_isCheck = false;
                    holder.collect.setImageResource(R.drawable.dis_collect);
                    break;
                case 4://取消收藏失败
                    holder.collect_isCheck = true;
                    holder.collect.setImageResource(R.drawable._collect);
                    break;
            }
        }
    };

    public IJKVideoPlayerAdapter(Context context, List<Video> videoList, boolean isLogin,FragmentManager manager,Activity activity) {
        this.context = context;
        this.videoList = videoList;
        this.isLogin = isLogin;
        this.activity = activity;
        this.manager = manager;
        getInformation();
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
        public View bottomSheetDialogView;
        public CommentFragment commentFragment;
        public boolean like_isCheck;
        public boolean collect_isCheck;
        public TextView title;
        public TextView intro;
        public String video_id;
        public ViewHolder(@NonNull View view) {
            super(view);
            ijkVideoView = view.findViewById(R.id.ijkVideoPlayer);
            like = (ImageView) view.findViewById(R.id.like);
            comment = (ImageView) view.findViewById(R.id.comment);
            collect = (ImageView) view.findViewById(R.id.collect);
            transmit = (ImageView) view.findViewById(R.id.transmit);
            title = (TextView) view.findViewById(R.id.video_title);
            intro = (TextView) view.findViewById(R.id.video_intro);

            dialog = new BottomSheetDialog(context);
            bottomSheetDialogView = LayoutInflater.from(context).inflate(R.layout.bottomdialog,null);
            dialog.setContentView(bottomSheetDialogView);
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
        Video video = videoList.get(position);
        holder.ijkVideoView.setUrl(video.url);
        Log.d("changeVideo", "onBindViewHolder: " + video.url);
        holder.like_isCheck = video.isLike;
        holder.collect_isCheck = video.isCollect;
        holder.ijkVideoView.setPlayerConfig(playerConfig);
        //holder.ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);//设置视频缩放模式
        holder.title.setText(video.name);
        Log.d("changeVideo", "onBindViewHolder: " + video.name);
        holder.intro.setText(video.intro);
        Log.d("changeVideo", "onBindViewHolder: " + video.intro);
        holder.video_id = video.id;
        holder.commentFragment = new CommentFragment(holder.video_id,email,photo_url);
        initIcon(holder);
        setTransitionOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
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
    public void getInformation(){
        sp = context.getSharedPreferences("Information", Context.MODE_PRIVATE);
        photo_url = sp.getString("photo_url",null);
        name = sp.getString("name",null);
        email = sp.getString("email",email);
    }
    public void setTransitionOnClickListener(ViewHolder holder){
        if (isLogin){
            holder.like.setOnClickListener(new View.OnClickListener() {//点赞
                @Override
                public void onClick(View view) {
                    if (view.getId() == R.id.like){
                        if (holder.like_isCheck){
                            holder.like_isCheck = false;
                            holder.like.setImageResource(R.drawable.dislike);
                            sendDislikeRequire(holder,holder.video_id);
                        }else {
                            holder.like_isCheck = true;
                            holder.like.setImageResource(R.drawable.like);
                            sendLikeRequire(holder,holder.video_id);
                        }
                    }
                }
            });

            holder.comment.setOnClickListener(new View.OnClickListener() {//评论
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"该功能正在开发中，敬请期待吧！",Toast.LENGTH_LONG).show();
                    CommentFragment commentFragment = (CommentFragment) manager.findFragmentByTag("CommentFragment");
                    if (commentFragment == null){
                        holder.commentFragment.show(manager,"CommentArea");
                    }
                }
            });

            holder.collect.setOnClickListener(new View.OnClickListener() {//收藏
                @Override
                public void onClick(View view) {
                    if (holder.collect_isCheck){
                        holder.collect_isCheck = false;
                        holder.collect.setImageResource(R.drawable.dis_collect);
                        sendCancelCollectRequire(holder,holder.video_id);
                    }else {
                        holder.collect_isCheck = true;
                        holder.collect.setImageResource(R.drawable._collect);
                        sendCollectRequire(holder,holder.video_id);
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
        }else {
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"请您先登录账号！",Toast.LENGTH_SHORT).show();
                }
            });

            holder.collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"请您先登录账号！",Toast.LENGTH_SHORT).show();
                }
            });
            holder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"请您先登录账号！",Toast.LENGTH_SHORT).show();
                }
            });
            holder.transmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"请您先登录账号！",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void sendLikeRequire(ViewHolder holder,String video_id){
        Log.d("Like", "sendLikeRequire: " + Server_IP + Server_Like_Video);
        Log.d("Like", "sendLikeRequire: " + video_id + "  " + email);
        OkHttpsUtils.sendLikeVideo(Server_IP + Server_Like_Video,email,video_id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("Like", "onResponse: " + respondData);
                    Log.d("Like", "onResponse: " + jsonObject.getInt("code"));
                    if (jsonObject.getInt("code") == 200){
                        Log.d("Like", "onResponse: ok");
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"点赞成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Message message = new Message();
                        message.obj = holder;
                        message.what = 1;
                        handler.sendMessage(message);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"点赞请求失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void sendDislikeRequire(ViewHolder holder,String video_id){
        OkHttpsUtils.sendDislikeVideo(Server_IP + Server_Dislike_Video,email, video_id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("Dislike", "onResponse: " + respondData);
                    if (jsonObject.getInt("code") == 200){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"我们会加倍努力！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Message message = new Message();
                        message.obj = holder;
                        message.what = 2;
                        handler.sendMessage(message);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"取消失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void sendCollectRequire(ViewHolder holder,String video_id){
        Log.d("Collect", "sendCollectRequire: " + Server_IP + Server_Collect_Video);
        OkHttpsUtils.sendCollectVideo(Server_IP + Server_Collect_Video, email,video_id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("Collect", "onResponse: " + respondData);
                    if (jsonObject.getInt("code") == 200){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"收藏成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Message message = new Message();
                        message.obj = holder;
                        message.what = 3;
                        handler.sendMessage(message);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"收藏失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void sendCancelCollectRequire(ViewHolder holder,String video_id){
        Log.d("DisCollect", "sendCancelCollectRequire: " + Server_IP + Server_CancelCollect_Video);
        OkHttpsUtils.sendCancelCollectVideo(Server_IP + Server_CancelCollect_Video, email,video_id, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("DisCollect", "onResponse: " + respondData);
                    if (jsonObject.getInt("code") == 200){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"取消收藏成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Message message = new Message();
                        message.obj = holder;
                        message.what = 4;
                        handler.sendMessage(message);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"取消失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

