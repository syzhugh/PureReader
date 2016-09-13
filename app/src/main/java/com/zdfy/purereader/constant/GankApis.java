package com.zdfy.purereader.constant;

/**
 * Created by ZhangPeng on 2016/9/12.
 */
public class GankApis {
    //////////////////GET請求方式////////////////////////
    public static String BaseFuLiImages_Url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/";
    public static String getBaseFuLiImages_Url(int page) {
        return BaseFuLiImages_Url+page;
    }
}