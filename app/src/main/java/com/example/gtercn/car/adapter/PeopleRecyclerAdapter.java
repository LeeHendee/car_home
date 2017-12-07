package com.example.gtercn.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.PeopleRollBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.util.List;


public class PeopleRecyclerAdapter extends BaseAdapter{

    private Context context;
    private List<PeopleRollBean.ResultBean> list;
    private float density = 1.5f;

    public PeopleRecyclerAdapter(Context context , List<PeopleRollBean.ResultBean> list){
        this.context =context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list!= null) {
            return list.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler;
        if (convertView == null) {
            hodler = new Hodler();
            convertView = View.inflate(context, R.layout.item_people_recyclerview, null);
            hodler.mImageView = (ImageView) convertView.findViewById(R.id.item_people_recycler_imageview);
            hodler.mNameTextView = (TextView) convertView.findViewById(R.id.item_people_recycler_textview);
            convertView.setTag(hodler);
        } else {
            hodler = (Hodler) convertView.getTag();
        }
        hodler.mNameTextView.setText(list.get(position).getNickname());
        String url = list.get(position).getAvatarUrl();
        if(url!=null) {
            if (url.contains("\\")) {
                url = TAppUtils.formatUrl(url);
            }
            THttpOpenHelper.newInstance().setImageBitmap(hodler.mImageView,url,(int)(50*density),(int)(50*density),R.drawable.personal_info_portrait,R.drawable.personal_info_portrait);
        }else{
           hodler.mImageView.setBackgroundResource(R.drawable.personal_info_portrait);
        }
        return convertView;
    }

    private class Hodler {
        ImageView mImageView;
        TextView mNameTextView;
    }
}