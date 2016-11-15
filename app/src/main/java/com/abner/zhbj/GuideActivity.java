package com.abner.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private android.support.v4.view.ViewPager vpguide;
    private android.widget.Button btnstart;
    private android.widget.LinearLayout llguide;
    private android.widget.RelativeLayout activityguide;
    private ArrayList<ImageView> mImageViewArrayList;
    private int[] mImageIds = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private ImageView ivredpoint;
    private int mPontDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        this.activityguide = (RelativeLayout) findViewById(R.id.activity_guide);
        this.llguide = (LinearLayout) findViewById(R.id.ll_guide);
        this.btnstart = (Button) findViewById(R.id.btn_start);
        this.vpguide = (ViewPager) findViewById(R.id.vp_guide);
        this.ivredpoint = (ImageView) findViewById(R.id.iv_red_point);
        initData();

        vpguide.setAdapter(new GuideAdapter());

        vpguide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面状态发生改变
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int leftMargin = (int) (positionOffset * mPontDistance + position * mPontDistance);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivredpoint.getLayoutParams();
                layoutParams.leftMargin = leftMargin;
                ivredpoint.setLayoutParams(layoutParams);
            }

            //某个页面被选中
            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewArrayList.size()-1){
                    btnstart.setVisibility(View.VISIBLE);
                }else {
                    btnstart.setVisibility(View.INVISIBLE);
                }
            }

            //当页面滑动过程中的回掉
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //计算两个小圆点的距离
        ivredpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPontDistance = llguide.getChildAt(1).getLeft() - llguide.getChildAt(0).getLeft();
                ivredpoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    //初始化数据
    private void initData() {
        mImageViewArrayList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            mImageViewArrayList.add(view);
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViewArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化Item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewArrayList.get(position);
            container.addView(view);
            return view;
        }

        //销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViewArrayList.get(position));
        }
    }
}
