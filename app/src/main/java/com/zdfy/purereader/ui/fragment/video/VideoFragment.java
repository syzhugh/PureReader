package com.zdfy.purereader.ui.fragment.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.VideoPagerAdapter;
import com.zdfy.purereader.constant.Constant;
import com.zdfy.purereader.domain.VideoInfo;
import com.zdfy.purereader.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

public class VideoFragment extends Fragment {

    @Bind(R.id.fragment_video_viewpager)
    ViewPager videoViewpager;
    @Bind(R.id.fragment_video_daily)
    TextView videoDaily;
    @Bind(R.id.fragment_video_findmore)
    TextView videoFindmore;
    @Bind(R.id.fragment_video_fav)
    TextView videoFav;


    private View view;
    private List<Fragment> list;
    private VideoPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_video, null);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {
        list = new ArrayList<>();
        list.add(new VDailyFragment());
        list.add(new VFindFragment());
        list.add(new VFavFragment());
        adapter = new VideoPagerAdapter(getActivity().getSupportFragmentManager(), list);
        videoViewpager.setAdapter(adapter);
        videoViewpager.setOnPageChangeListener(pageChangeListener);
        videoViewpager.setCurrentItem(0);
        videoDaily.setSelected(true);
    }

    /*接口*/

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            videoDaily.setSelected(position == 0);
            videoFindmore.setSelected(position == 1);
            videoFav.setSelected(position == 2);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    /*辅助方法*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.fragment_video_daily, R.id.fragment_video_findmore, R.id.fragment_video_fav})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_video_daily:
                videoViewpager.setCurrentItem(0);
                break;
            case R.id.fragment_video_findmore:
                videoViewpager.setCurrentItem(1);
                break;
            case R.id.fragment_video_fav:
                videoViewpager.setCurrentItem(2);
                break;
        }
    }

}
