package com.zdfy.purereader.adapter;
import android.support.v4.app.FragmentManager;

import com.zdfy.purereader.ui.fragment.BaseFragment;
import com.zdfy.purereader.ui.fragment.FragmentFactory;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

public class MainPageAdapter extends BasePageAdapter {
    public MainPageAdapter(FragmentManager fm, String[] titles) {
        super(fm, titles);
    }
    @Override
    protected BaseFragment getFragment(int position) {
        return FragmentFactory.createNewsFragment(position,titles[position]);
    }
}
