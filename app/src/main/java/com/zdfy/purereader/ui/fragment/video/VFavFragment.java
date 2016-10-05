package com.zdfy.purereader.ui.fragment.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.VideoDailyAdapter;
import com.zdfy.purereader.adapter.VideoFavAdapter;
import com.zdfy.purereader.database.MDataBase;
import com.zdfy.purereader.domain.VideoInfo;
import com.zdfy.purereader.ui.activity.VideoDetailActivity;
import com.zdfy.purereader.ui.fragment.BaseFragment;
import com.zdfy.purereader.ui.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VFavFragment extends BaseFragment {

    @Bind(R.id.fragment_video_fav_recyclerview)
    RecyclerView fragmentVideoFavRecyclerview;

    @Bind(R.id.fragment_video_fav_shade)
    TextView fragmentVideoFavShade;

    private View view;
    private VideoFavAdapter adapter;

    private MDataBase dataBase;
    private List<VideoInfo.IssueListBean.ItemListBean.DataBean> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new MDataBase(getActivity());
    }

    @Override
    protected View onCreateSuccessView() {
        Log.i("info", "VFavFragment:onCreateSuccessView----------------------");
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_fav, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initImplViews() {
        mParentRecyclerView = fragmentVideoFavRecyclerview;
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
        adapter = new VideoFavAdapter(getActivity(), list);
        mParentlayoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mParentRecyclerView.setLayoutManager(mParentlayoutManager);
        mParentRecyclerView.setAdapter(adapter);
        mParentRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initEvents() {
        adapter.setlistener(new VideoDailyAdapter.onItemCicklistener() {
            @Override
            public void onClick(View view, int position) {
                VideoInfo.IssueListBean.ItemListBean itemListBean = new VideoInfo.IssueListBean.ItemListBean();
                itemListBean.setData(list.get(position));

                Intent it = new Intent(getActivity(), VideoDetailActivity.class);
                it.putExtra("itemListBean", itemListBean);
                getActivity().startActivity(it);
            }
        });
        mParentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE)
                    return;
                int lastVisibleItemPosition = getLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    LoadMoreDatas();
                }
            }
        });
    }

    @Override
    protected LoadingPage.ResultState onLoad() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(new Object());
        return CheckData(list);
    }


    private boolean canScroll = true;

    @Override
    protected void handleMsgByChild(Message msg) {
        if (msg == null) {
            return;
        }
        if (msg.obj == null)
            return;

        final List<VideoInfo.IssueListBean.ItemListBean.DataBean> obj = (List<VideoInfo.IssueListBean.ItemListBean.DataBean>) msg.obj;
        if (list.size() != obj.size()) {
            list.clear();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    list.addAll(obj);
                    adapter.notifyDataSetChanged();
                    if (list.size() == 0)
                        fragmentVideoFavShade.setVisibility(View.VISIBLE);
                    else
                        fragmentVideoFavShade.setVisibility(View.GONE);
                }
            });
        }
    }


    private void getDataFromNet() {
        List<VideoInfo.IssueListBean.ItemListBean.DataBean> all = dataBase.getAll();
        Message message = mHandler.obtainMessage();
        message.obj = all;
        mHandler.sendMessage(message);
    }


    @Override
    protected void RefreshDatas() {
        getDataFromNet();
    }

    @Override
    protected void LoadMoreDatas() {
        getDataFromNet();
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshDatas();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}