package com.abner.zhbj.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.abner.zhbj.MainActivity;
import com.abner.zhbj.R;
import com.abner.zhbj.base.BasePager;
import com.abner.zhbj.base.implement.GovaFairsPager;
import com.abner.zhbj.base.implement.HomePager;
import com.abner.zhbj.base.implement.NewsCenterPager;
import com.abner.zhbj.base.implement.SettingPager;
import com.abner.zhbj.base.implement.SmartServicePager;
import com.abner.zhbj.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Project   com.abner.zhbj.fragment
 *
 * @Author Abner
 * Time   2016/10/12.10:43
 */

public class ContentFragment extends BaseFragment {

    private NoScrollViewPager mVpContent;
    private ArrayList<BasePager> mPagers;
    private RadioGroup rggroup;

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_content, null);
        mVpContent = (NoScrollViewPager) view.findViewById(R.id.vp_content);
        rggroup = ((RadioGroup) view.findViewById(R.id.rg_group));
        return view;
    }


    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(getActivity()));
        mPagers.add(new NewsCenterPager(getActivity()));
        mPagers.add(new SmartServicePager(getActivity()));
        mPagers.add(new GovaFairsPager(getActivity()));
        mPagers.add(new SettingPager(getActivity()));
        mVpContent.setAdapter(new ContentAdapter());

        rggroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        mVpContent.setCurrentItem(0, false);
                        break;
                    case R.id.rb_newscenter:
                        mVpContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smartservice:
                        mVpContent.setCurrentItem(2, false);
                        break;
                    case R.id.rb_govafairs:
                        mVpContent.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        mVpContent.setCurrentItem(4, false);
                        break;
                }
            }
        });

        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mPagers.get(position);
                pager.initData();

                if (position == 0 || position == mPagers.size() - 1) {
                    setSlidingMenuEnable(false);
                } else {
                    setSlidingMenuEnable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mPagers.get(0).initData();
        setSlidingMenuEnable(false);
    }

    private void setSlidingMenuEnable(boolean enable) {

        final SlidingMenu slidingMenu = ((MainActivity) getActivity()).getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }


    class ContentAdapter extends PagerAdapter {

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
            BasePager pager = mPagers.get(position);
            View view = pager.mRootView;
            //   pager.initData();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
   // 获取新闻中心
    public NewsCenterPager getNewsCenterPager(){
        return ((NewsCenterPager) mPagers.get(1));
    }


}


