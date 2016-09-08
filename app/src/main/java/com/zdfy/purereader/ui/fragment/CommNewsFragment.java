package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.NewsAdapter;
import com.zdfy.purereader.constant.ApiConstants;
import com.zdfy.purereader.domain.NewsInfo;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.zdfy.purereader.ui.view.LoadingPage.ResultState;
import com.zdfy.purereader.utils.HttpUtils;
import com.zdfy.purereader.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/6.
 */
@SuppressLint("ValidFragment")
public class CommNewsFragment extends BaseFragment {
    //    @Bind(R.id.banner)
//    Banner mBanner;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ContentlistEntity> datas;
    private NewsAdapter mAdapter;
    private String channelName;
    private LinearLayoutManager layoutManager;
    private List<ContentlistEntity> contentlist;
    private int mCurrentPage = 1;
    private int mCurrentMaxResult = 20;
    boolean isLoading;
    private int addDatasType = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (contentlist == null) {
                contentlist = new ArrayList<>();
                mAdapter = new NewsAdapter(contentlist);
                mRecyclerView.setAdapter(mAdapter);
            }
           
            if (addDatasType == 1) {
                contentlist.addAll(0, (List<ContentlistEntity>)msg.obj);
                System.out.println(contentlist.size());
                mAdapter.notifyItemRangeInserted(0, ((List<ContentlistEntity>) msg.obj).size());
            }
            if (addDatasType == 2) {
                int position = contentlist.size() - 1;
                contentlist.addAll((List<ContentlistEntity>)msg.obj);
                mAdapter.notifyItemRangeInserted(position, ((List<ContentlistEntity>)msg.obj).size());
            }
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
        layoutManager = new LinearLayoutManager(UiUtils.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        mSwipeRefreshLayout.setRefreshing(true);
        getDataFromNet(ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addDatasType = 1;
                RefreshDatas();
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

//        RequestParams params=new RequestParams(ApiConstants.getHttpUrlByChannelName(channelName));
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                System.out.println(result);
//                if (result!=null){
//                    NewsInfo newsInfo= JSON.parseObject(result, NewsInfo.class);
//                     contentlist = newsInfo.getShowapi_res_body().getPagebean().getContentlist();
//                    System.out.println(contentlist.size());
//                    mAdapter=new NewsAdapter(contentlist);
//                    mRecyclerView.setAdapter(mAdapter);
//                }
//            
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(UiUtils.getContext(), "ERROR", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
        return view;
    }

    private void RefreshDatas() {
        mCurrentMaxResult += 20;
        if (mCurrentMaxResult > 100) {
            mCurrentPage++;
            mCurrentMaxResult = 20;
        }
        String url = ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult);
        getDataFromNet(url);
    }

    private void getDataFromNet(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtils.doPost(url, null);
                if (result != null) {
                    NewsInfo newsInfo = JSON.parseObject(result, NewsInfo.class);

                    if (newsInfo != null) {
                        List<ContentlistEntity> mdatas = newsInfo.getShowapi_res_body().getPagebean().getContentlist();
                        Message message = new Message();
                        message.obj = mdatas;
                        mHandler.sendMessage(message);
                    }

                }
            }
        }).start();
    }

}
