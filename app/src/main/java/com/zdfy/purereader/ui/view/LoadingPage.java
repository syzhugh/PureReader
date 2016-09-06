package com.zdfy.purereader.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.zdfy.purereader.R;
import com.zdfy.purereader.utils.UiUtils;

import static com.zdfy.purereader.constant.Constant.STATE_LOAD_EMPTY;
import static com.zdfy.purereader.constant.Constant.STATE_LOAD_ERROR;
import static com.zdfy.purereader.constant.Constant.STATE_LOAD_LOADING;
import static com.zdfy.purereader.constant.Constant.STATE_LOAD_SUCCESS;
import static com.zdfy.purereader.constant.Constant.STATE_LOAD_UNDO;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

public abstract class LoadingPage extends FrameLayout {
    private int mCurrentState = STATE_LOAD_UNDO;// 当前状态

    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPage(Context context) {
        this(context, null);
    }
    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }
    private void initViews() {
        // 将加载中的布局添加给当前的帧布局
        if (mLoadingPage == null) {
            mLoadingPage = UiUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);
        }
        //初始化加载失败布局
        if (mErrorPage == null) {
            mErrorPage = UiUtils.inflate(R.layout.page_error);
            addView(mErrorPage);
        }
        //初始化加载空布局
        if (mEmptyPage == null) {
            mEmptyPage = UiUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }
        showPage();

    }

    /**
     * 显示界面
     */
    private void showPage() {
        mLoadingPage
                .setVisibility((mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING) ? View.VISIBLE
                        : GONE);
        mErrorPage
                .setVisibility(mCurrentState == STATE_LOAD_ERROR ? VISIBLE
                        : GONE);
        mEmptyPage
                .setVisibility(mCurrentState == STATE_LOAD_EMPTY ? VISIBLE
                        : GONE);
        if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }
        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? VISIBLE : GONE);
        }
    }

    /**
     * 创建成功界面
     *
     * @return
     */
    //创建成功的界面
    protected abstract View onCreateSuccessView();
    
    //返回结果的状态
    protected abstract ResultState onLoad();

    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);
        private int state;

        ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
