package com.example.gtercn.car.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.ExpertTopTypeBean;
import com.example.gtercn.car.utils.TLog;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

public class ExpertTopTitleAdapter extends BaseAdapter {

    private static final String TAG = "ExpertTopTitleAdapter";

    private Context mContext;

    private List<ExpertTopTypeBean.ResultBean> mTypeList;

    private int mSelect;

    public ExpertTopTitleAdapter(Context context, List<ExpertTopTypeBean.ResultBean> typeList) {
        this.mTypeList = typeList;
        TLog.i(TAG, "--->>init typelist.size is " + typeList.size());
        this.mContext = context;
    }

    public void changeSelected(int position) { //刷新方法
        if (position != mSelect) {
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        TLog.i(TAG, "---getCount : " + mTypeList.size());
        return mTypeList != null ? mTypeList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        ExpertTopTypeBean.ResultBean bean = mTypeList.get(position);
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_expert_notice_item, parent, false);
            holder.mTextView = (TextView) convertView.findViewById(R.id.listview_expert_notice_item_textview);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mTextView.setText(bean.getType_value());

        if (mSelect == position) {
            convertView.setBackgroundResource(R.drawable.expert_list_item_bg);  //选中项背景
            holder.mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.blue1));
        } else {
            convertView.setBackgroundResource(R.color.white);  //其他项背景
            holder.mTextView.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    private class Holder {
        TextView mTextView;
    }
}
