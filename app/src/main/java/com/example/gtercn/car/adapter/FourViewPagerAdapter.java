package com.example.gtercn.car.adapter;

/**
 * Created by Administrator on 2016/10/17.
 */

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gtercn.car.R;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.util.List;


/**
 * 广告轮播条的viewpager的适配器
 */
public class FourViewPagerAdapter extends PagerAdapter {
    private static String TAG = FourViewPagerAdapter.class.getSimpleName();

    private List<String> mFourList;
    private static float density = 1.5f;
    private Context context;
    private FragmentManager fm;
    private DisplayMetrics mDm;

    /**
     * toolbar width, UI left bar.
     */
    private static final int TOOLBAR_WIDTH = 1;

    private int mHeight = 300;

    private int mWidth = 400;

    /**
     * 高/宽 比率
     */
    private static final float mRatio = 0.75f;


    public FourViewPagerAdapter(List<String> mFourList, Context context, FragmentManager fm) {
        this.mFourList = mFourList;
        this.context = context;
        this.fm = fm;
        mDm = context.getResources().getDisplayMetrics();
        density = mDm.density;

        mWidth = mDm.widthPixels - (int) (density * TOOLBAR_WIDTH);
        mHeight = (int) (mWidth * mRatio);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFourList != null ? mFourList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_item_vp);
            // TODO Auto-generated method stub
            //通过代码创建ImageView
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            //设置图片拉伸方式
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //给图片指定资源
            if (mFourList.get(position) != null) {
            String url = mFourList.get(position);
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
                THttpOpenHelper.newInstance().setImageBitmap(imageView, url, mWidth, mHeight,
                        R.drawable.icon_default,
                        R.drawable.icon_default);
            } else {
                imageView.setImageResource(R.drawable.icon_default);
            }
            //绑定适配器
            container.addView(view);
            return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
