package com.abner.zhbj.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Project   com.abner.zhbj.utils
 *
 * @Author Abner
 * Time   2016/10/21.16:00
 */

public class NetCacheUtils {

    private final LocalCacheUtils mLocalCacheUtils;
    private final MemoryCacheUtils mMemoryCacheUtils;
    private ImageView imageView;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    public void getBitMapFromNet(ImageView imageView, String url) {
        new BitmapTask().execute(imageView, url);
    }

    class BitmapTask extends AsyncTask<Object, Integer, Bitmap> {

        private String url;

        //预加载。运行在主线程
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //正在加载，运行在子线程
        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];

            //           imageView.setTag(imageView);

            //开始下载图片
            Bitmap bitmap = download(url);
            return bitmap;
        }

        //进度更新，主线程
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //取消
        @Override
        protected void onCancelled(Bitmap aVoid) {
            super.onCancelled(aVoid);
        }

        //加载结束，运行在主线程
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {

                //             String url = (String) imageView.getTag();
                //           if (url.equals(this.url)) {
                imageView.setImageBitmap(result);
                //         }
            }
            mLocalCacheUtils.setLocalCache(url, result);
            mMemoryCacheUtils.setMemoryCache(url,result);
            super.onPostExecute(result);
        }
    }

    //下载图片
    private Bitmap download(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);   //连接超时
            conn.setReadTimeout(5000);      //读取超时

            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream( is);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}
