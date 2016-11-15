package com.abner.zhbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abner.zhbj.MainActivity;
import com.abner.zhbj.R;
import com.abner.zhbj.base.implement.NewsCenterPager;
import com.abner.zhbj.domain.NewsMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Project   com.abner.zhbj.fragment
 *
 * @Author Abner
 * Time   2016/10/12.10:42
 */

public class LeftMenuFragment extends BaseFragment {

    private ListView lvlist;
    private ArrayList<NewsMenu.newsMenuData> mNewsMenuData;
    private int mCurrentPos = 0;
    private MyAdapter mMyAdapter;

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_left_menu, null);
        lvlist = ((ListView) view.findViewById(R.id.lv_list));
        return view;
    }

    @Override
    public void initData() {
    }

    public void setMenuData(ArrayList<NewsMenu.newsMenuData> data) {
        mCurrentPos = 0;
        mNewsMenuData = data;
        mMyAdapter = new MyAdapter();
        lvlist.setAdapter(mMyAdapter);

        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;
                mMyAdapter.notifyDataSetChanged();

                toggle();
                //侧边栏点击之后修改新闻中心的FrameLayout的内容
                setCurrentDetailPager(position);
            }
        });

    }

    private void setCurrentDetailPager(int position) {
        ContentFragment contentFragment = ((MainActivity) getActivity()).getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        newsCenterPager.setCurrentDetailPager(position);
    }

    /**
     * 打开或者关闭侧边栏
     */
    private void toggle() {
        SlidingMenu slidingMenu = ((MainActivity) getActivity()).getSlidingMenu();
        slidingMenu.toggle();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNewsMenuData.size();
        }

        @Override
        public NewsMenu.newsMenuData getItem(int position) {
            return mNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.list_item_left_menu, null);
                holder = new ViewHolder();
                holder.tvmenu = ((TextView) convertView.findViewById(R.id.tv_menu));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvmenu.setText(getItem(position).title);

            if (position == mCurrentPos) {
                holder.tvmenu.setEnabled(true);
            } else {
                holder.tvmenu.setEnabled(false);
            }

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tvmenu;
    }

}
