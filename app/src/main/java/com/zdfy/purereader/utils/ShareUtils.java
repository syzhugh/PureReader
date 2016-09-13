package com.zdfy.purereader.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ZhangPeng on 2016/9/12.
 */
public class ShareUtils {
    public static void shareUrl(Context context,String title,String url){
        Intent sendIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, title + " " + url + " 分享来自~PureReader");
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "分享到"));
    } 
}
