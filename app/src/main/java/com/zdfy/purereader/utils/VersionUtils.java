package com.zdfy.purereader.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by ZhangPeng on 2016/7/3.
 */
public class VersionUtils {
    /*获取包的的版本号Name*/
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            //获取包的信息
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*获取本地app包的的版本号*/
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            //获取包的信息
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
