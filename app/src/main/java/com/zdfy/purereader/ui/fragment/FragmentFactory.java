package com.zdfy.purereader.ui.fragment;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

import java.util.HashMap;

/**
 * 批量生产Fragment
 */

public class FragmentFactory {
    public static HashMap<Integer, BaseFragment> mFragments = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = mFragments.get(position);
        if (fragment == null) {
//            fragment = new CommNewsFragment(url);
            fragment=new CommNewsFragment();
        }
        if (fragment != null) {
            mFragments.put(position, fragment);
        }
        return fragment;
    }
}
