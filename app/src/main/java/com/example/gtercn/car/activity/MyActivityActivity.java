package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.MyActivityAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.MyActivityBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyActivityActivity extends BaseActivity implements ResponseJSONObjectListener {
    private static final String TAG = "MyActivityActivity";
    private TextView mCallbackText;
    private ListView mListView;
    private CarApplication mApplication;
    private List<MyActivityBean> list;
    private User mUser;
    private View view;
    private MyActivityAdapter mAdapter;

    @BindView(R.id.custom_empty_view_textview)
    TextView mEmptyViewText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);
        ButterKnife.bind(this);
        mApplication = (CarApplication) getApplication();
        mUser = mApplication.getUser();
        mCallbackText = (TextView) findViewById(R.id.my_activity_callback_textview);
        mListView = (ListView) findViewById(R.id.my_activity_listview);
        view = findViewById(R.id.my_activity_emptyview);
        mCallbackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(list!=null) {
//            list.clear();
//        }
    }

    //请求数据
    private void getData() {
        if (mUser == null) {
            return;
        } else {
            String token = mUser.getResult().getToken();
            String sign = MD5.getSign(ApiManager.URL_MY_ACTIVITY, mUser);
            String t = MD5.gettimes();
            String url = getUrl(ApiManager.URL_MY_ACTIVITY, token, t, sign);
            JSONObject params = new JSONObject();
            try {

                params.put("city_code", SharedPreferenceHelper.getValue(ApiManager.CITY_CODE));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, TAG);
            showDialog(getString(R.string.loading_data));
        }
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
        if (response != null) {
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    TAppUtils.logout(mApplication,code);
                    if (TextUtils.equals(code, "0")) {
                        Gson gson = new Gson();
                        JSONArray array = response.getJSONArray("result");
                        list = gson.fromJson(array.toString(),
                                new TypeToken<List<MyActivityBean>>() {
                                }.getType());
                        if(list!=null && list.size()!=0) {
                            view.setVisibility(View.GONE);
                            mAdapter = new MyActivityAdapter(this, list);
                            mListView.setAdapter(mAdapter);
                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MyActivityActivity.this, SelfDrivingTravelDetailActivity.class);
                                    intent.putExtra("id", list.get(position).getId());
                                    startActivity(intent);
                                }
                            });
                        }else {
                            mEmptyViewText.setText("没有相关活动信息");
                            view.setVisibility(View.VISIBLE);
                        }

                    } else {
                        closeDialog();
                        mEmptyViewText.setText("没有相关活动信息");
                        view.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
            }
        } else {
            //请求返回的为Null；
            mEmptyViewText.setText("没有相关活动信息");
            view.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        mEmptyViewText.setText("没有相关活动信息");
        view.setVisibility(View.VISIBLE);
        showToastMsg(error);
    }

}
