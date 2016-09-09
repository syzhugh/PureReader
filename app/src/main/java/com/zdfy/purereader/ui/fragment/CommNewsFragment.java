package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.NewsAdapter;
import com.zdfy.purereader.constant.ApiConstants;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.zdfy.purereader.ui.activity.NewsDetailActivity;
import com.zdfy.purereader.ui.view.LoadingPage.ResultState;
import com.zdfy.purereader.utils.NetworkStateUtils;
import com.zdfy.purereader.utils.StringUtils;
import com.zdfy.purereader.utils.UiUtils;
import com.zdfy.purereader.utils.protocol.NewsProtocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/6.
 */
@SuppressLint("ValidFragment")
public class CommNewsFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsAdapter mAdapter;
    private String channelName;
    private LinearLayoutManager layoutManager;
    private List<ContentlistEntity> contentlist;
    private int mCurrentPage = 1;
    private int mCurrentMaxResult = 20;
    boolean isLoading;
    private int addDatasType = 1;
    private NewsProtocol protocol;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (contentlist == null) {
                contentlist = new ArrayList<>();
                mAdapter = new NewsAdapter(contentlist);
                mRecyclerView.setAdapter(mAdapter);
            }
            List<ContentlistEntity> msgDatas = (List<ContentlistEntity>) msg.obj;
            List<ContentlistEntity> tempDatas = new ArrayList<>();
            if (msgDatas != null) {
                if (msgDatas.size() > 20) {
                    for (int i = msgDatas.size() - 20; i < msgDatas.size(); i++) {
                        if (!StringUtils.isEmpty(msgDatas.get(i).getTitle()) &&
                                (msgDatas.get(i).getImageurls() != null) &&
                                (msgDatas.get(i).getImageurls().size() >= 0)) {
                            tempDatas.add(msgDatas.get(i));
                        }
                    }
                } else {
                    tempDatas = msgDatas;
                }
            }

            if (addDatasType == 1) {
                contentlist.addAll(0, tempDatas);
                mAdapter.notifyItemRangeInserted(0, tempDatas.size());
            }

            if (addDatasType == 2) {
                int position = contentlist.size() - 1;
                contentlist.addAll(tempDatas);
                mAdapter.notifyItemRangeInserted(position, tempDatas.size());
            }

            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(UiUtils.getContext(), NewsDetailActivity.class);
                    intent.putExtra(Constant.NEWS_BEAN, (Serializable) contentlist.get(position));
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            super.handleMessage(msg);
        }
    };
    private View view;


    public CommNewsFragment(String channelName) {
        this.channelName = channelName;
    }

    @Override
    protected ResultState onLoad() {
        //返回状态结果
        //return resultState;
        if (protocol == null) {
            protocol = new NewsProtocol();
        }
        String url = ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult);
        return CheckData(protocol.getData(url));
    }

    @Override
    protected View onCreateSuccessView() {
        view = UiUtils.inflate(R.layout.fragment_commnews);
        ButterKnife.bind(this, view);
        //初始化控件以及初始加載的數據
        initUI();
        //处理事务
        initEvents();
        return view;
    }

    private void initEvents() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                        addDatasType = 1;
                        if (!NetworkStateUtils.networkConnected(UiUtils.getContext())) {
                            showNoNetWork();
                        }
                        RefreshDatas();
                    }
                }, 500);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {

                    boolean refreshing = mSwipeRefreshLayout.isRefreshing();

                    if (refreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }

                    if (!isLoading) {
                        isLoading = true;
                        addDatasType = 2;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RefreshDatas();
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }
        });
    }

    /**
     * 显示没有网络,需要去设置
     */
    private void showNoNetWork() {
        Snackbar.make(view, R.string.no_network_connected, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.go_to_set, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }

    private void initUI() {
        layoutManager = new LinearLayoutManager(UiUtils.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //设置下拉刷新的按钮的颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        mSwipeRefreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        getDataFromNet(ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult));
    }

    /**
     * 刷新数据
     */
    private void RefreshDatas() {
        mCurrentMaxResult += 20;
        mCurrentPage += 1;
        if (mCurrentMaxResult > 100) {
            mCurrentMaxResult = 20;
        }
        String url = ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult);
        getDataFromNet(url);
    }

    /**
     * 从网络获取数据
     *
     * @param url
     */
    private void getDataFromNet(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ContentlistEntity> mdatas = protocol.getData(url);
                Message message = mHandler.obtainMessage();
                message.obj = mdatas;
                mHandler.sendMessage(message);
            }
        }).start();
    }
}
