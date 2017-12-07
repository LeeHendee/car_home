package com.example.gtercn.car.adapter;

/**
 * Created by Administrator on 2016/10/17.
 */

import android.content.Context;
import android.content.Intent;
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
import com.example.gtercn.car.activity.AdvertisementActivity;
import com.example.gtercn.car.bean.HomeAdBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.util.List;


/**
 * 广告轮播条的viewpager的适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    private static String TAG = ViewPagerAdapter.class.getSimpleName();
    //数据源
    private List<HomeAdBean> mList;
    private List<String> mFourList;
    private static float density = 1.5f;
    private Context context;
    private FragmentManager fm;
    private DisplayMetrics mDm;

    /**
     * toolbar width, UI left bar.
     */
    private static final int TOOLBAR_WIDTH = 56;

    private int mHeight = 300;

    private int mWidth = 400;

    /**
     * 高/宽 比率
     */
    private static final float mRatio = 0.75f;


    public void refresh( List<HomeAdBean> list) {
        this.mList=list;
        notifyDataSetChanged();
    }


    public ViewPagerAdapter(List<HomeAdBean> list, Context context, FragmentManager fm) {
        this.mList = list;
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
        return mList != null ? mList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if (mList.size() == 0) {
            return null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_item_vp);
        // TODO Auto-generated method stub
        //通过代码创建ImageView
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
        //设置图片拉伸方式
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //给图片指定资源
        String url = mList.get(position).getPicture_url();
        if (url != null) {
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
            THttpOpenHelper.newInstance().setImageBitmap(imageView, url, mWidth, mHeight,
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        }else {
            imageView.setImageResource(R.drawable.icon_default);
        }
        //广告的点击监听
        final int j = position;
        final String htmurl = mList.get(position).getHtml_url();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int type = 0;
//
//                switch (type){
//                    case 0:
                        Intent intent = new Intent(context, AdvertisementActivity.class);
                        //传值
                        intent.putExtra("url", htmurl);
                        context.startActivity(intent);
//                        break;
//                }

            }
        });
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
