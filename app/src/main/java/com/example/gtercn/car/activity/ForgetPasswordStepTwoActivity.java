package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.bean.ForgetPasswordBean;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpsOpenHelper;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : LiHang
 * data : 2016/12/9.
 * description:
 * 忘记密码第二步 画面
 */
public class ForgetPasswordStepTwoActivity extends BaseActivity implements ResponseJSONObjectListener {
    private static final String TAG = ForgetPasswordStepOneActivity.class.getSimpleName();

    private static final int TYPE = 1;

    @BindView(R.id.forget_password_newpassword)
    EditText mNewPassword;

    @BindView(R.id.forget_password_confirm_newpassword)
    EditText mConfirmNewPassword;

    @BindView(R.id.forget_password_determine_btn)
    TextView mDetermineBtn;

    private ForgetPasswordBean mBean;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_step_two);
        ButterKnife.bind(this);
        mBar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
        barTitle.setText("忘记密码");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBean = (ForgetPasswordBean) getIntent().getSerializableExtra("bean");
        mDetermineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitData();
            }
        });
    }

    //提交数据
    private void commitData() {
        if (mBean == null) {
            return;
        }
        String newPassword = mNewPassword.getText().toString().trim();
        String confirmNewPassword = mConfirmNewPassword.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)) {
            mNewPassword.requestFocus();
            showToastMsg(getString(R.string.password_null));
            return;
        }

        if (TextUtils.isEmpty(confirmNewPassword)) {
            mConfirmNewPassword.requestFocus();
            showToastMsg(getString(R.string.confirm_password_null));
            return;
        }

        if (!TextUtils.equals(newPassword, confirmNewPassword)) {
            mConfirmNewPassword.requestFocus();
            showToastMsg(getString(R.string.password_not_same));
            return;
        }
//        stopKeyboard();
        try {
            String phone = mBean.getPhone();
            String verify = mBean.getVerify();
            JSONObject map = new JSONObject();
            map.put("login_phone", phone);
            map.put("verify_code", verify);
            map.put("verify_type", "1");
            map.put("password", newPassword);
            String url = ApiManager.URL_FORGETPASSWORD;
            THttpsOpenHelper.newInstance().requestJsonObjectPost(url, map, this, TYPE, TAG);
            showDialog(getString(R.string.dialog_commit));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            //提交数据成功返回结果
            case 1:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            String message = response.getString("message");
                            showToastMsg(message);
                            if (TextUtils.equals(code, "0")) {
                                showMessage(response);
                                finish();
                            } else {
                                //非正常返回码
                                showErrMessage(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastMsg(getString(R.string.response_commit_null));
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

    private void showErrMessage(JSONObject response) {
        if (response.has("message")) {
            try {
                String message = response.getString("message");
                if (!TextUtils.isEmpty(message)) {
                    showToastMsg(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMessage(JSONObject response) {
        if (response.has("message")) {
            try {
                String message = response.getString("message");
                if (!TextUtils.isEmpty(message)) {
                    showToastMsg(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
