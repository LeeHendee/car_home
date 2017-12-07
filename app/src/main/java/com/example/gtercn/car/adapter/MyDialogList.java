package com.example.gtercn.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.CarWashBean;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.widget.MyDialog;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public class MyDialogList extends BaseAdapter {
    private static final String TAG = "MyDialogList";
    private Context context;
    private List<String> mList;
    private MyDialog myDialog;
    private CarWashBean carWashBean;
    private int num;

    public MyDialogList(Context context, List<String> mList, MyDialog myDialog) {
        this.context = context;
        this.mList = mList;
        this.myDialog = myDialog;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Hodler hodler;
        if (convertView == null) {
            hodler = new Hodler();
            convertView = View.inflate(context, R.layout.mydialog_listview_item, null);
            hodler.mButton = (Button) convertView.findViewById(R.id.mydialog_listview_item_button);
            convertView.setTag(hodler);
        } else {
            hodler = (Hodler) convertView.getTag();
        }
        hodler.mButton.setText(mList.get(position));
        hodler.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, mList.get(position), Toast.LENGTH_SHORT).show();
                TAppUtils.getPhone(context,mList.get(position));
                myDialog.dismiss();
            }
        });
        return convertView;
    }

    private class Hodler {
        Button mButton;
    }
}
