package com.abner.zhbj.domain;

import java.util.ArrayList;

/**
 * Project   com.abner.zhbj.domain
 *
 * @Author Abner
 * Time   2016/10/15.13:53
 */

public class NewsTabBean {
    public NewsTab data;

    public class NewsTab {
        public String more;
        public ArrayList<NewsData> news;
        public ArrayList<TopNewsData> topnews;
    }

    //新闻列表
    public class NewsData {
        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;


    }

    //头条新闻
    public class TopNewsData {
        public int id;
        public String pubdate;
        public String title;
        public String topimage;
        public String news;
        public String url;
    }

}
