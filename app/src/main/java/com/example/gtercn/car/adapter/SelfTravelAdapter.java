package com.example.gtercn.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.LoginActivity;
import com.example.gtercn.car.activity.SelfDrivingTravelDetailActivity;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.SelfTravelBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SelfTravelAdapter extends BaseAdapter implements ResponseJSONObjectListener{
    private static final String TAG = "SelfTravelAdapter";
    private static final int SELFENROL = 52611032;
    private static final int SELFREMOVEENROL = 10320648;
    private static final int SELFCANCEL =  52613194;
    private CarApplication mApplication;
    private User mUser;
    private Context context;
    private List<SelfTravelBean.ResultBean> mList;
    private Handler mHandler;

    private float density = 1.5f;

    public SelfTravelAdapter(Context context, List<SelfTravelBean.ResultBean> mList) {
        this.context = context;
        this.mList = mList;
        mApplication = (CarApplication) context.getApplicationContext();
        mUser = mApplication.getUser();
        //TLog.e(TAG,"--SelfTravelAdapter->"+mUser.getResult());
        density = context.getResources().getDisplayMetrics().density;

    }

    @Override
    public int getCount() {
        return mList != null ? mList.size(): 0;
    }

    @Override
    public Object getItem(int position) {
        return mList != null ? mList.get(position): null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.listview_self_travel_item, null);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.self_travel_item_imageview);
            holder.mTextView = (TextView) convertView.findViewById(R.id.self_travel_item_title_textview);
            holder.mCancelTextView = (TextView)convertView.findViewById(R.id.self_travel_item_cancel_textview);
            holder.mApplyTextView = (TextView) convertView.findViewById(R.id.self_travel_item_apply_textview);
            holder.mLayout = (RelativeLayout)convertView.findViewById(R.id.self_travel_item_relativelayout);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mTextView.setText(mList.get(position).getTitle());


        if(mUser !=null) {
            if (mList.get(position).getPublic_flag().equals("0") == true) {//是否为发布者0不是，1是
                //用户
                if (mList.get(position).getAvailable_flag() == 1) { //是否有效1有效  ， 0无效
                    //活动有效
                    holder.mApplyTextView.setVisibility(View.VISIBLE);
                    holder.mCancelTextView.setVisibility(View.GONE);
                    if (mList.get(position).getEnroll_flag() == null || mList.get(position).getEnroll_flag().equals("0") == true) {//报名flag(1:能,0:不能)

                        holder.mCancelTextView.setVisibility(View.GONE);
                        holder.mApplyTextView.setVisibility(View.GONE);
                    } else {
                        if (mList.get(position).getSign_flag() == 0) {//(1：报名，0：、未报名)
                            holder.mCancelTextView.setVisibility(View.GONE);
                            holder.mApplyTextView.setVisibility(View.VISIBLE);
                            holder.mApplyTextView.setSelected(false);
                            holder.mApplyTextView.setText("马上报名");

                        } else {
                            holder.mCancelTextView.setVisibility(View.GONE);
                            holder.mApplyTextView.setVisibility(View.VISIBLE);
                            holder.mApplyTextView.setSelected(true);
                            holder.mApplyTextView.setText("已报名");
                        }
                    }
                } else {

                    holder.mCancelTextView.setVisibility(View.GONE);
                    holder.mApplyTextView.setVisibility(View.GONE);
                }
            } else {
                holder.mCancelTextView.setVisibility(View.VISIBLE);
                holder.mApplyTextView.setVisibility(View.GONE);
                //发布者
                if (mList.get(position).getAvailable_flag() == 1) {
                    //活动有效

                    holder.mCancelTextView.setSelected(false);
                    holder.mCancelTextView.setText("取消活动");
                    holder.mCancelTextView.setClickable(true);
                } else {

                    holder.mCancelTextView.setVisibility(View.GONE);
                    holder.mApplyTextView.setVisibility(View.GONE);
                }
            }
        }else{
            if(mList.get(position).getAvailable_flag() == 1) {
                holder.mCancelTextView.setVisibility(View.GONE);
                holder.mApplyTextView.setVisibility(View.VISIBLE);
                holder.mApplyTextView.setSelected(false);
                holder.mApplyTextView.setText("马上报名");
            }else{

                holder.mCancelTextView.setVisibility(View.GONE);
                holder.mApplyTextView.setVisibility(View.GONE);
            }
        }

        holder.mApplyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser != null) {
                    String token = mUser.getResult().getToken();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("self_driving_id",mList.get(position).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (holder.mApplyTextView.isSelected()) {
                        String sign = MD5.getSign(ApiManager.URL_SELF_REMOVE_ENROL, mUser);
                        String time = MD5.gettimes();
                        String url = ApiManager.URL_SELF_REMOVE_ENROL +"?token="+token+"&t="+time+"&sign="+sign;
                        ApiManager.SelfRemoveEnrol(url,jsonObject,SelfTravelAdapter.this,SELFREMOVEENROL,TAG);
                    } else {
                        String sign = MD5.getSign(ApiManager.URL_SELF_ENROL, mUser);
                        String time = MD5.gettimes();
                        String url = ApiManager.URL_SELF_ENROL +"?token="+token+"&t="+time+"&sign="+sign;
                        ApiManager.SelfEnrol(url, jsonObject,SelfTravelAdapter.this,SELFENROL, TAG);
                    }
                    mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 10320682:
                                    holder.mApplyTextView.setSelected(true);
                                    holder.mApplyTextView.setText("已报名");
                                    break;
                                case 6480682:
                                    holder.mApplyTextView.setSelected(false);
                                    holder.mApplyTextView.setText("马上报名");
                                    break;
                            }

                        }
                    };
                }
                else{
                    Toast.makeText(context,"请先登录！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }

        });
        holder.mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser != null) {
                    String token = mUser.getResult().getToken();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("self_driving_id",mList.get(position).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (holder.mApplyTextView.isSelected() == false) {
                        String sign = MD5.getSign(ApiManager.URL_SELF_CANCEL, mUser);
                        String time = MD5.gettimes();
                        String url = ApiManager.URL_SELF_CANCEL +"?token="+token+"&t="+time+"&sign="+sign;
                       THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,SelfTravelAdapter.this,SELFCANCEL,TAG);
                    }
                    mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 6483194:
                                    holder.mCancelTextView.setSelected(true);
                                    holder.mCancelTextView.setText("活动结束");
                                    holder.mCancelTextView.setClickable(false);
                                    break;
                            }

                        }
                    };
                }
                else{
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });


        List<String> urlList = mList.get(position).getPic_urls_list();

        String url = null;

        if(urlList != null ){
            url = urlList.get(0);
        }

        if(TextUtils.isEmpty(url) == false){
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
            THttpOpenHelper.newInstance().setImageBitmap(holder.mImageView, url,
                    (int)(110 * density), (int)(80 * density),
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        }else {
            holder.mImageView.setImageResource(R.drawable.icon_default);
        }
       holder.mLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   Intent intent = new Intent(context, SelfDrivingTravelDetailActivity.class);
                   Bundle bundle = new Bundle();
                   intent.putExtra("id", mList.get(position).getId());
                   context.startActivity(intent);

           }
       });
        return convertView;
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        switch (type) {
            case SELFENROL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                Message msg = mHandler.obtainMessage();
                                msg.what = 10320682;
                                mHandler.sendMessage(msg);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                    //请求返回的为Null；
                }
                break;
            case SELFREMOVEENROL :
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                Message msg = mHandler.obtainMessage();
                                msg.what = 6480682;
                                mHandler.sendMessage(msg);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                    //请求返回的为Null；
                }
                break;
            case  SELFCANCEL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                Message msg = mHandler.obtainMessage();
                                msg.what = 6483194;
                                mHandler.sendMessage(msg);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                    //请求返回的为Null；
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {

    }

    private class Holder {
        TextView mTextView;
        TextView mCancelTextView;
        RelativeLayout mLayout;
      //  TextView mTellTextview;
        TextView mApplyTextView;
        ImageView mImageView;
    }
}
