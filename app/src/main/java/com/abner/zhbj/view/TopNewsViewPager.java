package com.abner.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Project   com.abner.zhbj.view
 *
 * @Author Abner
 * Time   2016/10/15.16:20
 */

public class TopNewsViewPager extends ViewPager {

    private int mStartx;
    private int mStarty;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartx = ((int) ev.getX());
                mStarty = ((int) ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int dx = moveX - mStartx;
                int dy = moveY - mStarty;
                if (Math.abs(dx) > Math.abs(dy)) {
                    int currentItem = getCurrentItem();
                    if (dx > 0) {
                        if (currentItem == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        int count = getAdapter().getCount();
                        if (count - 1 == currentItem) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
