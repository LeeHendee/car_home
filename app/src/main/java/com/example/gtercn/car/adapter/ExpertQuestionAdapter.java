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
import com.example.gtercn.car.activity.PhotoViewActivity;
import com.example.gtercn.car.bean.ExpertQuestionBean;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * author : LiHang.
 * data : 2016/11/17.
 * description:
 * 问题墙adapter
 */
public class ExpertQuestionAdapter extends RecyclerView.Adapter<ExpertQuestionAdapter.QuestionViewHolder> {

    private static final String TAG = "ExpertQuestionAdapter";

    private Context mContext;

    private List<ExpertQuestionBean.ResultBean> mList;

    private IRecyclerItemListener<Integer> itemListener;

    private float density = 1.5f ;

    public void setItemListener(IRecyclerItemListener<Integer> itemListener) {
        this.itemListener = itemListener;
    }

    public ExpertQuestionAdapter(Context context, List<ExpertQuestionBean.ResultBean> list) {
        this.mContext = context;
        this.mList = list;

        density = mContext.getResources().getDisplayMetrics().density;

    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_expert_question,parent,false);
        QuestionViewHolder holder = new QuestionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        sortInfo();
        ExpertQuestionBean.ResultBean bean = mList.get(position);
        if (bean == null) {
            TLog.i(TAG, "----->>ExpertQuestionBean.ResultBean is null !!");
            return;
        }

        String url = bean.getUser_portrait();
        holder.headPortrait.setImageResource(R.drawable.personal_info_portrait);
        if (TextUtils.isEmpty(url)) {
            holder.headPortrait.setImageResource(R.drawable.personal_info_portrait);
        }else{
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
            THttpOpenHelper.newInstance().setImageBitmap(holder.headPortrait, url,
                    (int)(50 * density), (int)(50 * density),
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        }
        holder.nickName.setText(bean.getNickname());


//        String interval = TimeComputeUtil.getInterval(bean.getInsert_time());
//        TLog.i(TAG, "------->>interval is " + interval);
        holder.commitTime.setText(bean.getPublish_time());

        holder.questionContent.setText(bean.getContent());
        holder.numberOfReply.setText(bean.getReply_num());

        final  int p = position;
        holder.questionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.callback(p);
            }
        });

        holder.questionImgLyt.removeAllViews();

        /**
         * 以下是处理加载图片，并可以点击查看图片，未测试。
         */
        String arrayStr = (String) bean.getRes_url_list();
        if(!TextUtils.isEmpty(arrayStr)){
            String[] array = arrayStr.split(",");
            for(String str : array){
                String imgUrl = str;
                if(!TextUtils.isEmpty(imgUrl)){
                    View imgView = LayoutInflater.from(mContext).inflate(R.layout.road_gridview_item, null);
                    ImageView img = (ImageView) imgView.findViewById(R.id.gridview_img);

                    if(imgUrl.contains("\\")){
                        imgUrl = TAppUtils.formatUrl(imgUrl);
                    }

                    /**
                     * 更换缺省图片
                     */
                    THttpOpenHelper.newInstance().setImageBitmap(img, imgUrl,
                            (int)(90 * density), (int)(90 * density),
                            R.drawable.icon_default,
                            R.drawable.icon_default);

                    final String xxUrl = imgUrl;
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, PhotoViewActivity.class);
                            intent.putExtra("url", xxUrl);
                            mContext.startActivity(intent);
                        }
                    });

                    holder.questionImgLyt.addView(imgView);
//                    View emptyView = new View(mContext);
//                    ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
//                    lp.width = (int)(5* density);
//                    lp.height = (int)( 90 * density);
//                    emptyView.setLayoutParams(lp);
//                    holder.questionImgLyt.addView(emptyView);

                }


            }
        }


    }

    private void sortInfo() {
        Comparator<ExpertQuestionBean.ResultBean> itemComparator = new Comparator<ExpertQuestionBean.ResultBean>() {
            @Override
            public int compare(ExpertQuestionBean.ResultBean lhs, ExpertQuestionBean.ResultBean rhs) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                Date dateLeft = null;
                Date dateRight = null;
                try {
                    dateLeft = sdf.parse(lhs.getInsert_time());
                    dateRight = sdf.parse(rhs.getInsert_time());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return dateRight.compareTo(dateLeft);
            }
        };
        Collections.sort(mList, itemComparator);
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder{
        ImageView headPortrait;
        TextView nickName;
        TextView commitTime;
        TextView questionContent;
        TextView numberOfReply;
        LinearLayout questionLayout;
        LinearLayout questionImgLyt;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            nickName = (TextView) itemView.findViewById(R.id.user_nickname);
            commitTime = (TextView) itemView.findViewById(R.id.question_commit_time);
            questionContent = (TextView) itemView.findViewById(R.id.question_content);
            numberOfReply = (TextView) itemView.findViewById(R.id.number_of_reply);
            headPortrait = (ImageView) itemView.findViewById(R.id.question_portrait);
            questionLayout = (LinearLayout) itemView.findViewById(R.id.question_item_ll);
            questionImgLyt = (LinearLayout) itemView.findViewById(R.id.dynamic_img_view);
        }
    }
}
