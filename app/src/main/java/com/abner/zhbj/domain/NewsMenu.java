package com.abner.zhbj.domain;

import java.util.ArrayList;

/**
 * Project   com.abner.zhbj.domain
 *
 * @Author Abner
 * Time   2016/10/13.13:55
 */

public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<newsMenuData> data;


    //侧边栏菜单的对象
    public class newsMenuData {
        public int id;
        public String title;
        public int type;
        public ArrayList<NewsTabData> children;
    }

    //页签的对象
    public class NewsTabData {
        public int id;
        public String title;
        public int type;
        public String url;
    }
}
