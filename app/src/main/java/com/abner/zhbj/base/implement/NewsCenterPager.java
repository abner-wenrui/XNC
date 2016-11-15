package com.abner.zhbj.base.implement;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.abner.zhbj.MainActivity;
import com.abner.zhbj.base.BaseMenuDetailPage;
import com.abner.zhbj.base.BasePager;
import com.abner.zhbj.base.implement.menu.InteractMenuDetailPager;
import com.abner.zhbj.base.implement.menu.NewsMenuDetailPager;
import com.abner.zhbj.base.implement.menu.PhotosMenuDetailPager;
import com.abner.zhbj.base.implement.menu.TopicMenuDetailPager;
import com.abner.zhbj.domain.NewsMenu;
import com.abner.zhbj.fragment.LeftMenuFragment;
import com.abner.zhbj.utils.CacheUtils;
import com.abner.zhbj.utils.ConstantValue;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;


/**
 * Project   com.abner.zhbj.base.implement
 *
 * @Author Abner
 * Time   2016/10/12.16:13
 */

public class NewsCenterPager extends BasePager {

    private ArrayList<BaseMenuDetailPage> mMenuDetailPages;
    private NewsMenu mNewsData;

    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        return super.initView();
    }

    @Override
    public void initData() {
        tvTitle.setText("新闻");
        ibMenu.setVisibility(View.VISIBLE);

        String cache = CacheUtils.getCache(mActivity, ConstantValue.CATEGORY_URL);
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        } else {
            getDataFromServer();
        }
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(ConstantValue.CATEGORY_URL);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                processData(result);
                CacheUtils.setCache(mActivity, ConstantValue.CATEGORY_URL, result);
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

            }
        });
    }

    private void processData(String json) {
        Gson gson = new Gson();
        mNewsData = gson.fromJson(json, NewsMenu.class);
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = mainUI.getLeftMenuFragment();
        leftMenuFragment.setMenuData(mNewsData.data);

        mMenuDetailPages = new ArrayList<>();
        mMenuDetailPages.add(new NewsMenuDetailPager(mActivity, mNewsData.data.get(0).children));
        mMenuDetailPages.add(new TopicMenuDetailPager(mActivity));
        mMenuDetailPages.add(new PhotosMenuDetailPager(mActivity));
        mMenuDetailPages.add(new InteractMenuDetailPager(mActivity));
        setCurrentDetailPager(0);
    }

    public void setCurrentDetailPager(int position) {
        BaseMenuDetailPage pager = mMenuDetailPages.get(position);
        View view = pager.mRootView;
        flContent.removeAllViews();
        flContent.addView(view);
        tvTitle.setText(mNewsData.data.get(position).title);
    }
}
