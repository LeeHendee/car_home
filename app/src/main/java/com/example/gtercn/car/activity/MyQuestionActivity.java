package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.MyQuestionAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.QuestionBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : LiHang.
 * data : 2017/2/21.
 * description:
 *    我的提问
 */
public class MyQuestionActivity extends BaseActivity implements ResponseJSONObjectListener ,MyQuestionAdapter.itemClickListener{

    private static final String TAG = "MyQuestionActivity";

    private MyQuestionAdapter mAdapter;

    private List<QuestionBean.ResultBean> mList;

    private CarApplication mApp;

    @BindView(R.id.my_question_recy)
    RecyclerView mRecyView;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @BindView(R.id.empty_question_view)
    View mEmptyView;

    @BindView(R.id.custom_empty_view_textview)
    TextView mEmptyViewText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        ButterKnife.bind(this);
        mTitle.setText("我的提问");
        mEmptyView.setVisibility(View.GONE);
        initData();
        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        mApp = (CarApplication) getApplication();
        mAdapter = new MyQuestionAdapter(mList, this);
        mAdapter.setItemClickListener(this);
        mRecyView.setLayoutManager(new LinearLayoutManager(this));
        mRecyView.setAdapter(mAdapter);
        User user = mApp.getUser();
        if (user == null) {
            showToastMsg("用户信息不存在!");
            return;
        }
        String sign = MD5.getSign(ApiManager.URL_MY_QUESTION, user);
        String t = MD5.gettimes();
        String url = ApiManager.URL_MY_QUESTION + "?token=" + user.getResult().getToken() + "&t=" + t + "&sign=" + sign;
        JSONObject params = new JSONObject();
        THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 0, TAG);
        showDialog(getString(R.string.loading_data));
    }

    @Override
    public void itemClick(QuestionBean.ResultBean bean) {
        String id = bean.getId();
        Intent goQuestionDetail = new Intent(this,ExpertQuestionDetailActivity.class);
        goQuestionDetail.putExtra("id",id);
        startActivity(goQuestionDetail);
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
            TLog.i(TAG, "--->>response is = " + response.toString());
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    TAppUtils.logout(mApp,code);
                    TLog.i(TAG, "--->>code is " + code);
                    if (TextUtils.equals(code, "0")) {
                        Gson gson = new Gson();
                        QuestionBean bean = gson.fromJson(response.toString(), QuestionBean.class);
                        if (bean != null) {
                            TLog.i(TAG, "--->>bean isnot null");
                            List<QuestionBean.ResultBean> list = bean.getResult();
                            if (list != null && list.size() > 0) {
                                mList.addAll(list);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                TLog.i(TAG, "--->>bean isnot null");
                                mEmptyViewText.setText("没有相关问题信息");
                                mEmptyView.setVisibility(View.VISIBLE);
                            }
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
}
