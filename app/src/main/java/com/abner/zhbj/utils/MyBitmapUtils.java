package com.abner.zhbj.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.abner.zhbj.R;

/**
 * Project   com.abner.zhbj.utils
 *
 * @Author Abner
 * Time   2016/10/21.16:08
 */

public class MyBitmapUtils {

    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils() {
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
        mMemoryCacheUtils = new MemoryCacheUtils();
    }

    public void display(ImageView imageView, String url) {
        //设置默认图片
        imageView.setImageResource(R.drawable.pic_item_list_default);
        //优先从内存加载图片
        Bitmap bitmap = mMemoryCacheUtils.getMenoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Log.i("-----", "从内存中加载图片了");
            return;
        }
        //其次从本地sdcard加载图片
        bitmap = mLocalCacheUtils.getLocalCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Log.i("-------", "从本地加载图片了");
            mMemoryCacheUtils.setMemoryCache(url, bitmap);
            return;
        }
        //最后从网络下载图片
        Log.i("-------", "从网络下载图片了");
        mNetCacheUtils.getBitMapFromNet(imageView, url);
    }
}
