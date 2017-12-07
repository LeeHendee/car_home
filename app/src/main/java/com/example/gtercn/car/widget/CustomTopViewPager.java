package com.example.gtercn.car.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016-11-2.
 */
public class CustomTopViewPager extends ViewPager {
    private static final String TAG = CustomTopViewPager.class.getSimpleName();
    private Context mContext;
    private int mWidth = 400;
    private int mHeight = 300;

    private boolean isIntercept = false;

    public CustomTopViewPager(Context context){
        super(context);
        init(context);
    }

    public CustomTopViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    private void init(Context ctx){
        mContext = ctx;
        float density = mContext.getResources().getDisplayMetrics().density;
        mWidth = mContext.getResources().getDisplayMetrics().widthPixels - (int)(density * 56);
        mHeight = (int)(mWidth * 0.75f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(getDefaultSize(mWidth, widthMeasureSpec),
//                getDefaultSize(mHeight,heightMeasureSpec));

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
