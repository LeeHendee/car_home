package com.example.gtercn.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.bean.SelfSubCommentBean;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.MyDialog;

/**
 * Created by Administrator on 2016/11/17.
 */

public class SelfSubCommentAdapter extends BaseAdapter {
    private static final String TAG = "SelfSubCommentAdapter";
    private Context context;
    private MyDialog myDialog;
    private SelfSubCommentBean selfSubCommentBean;
    private EditText mEditText;
    private String self_id;

    public SelfSubCommentAdapter(Context context, SelfSubCommentBean selfSubCommentBean, EditText mEditText) {
        TLog.i(TAG, "------SelfSubCommentAdapter------>" + selfSubCommentBean.getResult());
        this.context = context;
        this.selfSubCommentBean = selfSubCommentBean;
        this.mEditText = mEditText;
    }

    @Override
    public int getCount() {
        return selfSubCommentBean.getResult().size();
    }

    @Override
    public Object getItem(int position) {
        return selfSubCommentBean.getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ParentViewHolder pvh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.expandlistview_chirld_item, null);
            pvh = new ParentViewHolder();
            pvh.mNameTextView = (TextView) convertView.findViewById(R.id.expandlistview_chirld_item_nametext);
            pvh.mToNameTextView = (TextView) convertView.findViewById(R.id.expandlistview_chirld_item_tonametext);
            pvh.mAllCommenttextView = (TextView) convertView.findViewById(R.id.expandlistview_chirld_item_comment);
            convertView.setTag(pvh);
        } else {
            pvh = (ParentViewHolder) convertView.getTag();
        }
        pvh.mNameTextView.setText(selfSubCommentBean.getResult().get(position).getNickname());
        pvh.mToNameTextView.setText(selfSubCommentBean.getResult().get(position).getTo_nickname());
        pvh.mAllCommenttextView.setText(selfSubCommentBean.getResult().get(position).getContent());
//        pvh.mAllCommenttextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mEditText.setFocusable(true);
//                mEditText.setFocusableInTouchMode(true);
//                mEditText.requestFocus();
//                mEditText.findFocus();
//                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                // imm.showSoftInputFromInputMethod(mEditText.getWindowToken(), 0);
//                // imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
//                imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);// 显示输入法
//            }
//        });

        return convertView;

    }


    class ParentViewHolder {
        TextView mNameTextView;
        TextView mToNameTextView;
        TextView mAllCommenttextView;

    }
}
