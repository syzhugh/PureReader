package com.zdfy.purereader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.List;

import static android.R.attr.fragment;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VideoPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private FragmentManager manager;

    public VideoPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.manager = fm;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        manager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        Fragment fragment = list.get(position);
        manager.beginTransaction().hide(fragment).commit();
    }
}
