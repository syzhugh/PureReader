package com.zdfy.purereader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

public class MainPageAdapter extends FragmentPagerAdapter {
    private List<String> titles;

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }
    @Override
    public int getCount() {
        return titles.size();
    }
}
