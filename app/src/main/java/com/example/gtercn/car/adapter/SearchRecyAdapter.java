package com.example.gtercn.car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.CarWashBean;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * author : LiHang.
 * data : 2017/2/13.
 * description:
 * 搜索页面适配器;
 */
public class SearchRecyAdapter extends RecyclerView.Adapter<SearchRecyAdapter.SearchViewHolder> {

    private static final String TAG = "SearchRecyAdapter";

    private Context mContext;

    private List<CarWashBean.ResultBean> mList;

    private IRecyclerItemListener mItemListener;
    protected float density = 1.5f;

    public void setItemListener(IRecyclerItemListener mItemListener) {
        this.mItemListener = mItemListener;
    }

    public SearchRecyAdapter(Context mContext, List<CarWashBean.ResultBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        density = mContext.getResources().getDisplayMetrics().density;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        final CarWashBean.ResultBean searchResult = mList.get(position);
        holder.nameTv.setText(searchResult.getShop_name());
        if(searchResult.getDistance()<1000){
            holder.distanceTv.setText((new DecimalFormat("0").format(searchResult.getDistance()) + "m"));
        }else if(searchResult.getDistance() > 1000000){
            holder.distanceTv.setText( "999+Km");
        }else if(searchResult.getDistance() > 100000){
            holder.distanceTv.setText((new DecimalFormat("0").format(searchResult.getDistance()/1000) + "Km"));
        }else {
            holder.distanceTv.setText((new DecimalFormat("0.00").format(searchResult.getDistance()/1000) + "Km"));
        }
        holder.mRatingBar.setRating(Float.valueOf(searchResult.getShop_score()));
        String url = TAppUtils.formatUrl(searchResult.getShop_pic_url());
        if (url != null){
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
            THttpOpenHelper.newInstance().setImageBitmap(holder.imgIv, url,(int)(100*density),(int)(75*density),
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        }else{
            holder.imgIv.setImageResource(R.drawable.icon_default);
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.callback(searchResult.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIv;

        TextView nameTv;

        TextView distanceTv;

        LinearLayout itemLayout;

        RatingBar mRatingBar;

        public SearchViewHolder(View itemView) {
            super(itemView);
            imgIv = (ImageView) itemView.findViewById(R.id.search_company_iv);
            nameTv = (TextView) itemView.findViewById(R.id.search_company_name);
            distanceTv = (TextView) itemView.findViewById(R.id.search_distance);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.search_item_layout);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.search_company_score_iv);

        }
    }
}
