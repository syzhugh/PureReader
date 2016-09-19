package com.zdfy.purereader.ui.activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zdfy.purereader.R;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.constant.ZhiHuApiConstants;
import com.zdfy.purereader.utils.HttpUtils;
import com.zdfy.purereader.utils.ShareUtils;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.Bind;
import butterknife.ButterKnife;
public class ZhiHuDetailActivity extends BaseDetailActivity {
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
    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    private String zhihu_title;
    private int id;
    private String mImage;
    private String mShareUrl = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_BODY_NOT_EXISTS: {
                    Bundle data = msg.getData();
                    mShareUrl = data.getString(Constant.SHARE_URL);
                    mImageView.setImageResource(R.drawable.no_img);
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
                case Constant.MSG_BODY_EXISTS: {
                    Bundle data = msg.getData();
                    mShareUrl = data.getString(Constant.SHARE_URL);
                    boolean is_null = data.getBoolean(Constant.IMAGE_IS_NULL);
                    if (is_null) {
                        if (mImage == null) {
                            mImageView.setImageResource(R.drawable.no_img);
                            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } else {
                            Glide.with(ZhiHuDetailActivity.this).load(mImage).override(500, 600).centerCrop().into(mImageView);
                        }
                    } else {
                        Glide.with(ZhiHuDetailActivity.this).load(data.getString(Constant.IMAGE_FROM_NET)).override(500, 600).centerCrop().into(mImageView);
                        mTextView.setText(data.getString(Constant.TEXT_DESC));
                    }
                    mParentWebView.loadDataWithBaseURL("x-data://base", data.getString(Constant.HTML), "text/html", "utf-8", null);
                }
                break;
            }
            mLoadUrl(mShareUrl);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShareUtils.shareUrl(ZhiHuDetailActivity.this, zhihu_title, mShareUrl);
                }
            });
            super.handleMessage(msg);
        }
    };

    @Override
    protected void initEvents() {
        //do nothing
    }

    @Override
    protected void setData() {
        if (zhihu_title != null) {
            mToolbarLayout.setTitle(zhihu_title);
        }
        HttpUtils.doGetAsyn(ZhiHuApiConstants.NEWS + id, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    // 需要注意的是这里有可能没有body。。。 好多坑。。。
                    // attention here, it may not contain 'body'
                    // 如果没有body，则加载share_url中内容
                    // if 'body' is null, load the content of share_url
                    if (jsonObject.isNull("body")) {
                        Message msg = Message.obtain();
                        msg.what = Constant.MSG_BODY_NOT_EXISTS;
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.SHARE_URL, jsonObject.getString("share_url"));
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    } else {
                        // body不为null  body is not null
                        Message msg = Message.obtain();
                        msg.what = Constant.MSG_BODY_EXISTS;
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.SHARE_URL, jsonObject.getString("share_url"));
                        if (!jsonObject.isNull("image")) {
                            bundle.putBoolean(Constant.IMAGE_IS_NULL, false);
                            bundle.putString(Constant.IMAGE_FROM_NET, jsonObject.getString("image"));
                            bundle.putString(Constant.TEXT_DESC, jsonObject.getString("image_source"));
                        } else {
                            bundle.putBoolean(Constant.IMAGE_IS_NULL, true);
                        }
                        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
                        // in fact,in api,css addresses are given as an array
                        // api中还有js的部分，这里不再解析js
                        // javascript is included,but here I don't use it
                        // 不再选择加载网络css，而是加载本地assets文件夹中的css
                        // use the css file from local assets folder,not from network
                        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";
                        // body中替换掉img-place-holder div
                        // replace the img-place-holder with an empty value in body
                        // 可以去除网页中div所占的区域
                        // to avoid the div occupy the area
                        // 如果没有去除这个div，那么整个网页的头部将会出现一部分的空白区域
                        // if we don't delete the div, the web page will have a blank area

                        String content = jsonObject.getString("body").replace("<div class=\"img-place-holder\">", "");
                        // div headline占据了一段高度，需要手动去除
                        // delete the headline div
                        content = content.replace("<div class=\"headline\">", "");

                        // 根据主题的不同确定不同的加载内容
                        // load content judging by different theme
                        String theme = "<body className=\"\" onload=\"onLoaded()\">";
//                                if (App.getThemeValue() == Theme.NIGHT_THEME) {
//                                    theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
//                                }
                        String html = "<!DOCTYPE html>\n"
                                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                                + "<head>\n"
                                + "\t<meta charset=\"utf-8\" />"
                                + css
                                + "\n</head>\n"
                                + theme
                                + content
                                + "</body></html>";
//                        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "utf-8", null);
                        bundle.putString(Constant.HTML, html);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void loadWebViewUrl() {
//do nothing
    }
    @Override
    protected void msetContentView() {
        setContentView(R.layout.activity_universal_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDatas() {
        zhihu_title = getIntent().getStringExtra(Constant.ZHIHU_TITLE);
        id = getIntent().getIntExtra(Constant.ZHIHU_ID, 0);
        mImage = getIntent().getStringExtra(Constant.ZHIHU_IMAGE);
    }
    @Override
    protected void ImplParents() {
        mParentWebView = mWebView;
        mParentToolbar = mToolbar;
        mParentToolbarLayout = mToolbarLayout;
    }
    protected String setUri() {
        return ZhiHuApiConstants.ZHIHU_DAILY_BASE_URL + this.id;
    }
}
