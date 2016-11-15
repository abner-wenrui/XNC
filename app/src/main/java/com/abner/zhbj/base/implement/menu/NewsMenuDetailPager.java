package com.abner.zhbj.base.implement.menu;


import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abner.zhbj.MainActivity;
import com.abner.zhbj.R;
import com.abner.zhbj.base.BaseMenuDetailPage;
import com.abner.zhbj.domain.NewsMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Project   com.abner.zhbj.base.implement.menu
 * <p>
 * 菜单详情页-新闻
 *
 * @Author Abner
 * Time   2016/10/13.19:20
 */

public class NewsMenuDetailPager extends BaseMenuDetailPage {

    private ArrayList<NewsMenu.NewsTabData> mTabDatas;
    private ViewPager mViewPager;
    private ArrayList<TabDetialPager> mPagers;
    private TabPageIndicator mIndicator;
    private ImageView ivnextPage;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        mTabDatas = children;
        initData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_news_menu_detail, null);
        mViewPager = ((ViewPager) view.findViewById(R.id.vp_news_menu_detail));
        mIndicator = ((TabPageIndicator) view.findViewById(R.id.indictor));
        ivnextPage = ((ImageView) view.findViewById(R.id.iv_btn_next));
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        for (int i = 0; i < mTabDatas.size(); i++) {
            TabDetialPager tabDetialPager = new TabDetialPager(mActivity, mTabDatas.get(i));
            mPagers.add(tabDetialPager);
        }
        mViewPager.setAdapter(new MyAdapter());
        mIndicator.setViewPager(mViewPager);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setSlidingMenuEnable(true);
                } else {
                    setSlidingMenuEnable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ivnextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });

    }

    private void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }


    class MyAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            NewsMenu.NewsTabData newsTabData = mTabDatas.get(position);
            return newsTabData.title;
        }

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetialPager pager = mPagers.get(position);
            View view = pager.mRootView;
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
