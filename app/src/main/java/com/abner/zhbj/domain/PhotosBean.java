package com.abner.zhbj.domain;

import java.util.ArrayList;

/**
 * Project   com.abner.zhbj.domain
 *
 * @Author Abner
 * Time   2016/10/20.23:51
 */

public class PhotosBean {
    public PhotosData date;
    public class PhotosData{
        public ArrayList<PhotoNews> news;

    }
    public class PhotoNews{
        public int id;
        public String listimages;
        public String title;
    }
}
