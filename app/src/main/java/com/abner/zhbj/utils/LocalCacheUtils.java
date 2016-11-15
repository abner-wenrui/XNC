package com.abner.zhbj.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Project   com.abner.zhbj.utils
 *
 * @Author Abner
 * Time   2016/10/23.13:49
 */

public class LocalCacheUtils {

    private static final String LOCAL_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache";

    //写缓存
    public void setLocalCache(String url, Bitmap bitmap) {
        File dir = new File(LOCAL_CACHE_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();   //创建文件夹
        }
        try {
            String fileName = MD5Encoder.encode(url);
            File cacheFile = new File(dir, fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cacheFile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读缓存
    public Bitmap getLocalCache(String url) {
        try {
            File cacheFile = new File(LOCAL_CACHE_PATH, MD5Encoder.encode(url));
            if (cacheFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(cacheFile));
                return bitmap;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
