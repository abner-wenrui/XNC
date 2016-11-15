package com.abner.zhbj.utils;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Project   com.abner.zhbj.utils
 *
 * @Author Abner
 * Time   2016/10/25.19:16
 */

public class MemoryCacheUtils {
    //private HashMap<String, Bitmap> mMemoryCache = new HashMap<String, Bitmap>();
    //private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<String, SoftReference<Bitmap>>();
    LruCache<String, Bitmap> mMenoryCache;


    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.i("-------", maxMemory + "");
        mMenoryCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {
            //返回每张图片大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };

    }

    //写缓存
    public void setMemoryCache(String url, Bitmap bitmap) {
        //  mMemoryCache.put(url, bitmap);
//        SoftReference<Bitmap> bitmapSoftReference = new SoftReference<>(bitmap);
//        mMemoryCache.put(url,bitmapSoftReference);
        mMenoryCache.put(url, bitmap);
    }

    //读缓存
    public Bitmap getMenoryCache(String url) {
//        SoftReference<Bitmap> bitmapSoftReference = mMemoryCache.get(url);
//        if (bitmapSoftReference != null){
//            return bitmapSoftReference.get();
//        }
//        return null;
        return mMenoryCache.get(url);
    }

}
