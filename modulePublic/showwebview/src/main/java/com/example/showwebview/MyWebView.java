package com.example.showwebview;

import android.content.Context;
import android.view.KeyEvent;
import android.webkit.WebView;

import androidx.annotation.NonNull;

public class MyWebView extends WebView {
    public MyWebView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);

    }
}
