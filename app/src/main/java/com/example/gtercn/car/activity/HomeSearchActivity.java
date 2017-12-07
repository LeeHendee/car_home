package com.example.gtercn.car.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.SearchRecyAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.CarWashBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.DividerItemDecoration;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : LiHang.
 * data : 2017/2/13.
 * description:
 * home搜索功能页面
 */
public class HomeSearchActivity extends BaseActivity implements ResponseJSONObjectListener {

    private static final String TAG = "HomeSearchActivity";

    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.search_back_tv)
    TextView mBackTv;

    @BindView(R.id.search_et)
    EditText mSearchEt;

    @BindView(R.id.search_recy)
    RecyclerView mRecyclerView;

    @BindView(R.id.hot_search)
    LinearLayout mHotSearch;

    @BindView(R.id.search_rescue)
    TextView mHotRescue;

    @BindView(R.id.search_clean_car)
    TextView mHotCleanCar;

    @BindView(R.id.search_tyre)
    TextView mHotTyre;

    @BindView(R.id.search_maintain)
    TextView mHotMaintain;

    @BindView(R.id.search_repair)
    TextView mHotRepair;

    @BindView(R.id.empty_search_view)
    View mEmptyView;

    @BindView(R.id.custom_empty_view_textview)
    TextView mEmptyViewText;



    private List<CarWashBean.ResultBean> mSearchList;

    private SearchRecyAdapter mAdapter;

    private CarApplication mApp;

    private User mUser;

    private int FLAG = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initListener();
        mEmptyView.setVisibility(View.GONE);
        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Drawable drawable1 = ContextCompat.getDrawable(mContext, R.drawable.icon_search);
        drawable1.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        mSearchEt.setCompoundDrawables(drawable1, null, null, null);//只放左边
        mSearchEt.setOnEditorActionListener(new MyEditor());
    }

    private void initData(String keyword) {
        mApp = (CarApplication) getApplication();
        mUser = mApp.getUser();
        mSearchList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new SearchRecyAdapter(this, mSearchList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemListener(new IRecyclerItemListener() {
            @Override
            public void callback(Object o) {
                /**
                 *  根据实际情况处理。
                 */
                Intent intent = new Intent(HomeSearchActivity.this, FourServeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("shop_id", (String) o);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        String token = null;
        String url = null;
        if (mUser != null) {
            token = mUser.getResult().getToken();
            url = ApiManager.URL_SEARCH_HOME + "?token=" + token;
        } else {
            url = ApiManager.URL_SEARCH_HOME;
        }

        if (keyword == null || keyword.length() == 0) {
            showToastMsg("请输入要搜索的内容");
            return;
        }

        JSONObject params = new JSONObject();
        try {
            params.put("city_code", SharedPreferenceHelper.getValue(ApiManager.CITY_CODE));
            params.put("keyword", keyword);
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 0, TAG);
            showDialog(getString(R.string.loading_data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({
            R.id.search_back_tv,
            R.id.search_et,
            R.id.search_rescue,
            R.id.search_clean_car,
            R.id.search_tyre,
            R.id.search_maintain,
            R.id.search_repair,
    })
    void onSearchListener(View v) {
        switch (v.getId()) {
            case R.id.search_back_tv:
                finish();
                break;
            case R.id.search_rescue:
                initData("中心");
                break;
            case R.id.search_clean_car:
                initData("洗车");
                FLAG = 9;
                break;
            case R.id.search_tyre:
                initData("轮胎");
                FLAG = 7;
                break;
            case R.id.search_maintain:
                initData("保养");
                FLAG = 8;
                break;
            case R.id.search_repair:
                initData("修车");
                FLAG = 10;
                break;
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
            TLog.i(TAG, "-->>onSuccessResponse response is " + response.toString());
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    TAppUtils.logout(mApp,code);
                    if (TextUtils.equals("0", code)) {
                        Gson gson = new Gson();
                        CarWashBean searchBean = gson.fromJson(response.toString(), CarWashBean.class);
                        if (searchBean != null) {
                            List<CarWashBean.ResultBean> list = searchBean.getResult();

                            if (list == null || list.size() == 0) {
//                                showSnack(mSearchEt, "没有相关信息");
                                mEmptyViewText.setText("没有搜索到相关信息");
                                mEmptyView.setVisibility(View.VISIBLE);
                            }else {
                                mEmptyView.setVisibility(View.GONE);
                            }

                            BDLocation bdLocation = AppLocation.newInstance().getBDLocation();
                            LatLng curPoint = null;
                            if(bdLocation!=null) {
                                curPoint = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                            }else{
                                String appLatitude = SharedPreferenceHelper.getValue(ApiManager.LAT);
                                String appLongitude = SharedPreferenceHelper.getValue(ApiManager.LNG);
                                curPoint = new LatLng(Double.valueOf(appLatitude),Double.valueOf(appLongitude));
                            }

                            mSearchList.clear();
                            for (CarWashBean.ResultBean bean : list) {
                                if(bean.getLatitude()!=null && bean.getLongtitude()!=null) {
                                    LatLng shopPoint = new LatLng(Double.valueOf(bean.getLatitude()),
                                            Double.valueOf(bean.getLongtitude()));
                                    double dis = DistanceUtil.getDistance(curPoint, shopPoint);
                                    double   f1   =   Math.floor(dis);
                                    bean.setDistance(f1);
                                }
                                mSearchList.add(bean);
                            }

                            mAdapter.notifyDataSetChanged();
                            hideKeyboard(mSearchEt);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }

    class MyEditor implements EditText.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (EditorInfo.IME_ACTION_SEARCH == actionId || EditorInfo.IME_ACTION_DONE == actionId) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                initData(mSearchEt.getText().toString().trim());
                return true;
            }
            return false;
        }
    }
}
