package com.zdfy.purereader.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.zdfy.purereader.R;
import com.zdfy.purereader.adapter.MainPageAdapter;
import com.zdfy.purereader.utils.UiUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by ZhangPeng on 2016/9/6.
 */
public class NewsFragment extends BaseFragment {
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    private String[] tabTitles;
   @Override
    protected View initViews() {
        View view = View.inflate(getActivity(),R.layout.fragment_commnews,null);
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
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
