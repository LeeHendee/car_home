package com.example.gtercn.car.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.FourServiceBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.widget.MyDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/11/17.
 * 按评分，距离刷新的Adapter
 *
 */

public class CarWashDistanceAdapter extends BaseAdapter {
    private static final String TAG = "CarWashDistanceAdapter";
    private Context context;
    private MyDialog myDialog;
    private ArrayList<FourServiceBean> arrayList;
    private  boolean scrollState=false;
    private float density = 1.5f;

    public void setScrollState(boolean scrollState) {
        this.scrollState = scrollState;
    }
    public CarWashDistanceAdapter(Context context, ArrayList<FourServiceBean> arrayList) {
        this.context = context;
        this.arrayList=arrayList;
    }
    public void refresh( ArrayList<FourServiceBean> arrayList) {
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
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
            convertView = View.inflate(context, R.layout.listview_car_wash_item, null);
            hodler.mImageView = (ImageView) convertView.findViewById(R.id.car_wash_item_imageview);
            hodler.mTelImageview = (ImageView) convertView.findViewById(R.id.car_wash_item_phone_imageview);
            hodler.mTitleTextView = (TextView) convertView.findViewById(R.id.car_wash_item_title_textview);
            hodler.mDistanceTextView = (TextView) convertView.findViewById(R.id.car_wash_item_distance_textview);
            hodler.mSubTextView = (TextView) convertView.findViewById(R.id.car_wash_item_subtitle_textview);
            hodler.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.car_wash_item_relativelayout);
            hodler.mRatingBar = (RatingBar) convertView.findViewById(R.id.car_wash_item_ratingbar);
            convertView.setTag(hodler);
        } else {
            hodler = (Hodler) convertView.getTag();

        }
        hodler.mTitleTextView.setText(arrayList.get(position).getShop_name());
        hodler.mSubTextView.setText(arrayList.get(position).getDetail_address());
        if(arrayList.get(position).getDistance()/1000 <= 1){
            hodler.mDistanceTextView.setText((new DecimalFormat("0").format(arrayList.get(position).getDistance()) + "m"));
        }else {
            if(arrayList.get(position).getDistance()/1000>=1000){
                hodler.mDistanceTextView.setText("999+km");
            }else if(arrayList.get(position).getDistance()/1000>=100){
                hodler.mDistanceTextView.setText((new DecimalFormat("0").format(arrayList.get(position).getDistance()/1000)+ "km"));
            }else {
                hodler.mDistanceTextView.setText((new DecimalFormat("0.00").format(arrayList.get(position).getDistance()/1000)+ "km"));
            }
        }
        if(arrayList.get(position).getScore()!=null  ) {
            /**
             * 当值为空时，会出错
             */
            if(TextUtils.isEmpty(arrayList.get(position).getScore())){
                hodler.mRatingBar.setRating(1);
            }else {
                hodler.mRatingBar.setRating(Integer.valueOf(arrayList.get(position).getScore()));
            }
        }

        final String[] sourceStrArray = arrayList.get(position).getTel_num_list().split(",");

        hodler.mTelImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog = new MyDialog(context, R.style.MyDialog, new MyDialog.LeaveMyDialogListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.mydialog_clear:
                                myDialog.dismiss();
                                break;

                        }
                    }
                }, Arrays.asList(sourceStrArray),myDialog);
                myDialog.show();
            }
        });

        String url = arrayList.get(position).getShop_pic_url();
        if(url!=null) {
            if (url.contains("\\")) {
                url = TAppUtils.formatUrl(url);
            }
        }
        if (!scrollState){
            //如果当前不是滑动的状态，我们填充真数据
            //加载图片
            if(url!= null) {
                THttpOpenHelper.newInstance().setImageBitmap(hodler.mImageView, url,
                        (int)(100 * density), (int)(75 * density),
                        R.drawable.icon_default,
                        R.drawable.icon_default);
                //设置tag为1表示已加载过数据
                hodler.mImageView.setTag("1");
            }else{
                hodler.mImageView.setImageResource(R.drawable.icon_default1);
                hodler.mImageView.setTag("1");
            }
        }else{
            //如果当前是滑动的状态，我们填充假数据
            //将数据image_url保存在Tag当中
            if(url != null) {
                hodler.mImageView.setTag(url);
                //设置默认显示图片（最好是本地资源的图片）
                hodler.mImageView.setImageResource(R.drawable.icon_default);
            }else{
                //url为空的时候给标记“2”
                hodler.mImageView.setTag("2");
                hodler.mImageView.setImageResource(R.drawable.icon_default);
            }
        }
        return convertView;
    }

    private class Hodler {
        ImageView mImageView;
        ImageView mTelImageview;
        TextView mTitleTextView;
        TextView mSubTextView;
        TextView mDistanceTextView;
        RelativeLayout relativeLayout;
        RatingBar mRatingBar;
    }
}
