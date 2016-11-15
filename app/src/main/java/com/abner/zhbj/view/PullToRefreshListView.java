package com.abner.zhbj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abner.zhbj.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Project   com.abner.zhbj.view
 *
 * @Author Abner
 * Time   2016/10/16.16:44
 */

public class PullToRefreshListView extends ListView {

    private View mHeaderView;
    private int mHeaderViewHeight;
    private int mDownY;
    private static final int START_PULL_TO_REFRESH = 1;
    private static final int START_RELEASE_TO_REFRESH = 2;
    private static final int START_REFRESHING = 3;
    private static int mCurrentState = START_PULL_TO_REFRESH;
    private ProgressBar pbbar;
    private ImageView ivarrow;
    private TextView tvlisttitle;
    private TextView tvtime;
    private RotateAnimation mAnimaUp;
    private RotateAnimation mAnimaDown;
    private onRefreshListener mListener;
    private View mFootView;
    private int mFootViewHeight;
    private boolean ISLODINGMORE = false;


    public PullToRefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }


    private void initAnimation() {
        mAnimaUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimaUp.setDuration(500);
        mAnimaUp.setFillAfter(true);
        mAnimaDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimaDown.setDuration(500);
        mAnimaDown.setFillAfter(true);
    }

    //初始化脚布局
    private void initFooterView() {
        mFootView = View.inflate(getContext(), R.layout.pull_to_refresh_foot, null);
        addFooterView(mFootView);
        mFootView.measure(0, 0);
        mFootViewHeight = mFootView.getMeasuredHeight();
        mFootView.setPadding(0, -mFootViewHeight, 0, 0);
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() == getCount() - 1 && ISLODINGMORE == false && mCurrentState == START_PULL_TO_REFRESH) {
                    ISLODINGMORE = true;
                    mFootView.setPadding(0, 0, 0, 0);
                    setSelection(getCount() - 1);
                    if (mListener != null) {
                        mListener.onLode();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    //初始化头布局
    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh_head, null);
        pbbar = ((ProgressBar) mHeaderView.findViewById(R.id.pb_bar));
        ivarrow = ((ImageView) mHeaderView.findViewById(R.id.iv_arrow));
        tvlisttitle = ((TextView) mHeaderView.findViewById(R.id.tv_list_title));
        tvtime = ((TextView) mHeaderView.findViewById(R.id.tv_time));

        initAnimation();
        this.addHeaderView(mHeaderView);
        //隐藏头布局
        mHeaderView.measure(0, 0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ((int) ev.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDownY == -1) {
                    mDownY = ((int) ev.getY());
                }
                if (mCurrentState == START_REFRESHING) {
                    mDownY = -1;
                    break;
                }
                int moveY = ((int) ev.getY());
                int dy = moveY - mDownY;
                if (dy > 0 && getFirstVisiblePosition() == 0) {
                    int padding = dy - mHeaderViewHeight;
                    mHeaderView.setPadding(0, padding, 0, 0);

                    if (padding > 0 && mCurrentState != START_RELEASE_TO_REFRESH) {
                        mCurrentState = START_RELEASE_TO_REFRESH;
                        refreshState();
                    } else if (padding < 0 && mCurrentState != START_PULL_TO_REFRESH) {
                        mCurrentState = START_PULL_TO_REFRESH;
                        refreshState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mDownY = -1;
                if (mCurrentState == START_RELEASE_TO_REFRESH && ISLODINGMORE == false) {
                    mCurrentState = START_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);
                    refreshState();
                    if (mListener != null) {
                        mListener.onRefresh();
                    }
                } else if (mCurrentState == START_PULL_TO_REFRESH) {
                    mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }


    public void onRefreshComplete() {
        if (!ISLODINGMORE) {
            mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
            tvlisttitle.setText("下拉刷新");
            pbbar.setVisibility(View.INVISIBLE);
            ivarrow.setVisibility(View.VISIBLE);
            mCurrentState = START_PULL_TO_REFRESH;
        } else {
            mFootView.setPadding(0, -mFootViewHeight, 0, 0);
            ISLODINGMORE = false;
        }
    }

    public void onRefreshTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String data = dateFormat.format(new Date());
        tvtime.setText(data);
    }

    private void refreshState() {
        switch (mCurrentState) {
            case START_PULL_TO_REFRESH:
                ivarrow.setVisibility(View.VISIBLE);
                pbbar.setVisibility(View.INVISIBLE);
                tvlisttitle.setText("下拉刷新");
                ivarrow.startAnimation(mAnimaDown);
                break;
            case START_RELEASE_TO_REFRESH:
                ivarrow.setVisibility(View.VISIBLE);
                pbbar.setVisibility(View.INVISIBLE);
                tvlisttitle.setText("释放刷新");
                ivarrow.startAnimation(mAnimaUp);
                break;
            case START_REFRESHING:
                tvlisttitle.setText("加载更多");
                ivarrow.clearAnimation();
                ivarrow.setVisibility(View.INVISIBLE);
                pbbar.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setOnRefreshListener(onRefreshListener listener) {
        mListener = listener;
    }

    public interface onRefreshListener {
        void onRefresh();

        void onLode();
    }

}
