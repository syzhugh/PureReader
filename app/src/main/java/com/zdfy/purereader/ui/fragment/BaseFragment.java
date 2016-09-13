package com.zdfy.purereader.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.BaseAdapter;
import com.zdfy.purereader.ui.view.LoadingPage;
import com.zdfy.purereader.ui.view.SpacesItemDecoration;
import com.zdfy.purereader.utils.NetworkStateUtils;
import com.zdfy.purereader.utils.UiUtils;

import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/5.
 */
public abstract class BaseFragment extends Fragment {
    
    LoadingPage mLoadingPage;
    
    //是否在加载
    protected boolean isLoading;
    //刷新的类型,默认下拉刷新
    protected int addDatasType = 1;
    
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsgByChild(msg);
            super.handleMessage(msg);
        }
    };
    private View successView;
    protected LinearLayoutManager mParentlayoutManager;
    protected RecyclerView mParentRecyclerView;
    protected SwipeRefreshLayout mParentSwipeRefreshLayout;
    protected BaseAdapter mAdapter;

    /**
     * 由子类去实现消息异步
     * @param msg
     */
    protected abstract void handleMsgByChild(Message msg);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(UiUtils.getContext()) {
            @Override
            protected View onCreateSuccessView() {
                successView = BaseFragment.this.onCreateSuccessView();
                //关联父类公共控件
                initImplViews();
                //初始化控件以及初始加載的數據
                initUI();
                //处理事务
                initEvents();
                return successView;
            }
            @Override
            protected ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        loadData();
        return mLoadingPage;
    }

    /**
     * 关联SwipeRefreshLayout 和 RecyclerView
     */
    protected abstract void initImplViews();

    /**
     * 返回加载网络数据的结果
     *
     * @return
     */
    protected abstract LoadingPage.ResultState onLoad();
    /**
     * 加载成功的布局
     *
     * @return
     */
    protected abstract View onCreateSuccessView();

    public void loadData() {
        if (mLoadingPage != null) {
            mLoadingPage.loadData();
        }
    }

    /**
     * 根据返回的数据判断状态
     * @param obj
     * @return
     */
    public LoadingPage.ResultState CheckData(Object obj) {
        if (obj != null) {
            if (obj instanceof List) {
                List data = (List) obj;
                if (data.isEmpty()) {
                    return LoadingPage.ResultState.STATE_EMPTY;
                } else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }

        return LoadingPage.ResultState.STATE_ERROR;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mParentlayoutManager=new LinearLayoutManager(UiUtils.getContext());
        setLayoutManager();
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mParentRecyclerView.addItemDecoration(decoration);
        //设置下拉刷新的按钮的颜色
        mParentSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        mParentSwipeRefreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        mParentSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        mParentSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        RefreshDatas();
    }
    protected abstract void setLayoutManager();
    private void initEvents() {
        mParentSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mParentSwipeRefreshLayout.setRefreshing(true);
                        addDatasType = 1;
                        if (!NetworkStateUtils.networkConnected(UiUtils.getContext())) {
                            showNoNetWork();
                        }
                        RefreshDatas();
                    }
                }, 500);
            }
        });

        mParentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = getLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    boolean refreshing = mParentSwipeRefreshLayout.isRefreshing();
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
                                if (!NetworkStateUtils.networkConnected(UiUtils.getContext())) {
                                    showNoNetWork();
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

    protected int getLastVisibleItemPosition() {
        return mParentlayoutManager.findLastVisibleItemPosition();
    }

    /**
     * 加载更多数据
     */
    protected abstract void LoadMoreDatas();
    /**
     * 刷新数据
     */
    protected abstract void RefreshDatas();
    /**
     * 显示没有网络,需要去设置
     */
    protected void showNoNetWork() {
        Snackbar.make(successView, R.string.no_network_connected, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.go_to_set, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }
}
