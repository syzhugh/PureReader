package com.zdfy.purereader.ui.fragment.video;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.VideoDailyAdapter;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.constant.ZhiHuApiConstants;
import com.zdfy.purereader.domain.DouBanInfo;
import com.zdfy.purereader.domain.VideoInfo;
import com.zdfy.purereader.http.protocol.DouBanProtocol;
import com.zdfy.purereader.http.protocol.VideoDailyProtocol;
import com.zdfy.purereader.ui.activity.VideoDetailActivity;
import com.zdfy.purereader.ui.fragment.BaseFragment;
import com.zdfy.purereader.ui.view.LoadingPage;
import com.zdfy.purereader.ui.view.SpacesItemDecoration;
import com.zdfy.purereader.utils.HttpUtils;
import com.zdfy.purereader.utils.NetworkStateUtils;
import com.zdfy.purereader.utils.UiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VDailyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.fragment_video_daily_recyclerview)
    RecyclerView fragmentVideoDailyRecyclerview;
    @Bind(R.id.fragment_video_daily_fresh)
    SwipeRefreshLayout fragmentVideoDailyFresh;

    private View view;
    private VideoDailyAdapter adapter;
    private VideoDailyProtocol dailyProtocol;

    private List<VideoInfo.IssueListBean.ItemListBean> list;


    private int day;


    @Override
    protected View onCreateSuccessView() {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_daily, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initImplViews() {
        mParentRecyclerView = fragmentVideoDailyRecyclerview;
        mParentSwipeRefreshLayout = fragmentVideoDailyFresh;
        Calendar instance = Calendar.getInstance();
        day = instance.get(Calendar.DAY_OF_YEAR);
    }

    @Override
    protected void initUI() {
        /*use for video*/
        initVideoUI();

        mParentSwipeRefreshLayout.setOnRefreshListener(this);
        //设置下拉刷新的按钮的颜色
        mParentSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        //设置手指在屏幕上下拉多少距离开始刷新
        mParentSwipeRefreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        mParentSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        mParentSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        RefreshDatas();
    }

    @Override
    protected void setLayoutManager() {
        /*nothing need to do*/
    }

    private void initVideoUI() {
        list = new ArrayList<>();
        adapter = new VideoDailyAdapter(getActivity(), list);

        mParentlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mParentRecyclerView.setLayoutManager(mParentlayoutManager);
        mParentRecyclerView.setAdapter(adapter);
        mParentRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initEvents() {
        adapter.setlistener(new VideoDailyAdapter.onItemCicklistener() {
            @Override
            public void onClick(View view, int position) {
                VideoInfo.IssueListBean.ItemListBean itemListBean = list.get(position);
                Intent it = new Intent(getActivity(), VideoDetailActivity.class);
                it.putExtra("itemListBean", itemListBean);
                getActivity().startActivity(it);
            }
        });
        mParentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("info", "VDailyFragment:onScrollStateChanged----------------------");
                if (newState != RecyclerView.SCROLL_STATE_IDLE)
                    return;
                Log.i("info", "SCROLL_STATE_IDLE----------------------");
                int lastVisibleItemPosition = getLastVisibleItemPosition();

                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    boolean refreshing = mParentSwipeRefreshLayout.isRefreshing();
                    if (refreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!NetworkStateUtils.networkConnected(UiUtils.getContext())) {
                                    showNoNetWork();
                                    mParentRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1 - 1);
                                }
                                LoadMoreDatas();
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }
        });

    }

    @Override
    protected LoadingPage.ResultState onLoad() {
        if (dailyProtocol == null) {
            dailyProtocol = new VideoDailyProtocol();
        }
        return CheckData(dailyProtocol.getData(Constant.VIDEO_DAILY, 2));
    }


    @Override
    protected void handleMsgByChild(Message msg) {
        if (msg == null) {
            return;
        }
        if (msg.obj == null)
            return;

        switch (msg.arg1) {
            case 0:
                list.clear();
                list.addAll((List<VideoInfo.IssueListBean.ItemListBean>) msg.obj);
                if (mParentSwipeRefreshLayout.isRefreshing())
                    mParentSwipeRefreshLayout.setRefreshing(false);
                break;
            case 1:
                list.addAll((List<VideoInfo.IssueListBean.ItemListBean>) msg.obj);
                break;
        }
        adapter.notifyItemInserted(adapter.getItemCount());
    }


    /*0头部加载，1尾部加载*/
    private void getDataFromNet(final String dailyUrl, final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<VideoInfo.IssueListBean.ItemListBean> data = dailyProtocol.getData(dailyUrl, 2);
                Message message = mHandler.obtainMessage();
                message.arg1 = type;
                message.obj = data;
                mHandler.sendMessage(message);
            }
        }).start();
    }


    @Override
    protected void RefreshDatas() {
        getDataFromNet(Constant.VIDEO_DAILY, 0);
    }

    @Override
    protected void LoadMoreDatas() {
        if (dailyProtocol.getLastPage() != null)
            getDataFromNet(dailyProtocol.getLastPage(), 1);
    }

    @Override
    public void onRefresh() {
        RefreshDatas();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
