package com.example.gtercn.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.widget.PopPhoneMenu;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class PopWindowPhoneAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private PopPhoneMenu popPhoneMenu;

    public PopWindowPhoneAdapter(Context context, List<String> list, PopPhoneMenu popPhoneMenu) {
        this.context = context;
        this.list = list;
        this.popPhoneMenu = popPhoneMenu;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Hodler hodler;
        if (convertView == null) {
            hodler = new Hodler();
            convertView = View.inflate(context, R.layout.popwindow_phone_listview_item, null);
            hodler.mTextView = (TextView) convertView.findViewById(R.id.popwindow_phone_listview_item_textview);
            convertView.setTag(hodler);
        } else {
            hodler = (Hodler) convertView.getTag();
        }
        hodler.mTextView.setText(list.get(position));
        hodler.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAppUtils.getPhone(context,list.get(position));
                popPhoneMenu.dismiss();

            }
        });
        return convertView;
    }

    private class Hodler {

        TextView mTextView;

    }
}
