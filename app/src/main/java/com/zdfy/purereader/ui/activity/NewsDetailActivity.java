package com.zdfy.purereader.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;

import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @Bind(R.id.image_view)
    ImageView mImageView;
    @Bind(R.id.text_view)
    TextView mTextView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.webView)
    WebView mWebView;
    private ContentlistEntity datas;
    private ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = (ContentlistEntity) getIntent().getSerializableExtra(Constant.NEWS_BEAN);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        initWebView();
        initToolBarLayout();
        setData();
        initEvent();
    }

    private void initEvent() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, datas.getTitle() + " " + datas.getLink() + " 分享来自~PureReader");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "分享到"));
            }
        });
    }

    private void initToolBarLayout() {
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
//        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
//        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initWebView() {
        mProgressDialog = new ProgressDialog(NewsDetailActivity.this);
        mProgressDialog.setMessage("加载中,请稍后...");
        WebSettings settings = mWebView.getSettings();
        //滑动动条始终显示
        mWebView.setScrollbarFadingEnabled(true);
        //能够和js交互
        settings.setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标

//        设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小 
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //开启DOM storage API功能
        settings.setDomStorageEnabled(true);

        //开启application Cache功能
        settings.setAppCacheEnabled(false);


        loadUrl(datas.getLink());
        // 设置在本WebView内可以通过按下返回上一个html页面
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void loadUrl(String url) {
        if (mWebView != null) {
            mWebView.setWebViewClient(new WebViewClient() {
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
            mWebView.loadUrl(url);
        }
    }

    /**
     * 设置数据
     */

    private void setData() {
        mToolbarLayout.setTitle(datas.getTitle());
        if (datas.getImageurls() != null && datas.getImageurls().size() > 0) {
            x.image().bind(mImageView, datas.getImageurls().get(0).getUrl());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
