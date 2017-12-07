package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.PeopleRecyclerAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.PeopleRollBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 自驾游参与的名单页
 */
public class PeopleRollActivity extends BaseActivity implements ResponseJSONObjectListener{
    private static final String TAG = "PeopleRollActivity";
    private static final int PEOPLEROLL = 6820830;
    private ListView mRecyclerView;
    private TextView mTitleTextView;
    private TextView mCallBackTextView;
    private PeopleRecyclerAdapter mPeopleRecyclerAdapter;
    private String id;
    private CarApplication mApplication;
    private User mUser;
    private PeopleRollBean mPeopleRollBean;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_roll);
        id = getIntent().getStringExtra("id");
        View view = findViewById(R.id.activity_people_include);
        mTitleTextView = (TextView)view.findViewById(R.id.toolbar_title);
        mCallBackTextView = (TextView)view.findViewById(R.id.toolbar_back_tv);
        mCallBackTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleTextView.setText("参与名单");
        mRecyclerView = (ListView)findViewById(R.id.activity_people_recyclerview);
        mView = findViewById(R.id.activity_people_empty_view);
        mApplication = (CarApplication) getApplication();
        showDialog(getString(R.string.dialog_data));
        mUser = mApplication.getUser();
        String sign = MD5.getSign(ApiManager.URL_SELF_ROLL, mUser);
        String t =  MD5.gettimes();
        String url = ApiManager.URL_SELF_ROLL+"?token="+mUser.getResult().getToken()+"&t="+t+"&sign="+sign;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("self_driving_id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,PEOPLEROLL,TAG);
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        Log.i(TAG,"-----------response------------>"+response);
        if (response != null) {
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    TAppUtils.logout(mApplication,code);
                    if (TextUtils.equals(code, "0")) {
                        Gson gson = new Gson();
                        mPeopleRollBean = gson.fromJson(String.valueOf(response),PeopleRollBean.class);
                        Log.i(TAG,"----mPeopleRollBean--->");
                        List<PeopleRollBean.ResultBean> list = mPeopleRollBean.getResult();
                        if(list!=null && list.size()!=0) {
                            mView.setVisibility(View.GONE);
                            mPeopleRecyclerAdapter = new PeopleRecyclerAdapter(this, list);
                            mRecyclerView.setAdapter(mPeopleRecyclerAdapter);
                        }else{
                            mView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        closeDialog();
                       mView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }
        } else {
            //请求返回的为Null；
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
closeDialog();
    }
}
