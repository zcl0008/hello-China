package com.example.showwebview;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.showwebview.R;

@Route(path = "/modulePublic/showwebview/ShowWebViewActivity")
public class ShowWebViewActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    LoadingAnimation loadingAnimation;

    @Autowired(name = "url")
    String url;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_web_view);
        ARouter.getInstance().inject(this);

        imageView = (ImageView) findViewById(R.id.loading);
        textView = (TextView) findViewById(R.id.text);
        loadingAnimation = new LoadingAnimation(imageView);

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);//启用WebView的JavaScript执行功能，让WebView能够运行网页中的JavaScript代码。

        webView.getSettings().setDomStorageEnabled(true);//启用DOM存储API，支持网页使用localStorage来存储数据。

        webView.getSettings().setDatabaseEnabled(true);//启用数据库存储API，支持网页使用Web SQL数据库存储数据。

        //webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//允许混合内容，即允许HTTPS页面加载HTTP资源。

        //webView.getSettings().setUseWideViewPort(true);//设置WebView是否支持使用宽视窗，使页面按实际尺寸显示。

        //webView.getSettings().setLoadWithOverviewMode(true);//设置WebView是否以overview模式加载页面，即缩小内容以适应屏幕宽度。
        // 是否支持缩放，默认为true
        //webView.getSettings().setSupportZoom(false);//设置WebView是否支持缩放，默认为true。
        // 是否使用内置的缩放控件
        //webView.getSettings().setBuiltInZoomControls(false);//设置WebView是否使用内置的缩放控件。

        webView.setWebViewClient(new MyWebViewClient());

        // 清缓存和记录，缓存引起的白屏
        webView.clearCache(true);
        webView.clearHistory();
        //... 有很多clear的方法

        webView.loadUrl(url);
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            loadingAnimation.ReleaseAnimation();
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}