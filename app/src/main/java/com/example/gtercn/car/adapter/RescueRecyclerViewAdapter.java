package com.example.gtercn.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.RescueDetailActivity;
import com.example.gtercn.car.activity.RescureShopDetailActivity;
import com.example.gtercn.car.bean.RescueListBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/11/16.
 * description:
 *    紧急救援RecyclerView适配器;
 */
public class RescueRecyclerViewAdapter extends RecyclerView.Adapter<RescueRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "RescueRecyclerViewAdapter";
    private Context mContext;
    private List<RescueListBean.ResultBean> mBeans;
    private float density = 1.5f;

    public void setChange(List<RescueListBean.ResultBean> beans) {
        this.mBeans = beans;
        notifyDataSetChanged();
    }

    public RescueRecyclerViewAdapter(Context context, List<RescueListBean.ResultBean> list) {
        this.mContext = context;
        this.mBeans = list;
    }

    public void addData(List<RescueListBean.ResultBean> beans) {
        mBeans = beans;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_urgent_rescue, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final RescueListBean.ResultBean bean = mBeans.get(position);
        final Bundle bundle = new Bundle();
        String url = TAppUtils.formatUrl(bean.getHead_portrait_url());
        if (!TextUtils.isEmpty(url))
            THttpOpenHelper.newInstance().setImageBitmap(holder.companyPic, url,
                    (int)(100 * density),
                    (int)(75 * density),
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        else
            holder.companyPic.setImageResource(R.drawable.icon_default);

        holder.rescueCompanyName.setText(bean.getShop_name());
        holder.evaluateStars.setRating(Float.valueOf(bean.getShop_score()));
        if(bean.getDistance()/1000 <= 1){
            holder.distance.setText((new DecimalFormat("0").format(bean.getDistance()) + "m"));
        }else {
            if(bean.getDistance()/1000>=1000){
                holder.distance.setText("999+km");
            }else if(bean.getDistance()/1000>=100){
                holder.distance.setText((new DecimalFormat("0").format(bean.getDistance()/1000)+ "km"));
            }else {
                holder.distance.setText((new DecimalFormat("0.00").format(bean.getDistance()/1000)+ "km"));
            }
        }
        holder.selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RescueDetailActivity.class);
                bundle.putSerializable("rescue_bean", bean);
                intent.putExtra("bdl", bundle);
                mContext.startActivity(intent);
            }
        });
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSHopDetail = new Intent(mContext, RescureShopDetailActivity.class);
                goSHopDetail.putExtra("shopId", bean.getId());
                mContext.startActivity(goSHopDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mBeans == null) {
            return 0;
        } else {
            return mBeans.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView rescueCompanyName;
        private TextView distance;
        private ImageView companyPic;
        private RatingBar evaluateStars;
        private TextView selectBtn;
        private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            rescueCompanyName = (TextView) itemView.findViewById(R.id.rescue_company_name);
            distance = (TextView) itemView.findViewById(R.id.rescue_distance);
            companyPic = (ImageView) itemView.findViewById(R.id.rescue_company_iv);
            evaluateStars = (RatingBar) itemView.findViewById(R.id.rescue_company_score_iv);
            selectBtn = (TextView) itemView.findViewById(R.id.rescue_select_btn);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.rescue_item_layout);
        }
    }
}
