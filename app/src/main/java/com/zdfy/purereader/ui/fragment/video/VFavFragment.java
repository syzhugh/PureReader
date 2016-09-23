package com.zdfy.purereader.ui.fragment.video;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdfy.purereader.ui.fragment.BaseFragment;
import com.zdfy.purereader.ui.view.LoadingPage;

/**
 * Created by Yaozong on 2016/9/20.
 */

public class VFavFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("info", "VFavFragment:onResume----------------------");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("info", "VFavFragment:onStart----------------------");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("info", "VFavFragment:onPause----------------------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("info","VFavFragment:onDestroy----------------------");
    }
}