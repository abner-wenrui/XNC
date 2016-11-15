package com.abner.zhbj.base.implement.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.abner.zhbj.base.BaseMenuDetailPage;

/**
 * Project   com.abner.zhbj.base.implement.menu
 *
 * 菜单详情页-互动
 *
 * @Author Abner
 * Time   2016/10/13.19:20
 */

public class InteractMenuDetailPager extends BaseMenuDetailPage {
    public InteractMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("菜单详情页-互动");
        textView.setTextSize(30);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
