package com.zdfy.purereader.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
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
    
    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }
    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public LoadingPage(Context context) {
        super(context);
        initView();
    }
    private void initView() {
        // 初始化加载中的布局
        if (mLoadingPage == null) {
            mLoadingPage = UiUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);// 将加载中的布局添加给当前的帧布局
        }

        // 初始化加载失败布局
        if (mErrorPage == null) {
            mErrorPage = UiUtils.inflate(R.layout.page_error);
            Button mBtnRety= (Button) mErrorPage.findViewById(R.id.btn_retry);
            mBtnRety.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //重新加载数据
                    loadData();
                }
            });
            addView(mErrorPage);
        }

        // 初始化数据为空布局
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
                        : View.GONE);

        mErrorPage
                .setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE
                        : View.GONE);

        mEmptyPage
                .setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE
                        : View.GONE);

        // 当成功布局为空,并且当前状态为成功,才初始化成功的布局
        if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }
        if (mSuccessPage != null) {
            mSuccessPage
                    .setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE
                            : View.GONE);
        }
    }

    /**
     * 创建成功界面
     *
     * @return
     */
    //创建成功的界面
    protected abstract View onCreateSuccessView();

    /**
     * 加载网络数据,返回请求网络结束后的状态
     */
    protected abstract ResultState onLoad();

    /**
     * 枚举类型的状态
     */
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

    /**
     * 开始加载数据
     */
    public void loadData() {
        if (mCurrentState != STATE_LOAD_LOADING) {
            mCurrentState = STATE_LOAD_LOADING;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    //运行在Ui线程
                    UiUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState();
                                //根据状态显示界面
                                showPage();
                            }
                        }
                    });

                }
            }).start();
        }
    }

}
