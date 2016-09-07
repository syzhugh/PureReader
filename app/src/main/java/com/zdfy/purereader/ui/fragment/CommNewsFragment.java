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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (contentlist == null) {
                contentlist = new ArrayList<>();
                mAdapter = new NewsAdapter(contentlist);
                mRecyclerView.setAdapter(mAdapter);
            }
            contentlist.addAll((List<ContentlistEntity>) msg.obj);
            mAdapter.notifyDataSetChanged();
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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentMaxResult += 20;
                if (mCurrentMaxResult >= 100) {
                    mCurrentMaxResult = 20;
                    mCurrentPage++;
                }
                String url = ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult);
                getDataFromNet(url);
            }
        });
        layoutManager = new LinearLayoutManager(UiUtils.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        getDataFromNet(ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult));
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

    private void getDataFromNet(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtils.doPost(url, null);
                if (result != null) {
                    NewsInfo newsInfo = JSON.parseObject(result, NewsInfo.class);
                    List<ContentlistEntity> mdatas = newsInfo.getShowapi_res_body().getPagebean().getContentlist();
                    Message message = new Message();
                    message.obj = mdatas;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

}
