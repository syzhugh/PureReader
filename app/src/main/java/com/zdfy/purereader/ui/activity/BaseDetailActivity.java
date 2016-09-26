package com.zdfy.purereader.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zdfy.purereader.R;

/**
 * Created by ZhangPeng on 2016/9/12.
 */
public abstract class BaseDetailActivity extends AppCompatActivity {
    
    protected Toolbar mParentToolbar;
    protected CollapsingToolbarLayout mParentToolbarLayout;
    protected ProgressDialog mProgressDialog = null;
    protected WebView mParentWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        msetContentView();
        ImplParents();
        initViews();
        initWebViews();
        setData();
        initEvents();
    }

    /**
     * 处理点击事件
     */
    protected abstract void initEvents();

    /**
     * 设置数据
     */
    protected abstract void setData();

    private void initViews() {
        mParentToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mParentToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mParentToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mParentToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
        setSupportActionBar(mParentToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 加载webView
     */
    protected abstract void loadWebViewUrl();
    /**
     * 初始化布局
     */
    protected abstract void msetContentView();

    /**
     * 初始化数据
     */
    protected abstract void initDatas();

    /**
     * 关联共性控件
     */
    protected abstract void ImplParents();

    private void initWebViews() {
        mProgressDialog = new ProgressDialog(BaseDetailActivity.this);
        mProgressDialog.setMessage("加载中,请稍后...");
        WebSettings settings = mParentWebView.getSettings();
        //滑动动条始终显示
        mParentWebView.setScrollbarFadingEnabled(true);
        //能够和js交互
        settings.setJavaScriptEnabled(true);


        settings.setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小 
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //开启DOM storage API功能
        settings.setDomStorageEnabled(true);

        //开启application Cache功能
        settings.setAppCacheEnabled(false);
        
        loadWebViewUrl();
        // 设置在本WebView内可以通过按下返回上一个html页面
        mParentWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mParentWebView.canGoBack()) {
                        mParentWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }
    //加载数据
    protected void mLoadUrl(String url) {
        if (mParentWebView != null) {
            mParentWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mProgressDialog.show();
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    mProgressDialog.dismiss();
                }

            });
            mParentWebView.loadUrl(url);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_open_inbroswer,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_open_in_browser){
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(setUri())));
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract String setUri();
        
}
