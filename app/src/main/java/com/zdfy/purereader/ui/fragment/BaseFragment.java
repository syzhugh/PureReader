package com.zdfy.purereader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdfy.purereader.ui.view.LoadingPage;
import com.zdfy.purereader.utils.UiUtils;

import java.util.List;

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

    public void loadData() {
        if (mLoadingPage != null) {
            mLoadingPage.loadData();
        }
    }

    /**
     * 根据返回的数据判断状态
     * @param obj
     * @return
     */
    public LoadingPage.ResultState CheckData(Object obj) {
        if (obj != null) {
            if (obj instanceof List) {
                List data = (List) obj;
                if (data.isEmpty()) {
                    return LoadingPage.ResultState.STATE_EMPTY;
                } else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }

        return LoadingPage.ResultState.STATE_ERROR;
    }
}
