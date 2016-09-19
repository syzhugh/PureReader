package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.NewsAdapter;
import com.zdfy.purereader.constant.ApiConstants;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.NewsInfo.ShowapiResBodyEntity.PagebeanEntity.ContentlistEntity;
import com.zdfy.purereader.ui.activity.NewsDetailActivity;
import com.zdfy.purereader.ui.view.LoadingPage.ResultState;
import com.zdfy.purereader.utils.StringUtils;
import com.zdfy.purereader.utils.UiUtils;
import com.zdfy.purereader.http.protocol.NewsProtocol;

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

    private String channelName;
    private List<ContentlistEntity> contentlist;
    private int mCurrentPage = 1;
    private int mCurrentMaxResult = 20;
    private NewsProtocol protocol;
    private View view;

    public CommNewsFragment(String channelName) {
        this.channelName = channelName;
    }

    @Override
    protected void handleMsgByChild(Message msg) {
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
    }

    @Override
    protected void initImplViews() {
        mParentRecyclerView=mRecyclerView;
        mParentSwipeRefreshLayout=mSwipeRefreshLayout;
    }

    @Override
    protected ResultState onLoad() {
        //返回状态结果
        //return resultState;
        if (protocol == null) {
            protocol = new NewsProtocol();
        }
        String url = ApiConstants.getHttpUrlByChannelName(channelName, mCurrentPage, mCurrentMaxResult);
        return CheckData(protocol.getData(url,1));
     }

    @Override
    protected View onCreateSuccessView() {
        view = UiUtils.inflate(R.layout.fragment_commnews);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void setLayoutManager() {
        mParentRecyclerView.setLayoutManager(mParentlayoutManager);
    }

    @Override
    protected void LoadMoreDatas() {
        RefreshDatas();
    }
    /**
     * 刷新数据
     */
    protected void RefreshDatas() {
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
                List<ContentlistEntity> mdatas = protocol.getData(url,1);
                Message message = mHandler.obtainMessage();
                message.obj = mdatas;
                mHandler.sendMessage(message);
            }
        }).start();
    }
}
