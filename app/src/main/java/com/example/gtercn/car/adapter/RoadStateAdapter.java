package com.example.gtercn.car.adapter;

/**
 * Created by Administrator on 2016/12/13.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.gtercn.car.R;

import java.util.List;

/**
 * 添加图片上传 适配器
 */
public class RoadStateAdapter extends BaseAdapter {
    private Context mContext;
    private List<Bitmap> mList;
    private LayoutInflater mInflater;
    private GridView mGridView;
    private int gridViewH;
    private int imageViewH;

    public RoadStateAdapter(Context context, List<Bitmap> data, GridView mGridView) {
        this.mContext = context;
        this.mList = data;
        this.mGridView = mGridView;
        mInflater = LayoutInflater.from(context);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGridView.getLayoutParams();
        gridViewH = params.height;

    }

    @Override
    public int getCount() {
        if(mList != null)
            return mList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mList != null)
            return mList.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.road_gridview_item, null);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.gridview_img);
            //
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.imageView
                    .getLayoutParams();
            imageViewH = params.height;
            convertView.setTag(holder);
        } else {
            setGridView();
            holder = (Holder) convertView.getTag();
        }

//        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//        int[] parameter = { mList.get(position).getWidth(),mList.get(position).getHeight() };
//        holder.imageView.setTag(parameter);
//        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
//        int screenWidth=dm.widthPixels;
//        if(mList.get(position).getWidth()<=screenWidth){
            holder.imageView.setImageBitmap(mList.get(position));
//        }else{
//           // Bitmap bmp=Bitmap.createScaledBitmap(mList.get(position), screenWidth, mList.get(position).getHeight()*screenWidth/mList.get(position).getWidth(), true);
//            Bitmap bmp = ZoomImg.zoomImg(mList.get(position),90,90);
//            holder.imageView.setImageBitmap(bmp);
//        }
        return convertView;
    }

    private void setGridView() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGridView.getLayoutParams();
        if (mList.size() < 3) {
            lp.height = gridViewH;
        }
        mGridView.setLayoutParams(lp);
    }

    private class Holder {
        ImageView imageView;
    }

}
