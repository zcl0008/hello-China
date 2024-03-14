package com.example.module.store.tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.store.R;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends com.youth.banner.adapter.BannerAdapter<Product,BannerAdapter.ViewHolder>{
    Context context;

    public BannerAdapter(Context context,List<Product> products) {
        super(products);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindView(ViewHolder holder, Product product, int position, int size) {
        holder.textView.setText(product.getName());
        holder.imageView.setImageResource(product.getPicture());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                        .withString("url",product.getUrl())
                        .navigation(context);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner_pic);
            textView = itemView.findViewById(R.id.banner_name);
        }
    }
}
