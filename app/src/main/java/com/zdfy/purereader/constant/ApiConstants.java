package com.zdfy.purereader.constant;

/**
 * Created by ZhangPeng on 2016/9/7.
 */
public class ApiConstants {

//    channelName 	新闻频道名称，可模糊匹配。用频道关键字即可，如：军事，财经，				国内，国际等等。
//    原App中可以实现的栏目
//    头条 汽车 房产 科技 星座 旅游 时尚 娱乐（划线的不能实现）
//    可以添加的栏目
//    国内 国际 互联网 体育 游戏
//    needHtml		是否需要返回正文的html格式，1为需要，其他为不需要
//    Page			页数，默认1。每页最多20条记录
//    maxResult		每页返回记录数，值在20-200之间
//    needAllList		是否需要最全的返回资料（比较复杂，可以先不用）。包括每一段文				本和每一张图。用list的形式返回。默认是1，改成0

    public static String showapi_appid = "22587";
    public static String showapi_sign = "8d739c2d3b854178957cf8e9de3e9371";
    String HttpFromUrl =
            "https://route.showapi.com/109-35?channelName=" +
                    "国内" +
                    "&page=" +
                    "1" +
                    "&maxResult=" +
                    "20" +
                    "&needHtml=1&needAllList=0&" +
                    "showapi_appid=" + showapi_appid +
                    "&showapi_sign=" + showapi_sign;

    /**
     * 通过频道名称返回地址
     *
     * @param channelName
     * @return
     */
    public static String getHttpUrlByChannelName(String channelName) {
        return "https://route.showapi.com/109-35?channelName="
                + channelName
                + "&page="
                + "1"
                + "&maxResult="
                + "20"
                + "&needHtml=0"
                + "&needAllList=0&"
                + "showapi_appid=" + showapi_appid
                + "&showapi_sign=" + showapi_sign;
    }
    
    public static String getHttpUrlByChannelName(String channelName, int page, int maxResult) {
        return "https://route.showapi.com/109-35?channelName="
                + channelName
                + "&page="
                + page
                + "&maxResult="
                + maxResult
                + "&needHtml=0"
                + "&needAllList=0&"
                + "showapi_appid=" + showapi_appid
                + "&showapi_sign=" + showapi_sign;
    }
}
