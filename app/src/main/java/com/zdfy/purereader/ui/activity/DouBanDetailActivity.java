package com.zdfy.purereader.ui.activity;

import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.DouBanInfo;
import com.zdfy.purereader.utils.ShareUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DouBanDetailActivity extends BaseDetailActivity {

    @Bind(R.id.image_view)
    ImageView mImageView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.webView)
    WebView mWebView;

    @Bind(R.id.fab)
    FloatingActionButton mFab;
    private DouBanInfo datas;
    private ProgressDialog mProgressDialog = null;

    @Override
    protected void initDatas() {
        datas = (DouBanInfo) getIntent().getSerializableExtra(Constant.DOUBAN_BEAN);
    }
    @Override
    protected void msetContentView() {
        setContentView(R.layout.activity_universal_detail);
        ButterKnife.bind(this);
    }
    
    @Override
    protected void initEvents() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareUrl(DouBanDetailActivity.this,datas.getTitle(),datas.getShareUrl());
            }
        });
    }

    @Override
    protected void loadWebViewUrl() {
        mLoadUrl(datas.getShareUrl());
    }
    @Override
    protected void ImplParents() {
        mParentWebView = mWebView;
        mParentToolbar = mToolbar;
        mParentToolbarLayout = mToolbarLayout;
    }

    @Override
    protected String setUri() {
        return datas.getShareUrl();
    }

    @Override
    protected void setData() {
        if (datas != null) {
            mToolbarLayout.setTitle(datas.getTitle());
            if (datas.getThumb() != null) {
                Glide.with(DouBanDetailActivity.this)
                        .load(datas.getThumb())
                        .asBitmap()
                        .override(500, 600)
                        .priority(Priority.HIGH)
                        .error(R.drawable.no_img)
                        .into(mImageView);
            }
        }
    }
}
