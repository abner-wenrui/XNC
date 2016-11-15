package com.abner.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * Project   com.abner.zhbj.base.implement
 *
 * @Author Abner
 * Time   2016/10/13.19:14
 */

public abstract class BaseMenuDetailPage {

    public View mRootView;
    public Activity mActivity;

    public BaseMenuDetailPage(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    //初始化布局
    public abstract View initView();
    //初始化数据

    public void initData(){

    }

}
