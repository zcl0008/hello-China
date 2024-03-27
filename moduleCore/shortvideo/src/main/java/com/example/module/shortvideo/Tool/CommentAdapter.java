package com.example.module.shortvideo.Tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.shortvideo.Entity.Comment;
import com.example.module.shortvideo.R;
import com.example.tool.GlideUtil;

import java.util.List;

/**
 * @ClassName CommitAdapter
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-23
 * @Version 1.0
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    List<Comment> commits;

    public CommentAdapter(Context context, List<Comment> commits) {
        this.context = context;
        this.commits = commits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = commits.get(position);
        GlideUtil.loadImage(context,holder.photo,comment.photo_url);
        holder.comment.setText(comment.text);
        holder.name.setText(comment.name);
        holder.time.setText(comment.time);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return commits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView comment;
        TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.item_profile_photo);
            name = itemView.findViewById(R.id.item_name);
            comment = itemView.findViewById(R.id.item_comment);
            time = itemView.findViewById(R.id.item_time);
        }
    }
}