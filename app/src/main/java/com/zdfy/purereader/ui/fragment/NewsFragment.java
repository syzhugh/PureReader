package com.zdfy.purereader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_news, null);
        ButterKnife.bind(this, view);
        tabTitles = UiUtils.getStringArray(R.array.tabTitles);
        MainPageAdapter adapter = new MainPageAdapter(getActivity().getSupportFragmentManager(), tabTitles);
        mViewpager.setAdapter(adapter);
        mTabs.post(new Runnable() {
            @Override
            public void run() {
                mTabs.setupWithViewPager(mViewpager);
            }
        });
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                BaseFragment fragment = FragmentFactory.createFragment(tab.getPosition(), tabTitles[tab.getPosition()]);
                fragment.loadData();
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
