package com.zdfy.purereader.ui.fragment;

import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.ImgAdapter;
import com.zdfy.purereader.constant.GankApis;
import com.zdfy.purereader.domain.GankImgInfo.ResultsEntity;
import com.zdfy.purereader.http.protocol.GankImgProtocol;
import com.zdfy.purereader.ui.view.LoadingPage;
import com.zdfy.purereader.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

public class PicFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<ResultsEntity> imgLists;
    private int page = 1;
    private GankImgProtocol mImgProtocol;
    private View view;
    //    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void handleMsgByChild(Message msg) {
        if (imgLists == null) {
            imgLists = new ArrayList<>();
            mAdapter = new ImgAdapter(imgLists);
            mParentRecyclerView.setAdapter(mAdapter);
        }
        if (addDatasType == 1) {
            imgLists.clear();
            imgLists.addAll((List<ResultsEntity>) msg.obj);
            mAdapter.notifyDataSetChanged();
        }
        if (addDatasType == 2) {
            int position = imgLists.size();
            imgLists.addAll((List<ResultsEntity>) msg.obj);
            mAdapter.notifyItemRangeInserted(position, ((List<ResultsEntity>) msg.obj).size());
        }
        if (mParentSwipeRefreshLayout.isRefreshing()) {
            mParentSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void initImplViews() {
        mParentSwipeRefreshLayout = mSwipeRefreshLayout;
        mParentRecyclerView = mRecyclerView;
    }

    @Override
    protected LoadingPage.ResultState onLoad() {
        if (mImgProtocol == null) {
            mImgProtocol = new GankImgProtocol();
        }
        return CheckData(mImgProtocol.getData(GankApis.getBaseFuLiImages_Url(page), 2));
    }

    @Override
    protected View onCreateSuccessView() {
        view = View.inflate(getActivity(), R.layout.fragment_pic, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void setLayoutManager() {
        //设置layoutManager
        gridLayoutManager = new GridLayoutManager(UiUtils.getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mParentRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected int getLastVisibleItemPosition() {
        return ((GridLayoutManager) gridLayoutManager).findLastVisibleItemPosition();
    }

    @Override
    protected void LoadMoreDatas() {
        RefreshDatas();
    }

    @Override
    protected void RefreshDatas() {
        if (addDatasType == 1) {
            page = 1;
        } 
        if (addDatasType==2){
            page++;  
        }
        getDataFromNet(GankApis.getBaseFuLiImages_Url(page));
    }

    private void getDataFromNet(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ResultsEntity> data = mImgProtocol.getData(url, 2);
                System.out.println(url);
                Message message = mHandler.obtainMessage();
                message.obj = data;
                mHandler.sendMessage(message);
            }
        }).start();
    }
}
