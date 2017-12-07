package com.example.gtercn.car.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.MyActivityBean;

import java.util.List;

/**
 * Created by admin on 2017/3/20.
 */

public class MyActivityAdapter extends BaseAdapter {

    private List<MyActivityBean> mList;

    private Context mContext;


    public MyActivityAdapter(Context mContext , List<MyActivityBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null){
            holder = new Holder();
            convertView = View.inflate(mContext, R.layout.item_my_activity, null);
            holder.mTitle = (TextView) convertView.findViewById(R.id.my_activity_title);
            holder.mTime = (TextView) convertView.findViewById(R.id.my_activity_time);
            holder.mPublicFlag = (TextView) convertView.findViewById(R.id.my_activity_public_state);
//            holder.mPublicFlag1 = (TextView) convertView.findViewById(R.id.my_activity_public_state1);
            holder.mAvailableFlag = (TextView) convertView.findViewById(R.id.my_activity_available_state);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mTime.setText(mList.get(position).getActivity_time());
        if (mList.get(position).getPublic_flag().equals("0")){
            holder.mPublicFlag.setText("我报名的");
            holder.mPublicFlag.setTextColor(ContextCompat.getColor(mContext, R.color.green2));
        }else {
            holder.mPublicFlag.setText("我发布的");
            holder.mPublicFlag.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        }
        if (mList.get(position).getAvailable_flag()==0){
            holder.mAvailableFlag.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class Holder {
        TextView mTitle;
        TextView mTime;
        TextView mPublicFlag;
//        TextView mPublicFlag1;
        TextView mAvailableFlag;
    }

}
