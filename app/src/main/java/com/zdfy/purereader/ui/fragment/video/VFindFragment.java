package com.zdfy.purereader.ui.fragment.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.VideoCategoryAdapter;
import com.zdfy.purereader.adapter.VideoDailyAdapter;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.VideoCategoryInfo;
import com.zdfy.purereader.domain.VideoInfo;
import com.zdfy.purereader.ui.activity.VideoDetailActivity;
import com.zdfy.purereader.ui.activity.VideoFindDetailActivity;
import com.zdfy.purereader.ui.view.DividerItemDecoration;
import com.zdfy.purereader.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VFindFragment extends Fragment implements HttpUtils.CallBack, VideoCategoryAdapter.onItemCicklistener {

    @Bind(R.id.fragment_video_daily_recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.fragment_video_daily_fresh)
    SwipeRefreshLayout freshLayout;
    private View view;


    private List<VideoCategoryInfo> list;
    private VideoCategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_video_daily, container, false);

        ButterKnife.bind(this, view);

        init();

        return view;
    }


    private void init() {

        list = new ArrayList<>();
        adapter = new VideoCategoryAdapter(getActivity(), list);
        adapter.setlistener(this);

        HttpUtils.doGetAsyn(Constant.VIDEO_FIND_MORE, this);

        GridLayoutManager manager = new GridLayoutManager(getActivity(),
                2, LinearLayoutManager.VERTICAL, false);

        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        recyclerview.setHasFixedSize(true);

    }


    @Override
    public void onRequestComplete(String result) {
        Log.i("info", "VFindFragment:onRequestComplete----------------------");
        Log.i("info", "" + result);

        JSONArray array = JSONArray.parseArray(result);
        for (int i = 0; i < array.size(); i++) {
            list.add(JSON.parseObject(array.get(i).toString(), VideoCategoryInfo.class));
        }

        adapter.notifyItemInserted(adapter.getItemCount());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(getActivity(), VideoFindDetailActivity.class);
        intent.putExtra(VideoFindDetailActivity.NAME, list.get(position).getName());
        getActivity().startActivity(intent);
    }
}
