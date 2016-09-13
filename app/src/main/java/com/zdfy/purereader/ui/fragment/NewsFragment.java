package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.MainPageAdapter;
import com.zdfy.purereader.utils.UiUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/6.
 */
public class NewsFragment extends Fragment {
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    private String[] tabTitles;
    private int oldPosition = 0;
    private FragmentTransaction transaction;
    private BaseFragment fragment;
    @SuppressLint("CommitTransaction")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_news, null);
        ButterKnife.bind(this, view);
        tabTitles = UiUtils.getStringArray(R.array.tabTitles);
        MainPageAdapter adapter = new MainPageAdapter(getChildFragmentManager(), tabTitles);
        mViewpager.setAdapter(adapter);
        //fragment页面预加载,解决数据丢失的问题
        mViewpager.setOffscreenPageLimit(10);
        mTabs.post(new Runnable() {
            @Override
            public void run() {
                mTabs.setupWithViewPager(mViewpager);
            }
        });
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragment = FragmentFactory.createNewsFragment(tab.getPosition(), tabTitles[tab.getPosition()]);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }
}
