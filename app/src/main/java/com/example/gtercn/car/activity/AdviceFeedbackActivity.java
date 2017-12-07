package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : LiHang.
 * data : 2016/8/5.
 * description:
 * 意见反馈 画面
 */
public class AdviceFeedbackActivity extends BaseActivity implements ResponseJSONObjectListener {
    private static final String TAG = AdviceFeedbackActivity.class.getSimpleName();

    private static final int TYPE = 1;

    @BindView(R.id.advice_content)
    EditText mContent;

    @BindView(R.id.advice_email)
    EditText mEmail;

    @BindView(R.id.advice_submit)
    Button mSubmint;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    private CarApplication mApplication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_feedback);
        ButterKnife.bind(this);
        mApplication = (CarApplication) getApplication();
        mTitle.setText("意见反馈");
        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick(R.id.advice_submit)
    void onClick(View view) {
        String content = mContent.getEditableText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            mContent.setError("请输入您的意见!");
            return;
        }
        String mail = mEmail.getEditableText().toString().trim();

        if (isLogin()) {
            try {
                User user = ((CarApplication) getApplication()).getUser();
                String token = user.getResult().getToken();
                String sign = MD5.getSign(ApiManager.URL_FEEDBACK, user);
                String t = MD5.gettimes();
                JSONObject map = new JSONObject();
                map.put("content", content);
                map.put("mail", mail);

                String url = ApiManager.URL_FEEDBACK + "?token=" + token + "&t=" + t + "&sign=" + sign;
                THttpOpenHelper.newInstance().requestJsonObjectPost(url, map, this, TYPE, "advice");
                showDialog(getString(R.string.dialog_commit));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //提示请登录
            showToastMsg("请先登录!");
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case 1:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String msg = response.getString("message");
                                showToastMsg(msg);
//                                showToastMsg("您的意见反馈已提交");
                                finish();
                            } else {
                                //非正常返回码
                                String errMsg = response.getString("err_message");
                                showToastMsg(errMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //请求返回的为Null；
                    showToastMsg(getString(R.string.response_commit_null));
                }
                break;
            default:
        }

    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    protected boolean isLogin() {
        return super.isLogin();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
