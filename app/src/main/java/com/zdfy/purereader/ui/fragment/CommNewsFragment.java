package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

@SuppressLint("ValidFragment")
public class CommNewsFragment extends BaseFragment {
    private String url;
    public CommNewsFragment(String url) {
        this.url=url;
    }

    @Override
    protected View initViews() {
        return null;
    }
}
