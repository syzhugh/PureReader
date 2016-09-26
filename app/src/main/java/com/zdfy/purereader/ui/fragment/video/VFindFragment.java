package com.zdfy.purereader.ui.fragment.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.VideoCategoryAdapter;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.VideoCategoryInfo;
import com.zdfy.purereader.http.protocol.CategoryProtocol;
import com.zdfy.purereader.ui.activity.VideoFindDetailActivity;
import com.zdfy.purereader.ui.fragment.BaseFragment;
import com.zdfy.purereader.ui.view.LoadingPage;
import com.zdfy.purereader.ui.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VFindFragment extends BaseFragment {


    @Bind(R.id.fragment_video_find_recyclerview)
    RecyclerView fragmentVideoFindRecyclerview;

    private View view;
    private List<VideoCategoryInfo> list;
    private VideoCategoryAdapter adapter;

    private CategoryProtocol categoryProtocol;


    @Override
    protected View onCreateSuccessView() {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_find, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initImplViews() {
        mParentRecyclerView = fragmentVideoFindRecyclerview;

        categoryProtocol = new CategoryProtocol();
    }

    @Override
    protected void initUI() {
        /*use for video*/
        initVideoUI();

        RefreshDatas();
    }

    @Override
    protected void setLayoutManager() {
        /*nothing need to do*/
    }

    private void initVideoUI() {
        list = new ArrayList<>();
        adapter = new VideoCategoryAdapter(getActivity(), list);

        mParentlayoutManager = new GridLayoutManager(
                getActivity(), 2, LinearLayoutManager.VERTICAL, false
        );
        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
        mParentRecyclerView.addItemDecoration(decoration);

        mParentRecyclerView.setLayoutManager(mParentlayoutManager);
        mParentRecyclerView.setAdapter(adapter);
        mParentRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initEvents() {
        adapter.setlistener(new VideoCategoryAdapter.onItemCicklistener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), VideoFindDetailActivity.class);
                intent.putExtra(VideoFindDetailActivity.NAME, list.get(position).getName());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected void handleMsgByChild(Message msg) {
        if (list == null)
            list = new ArrayList<>();
        if (msg == null)
            return;
        list.addAll((List<VideoCategoryInfo>) msg.obj);

        adapter.notifyItemInserted(adapter.getItemCount());
    }

    @Override
    protected void RefreshDatas() {
        getDataFromNet(Constant.VIDEO_FIND_MORE);
    }

    private void getDataFromNet(final String findUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<VideoCategoryInfo> data = categoryProtocol.getData(findUrl, 2);
                Message message = mHandler.obtainMessage();
                message.obj = data;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    protected void LoadMoreDatas() {
        /*nothing need to do*/
    }

    @Override
    protected LoadingPage.ResultState onLoad() {
        if (categoryProtocol == null) {
            categoryProtocol = new CategoryProtocol();
        }
        return CheckData(categoryProtocol.getData(Constant.VIDEO_FIND_MORE, 2));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
