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
}
