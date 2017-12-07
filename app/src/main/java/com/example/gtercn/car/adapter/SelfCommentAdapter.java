package com.example.gtercn.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.SelfDrivingTravelDetailActivity;
import com.example.gtercn.car.activity.SelfSubClassCommentActivity;
import com.example.gtercn.car.bean.CarWashBean;
import com.example.gtercn.car.bean.SelfDriveCommentBean;
import com.example.gtercn.car.bean.SelfSubCommentBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.MyDialog;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public class SelfCommentAdapter extends BaseAdapter {
    private static final String TAG = "SelfCommentAdapter";
    private static final int SUBCOMMENT = 13116097;
    private Context context;
    private List<CarWashBean> mList;
    private MyDialog myDialog;
    private SelfDriveCommentBean selfDriveCommentBean;
    private int num;
    private EditText mEditText;
    private String self_id;
    private SelfSubCommentBean selfSubCommentBean;
    private float density = 1.5f;

    private Object tag;
    private ListView mlistview;
    private ListView mListView;

    public SelfCommentAdapter(Context context, SelfDriveCommentBean selfDriveCommentBean, EditText mEditText, String self_id, ListView mListView) {
        this.context = context;
        this.selfDriveCommentBean = selfDriveCommentBean;
        this.mEditText = mEditText;
        this.self_id = self_id;
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        return selfDriveCommentBean.getResult().size();
    }

    @Override
    public Object getItem(int position) {
        return selfDriveCommentBean.getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ParentViewHolder pvh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.expandlistview_parent_item, null);
            pvh = new ParentViewHolder();
            pvh.mNameTextView = (TextView) convertView.findViewById(R.id.expandlistview_parent_item_nametext);
            pvh.mDetailTextview = (TextView) convertView.findViewById(R.id.expandlistview_parent_item_detailtext);
            pvh.mDayTextView = (TextView) convertView.findViewById(R.id.expandlistview_parent_item_daytext);
            pvh.mTimeTextView = (TextView) convertView.findViewById(R.id.expandlistview_parent_item_timestext);
            pvh.mCommentTextView = (TextView) convertView.findViewById(R.id.expandlistview_parent_item_comment_textview);
            pvh.mAllCommenttextView = (TextView) convertView.findViewById(R.id.expandlistview_parent_item_allcomment_textview);
            pvh.mLinearLayout=(LinearLayout) convertView.findViewById(R.id.expandlistview_parent_item_linearlayout);
            pvh.mHeadImageview = (ImageView)convertView.findViewById(R.id.expandlistview_parent_item_imageview);
            convertView.setTag(pvh);
        } else {
            pvh = (ParentViewHolder) convertView.getTag();
        }
        pvh.mNameTextView.setText(selfDriveCommentBean.getResult().get(position).getNickname());
        pvh.mDetailTextview.setText(selfDriveCommentBean.getResult().get(position).getContent());
        pvh.mDayTextView.setText(selfDriveCommentBean.getResult().get(position).getInsert_time());
        String url = selfDriveCommentBean.getResult().get(position).getAvatar_url() ;
        if(url != null) {
            if (url.contains("\\")) {
                url = TAppUtils.formatUrl(url);
            }
        }
        THttpOpenHelper.newInstance().setImageBitmap(pvh.mHeadImageview,url,(int)(60 * density),(int)(60 * density),R.drawable.personal_info_portrait,R.drawable.personal_info_portrait);
        pvh.mCommentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TLog.i(TAG, "-----mCommentTextView------->");
                mEditText.setFocusable(true);
                mEditText.setFocusableInTouchMode(true);
                mEditText.requestFocus();
                mEditText.findFocus();
                mEditText.setHint(R.string.self_reply);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // imm.showSoftInputFromInputMethod(mEditText.getWindowToken(), 0);
                // imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);// 显示输入法
                SelfDrivingTravelDetailActivity.isComment = false;
                SelfDrivingTravelDetailActivity.item_id = selfDriveCommentBean.getResult().get(position).getId();
                SelfDrivingTravelDetailActivity.to_user_id = selfDriveCommentBean.getResult().get(position).getUser_id();
                //  mEditText.performClick();

            }
        });
        pvh.mAllCommenttextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnet = new Intent(context, SelfSubClassCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("self_id", self_id);
                bundle.putSerializable("resultBean", selfDriveCommentBean.getResult().get(position));
                intnet.putExtra("bundle", bundle);
                context.startActivity(intnet);
            }
        });
        pvh.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnet = new Intent(context, SelfSubClassCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("self_id", self_id);
                bundle.putSerializable("resultBean", selfDriveCommentBean.getResult().get(position));
                intnet.putExtra("bundle", bundle);
                context.startActivity(intnet);
            }
        });

        return convertView;

    }


    class ParentViewHolder {
        TextView mNameTextView;
        ImageView mHeadImageview;
        TextView mDetailTextview;
        TextView mDayTextView;
        TextView mTimeTextView;
        TextView mCommentTextView;
        TextView mAllCommenttextView;
        LinearLayout mLinearLayout;
    }


}
