package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import com.zdfy.purereader.ui.view.LoadingPage.ResultState;


/**
 * Created by ZhangPeng on 2016/9/6.
 */

@SuppressLint("ValidFragment")
public class CommNewsFragment extends BaseFragment {
//    private String url;
//    public CommNewsFragment(String url) {
//        this.url=url;
//    }
    @Override
    protected ResultState onLoad() {
        return ResultState.STATE_EMPTY;
    }
    @Override
    protected View onCreateSuccessView() {
        return null;
    }
}
