package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.utils.Constants;
import com.example.gtercn.car.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author : LiHang.
 * data : 2017/2/8.
 * description:
 * 引导页:整体是一个可滑动的ViewPager;
 * 在每一页都有一个指示当前页位置的小圆点;
 */
public class GuideActivity extends BaseActivity {
    private static final String TAG = "GuideActivity";
    private ImageView mGuideView;
    private ImageView[] mPointViews;
    private ViewPager mGuidePager;
    private LinearLayout mGuidePointsLayout;
    private List<View> mGuideViews;
    private GuidePagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initDots();
        initListener();
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    private void initListener() {
        mGuidePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mPointViews.length; i++) {
                    mPointViews[i].setImageResource(R.mipmap.ic_guide_indicator_focused);
                    if (position != i) {
                        mPointViews[i].setImageResource(R.mipmap.ic_guide_indicator_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDots() {
        //初始化小圆点相关:
        mPointViews = new ImageView[mGuideViews.size()];
        float density = getResources().getDisplayMetrics().density;

        for (int i = 0; i < mPointViews.length; i++) {
            mGuideView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins((int) (5 * density), 0, (int) (5 * density), 0);
            lp.gravity = Gravity.CENTER;
            mGuideView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            mGuideView.setScaleType(ImageView.ScaleType.FIT_XY);
            mPointViews[i] = mGuideView;

            if (i == 0) {
                mPointViews[i].setImageResource(R.mipmap.ic_guide_indicator_focused);
            } else {
                mPointViews[i].setImageResource(R.mipmap.ic_guide_indicator_normal);
            }
            mGuidePointsLayout.addView(mPointViews[i], lp);
        }
        mGuidePager.setAdapter(mAdapter);
    }

    private void initData() {
        mAdapter = new GuidePagerAdapter();
        mGuideViews = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        mGuideViews.add(inflater.inflate(R.layout.guide_view_1, null));
        mGuideViews.add(inflater.inflate(R.layout.guide_view_2, null));
        mGuideViews.add(inflater.inflate(R.layout.guide_view_3, null));
//        mGuideViews.add(inflater.inflate(R.layout.guide_view_4,null));
    }

    /**
     * 加载xml中的控件;
     */
    private void initView() {
        mGuidePager = (ViewPager) findViewById(R.id.guide_viewpager);
        mGuidePointsLayout = (LinearLayout) findViewById(R.id.guide_viewPoint);
    }

    /**
     * 设置启动页标记为true:再次启动app时,将不走引导页;
     */
    private void setLaunchFlag() {
        SharedPreferenceHelper.setBoolean(Constants.APP_LAUNCH_FLAG, true);
    }

    private void startApp() {
        setLaunchFlag();
        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * ViewPager的Adapter;
     */
    class GuidePagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //将当前页加载进适配器容器;
            View view = mGuideViews.get(position);
            container.addView(view);
            //初始化最后一页的按钮,并添加监听事件;
            if (position == mGuideViews.size() - 1) {
                Button startApp = (Button) view.findViewById(R.id.app_start_btn);
                startApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startApp();
                    }
                });
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁当前页时,从容器移除;
            container.removeView(mGuideViews.get(position));
        }

        @Override
        public int getCount() {
            return mGuideViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
