package com.abner.zhbj.utils;

/**
 * Project   com.abner.zhbj.utils
 *
 * @Author Abner
 * Time   2016/10/10.22:59
 */

public class ConstantValue {
    //线上服务器
//    public static final java.lang.String SERVER_URL = "http://zhihuibj.sinaapp.com/zhbj";
    //是否是第一次使用应用
    public static final String IS_FIRST_ENTER = "is_first_enter";
    //服务器主域名
    public static final java.lang.String SERVER_URL = "http://10.0.2.2:8080/zhbj";
    //分类信息接口
    public static final java.lang.String CATEGORY_URL = SERVER_URL + "/categories.json";
    public static final java.lang.String PHOTOS_URL = SERVER_URL + "/photos/photos_1.json";

    //已读的新闻条目
    public static final String READ_IDS = "read_ids";
    //webView字体大小
    public static final String VP_TEXT_SISE = "vp_text_sise";
}
