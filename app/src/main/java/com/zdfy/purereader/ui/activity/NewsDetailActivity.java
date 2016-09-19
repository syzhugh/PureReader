package com.zdfy.purereader.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.DouBanInfo;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.zdfy.purereader.utils.ShareUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseDetailActivity {
    @Bind(R.id.image_view)
    ImageView mImageView;
    @Bind(R.id.text_view)
    TextView mTextView;
    
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.webView)
    WebView mWebView;
    
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    private ContentlistEntity datas;
    private DouBanInfo douBanDatas;
    protected void initEvents() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareUrl(NewsDetailActivity.this,datas.getTitle(),datas.getLink());
            }
        });
    }

    @Override
    protected void loadWebViewUrl() {
        mLoadUrl(datas.getLink());
    }

    @Override
    protected void msetContentView() {
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
    }
    @Override
    protected void initDatas() {
        datas = (ContentlistEntity) getIntent().getSerializableExtra(Constant.NEWS_BEAN);
    }

    @Override
    protected void ImplParents() {
        mParentWebView=mWebView;
        mParentToolbar=mToolbar;
        mParentToolbarLayout=mToolbarLayout;
    }

    @Override
    protected String setUri() {
        return datas.getLink();
    }

    /**
     * 设置数据
     */

    protected void setData() {
        if (datas!=null){
            mParentToolbarLayout.setTitle(datas.getTitle());
            if (datas.getImageurls() != null && datas.getImageurls().size() !=0) {
                Glide.with(NewsDetailActivity.this)
                        .load(datas.getImageurls().get(0).getUrl())
                        .asBitmap()
                        .override(500, 600)
                        .priority(Priority.HIGH)
                        .error(R.drawable.no_img)
                        .into(mImageView);
            }
        }
    }
}
