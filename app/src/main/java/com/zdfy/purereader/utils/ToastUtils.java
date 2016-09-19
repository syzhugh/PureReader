package com.zdfy.purereader.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZhangPeng on 2016/9/16.
 */
public class ToastUtils {
    /**
     * 单例吐司
     */
    public static Toast mToast=null;
    public static void showToast(Context mContext,String msg){
        if (mToast==null){
            mToast=Toast.makeText(mContext,"",Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
