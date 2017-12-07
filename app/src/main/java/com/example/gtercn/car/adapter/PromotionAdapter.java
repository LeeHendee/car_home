package com.example.gtercn.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.PromotionActivity;
import com.example.gtercn.car.bean.PromotionBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<PromotionBean.ResultBean> list;
    private float density = 1.5f;


    public PromotionAdapter(Context context, List<PromotionBean.ResultBean> mList) {
        this.context = context;
        list = mList;

    }

    public void refresh(ArrayList<PromotionBean.ResultBean> arrayList) {
        list = arrayList;
        TLog.i("PromotionFragment","------list------->"+list.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
       return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            //mOnItemClickListener.onItemClick(v, (String) v.getTag());
            mOnItemClickListener.onItemClick(v, (PromotionBean.ResultBean) v.getTag());
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, PromotionBean.ResultBean promotionBean);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public PromotionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyleview_promotion_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(PromotionAdapter.this);
        return holder;
    }

    @Override
    public void onBindViewHolder(PromotionAdapter.MyViewHolder holder, int position) {
        if (list.size() != 0) {
            holder.mTitleTextView.setText(list.get(position).getTitle());
            holder.mDayTextView.setText(list.get(position).getInsert_time());
            //  holder.mTimeTextView.setText(mList.get(position).getTime());
            holder.mDetailTextView.setText(list.get(position).getIntroduction());
            if (list.get(position).getPicture_list() != null) {
                /**
                 * 这样取值是不是有问题
                 */
                String url = list.get(position).getPicture_list().toString();
                if(url != null && url.contains("\\"))
                    url = TAppUtils.formatUrl(url);

                THttpOpenHelper.newInstance().setImageBitmap(holder.mImageView, url,
                        (int)(100 * density),
                        (int)(75 * density),
                        R.drawable.icon_default,
                        R.drawable.icon_default);
            }
            holder.itemView.setTag(list.get(position));

            final  int p = position;
           holder.mLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, PromotionActivity.class);
                   intent.putExtra("promotionId",list.get(p).getId());
                   context.startActivity(intent);
               }
           });




        }

    }


//    @Override
//    public int getItemC() {
//        TLog.i("PromotionFragment", "----getItemCount---->");
//        return size;
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mLayout;
        TextView mDetailTextView;
        TextView mTitleTextView;
        TextView mDayTextView;
        TextView mTimeTextView;
        ImageView mImageView;
        View mView;

        public MyViewHolder(View view) {
            super(view);
            mDetailTextView = (TextView) view.findViewById(R.id.promotion_item_detail_textview);
            mTitleTextView = (TextView) view.findViewById(R.id.promotion_item_title_textview);
            mDayTextView = (TextView) view.findViewById(R.id.promotion_item_day_textview);
            mTimeTextView = (TextView) view.findViewById(R.id.promotion_item_time_textview);
            mImageView = (ImageView) view.findViewById(R.id.promotion_item_imageview);
            mView = view.findViewById(R.id.promotion_item_line_view);
            mLayout = (RelativeLayout)view.findViewById(R.id.promotion_relativelayout);
        }
    }
}
