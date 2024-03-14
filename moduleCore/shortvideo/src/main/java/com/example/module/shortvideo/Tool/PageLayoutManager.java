package com.example.module.shortvideo.Tool;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Printer;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class PageLayoutManager extends LinearLayoutManager {

    private PagerSnapHelper pagerSnapHelper;
    private OnViewPagerListener onViewPagerListener;
    private RecyclerView recyclerView;
    private int myDrift;

    private RecyclerView.OnChildAttachStateChangeListener childAttachStateChangeListener =
            new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {
                    if (onViewPagerListener != null && getChildCount() == 1){
                        onViewPagerListener.onInitComplete(view);
                    }
                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (view.isAttachedToWindow()) {
                        if (myDrift >= 0){
                            if (onViewPagerListener != null){
                                onViewPagerListener.onPageRelease(true,getPosition(view),view);
                            }
                        }else {
                            if (onViewPagerListener != null){
                                onViewPagerListener.onPageRelease(false,getPosition(view),view);
                            }
                        }
                    }
                }
            };

    public PageLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        init();
    }

    private void init(){
        pagerSnapHelper = new PagerSnapHelper();
    }

    public void setOnViewPagerListener(OnViewPagerListener onViewPagerListener) {
        this.onViewPagerListener = onViewPagerListener;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        pagerSnapHelper.attachToRecyclerView(view);
        this.recyclerView = view;
        recyclerView.addOnChildAttachStateChangeListener(childAttachStateChangeListener);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (RecyclerView.SCROLL_STATE_IDLE == state){
            View view = pagerSnapHelper.findSnapView(this);
            if (view != null){
                int position = getPosition(view);
                if (onViewPagerListener != null && getChildCount() == 1){
                    onViewPagerListener.onPageSelected(position, position == getItemCount() - 1,view);
                }
            }
        }
        if (RecyclerView.SCROLL_STATE_DRAGGING == state) {
            View view = pagerSnapHelper.findSnapView(this);
            if (view != null){
                int position = getPosition(view);
            }

        }
        if (RecyclerView.SCROLL_STATE_SETTLING == state){
            View view = pagerSnapHelper.findSnapView(this);
            if (view != null){
                int position = getPosition(view);
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.myDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView. State state){
        this.myDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }
}
