package com.zdfy.purereader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.rey.material.app.DatePickerDialog;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.BaseAdapter;
import com.zdfy.purereader.adapter.DouBanAdapter;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.constant.ZhiHuApiConstants;
import com.zdfy.purereader.domain.DouBanInfo;
import com.zdfy.purereader.http.protocol.DouBanProtocol;
import com.zdfy.purereader.ui.activity.DouBanDetailActivity;
import com.zdfy.purereader.ui.view.DoubleClick;
import com.zdfy.purereader.ui.view.LoadingPage;
import com.zdfy.purereader.ui.view.OnDoubleClickListener;
import com.zdfy.purereader.utils.SPUtils;
import com.zdfy.purereader.utils.UiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/9.
 */
public class DouBanFragment extends BaseFragment {
    // birthday of douban moment is 2014,5,12
    DouBanProtocol mDouBanProtocol;
    List<DouBanInfo> mPostsEntities;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    // 用于加载对应日期的消息
    private int year, month, day;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDateForUrl();
    }

    private void initDateForUrl() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void handleMsgByChild(Message msg) {
        if (mPostsEntities == null) {
            mPostsEntities = new ArrayList<>();
            mAdapter = new DouBanAdapter(mPostsEntities);
            mParentRecyclerView.setAdapter(mAdapter);
        }
        if (addDatasType == 1 || addDatasType == 3) {
            mPostsEntities.clear();
            mPostsEntities.addAll((List<DouBanInfo>) msg.obj);
            mAdapter.notifyDataSetChanged();
        }

        if (addDatasType == 2) {
            int position = mPostsEntities.size();
            mPostsEntities.addAll((List<DouBanInfo>) msg.obj);
            mAdapter.notifyItemRangeInserted(position, ((List<DouBanInfo>) msg.obj).size());
        }
        if (mParentSwipeRefreshLayout.isRefreshing()) {
            mParentSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(UiUtils.getContext(), DouBanDetailActivity.class);
                intent.putExtra(Constant.DOUBAN_BEAN, mPostsEntities.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void initImplViews() {
        mParentRecyclerView = mRecyclerView;
        mParentSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @Override
    protected LoadingPage.ResultState onLoad() {
        if (mDouBanProtocol == null) {
            mDouBanProtocol = new DouBanProtocol();
        }
        return CheckData(mDouBanProtocol.getData(ZhiHuApiConstants.getDouBanUrl(year, month + 1, day), 2));
    }

    @Override
    protected View onCreateSuccessView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_douban, null);
        ButterKnife.bind(this, view);
        DoubleClick.registerDoubleClickListener(mFab, new OnDoubleClickListener() {
            @Override
            public void OnDoubleClick(View v) {
                mParentRecyclerView.scrollToPosition(0);
            }
            @Override
            public void OnSingleClick(View v) {
                final DatePickerDialog dialog = new DatePickerDialog(getContext());
                System.out.println(year + "-" + month + 1 + "-" + day);
                Calendar calendar = Calendar.getInstance();
                //api于2014.5.12诞生
                calendar.set(2014, 5, 12);
                dialog.dateRange(calendar.getTimeInMillis(), Calendar.getInstance().getTimeInMillis());
                dialog.date(day, month, year);
                dialog.show();
                dialog.positiveAction("确定");
                dialog.negativeAction("取消");
                dialog.positiveActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addDatasType = 3;
                        year = dialog.getYear();
                        month = dialog.getMonth();
                        day = dialog.getDay();
                        RefreshDatas();
                        dialog.dismiss();
                    }
                });
                dialog.negativeActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    @Override
    protected void setLayoutManager() {
            mParentRecyclerView.setLayoutManager(mParentlayoutManager);
    }

    @Override
    protected void LoadMoreDatas() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(year - 1900, month, --day);
        System.out.println(format.format(date));
        String formatss = format.format(date);
        String[] split = formatss.split("-");
        year = Integer.parseInt(split[0]);
        month = Integer.parseInt(split[1]) - 1;
        day = Integer.parseInt(split[2]);
        RefreshDatas();
    }

    @Override
    protected void RefreshDatas() {
        if (!(Boolean) SPUtils.get(UiUtils.getContext(), Constant.FIRST_TO_USE, false)) {
            Snackbar.make(mFab, R.string.first_to_use, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.I_has_know, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SPUtils.put(UiUtils.getContext(), Constant.FIRST_TO_USE, true);
                        }
                    }).show();
        }
        if (addDatasType == 1) {
            initDateForUrl();
        }
        getDataFromNet(ZhiHuApiConstants.getDouBanUrl(year, month + 1, day));
    }

    private void getDataFromNet(final String douBanUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DouBanInfo> data = mDouBanProtocol.getData(douBanUrl, 2);
                Message message = mHandler.obtainMessage();
                message.obj = data;
                mHandler.sendMessage(message);
            }
        }).start();
    }
}


