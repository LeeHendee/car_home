package com.example.gtercn.car.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gtercn.car.R;

import java.util.List;


/**
 * Created by Administrator on 2016-7-1.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment> mFragments;
    private String titles[];
    private int icons[];

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, Context context) {
        super(fm);
        this.mFragments = fragments;
        this.mContext = context;
        titles = mContext.getResources().getStringArray(R.array.main_tab_title);
        icons = new int[]{R.drawable.icon_home, R.drawable.icon_find, R.drawable.icon_shop};
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    public View getView(int position) {
        final TextView titleTv;
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_tab_custom_view, null);
        ImageView iconIv = (ImageView) view.findViewById(R.id.main_tab_icon);
        titleTv = (TextView) view.findViewById(R.id.main_tab_tv);
//        View relativeLayout = view.findViewById(R.id.main_tab_relativelayout);
        titleTv.setText(titles[position]);
        iconIv.setImageResource(icons[position]);
//        if(titleTv.isSelected()){
//            titleTv.setTextColor(R.color.blue2);
//        }else{
//           titleTv.setTextColor(R.color.text_title_color);
//        }
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (titleTv.isSelected()) {
//                    titleTv.setSelected(false);
//                } else {
//                    titleTv.setSelected(true);
//                }
//            }
//        });
        return view;
    }
}
