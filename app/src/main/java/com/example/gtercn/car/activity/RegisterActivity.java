package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpsOpenHelper;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.utils.TRegularExpression;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author : LiHang.
 * data : 2016/11/28.
 * description:
 *    注册页面:
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, ResponseJSONObjectListener {

    private static final String TAG = "RegisterActivity";

    private static final int TYPE_GET_VERIFICATION_CODE = 1;

    private EditText mPhoneEt;

    private EditText mVerificationEt;

    private EditText mPasswordEt;

    private EditText mPwdConfirmEt;

    private TextView mObtainVerificationTv;

    private Button mRegisterBtn;

    private Toolbar mBar;

    private CarApplication mApplication;

    private Handler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mHandler = new Handler(new MyCallback());
    }

    private void initView() {
        mApplication = (CarApplication) getApplication();

        mBar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
        barTitle.setText("注册");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhoneEt = (EditText) findViewById(R.id.register_phone_et);
        mVerificationEt = (EditText) findViewById(R.id.input_verification);
        mPasswordEt = (EditText) findViewById(R.id.register_pwd_et);
        mPwdConfirmEt = (EditText) findViewById(R.id.confirm_pwd_et);
        mObtainVerificationTv = (TextView) findViewById(R.id.obtain_verification_tv);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);

        mObtainVerificationTv.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);

        mVerificationEt.setNextFocusForwardId(R.id.register_pwd_et);
        mPasswordEt.setNextFocusForwardId(R.id.confirm_pwd_et);

        clearErrorState();

        mPwdConfirmEt.setOnEditorActionListener(new MyEditorActionListener());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.obtain_verification_tv:
                getVerification();
                break;
            case R.id.register_btn:
                processRegister();
                break;
        }
    }


    /**
     * verify_type验证码类别:0注册,1忘记密码,2手机号解绑,3手机号绑定"
     */
    private void getVerification() {
        String telNumber = mPhoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(telNumber)) {
            mPhoneEt.setError("请输入需要验证的手机号码!");
            return;
        }

        boolean isPhoneNo = TRegularExpression.isMobileNO(telNumber);
        if (!isPhoneNo) {
            mPhoneEt.setError("手机号码格式不正确!");
            return;
        }
//        stopKeyboard();
        try {
            JSONObject params = new JSONObject();
            params.put("login_phone", telNumber);
            params.put("verify_type", "0");
            String url = getUrl(ApiManager.URL_VERIFICATION, "user_id", "1", "1");
            THttpsOpenHelper.newInstance().requestJsonObjectPost(url, params, this, TYPE_GET_VERIFICATION_CODE, TAG);
            showDialog(getString(R.string.loading_data));
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void countDown() {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mObtainVerificationTv.setText(millisUntilFinished / 1000 + "秒后重新获取");
                mObtainVerificationTv.setClickable(false);
            }

            @Override
            public void onFinish() {
                mObtainVerificationTv.setText("获取验证码");
                mObtainVerificationTv.setClickable(true);
            }
        };
        countDownTimer.start();
    }


    /**
     *
     */
    private void processRegister() {
        String telNumber = mPhoneEt.getText().toString().trim();
        String verification = mVerificationEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        String confirmPWD = mPwdConfirmEt.getText().toString().trim();
        if (TextUtils.isEmpty(telNumber)) {
            mPhoneEt.setError("请输入手机号码!");
            return;
        }
        if (!TRegularExpression.isMobileNO(telNumber)) {
            mPhoneEt.setError("手机号码不正确!");
            return;
        }
        if (TextUtils.isEmpty(verification)) {
            mVerificationEt.setError("请输入验证码!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordEt.setError("请输入密码!");
            return;
        }
        if (TextUtils.isEmpty(confirmPWD)) {
            mPwdConfirmEt.setError("请确认密码!");
            return;
        }
        if (!TextUtils.equals(password, confirmPWD)) {
            showSnack(mPasswordEt, "两次输入的密码不一致,请修改后提交!");
            return;
        }

        //调接口进行登录
        JSONObject params = new JSONObject();
        try {
            params.put("login_phone", telNumber);
            params.put("password", password);
            params.put("verify_code", verification);
            params.put("verify_type", "0");
            params.put("device_type", "1");
            params.put("device_token", "1");

//            /**
//             * 消息推送device token 友盟推送。
//             */
//            String device_token = mApplication.getPushAgent().getRegistrationId();
//
//            if (TextUtils.isEmpty(device_token)) {
//                /**
//                 * 特别当获取 device_token 失败时，才会再调用。
//                 */
//                mApplication.initPushAgentRegister();
//            } else {
//                map.put("device_token", device_token);
            String register_url = getUrl(ApiManager.URL_REGISTER, "user_id", "1", "sign");
            THttpsOpenHelper.newInstance().requestJsonObjectPost(register_url, params, this, 2, TAG);
            showDialog(getString(R.string.loading_data));

//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case 1:
                if (response != null) {
                    TLog.i(TAG, "getVerification : -->>response is = " + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            String message = response.getString("message");
                            showToastMsg(message);
                            if (TextUtils.equals(code, "0")) {
                                if (response.has("result")) {
                                    JSONObject obj = response.getJSONObject("result");
                                    if (obj != null) {
                                        String verification = obj.getString("verify_code");
                                        if (verification != null) {
//                                            mVerificationEt.setText(verification);
//                                            showToastMsg("验证码为: " + verification);
                                            mHandler.sendEmptyMessage(100);
                                            showMessage(response);
                                        }
                                    }
                                }
                            }
//                            else if (TextUtils.equals(code,"6040")){
//
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;
            case 2:
                if (response != null) {
                    TLog.i(TAG, "register : -->>response is = " + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            String message = response.getString("message");
                            showToastMsg(message + "请登录!");
                            if (TextUtils.equals(code, "0")) {
                                if (response.has("result")) {
                                    finish();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }

    private class MyEditorActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                processRegister();
                return true;
            }
            return false;
        }
    }

    private void clearErrorState() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhoneEt.setError(null);
                mPasswordEt.setError(null);
                mVerificationEt.setError(null);
                mPwdConfirmEt.setError(null);
            }
        };
        mPhoneEt.addTextChangedListener(tw);
        mPasswordEt.addTextChangedListener(tw);
        mVerificationEt.addTextChangedListener(tw);
        mPwdConfirmEt.addTextChangedListener(tw);
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

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
