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

    public static BaseFragment createFragment(int position, String name) {
        BaseFragment fragment = mFragments.get(position);
        if (fragment == null) {
            /**
             * 根据channel创建不同的Fragment
             */
            fragment = new CommNewsFragment(name);
        }
        if (fragment != null) {
            mFragments.put(position, fragment);
        }
        return fragment;
    }
}
