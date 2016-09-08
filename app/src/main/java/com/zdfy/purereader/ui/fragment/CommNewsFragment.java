package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.NewsAdapter;
import com.zdfy.purereader.constant.ApiConstants;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.zdfy.purereader.ui.view.LoadingPage.ResultState;
import com.zdfy.purereader.utils.UiUtils;
import com.zdfy.purereader.utils.protocol.NewsProtocol;

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
    private ResultState resultState;
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
            if (msgDatas!=null){
                if (msgDatas.size()>20){
                    for (int i = msgDatas.size() - 20; i < msgDatas.size(); i++) {
                        tempDatas.add(msgDatas.get(i));
                    }
                }else{
                    tempDatas=msgDatas;
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
            
            //如何将这个值传递到onLoad()?????????????????;
            ResultState resultState = CheckData(contentlist);
            System.out.println(resultState);
            
            mSwipeRefreshLayout.setRefreshing(false);
            super.handleMessage(msg);
        }
    };
    public CommNewsFragment(String channelName) {
        this.channelName = channelName;
    }
    @Override
    protected ResultState onLoad() {
        return ResultState.STATE_SUCCESS;
    }
    @Override
    protected View onCreateSuccessView() {
        View view = UiUtils.inflate(R.layout.fragment_commnews);
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
                        addDatasType = 1;
                        RefreshDatas();  
                    }
                },500);
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

    private void initUI() {
        layoutManager = new LinearLayoutManager(UiUtils.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        mSwipeRefreshLayout.setRefreshing(true);
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
     * @param url
     */
    private void getDataFromNet(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NewsProtocol protocol=new NewsProtocol();
                List<ContentlistEntity> mdatas = protocol.getData(url);
                Message message = new Message();
                message.obj = mdatas;
                mHandler.sendMessage(message);
            }
        }).start();
    }
    
}
