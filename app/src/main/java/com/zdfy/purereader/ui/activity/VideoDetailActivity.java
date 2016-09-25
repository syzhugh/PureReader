package com.zdfy.purereader.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.zdfy.purereader.R;
import com.zdfy.purereader.domain.VideoInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.activity_video_detail_toolbar)
    Toolbar Toolbar;
    @Bind(R.id.activity_video_detail_blurred)
    ImageView Blurred;
    @Bind(R.id.activity_video_detail_title)
    TextView Title;
    @Bind(R.id.activity_video_detail_type)
    TextView Type;
    @Bind(R.id.activity_video_detail_description)
    TextView Description;
    @Bind(R.id.activity_video_detail_addfav)
    TextView Addfav;
    @Bind(R.id.activity_video_detail_addcache)
    TextView Addcache;

    @Bind(R.id.activity_video_detail_toplay)
    JCVideoPlayerStandard toplay;

    private RequestManager picManager;
    private Priority priority = Priority.NORMAL;
    private VideoInfo.IssueListBean.ItemListBean itemListBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        picManager = Glide.with(this);

        initToolbar();

        initData();

        initEvent();

    }


    private void initEvent() {
        Addcache.setOnClickListener(this);
        Addfav.setOnClickListener(this);
    }

    private void initData() {
        itemListBean = (VideoInfo.IssueListBean.ItemListBean) getIntent().getSerializableExtra("itemListBean");

        /*picture*/
        String detail = itemListBean.getData().getCover().getDetail();
        String blurred = itemListBean.getData().getCover().getBlurred();
        setPic(blurred, Blurred);


        /*content*/
        String title = itemListBean.getData().getTitle();
        String category = itemListBean.getData().getCategory();
        int duration = itemListBean.getData().getDuration();
        String description = itemListBean.getData().getDescription();
        setTxt(title, Title);
        setTxt(category + " / " + (duration / 60) + ":" + (duration % 60), Type);
        setTxt(description, Description);

        /*video*/
        String url = itemListBean.getData().getPlayUrl();
        toplay.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_LIST, ""
        );
        setPic(detail, toplay.thumbImageView);

        /*action*/
        int collectionNum = itemListBean.getData().getConsumption().getCollectionCount();
        setTxt(String.valueOf(collectionNum), Addfav);
    }


    public void setPic(String url, ImageView view) {
        picManager.load(url).centerCrop().into(view);
    }

    public void setTxt(String text, TextView view) {
        view.setText(text);
    }


    /*-------------一般接口--------------*/

    /*播放 收藏 缓存的处理*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_video_detail_addfav:

                if (Addfav.isSelected()) {
                    Addfav.setSelected(false);
                    /*取消收藏操作*/
                } else {
                    Addfav.setSelected(true);
                    /*收藏操作*/
                }
                break;
            case R.id.activity_video_detail_addcache:
                break;
        }
    }


    /*-------------toolbar_menu--------------*/

    private void initToolbar() {
        Toolbar.setTitle("视频阅读");
        setSupportActionBar(Toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*在这里隐藏不需要的menu项*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_qrcode).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    /*menu项事务处理*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        } else if (item.getItemId() == R.id.action_settings) {

        }
        return super.onOptionsItemSelected(item);
    }

    /*-------------结束播放进程--------------*/
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
