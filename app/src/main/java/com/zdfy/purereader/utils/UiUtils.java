package com.zdfy.purereader.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import com.zdfy.purereader.application.BaseApplication;
/**
 * Created by ZhangPeng on 2016/9/5.
 */
public class UiUtils {
    
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }
    // /////////////////加载资源文件 ///////////////////////////

    /**
     * 获取字符串
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 获取字符串数组
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    /**
     * 获取图片
     *
     * @param id
     * @return
     */

    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    /**
     * 获取颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    /**
     * 返回像素值
     *
     * @param id
     * @return
     */
    public static int getDimens(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    ////////////////////////屏幕密度转化////////////////////////

    /**
     * dp转px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dip + 0.5f);
    }

    /**
     * px转dip
     *
     * @param px
     * @return
     */
    public static float px2dip(int px) {
        return (px / getContext().getResources().getDisplayMetrics().density);
    }

    //////////////////////加载布局//////////////////////////
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }
    
    ///////////////////判断是否运行在主线程///////////////////
    public static boolean isRunOnUIThread() {
        return Process.myTid() == getMainThreadId() ? true : false;
    }

    /**
     * 在主线程当中运行
     * @param r
     */
    public static void runOnUiThread(Runnable r) {
        //如果是主线程,就让它运行
        if (isRunOnUIThread()) {
            r.run();
        } else {
            //如果是子线程,借助handler使其运行在主线程
            getHandler().post(r);
        }
    }
}
