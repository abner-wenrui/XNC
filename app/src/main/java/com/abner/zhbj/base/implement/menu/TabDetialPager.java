package com.abner.zhbj.base.implement.menu;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abner.zhbj.NewsDetailActivity;
import com.abner.zhbj.R;
import com.abner.zhbj.base.BaseMenuDetailPage;
import com.abner.zhbj.domain.NewsMenu;
import com.abner.zhbj.domain.NewsTabBean;
import com.abner.zhbj.utils.CacheUtils;
import com.abner.zhbj.utils.ConstantValue;
import com.abner.zhbj.utils.SpUtils;
import com.abner.zhbj.view.PullToRefreshListView;
import com.abner.zhbj.view.TopNewsViewPager;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Project   com.abner.zhbj.base.implement.menu
 *
 * @Author Abner
 * Time   2016/10/14.1:17
 */

public class TabDetialPager extends BaseMenuDetailPage {

    private NewsMenu.NewsTabData mTabData;
    private TopNewsViewPager vptopnews;
    private ArrayList<NewsTabBean.TopNewsData> mTopnews;
    private TextView tvtitle;
    private CirclePageIndicator mIndicator;
    private PullToRefreshListView lvcontent;
    private ArrayList<NewsTabBean.NewsData> mNews;
    private NewsAdapter mNewsAdapter;
    private String mMoreUrl;
    private Handler mHandler;

    public TabDetialPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        lvcontent = ((PullToRefreshListView) view.findViewById(R.id.lv_content));

        View listitemheader = View.inflate(mActivity, R.layout.list_item_header, null);
        vptopnews = ((TopNewsViewPager) listitemheader.findViewById(R.id.vp_top_news));
        tvtitle = ((TextView) listitemheader.findViewById(R.id.tv_title));
        mIndicator = ((CirclePageIndicator) listitemheader.findViewById(R.id.indictor));
        lvcontent.addHeaderView(listitemheader);

        lvcontent.setOnRefreshListener(new PullToRefreshListView.onRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLode() {
                lodeMoreFromServer();
            }
        });

        lvcontent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int headerViewsCount = lvcontent.getHeaderViewsCount();
                position -= headerViewsCount;
                NewsTabBean.NewsData news = mNews.get(position);
                String readIds = SpUtils.getString(mActivity, ConstantValue.READ_IDS, "");
                if (!readIds.contains(news.id + ",")) {
                    readIds += mNews.get(position).id + ",";
                    SpUtils.setString(mActivity, ConstantValue.READ_IDS, readIds);
                }
                TextView new_name = (TextView) view.findViewById(R.id.new_name_info);
                new_name.setTextColor(Color.GRAY);
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", mNews.get(position).url);
                mActivity.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(mActivity, ConstantValue.SERVER_URL + mTabData.url);
        if (TextUtils.isEmpty(cache)) {
            getDataFromServer();
        } else {
            procressData(cache, false);
        }

    }

    private void lodeMoreFromServer() {
        if (mMoreUrl != null) {
            getMoreDataFromServer();
        } else {
            Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
            lvcontent.onRefreshComplete();
        }
    }

    private void getMoreDataFromServer() {
        RequestParams params = new RequestParams(mMoreUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                procressData(result, true);
                Toast.makeText(mActivity, "成功了。。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                lvcontent.onRefreshComplete();
            }
        });
    }

    private void getDataFromServer() {

        RequestParams params = new RequestParams(ConstantValue.SERVER_URL + mTabData.url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                procressData(result, false);
                CacheUtils.setCache(mActivity, ConstantValue.SERVER_URL + mTabData.url, result);
                lvcontent.onRefreshTime();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, "刷新失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                lvcontent.onRefreshComplete();
            }
        });
    }

    private void procressData(String result, boolean isMore) {
        Gson gson = new Gson();
        NewsTabBean newsDetail = gson.fromJson(result, NewsTabBean.class);
        mTopnews = newsDetail.data.topnews;
        mMoreUrl = newsDetail.data.more;
        if (TextUtils.isEmpty(mMoreUrl)) {
            mMoreUrl = null;
        } else {
            mMoreUrl = ConstantValue.SERVER_URL + mMoreUrl;
        }

        if (!isMore) {
            if (mTopnews != null) {
                vptopnews.setAdapter(new TopNewsAdapter());
                mIndicator.setViewPager(vptopnews);
                mIndicator.setSnap(true);

                mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        tvtitle.setText(mTopnews.get(position).title);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                tvtitle.setText(mTopnews.get(0).title);
                mNews = newsDetail.data.news;
                if (mNews != null) {
                    mNewsAdapter = new NewsAdapter();
                    lvcontent.setAdapter(mNewsAdapter);
                }

                if (mHandler == null) {
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            int currentItem = vptopnews.getCurrentItem();
                            currentItem++;
                            if (currentItem>mTopnews.size()-1){
                                currentItem=0;
                            }
                            vptopnews.setCurrentItem(currentItem);
                            mHandler.sendEmptyMessageDelayed(0, 2000);
                        }
                    };
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                    vptopnews.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    mHandler.removeCallbacksAndMessages(null);
                                    break;
                                case MotionEvent.ACTION_CANCEL:
                                    mHandler.sendEmptyMessageDelayed(0, 2000);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    mHandler.sendEmptyMessageDelayed(0, 2000);
                                    break;
                            }
                            return false;
                        }
                    });
                }

            }
        } else {
            ArrayList<NewsTabBean.NewsData> moreNews = newsDetail.data.news;
            mNews.addAll(moreNews);
            mNewsAdapter.notifyDataSetChanged();
        }
    }

    class TopNewsAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mTopnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setImageResource(R.drawable.topnews_item_default);
            ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.topnews_item_default).build();
            x.image().bind(view, mTopnews.get(position).topimage, options);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNews.size();
        }

        @Override
        public NewsTabBean.NewsData getItem(int position) {
            return mNews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewInfoViewHolder holder;
            if (convertView == null) {
                holder = new NewInfoViewHolder();
                convertView = View.inflate(mActivity, R.layout.new_detail_info, null);
                holder.newimageinfo = (ImageView) convertView.findViewById(R.id.new_image_info);
                holder.newnameinfo = ((TextView) convertView.findViewById(R.id.new_name_info));
                holder.newtimeinfo = ((TextView) convertView.findViewById(R.id.new_time_info));
                convertView.setTag(holder);
            } else {
                holder = (NewInfoViewHolder) convertView.getTag();
            }
            ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.image_demo).build();
          x.image().bind(holder.newimageinfo, mNews.get(position).listimage, options);


            holder.newnameinfo.setText(getItem(position).title);
            holder.newtimeinfo.setText(getItem(position).pubdate);

            String readIds = SpUtils.getString(mActivity, ConstantValue.READ_IDS, "");
            if (readIds.contains(getItem(position).id + ",")) {
                holder.newnameinfo.setTextColor(Color.GRAY);
            }

            return convertView;
        }
    }

    class NewInfoViewHolder {
        ImageView newimageinfo;
        TextView newnameinfo;
        TextView newtimeinfo;
    }
}
