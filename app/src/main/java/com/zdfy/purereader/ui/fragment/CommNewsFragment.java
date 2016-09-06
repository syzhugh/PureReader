package com.zdfy.purereader.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import com.zdfy.purereader.ui.view.LoadingPage;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

@SuppressLint("ValidFragment")
public class CommNewsFragment extends BaseFragment {
//    private String url;
//    public CommNewsFragment(String url) {
//        this.url=url;
//    }
    LoadingPage mLoadingPage;
    @Override
    protected View initViews() {
        mLoadingPage = new LoadingPage(getContext()) {
            @Override
            protected View onCreateSuccessView() {
                return null;
            }
            @Override
            protected ResultState onLoad() {
                return ResultState.STATE_ERROR;
            }
        };
        return mLoadingPage;
    }
}
