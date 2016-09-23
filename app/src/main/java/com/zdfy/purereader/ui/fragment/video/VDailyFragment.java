package com.zdfy.purereader.ui.fragment.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.VideoDailyAdapter;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.VideoInfo;
import com.zdfy.purereader.ui.activity.VideoDetailActivity;
import com.zdfy.purereader.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VDailyFragment extends Fragment implements HttpUtils.CallBack, SwipeRefreshLayout.OnRefreshListener, VideoDailyAdapter.onItemCicklistener {


    @Bind(R.id.fragment_video_daily_recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.fragment_video_daily_fresh)
    SwipeRefreshLayout fragmentVideoDailyFresh;

    private View view;
    private List<VideoInfo.IssueListBean.ItemListBean> list;
    private VideoDailyAdapter adapter;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("info", "VDailyFragment:onCreateView----------------------");

        view = inflater.inflate(R.layout.fragment_video_daily, container, false);

        ButterKnife.bind(this, view);

        init();

        return view;

    }

    private void init() {

        list = new ArrayList<>();
        adapter = new VideoDailyAdapter(getActivity(), list);
        adapter.setlistener(this);

        HttpUtils.doGetAsyn(Constant.VIDEO_DAILY, this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        recyclerview.setHasFixedSize(true);


    }


    @Override
    public void onRequestComplete(String result) {

        VideoInfo info = JSON.parseObject(result, VideoInfo.class);
        list.addAll(info.getIssueList().get(0).getItemList());
        list.addAll(info.getIssueList().get(1).getItemList());

        Log.i("info", "VDailyFragment:onRequestComplete----------------------");

//        adapter.notifyDataSetChanged();
        adapter.notifyItemInserted(adapter.getItemCount());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {


    }

    @Override
    public void onClick(View view, int position) {

        Log.i("info", "VDailyFragment:onClick----------------------");

        VideoInfo.IssueListBean.ItemListBean itemListBean = list.get(position);

        Intent it = new Intent(getActivity(), VideoDetailActivity.class);

        it.putExtra("itemListBean", itemListBean);

        getActivity().startActivity(it);

    }


}
