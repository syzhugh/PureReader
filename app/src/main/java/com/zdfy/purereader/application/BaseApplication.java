package com.zdfy.purereader.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
/**
 * Created by ZhangPeng on 2016/9/5.
 */

/**
 * 进行全局初始化,定义几个常用的量
 */
public class BaseApplication extends Application {
    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
//        x.Ext.init(this);
        context = getApplicationContext();
        handler = new Handler();
        //拿到当前线程的id
        mainThreadId = android.os.Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
