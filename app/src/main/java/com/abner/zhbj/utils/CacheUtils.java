package com.abner.zhbj.utils;

import android.content.Context;

/**
 * Project   com.abner.zhbj.utils
 *
 * @Author Abner
 * Time   2016/10/13.14:39
 */

public class CacheUtils {

    /**
     * 以url为key，以json为value，保存在本地
     *
     * @param context
     * @param url
     * @param json
     */
    public static void setCache(Context context, String url, String json) {
        PrefUtils.setString(context, url, json);
    }

    /**
     * 以url为key，读取本地缓存
     *
     * @param context
     * @param url
     * @return json
     */
    public static String getCache(Context context, String url) {
        return PrefUtils.getString(context, url, null);
    }

}
