package com.example.module.shortvideo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module.shortvideo.Entity.Comment;
import com.example.module.shortvideo.Tool.CommentAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CommitFragment
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-23
 * @Version 1.0
 */

public class CommentFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    View bottomSheet_view;
    BottomSheetBehavior behavior;
    ImageView screen_state;
    ImageView cancel;
    TextView commit_num;
    RecyclerView recyclerView;
    CommentAdapter adapter;
    LinearLayoutManager manager;
    List<Comment> comments;

    EditText editText;
    Button send;

    boolean isFill;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //view = View.inflate(getContext(),R.layout.comment_area_fragment,null);
//        initWidget();
//        return view;
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheet_view  = view.findViewById(R.id.bottom_sheet_behavior);
        behavior = BottomSheetBehavior.from(bottomSheet_view);
        initWidget();
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void initWidget(){
        screen_state = bottomSheet_view.findViewById(R.id.screen_state);
        cancel = bottomSheet_view.findViewById(R.id.cancel);
        commit_num = bottomSheet_view.findViewById(R.id.comment_num);
        recyclerView = bottomSheet_view.findViewById(R.id.comment_area);
        editText = bottomSheet_view.findViewById(R.id.comment);
        send = bottomSheet_view.findViewById(R.id.send);

        screen_state.setOnClickListener(this);
        cancel.setOnClickListener(this);
        send.setOnClickListener(this);

        comments = new ArrayList<>();
        adapter = new CommentAdapter(getContext(),comments);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.screen_state){
            if (isFill){
                screen_state.setImageResource(R.drawable.shortvideo_fill_screen);
                behavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                Log.d("CommentStatus", "onClick: " + behavior.getState());
                isFill = false;
            }else {
                screen_state.setImageResource(R.drawable.shortvideo_shrink);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                Log.d("CommentStatus", "onClick: " + behavior.getState());
                isFill = true;
            }
        }
        if (view.getId() == R.id.cancel){
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        if (view.getId() == R.id.send){

        }
    }
}