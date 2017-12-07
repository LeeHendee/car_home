package com.example.gtercn.car.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.ExpertTopBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import java.util.List;

/**
 * author : LiHang.
 * data : 2016/11/16.
 * description:
 * 达人榜RecyclerView适配器
 */
public class ExpertTopContentAdapter extends RecyclerView.Adapter<ExpertTopContentAdapter.MyViewHolder> {

    private static final String TAG = "ExpertTopContentAdapter";

    private Context mContext;
    private float density = 1.5f;
    private List<ExpertTopBean.ResultBean> mList;

    private IRecyclerViewItemListener mListener;
    private View mView;

    public void setListener(IRecyclerViewItemListener mListener) {
        this.mListener = mListener;
    }

    public ExpertTopContentAdapter(Context mContext, List<ExpertTopBean.ResultBean> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.item_expert_top_display, parent, false);
        MyViewHolder holder = new MyViewHolder(mView);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ExpertTopBean.ResultBean bean = mList.get(position);

        String url = bean.getExpert_portrait_url();
        if(url != null){
            if(url.contains("\\"))
                url = TAppUtils.formatUrl(url);
            THttpOpenHelper.newInstance().setImageBitmap(holder.imageView, url,(int)(120*density),(int)(140*density),
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        }else {
            holder.imageView.setBackgroundResource(R.drawable.icon_default);
        }

        holder.title.setText(bean.getTop_title());
        holder.name.setText(bean.getExpert_name());
        holder.experience.setText(bean.getExpert_experience());

        final  int p = position;
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.listener(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        RelativeLayout itemLayout;
        TextView name;
        TextView experience;
        TextView title;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.expert_top_display_iv);
            itemLayout = (RelativeLayout) itemView.findViewById(R.id.expert_top_display_item_view);
            name = (TextView) itemView.findViewById(R.id.expert_top_name);
            experience = (TextView) itemView.findViewById(R.id.expert_top_experience);
            title = (TextView) itemView.findViewById(R.id.expert_top_title);
        }
    }

    public interface IRecyclerViewItemListener{
        void listener(int position);
    }
}
