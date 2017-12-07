package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.ForgetPasswordBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.bean.VerificationBean;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpsOpenHelper;
import com.example.gtercn.car.utils.TRegularExpression;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : LiHang.
 * data : 2016/12/9.
 * description:
 * 忘记密码第一步 画面
 */
public class ForgetPasswordStepOneActivity extends BaseActivity implements ResponseJSONObjectListener {
    private static final String TAG = ForgetPasswordStepOneActivity.class.getSimpleName();

    private static final int TYPE = 1;

    @BindView(R.id.forget_password_phone)
    EditText mPhone;

    @BindView(R.id.forget_password_verify)
    EditText mVerify;

    @BindView(R.id.forget_password_getverify)
    TextView mGetVerify;

    @BindView(R.id.forget_password_next)
    TextView mNextBtn;

    private String verificationCode;

    @BindView(R.id.toolbar)
    Toolbar mBar;
    private String mPhoneNo;

    private CarApplication mApp;

    private User mUser;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_step_one);
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

        mHandler = new Handler(new MyCallback());

        mGetVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneNo = mPhone.getText().toString().trim();
                if (TextUtils.isEmpty(mPhoneNo)) {
                    mPhone.requestFocus();
                    showToastMsg(getString(R.string.phone_null));
                    return;
                }
                if (!TRegularExpression.isMobileNO(mPhoneNo)) {
                    mPhone.requestFocus();
                    showToastMsg(getString(R.string.phone_format_error));
                    return;
                }
                getData();
            }
        });
        nextStep();
    }


    //获取验证码
    private void getData() {
//        stopKeyboard();
        try {
            JSONObject map = new JSONObject();
            map.put("login_phone", mPhone.getText().toString().trim());
            map.put("verify_type", "1");
            String url = ApiManager.URL_REGISTERVERIFY;
            THttpsOpenHelper.newInstance().requestJsonObjectPost(url, map, this, TYPE, TAG);
            showDialog(getString(R.string.loading_data));
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

    private void countDown() {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mGetVerify.setText(millisUntilFinished / 1000 + "秒后重新获取");
                mGetVerify.setClickable(false);
            }

            @Override
            public void onFinish() {
                mGetVerify.setText("获取验证码");
                mGetVerify.setClickable(true);
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            //获取验证码返回结果
            case 1:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            String message = response.getString("message");
                            showToastMsg(message);
                            if (TextUtils.equals(code, "0")) {
                                mHandler.sendEmptyMessage(100);
                                JSONObject obj = response.getJSONObject("result");
                                Gson gson = new Gson();
                                VerificationBean bean = gson.fromJson(obj.toString(),
                                        new TypeToken<VerificationBean>() {
                                        }.getType());
                                if (bean != null) {
                                    verificationCode = bean.getVerify_code();
                                    if (!TextUtils.isEmpty(verificationCode)) {
//                                        mVerify.setText(verificationCode);
                                        showMessage(response);
                                    }
                                }
                            } else {
                                showErrMessage(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToastMsg(R.string.response_load_null);
                        }
                    } else {
                        showToastMsg(R.string.response_load_null);
                    }
                } else {
                    showToastMsg(R.string.response_load_null);
                }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }

    //判断验证码是否正确，并进入下一步
    private void nextStep() {
        mApp = (CarApplication) getApplication();
        mUser = mApp.getUser();
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mPhone.getText().toString().trim();
                String verify = mVerify.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    mPhone.requestFocus();
                    showToastMsg(getString(R.string.phone_null));
                    return;
                }
//                if (!TextUtils.equals(mPhoneNo, mUser.getResult().getUser_info().getLogin_phone())) {
//                    showToastMsg("请输入注册的手机号!");
//                    return;
//                }
                if (TextUtils.isEmpty(verify)) {
                    mVerify.requestFocus();
                    showToastMsg(getString(R.string.verify_null));
                    return;
                }
                if (verify.equals(verificationCode)) {
                    Intent intent = new Intent(ForgetPasswordStepOneActivity.this, ForgetPasswordStepTwoActivity.class);
                    ForgetPasswordBean forgetPasswordBean = new ForgetPasswordBean();
                    forgetPasswordBean.setPhone(phone);
                    forgetPasswordBean.setVerify(verify);
                    intent.putExtra("bean", forgetPasswordBean);
                    startActivity(intent);
                    finish();
                } else {
                    showToastMsg(getString(R.string.verify_error));
                    return;
                }
            }
        });
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

    class MyCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100) {
                countDown();
            }
            return true;
        }
    }


}
