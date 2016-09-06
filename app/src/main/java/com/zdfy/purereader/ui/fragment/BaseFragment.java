package com.zdfy.purereader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdfy.purereader.ui.view.LoadingPage;
import com.zdfy.purereader.utils.UiUtils;

import butterknife.ButterKnife;

/**
 * Created by ZhangPeng on 2016/9/5.
 */
public abstract class BaseFragment extends Fragment {
    LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(UiUtils.getContext()) {
            @Override
            protected View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            protected ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        return mLoadingPage;
    }

    /**
     * 返回加载网络数据的结果
     *
     * @return
     */
    protected abstract LoadingPage.ResultState onLoad();

    /**
     * 加载成功的布局
     *
     * @return
     */
    protected abstract View onCreateSuccessView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void loadData() {
        if (mLoadingPage != null) {
            mLoadingPage.loadData();
        }
    }
}
