package com.example.gtercn.car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.QuestionBean;
import com.example.gtercn.car.utils.TAppUtils;

import java.util.List;

/**
 * author : LiHang.
 * data : 2017/2/22.
 * description:
 * 我的提问adapter
 */
public class MyQuestionAdapter extends RecyclerView.Adapter<MyQuestionAdapter.QuestionViewHolder> {

    private List<QuestionBean.ResultBean> mList;

    private Context mContext;

    private itemClickListener itemClickListener;

    public MyQuestionAdapter(List<QuestionBean.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setItemClickListener(MyQuestionAdapter.itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_question, parent, false);
        QuestionViewHolder holder = new QuestionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        final QuestionBean.ResultBean bean = mList.get(position);
        if (mList != null && mList.size() > 0) {
            holder.questionContent.setText(mList.get(position).getContent());
            String time = TAppUtils.trimTime(mList.get(position).getInsert_time());
            holder.insertTime.setText(time);
        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionContent;
        TextView insertTime;
        LinearLayout itemLayout;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            questionContent = (TextView) itemView.findViewById(R.id.my_question_content);
            insertTime = (TextView) itemView.findViewById(R.id.my_question_insert_time);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.question_item_ll);
        }
    }
    public interface itemClickListener {
        void itemClick(QuestionBean.ResultBean bean);
    }
}
