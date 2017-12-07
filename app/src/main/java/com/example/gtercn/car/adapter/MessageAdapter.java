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
import com.example.gtercn.car.activity.MessageActivity;
import com.example.gtercn.car.bean.MessageBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<MessageBean.ResultBean> list;
    private float density = 1.5f;


    public MessageAdapter(Context context, List<MessageBean.ResultBean> mList) {
        this.context = context;
        list = mList;

    }
    public void refresh( ArrayList<MessageBean.ResultBean> arrayList) {
        list=arrayList;
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
            mOnItemClickListener.onItemClick(v, (MessageBean.ResultBean) v.getTag());
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, MessageBean.ResultBean promotionBean);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyleview_promotion_item, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(MessageAdapter.this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {
        if (list.size()!=0) {
            holder.mTitleTextView.setText(list.get(position).getTitle());
            holder.mDayTextView.setText(list.get(position).getInsert_time());
            //  holder.mTimeTextView.setText(mList.get(position).getTime());
            holder.mDetailTextView.setText(list.get(position).getIntroduction());

            THttpOpenHelper tHttpOpenHelper = THttpOpenHelper.newInstance();
            if (list.get(position).getPicture_list() != null) {

                String url = list.get(position).getPicture_list();

                if(url != null && url.contains("\\")){
                    url = TAppUtils.formatUrl(url);
                }

                tHttpOpenHelper.setImageBitmap(holder.mImageView,
                        url,
                        (int)(100 * density),
                        (int)(75 * density),
                        R.drawable.icon_default1,
                        R.drawable.icon_default1);
            }

            final int p = position;

            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra("id",list.get(p).getId());
                    context.startActivity(intent);
                }
            });


            holder.itemView.setTag(list.get(position));




        }
    }



//    @Override
//    public int getItemC() {
//        TLog.i("PromotionFragment", "----getItemCount---->");
//        return size;
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mDetailTextView;
        TextView mTitleTextView;
        TextView mDayTextView;
        TextView mTimeTextView;
        ImageView mImageView;
        RelativeLayout mLayout;

        public MyViewHolder(View view) {
            super(view);
            mDetailTextView = (TextView) view.findViewById(R.id.promotion_item_detail_textview);
            mTitleTextView = (TextView) view.findViewById(R.id.promotion_item_title_textview);
            mDayTextView = (TextView) view.findViewById(R.id.promotion_item_day_textview);
            mTimeTextView = (TextView) view.findViewById(R.id.promotion_item_time_textview);
            mImageView = (ImageView) view.findViewById(R.id.promotion_item_imageview);
            mLayout = (RelativeLayout)view.findViewById(R.id.promotion_relativelayout);
        }
    }
}
