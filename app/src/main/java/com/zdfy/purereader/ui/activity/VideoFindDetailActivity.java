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

        LinearLayoutManager manager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        recyclerview.setHasFixedSize(true);
        recyclerview.setBackgroundColor(Color.BLUE);


        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                LinearLayoutManager getManager = (LinearLayoutManager) layoutManager;
                Log.i("info", "VideoFindDetailActivity:onScrollStateChanged----------------------");

                Log.i("info", "findFirstVisible:" + getManager.findFirstVisibleItemPosition());
                Log.i("info", "findFirstCompletelyVisible:" + getManager.findFirstCompletelyVisibleItemPosition());
                Log.i("info", "findLastVisible:" + getManager.findLastVisibleItemPosition());
                Log.i("info", "findLastCompletelyVisible:" + getManager.findLastCompletelyVisibleItemPosition());
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
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
//        adapter.notifyItemInserted(adapter.getItemCount());


        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
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
