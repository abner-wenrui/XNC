package com.abner.zhbj.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.abner.zhbj.MainActivity;
import com.abner.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Project   com.abner.zhbj.base
 *五个标签页的基类
 * @Author Abner
 * Time   2016/10/12.16:01
 */

public class BasePager {
    public Activity mActivity;
    public TextView tvTitle;
    public ImageButton ibMenu;
    public FrameLayout flContent;
    public  View mRootView;

    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_page, null);
        tvTitle = ((TextView) view.findViewById(R.id.tv_title));
        ibMenu = ((ImageButton) view.findViewById(R.id.ib_menu));
        flContent = ((FrameLayout) view.findViewById(R.id.fl_contetnt));
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return view;
    }
    /**
     * 打开或者关闭侧边栏
     */
    private void toggle() {
        SlidingMenu slidingMenu = ((MainActivity) mActivity).getSlidingMenu();
        slidingMenu.toggle();
    }

    public void initData() {

    }
}
