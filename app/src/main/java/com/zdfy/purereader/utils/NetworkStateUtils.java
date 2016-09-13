package com.zdfy.purereader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ZhangPeng on 2016/9/9.
 */
public class NetworkStateUtils {
    
    /**
     * 判断网络是否连接上了
     * @param mContext
     * @return
     */
    
    public static boolean networkConnected(Context mContext) {
        if (mContext != null) {
            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }
    /**
     * 检查WIFI是否连接
     */
    public static boolean wifiworkConnected(Context mContext){
        if (mContext!=null){
            ConnectivityManager manager= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info!=null){
                if (info.getType()== ConnectivityManager.TYPE_WIFI){
                    return info.isAvailable();
                }
            }
        }
        return false;
    }
    /**
     * 检查移动网络是否连接
     */
    public static boolean mobileDataConnected(Context mContext){
        if (mContext!=null){
            ConnectivityManager manager= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info!=null){
                if (info.getType()== ConnectivityManager.TYPE_MOBILE){
                    return info.isAvailable();
                }
            }
        }
        return false;
    }
}
