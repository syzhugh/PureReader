package com.zdfy.purereader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.zdfy.purereader.ui.fragment.BaseFragment;

/**
 * Created by ZhangPeng on 2016/9/12.
 */
public abstract class BasePageAdapter extends FragmentPagerAdapter {
    protected String[] titles;
    public BasePageAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles=titles;
    }
    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    protected abstract BaseFragment getFragment(int position);
//        return FragmentFactory.createFragment(position,titles[position]);
//    }

    @Override
    public int getCount() {
        return titles==null?0:titles.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
