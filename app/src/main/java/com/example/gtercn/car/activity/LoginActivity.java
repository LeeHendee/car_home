package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpsOpenHelper;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.utils.TRegularExpression;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author : LiHang.
 * data : 2016/11/28.
 * description:
 * 登录页面;
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, ResponseJSONObjectListener {

    private static final String TAG = "LoginActivity";

    private Toolbar mBar;

    private TextInputEditText mPhoneNoEt;

    private TextInputEditText mPasswordEt;

    private Button mLoginBtn;

    private TextView mForgetPWD;

    private TextView mRegisterTv;

    private CarApplication mApplication;

    private User mUser;

    private TextInputLayout mPhoneLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mApplication = (CarApplication) getApplication();
        mBar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
        barTitle.setText("登录");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPhoneNoEt = (TextInputEditText) findViewById(R.id.input_phone_et);
        mPasswordEt = (TextInputEditText) findViewById(R.id.input_pwd_et);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mForgetPWD = (TextView) findViewById(R.id.forget_pwd_tv);
        mRegisterTv = (TextView) findViewById(R.id.register_tv);
        mPhoneLayout = (TextInputLayout) findViewById(R.id.phone_input);


        mLoginBtn.setOnClickListener(this);
        mForgetPWD.setOnClickListener(this);
        mRegisterTv.setOnClickListener(this);

        mPhoneNoEt.setNextFocusForwardId(R.id.input_pwd_et);

        mPasswordEt.setOnEditorActionListener(new MyEditorActionListener());

        clearErrorState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                processLogin();
                break;
            case R.id.forget_pwd_tv:
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordStepOneActivity.class);
                startActivity(intent);
                break;
            case R.id.register_tv:
                Intent goRegister = new Intent(this, RegisterActivity.class);
                startActivity(goRegister);
                break;
        }
    }

    /**
     * 处理登录逻辑:
     */
    private void processLogin() {
        String telNumber = mPhoneNoEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        if (TextUtils.isEmpty(telNumber)) {
            mPhoneNoEt.setError("请输入手机号码!");
            return;
        }
        if (!TRegularExpression.isMobileNO(telNumber)) {
            mPhoneNoEt.setError("手机号码不正确!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordEt.setError("请输入密码!");
            return;
        }

        JSONObject params = new JSONObject();
        try {
            params.put("login_phone", telNumber);
            params.put("password", password);
            params.put("device_token", "1");
            params.put("device_type", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getUrl(ApiManager.URL_LOGIN, "1", "1", "1");
        THttpsOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, TAG);
        showDialog(getString(R.string.loading_data));
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
            TLog.i(TAG, "---->>onsuccess : response is = " + response.toString());
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    String message = response.getString("message");
                    showToastMsg(message);
                    if (TextUtils.equals(code, "0")) {
                            Gson gson = new Gson();
                            mUser = gson.fromJson(String.valueOf(response), User.class);
                            TLog.i(TAG,"----->gson"+mUser.getResult());
                            if (mUser != null) {
                                TLog.i(TAG,"----->mUser"+mUser.getResult());
                                TLog.i(TAG, "----->onSucceed token = " + mUser.getResult().getToken());
                                mApplication.setUser(mUser);
                                finish();
                                saveToken();
                            }

                    } else if (TextUtils.equals(code, "6021")) {
                        //输入密码错误
                        mPasswordEt.requestFocus();
                    } else if (TextUtils.equals(code, "6010")) {
                        mPhoneNoEt.requestFocus();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void saveToken() {
        String token = mUser.getResult().getToken();
        if (token != null) {
            SharedPreferenceHelper.setValue("token", token);
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
                processLogin();
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
                mPhoneNoEt.setError(null);
                mPasswordEt.setError(null);
            }
        };

        mPhoneNoEt.addTextChangedListener(tw);
        mPasswordEt.addTextChangedListener(tw);
    }
}
