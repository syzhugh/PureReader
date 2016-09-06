package com.zdfy.purereader.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zdfy.purereader.ui.fragment.FragmentFactory;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

public class MainPageAdapter extends FragmentPagerAdapter {
    private String[] titles;

    public MainPageAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }
    @Override
    public int getCount() {
        return titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
