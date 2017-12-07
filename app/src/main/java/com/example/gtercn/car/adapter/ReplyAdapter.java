package com.example.gtercn.car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.ReplyBean;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * author : LiHang.
 * data : 2016/12/27.
 * description:
 * 回复列表适配器;
 */
public class ReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ReplyAdapter";

    private List<ReplyBean.ResultBean> mReplyList;

    private Context mContext;

    private IRecyclerItemListener<Integer> itemListener;

    public void setItemListener(IRecyclerItemListener<Integer> itemListener) {
        this.itemListener = itemListener;
    }

    public ReplyAdapter(List<ReplyBean.ResultBean> replyList, Context context) {
        this.mReplyList = replyList;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {

        if (mReplyList == null) {
            return 0;
        }
        String itemId = mReplyList.get(position).getItem_id();

        return TextUtils.isEmpty(itemId) ? 1 : 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;

        switch (viewType) {
            case 1:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_reply_to_parent, parent, false);
                holder = new ToParentViewHolder(view);
                break;
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_reply_to_replier, parent, false);
                holder = new ToReplierViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        sortInfo();
        ReplyBean.ResultBean bean = mReplyList.get(position);

        final  int p = position;

        if (holder instanceof ToParentViewHolder) {

            ((ToParentViewHolder) holder).replierNickname.setText(bean.getNickname());
            ((ToParentViewHolder) holder).replyContent.setText(bean.getContent());
            ((ToParentViewHolder) holder).toParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.callback(p);
                }
            });
        }
        if (holder instanceof ToReplierViewHolder) {

            ((ToReplierViewHolder) holder).replierNickname.setText(bean.getNickname());
            ((ToReplierViewHolder) holder).replyContent.setText(bean.getContent());
            ((ToReplierViewHolder) holder).toNickname.setText((CharSequence) bean.getTo_nickname());
            ((ToReplierViewHolder) holder).toReplierLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.callback(p);
                }
            });
        }
    }

    /**
     * ListView or RecyclerView 数据排序方法
     * 需求:按照回复时间倒叙排列(最后回复的帖子,需要显示在首位);
     */
    private void sortInfo() {

        //构建比较器对象;Comparator是接口,所以要实现compare方法;
        Comparator<ReplyBean.ResultBean> itemComparator = new Comparator<ReplyBean.ResultBean>() {
            public int compare(ReplyBean.ResultBean info1, ReplyBean.ResultBean info2) {

                Date d1 = null;
                Date d2 = null;
                //构建时间格式化对象(参数为:需要格式化的时间格式)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                try {
                    //时间格式化对象对字符串格式的时间进行格式化,返回Date类型的结果;
                    d1 = sdf.parse(info1.getInsert_time());
                    d2 = sdf.parse(info2.getInsert_time());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //返回d2与d1的比较结果(逆序)--d1.compareTo(d2):返回顺序的结果
                return d2.compareTo(d1);
            }
        };
        //Collections.sort()真正的比较方法;参数是:需要进行排序的list集合,比较器对象(按照某一方式比较)
        Collections.sort(mReplyList, itemComparator);
    }

    @Override
    public int getItemCount() {
        return mReplyList != null ? mReplyList.size() : 0;
    }


    class ToParentViewHolder extends RecyclerView.ViewHolder {

        TextView replierNickname;

        TextView replyContent;

        LinearLayout toParentLayout;

        public ToParentViewHolder(View itemView) {
            super(itemView);
            replierNickname = (TextView) itemView.findViewById(R.id.to_parent_replier_nickname);
            replyContent = (TextView) itemView.findViewById(R.id.to_parent_reply_content);
            toParentLayout = (LinearLayout) itemView.findViewById(R.id.item_reply_parent_ll);
        }
    }

    class ToReplierViewHolder extends RecyclerView.ViewHolder {

        TextView replierNickname;

        TextView replyContent;

        TextView toNickname;

        LinearLayout toReplierLayout;

        public ToReplierViewHolder(View itemView) {
            super(itemView);
            replierNickname = (TextView) itemView.findViewById(R.id.to_replier_replier_nickname);
            replyContent = (TextView) itemView.findViewById(R.id.to_replier_reply_content);
            toNickname = (TextView) itemView.findViewById(R.id.to_replier_to_nickname);
            toReplierLayout = (LinearLayout) itemView.findViewById(R.id.item_reply_to_replier_ll);
        }
    }
}
