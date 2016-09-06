package com.zdfy.purereader.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.zdfy.purereader.utils.UiUtils;

/**
 * Created by ZhangPeng on 2016/9/6.
 */

public class VideoFragment extends BaseFragment {

    @Override
    protected View initViews() {
        TextView tv = new TextView(UiUtils.getContext());
        tv.setText("VideoFragment");
        tv.setTextColor(Color.BLACK);
        return tv;
    }
}
