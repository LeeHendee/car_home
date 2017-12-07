package com.example.gtercn.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.ExpertCircleDetailActivity;
import com.example.gtercn.car.bean.ExpertCircleListBean;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.utils.TimeComputeUtil;

import java.util.List;

/**
 * author : LiHang.
 * data : 2016/11/17.
 * description:
 * 达人圈RecyclerView适配器;
 */
public class ExpertCircleAdapter extends RecyclerView.Adapter<ExpertCircleAdapter.ExpertCircleViewHolder> {

    private static final String TAG = "ExpertCircleAdapter";

    private Context mContext;

    private List<ExpertCircleListBean.ResultBean> mList;

    private IRecyclerItemListener itemListener;

    private float density = 1.5f;

    public void setItemListener(IRecyclerItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public ExpertCircleAdapter(Context mContext, List<ExpertCircleListBean.ResultBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        density = mContext.getResources().getDisplayMetrics().density;
    }

    @Override
    public ExpertCircleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_expert_circle, parent, false);
        ExpertCircleViewHolder holder = new ExpertCircleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ExpertCircleViewHolder holder, int position) {
        final ExpertCircleListBean.ResultBean bean = mList.get(position);
        if (bean == null) {
            return;
        }
        if (bean.getExpert_portrait() != null){
            String url = bean.getExpert_portrait();
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
            THttpOpenHelper.newInstance().setImageBitmap(holder.headPortrait, url,
                    (int)(100 * density),
                    (int)(75 * density),
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        }else{
            holder.headPortrait.setImageResource(R.drawable.icon_default);
        }


        holder.name.setText(bean.getExpert_name());
        holder.articleName.setText(bean.getTitle());
        holder.favorNum.setText(String.valueOf(bean.getFavor_number()));
      //  holder.supportNum.setText(String.valueOf(bean.getSupport_number()));
        String interval = TimeComputeUtil.getInterval(bean.getInsert_time());
        holder.time.setText(interval);
        holder.numberOfGlance.setText(String.valueOf(bean.getGlance_number()));
        TLog.i(TAG, "------>> number of favourite " + bean.getFavor_number());

        if (TextUtils.equals(bean.getIs_favored(), "0")){
            holder.favorIv.setImageResource(R.drawable.icon_collect_normal);
        } else{
            holder.favorIv.setImageResource(R.drawable.icon_collect_select);
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExpertCircleDetailActivity.class);
                intent.putExtra("id", bean.getId());
                mContext.startActivity(intent);
            }
        });

        final  int p = position;
        holder.favorIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.callback(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ExpertCircleViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView supportNum;
        TextView favorNum;
        TextView articleName;
        TextView time;
        TextView numberOfGlance;
        ImageView headPortrait;
        ImageView supportIv;
        ImageView favorIv;
        LinearLayout itemLayout;

        public ExpertCircleViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.expert_name);
//            supportNum = (TextView) itemView.findViewById(R.id.number_of_support);
            favorNum = (TextView) itemView.findViewById(R.id.number_of_favor);
            articleName = (TextView) itemView.findViewById(R.id.article_name);
            time = (TextView) itemView.findViewById(R.id.time_of_commit);
            numberOfGlance = (TextView) itemView.findViewById(R.id.number_of_glance_over);
            headPortrait = (ImageView) itemView.findViewById(R.id.expert_iv);
//            supportIv = (ImageView) itemView.findViewById(R.id.icon_support);
            favorIv = (ImageView) itemView.findViewById(R.id.icon_favor);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.circle_item_ll);
        }
    }
}
