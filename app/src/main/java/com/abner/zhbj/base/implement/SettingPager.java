package com.abner.zhbj.base.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.abner.zhbj.base.BasePager;

/**
 * Project   com.abner.zhbj.base.implement
 *
 * @Author Abner
 * Time   2016/10/12.16:13
 */

public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        return super.initView();
    }

    @Override
    public void initData() {
        TextView textView = new TextView(mActivity);
        textView.setText("设置");
        textView.setTextColor(Color.RED);
        textView.setTextSize(28);
        textView.setGravity(Gravity.CENTER);
        flContent.addView(textView);
        tvTitle.setText("设置");

        ibMenu.setVisibility(View.GONE);
    }
}
