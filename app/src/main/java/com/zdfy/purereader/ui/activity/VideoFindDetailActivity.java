package com.zdfy.purereader.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.VideoFindDetailAdapter;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.VideoFindInfo;
import com.zdfy.purereader.utils.HttpUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoFindDetailActivity extends AppCompatActivity implements HttpUtils.CallBack {

    public static final String NAME = "name";

    @Bind(R.id.activity_video_find_detail_toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_video_find_detail_recyclerview)
    RecyclerView recyclerview;


    private String paramCategory;
    private String paramStrategy;

    private List<VideoFindInfo.ItemListBean> list;
    private VideoFindDetailAdapter adapter;
    private LinearLayoutManager manager;
    private RecyclerView.OnScrollListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_find_detail);
        ButterKnife.bind(this);
        initToolbar();

        init();

    }

    private void init() {

        list = new ArrayList<>();
        getData();
        adapter = new VideoFindDetailAdapter(this, list);

        manager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        recyclerview.setHasFixedSize(true);
        recyclerview.addOnScrollListener(listener);


    }

    private Handler handler = new Handler();

    /*数据准备*/
    @Override
    public void onRequestComplete(String result) {
        Log.i("info", "VideoFindDetail:onRequestComplete----------------------");
        Log.i("info", "" + result);

        VideoFindInfo videoFind = JSON.parseObject(result, VideoFindInfo.class);
        list.addAll(videoFind.getItemList());

        Log.i("info", "--" + list.size());

        adapter.notifyItemInserted(adapter.getItemCount());
//        recyclerview.scrollToPosition(0);
//        VideoFindDetailAdapter.ViewHolder holder = (VideoFindDetailAdapter.ViewHolder) recyclerview.findViewHolderForAdapterPosition(0);
//        holder.getShade().setAlpha(0f);

//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                adapter.notifyDataSetChanged();
//            }
//        });
    }

    private void getData() {
        paramCategory = getIntent().getStringExtra(NAME);
        paramStrategy = "date";
        String param = String.format(Constant.VIDEO_FIND_DETAIL_PARAM, URLEncoder.encode(paramCategory), paramStrategy);
        try {
            HttpUtils.doPostAsyn(Constant.VIDEO_FIND_DETAIL, param, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*-------------recyclerview 滑动处理--------------*/

    private MOnScrollListener listener = new MOnScrollListener();

    private class MOnScrollListener extends RecyclerView.OnScrollListener {

        private int position;
        private VideoFindDetailAdapter.ViewHolder holder;
        private VideoFindDetailAdapter.ViewHolder lastholder;


        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:

                    if (lastholder != null) {
                        lastholder.getToplay().changeUiToPauseShow();
                        int addProgress = lastholder.getToplay().progressBar.getProgress();
                        lastholder.setVideoProgress(addProgress);
                    }

                    if (lastholder == holder) {
                        recyclerview.scrollToPosition(position);
                        return;
                    }

                    recyclerview.scrollToPosition(position);
                    JCVideoPlayerStandard toplay = holder.getToplay();
                    int progress = holder.getVideoProgress();

                    if (progress == 0) {
                        toplay.startButton.performClick();
                    } else {
                        toplay.progressBar.setProgress(progress);
                        toplay.startButton.performClick();
                    }
                    lastholder = holder;
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:

                    position = manager.findFirstCompletelyVisibleItemPosition();
                    if (position == -1)
                        return;
                    holder = getHolder(position);
                    holder.getShade().setAlpha(0f);

                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int current = manager.findFirstCompletelyVisibleItemPosition();
            if (current == position || current == -1)
                return;
            else {
                holder.getShade().setAlpha(1f);
                position = current;
                holder = getHolder(position);
                holder.getShade().setAlpha(0f);
            }

        }


        private VideoFindDetailAdapter.ViewHolder getHolder(int position) {
            return (VideoFindDetailAdapter.ViewHolder) recyclerview.findViewHolderForAdapterPosition(position);
        }
    }

    /*-------------toolbar_menu--------------*/

    private void initToolbar() {
        toolbar.setTitle("视频分类");
        setSupportActionBar(toolbar);
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


}
