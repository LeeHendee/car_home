package com.example.gtercn.car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.MyFavouriteBean;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.utils.SwapWrapperUtils;
import com.example.gtercn.car.interfaces.SwipeMenuBuilder;
import com.example.gtercn.car.widget.recyclerview.SwipeMenuLayout;
import com.example.gtercn.car.widget.recyclerview.SwipeMenuView;

import java.util.List;

/**
 * author : LiHang.
 * data : 2016/8/10.
 * description:
 * 我的收藏recyclerView 适配器;
 */
public class MyFavouriteAdapter extends RecyclerView.Adapter<MyFavouriteAdapter.MyFavouriteViewHolder> {
    private static final String TAG = "MyFavouriteAdapter";
    private Context mContext;
    private List<MyFavouriteBean.ResultBean> mList;
    private SwipeMenuBuilder swipeMenuBuilder;
    private itemClickListener itemClickListener;

    private boolean isDisplayed = false;

    private float density = 1.5f;

    private int pos;

    public MyFavouriteAdapter(Context mContext, List<MyFavouriteBean.ResultBean> list) {
        this.mContext = mContext;
        this.mList = list;
        swipeMenuBuilder = (SwipeMenuBuilder) this.mContext;
        density = mContext.getResources().getDisplayMetrics().density;
    }

    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
        notifyDataSetChanged();
    }

    public void setItemClickListener(MyFavouriteAdapter.itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public MyFavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据数据创建右边的操作view
        SwipeMenuView menuView = swipeMenuBuilder.create();
        //包装用户的item布局
        SwipeMenuLayout swipeMenuLayout = SwapWrapperUtils.wrap(parent,
                R.layout.item_my_favourite, menuView, new BounceInterpolator(), new LinearInterpolator());
        MyFavouriteViewHolder holder = new MyFavouriteViewHolder(swipeMenuLayout);

        return holder;
    }

    public void remove(int pos) {
        mList.remove(pos);
        this.notifyItemRemoved(pos);
    }

    public int getPos(){
        return pos;
    }

    public void setPos(){
        pos = 0;
    }

    @Override
    public void onBindViewHolder(final MyFavouriteViewHolder holder, int position) {
        final MyFavouriteBean.ResultBean bean = mList.get(position);
        if (isDisplayed) {
            holder.cancelIv.setVisibility(View.VISIBLE);
        }

        if (bean != null) {
            TLog.i(TAG, "---->>onBindViewHolder bean is not null");
            holder.favouriteTitle.setText(bean.getTitle());
            String updateTime = TAppUtils.trimTime(bean.getUpdate_time());
            holder.preservation.setText(updateTime);

            final  int p = position;

            holder.cancelIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((SwipeMenuLayout) holder.itemView).smoothOpenMenu();
                    pos = p;
                }
            });
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemClick(bean);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class MyFavouriteViewHolder extends RecyclerView.ViewHolder {
        ImageView cancelIv;
//        ImageView favouritePic;
//        TextView productStatus;
        TextView favouriteTitle;
//        TextView nowPrice;
//        TextView orgPrice;
        TextView preservation;
        LinearLayout itemLayout;

        public MyFavouriteViewHolder(View itemView) {
            super(itemView);
            cancelIv = (ImageView) itemView.findViewById(R.id.favourite_iv);
//            favouritePic = (ImageView) itemView.findViewById(R.id.favourite_pic);
//            productStatus = (TextView) itemView.findViewById(R.id.favorities_status);
            favouriteTitle = (TextView) itemView.findViewById(R.id.favourite_title_tv);
//            nowPrice = (TextView) itemView.findViewById(R.id.favourite_price_now);
            preservation = (TextView) itemView.findViewById(R.id.favourite_preservation_tv);
//            orgPrice = (TextView) itemView.findViewById(R.id.favourite_price_org);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.item_ll);
        }
    }

    public interface itemClickListener {
        void itemClick(MyFavouriteBean.ResultBean bean);
    }


}
