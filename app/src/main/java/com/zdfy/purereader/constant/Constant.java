package com.zdfy.purereader.constant;

/**
 * Created by ZhangPeng on 2016/9/6.
 */
public class Constant {
    /////////////////根据当前状态来显示不同页面的自定义控件的属性////////////////////
    public static final int STATE_LOAD_UNDO = 1;// 未加载
    public static final int STATE_LOAD_LOADING = 2;// 正在加载
    public static final int STATE_LOAD_ERROR = 3;// 加载失败
    public static final int STATE_LOAD_EMPTY = 4;// 数据为空
    public static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    ///////////////////Intent传递常量/////////////////////////////////////////////
    public static final String NEWS_BEAN = "news_bean";
    public static final String DOUBAN_BEAN = "douban_bean";
    public static final String PIC_URL = "pic_url";
    public static final String PIC_CREATE = "pic_create";
    public static final String ZHIHU_ID = "zhihu_id";
    public static final String ZHIHU_TITLE = "zhihu_title";
    public static final String ZHIHU_IMAGE = "zhihu_image";
    public static final String HAS_NO_BODY = "has_no_body";
    public static final String SHARE_URL = "share_url";
    public static final String IMAGE_IS_NULL = "image_is_null";
    public static final String IMAGE_FROM_NET = "image_from_net";
    public static final String TEXT_DESC ="text_desc" ;
    public static final String HTML = "html";
    ////////////////////Handler常量///////////////////////////////////////////////
    public static final int MSG_BODY_NOT_EXISTS = 0;
    public static final int MSG_BODY_EXISTS = 1;
    public static final int LOADWEBVIEWURL = 2;
    ///////////////////SP部分////////////////////////////////////////////////////
    public static final String FIRST_TO_USE = "first_to_use";
    public static final String HASIN_SPALSH = "hasin_spalsh";


    /*-------------video--------------*/
    //每日精选
    public static final String VIDEO_DAILY="http://baobab.wandoujia.com/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //发现更多
    public static final String VIDEO_FIND_MORE="http://baobab.wandoujia.com/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //热门排行
    public static final String VIDEO_HOT_STRATEGY="http://baobab.wandoujia.com/api/v3/ranklist?num=10&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";
    //发现更多详情接口
    public static final String VIDEO_FIND_DETAIL="http://baobab.wandoujia.com/api/v3/videos?categoryName=%s&strategy=%s&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83";




}


