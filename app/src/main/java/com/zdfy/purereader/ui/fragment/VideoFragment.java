package com.zdfy.purereader.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zdfy.purereader.utils.UiUtils;
/**
 * Created by ZhangPeng on 2016/9/6.
 */

public class VideoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(UiUtils.getContext());
        tv.setText("VideoFragment");
        tv.setTextColor(Color.BLACK);
        return tv;
    }

}
