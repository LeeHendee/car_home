package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.MessageDetailBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageActivity extends BaseActivity implements View.OnClickListener , ResponseJSONObjectListener{
    private static final String TAG="MessageActivity";
    private static final int MESSAGEADD = 63270502;
    private static final int MESSAGEDEL = 63278096;
    private static final int MESSAGEDETIAL = 63276116;
    private TextView mCallbackText;
    private TextView mTitleBarTextView;
    private MessageDetailBean mMessageDetailBean;
    private WebView mWebView;
    private ImageView mFavorImageView;
    private TextView mTimeText;
    private TextView mTitleText;
    private TextView mResourceText;
    private CarApplication mApplication;
    private User mUser;
    private View mView;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mApplication = (CarApplication) getApplication();
        id = getIntent().getStringExtra("id");
        mCallbackText = (TextView) findViewById(R.id.message_activity_callback_textview);
        mTitleBarTextView = (TextView) findViewById(R.id.message_activity_title_textview);
        mTitleText =(TextView)findViewById(R.id.activity_message_title);
        mWebView = (WebView)findViewById(R.id.activity_message_webview);
        mTimeText =(TextView)findViewById(R.id.activity_message_time);
        mFavorImageView = (ImageView)findViewById(R.id.activity_message_toolbar_favor);
        mResourceText = (TextView)findViewById(R.id.activity_message_resource);
        mFavorImageView.setOnClickListener(this);

        mCallbackText.setOnClickListener(this);


    }
    private void getWebview() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置是否支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //取消滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        String url = mMessageDetailBean.getResult().getHtml_url();
        if(url.contains("\\")){
            url = TAppUtils.formatUrl(url);
        }
        mWebView.loadUrl(url);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = mApplication.getUser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mUser != null && mUser.getResult().getToken() != null){
            String url = ApiManager.URL_MESSAGE_DETAIL+"?token="+mUser.getResult().getToken();
            THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,MESSAGEDETIAL,TAG);
        }else {
            THttpOpenHelper.newInstance().requestJsonObjectPost(ApiManager.URL_MESSAGE_DETAIL,jsonObject,this,MESSAGEDETIAL,TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_activity_callback_textview:
                finish();
                break;
            case R.id.activity_message_toolbar_favor:
                mApplication = (CarApplication) getApplication();
                mUser = mApplication.getUser();
                if (mFavorImageView.isSelected()) {
                    String token = null;
                    if (mUser != null) {
                        String sign = MD5.getSign(ApiManager.URL_FAVORDEL, mUser);
                        String t =  MD5.gettimes();
                        token = mUser.getResult().getToken();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id",mMessageDetailBean.getResult().getId());
                            jsonObject.put("favor_type", "4");
                            String url = ApiManager.URL_FAVORDEL + "?token=" + token + "&t=" + t + "&sign=" + sign;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, MESSAGEDEL, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showDialog(getString(R.string.null_login));
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    String token = null;
                    if (mUser != null) {
                        String sign = MD5.getSign(ApiManager.URL_FAVORADD, mUser);
                        String t =  MD5.gettimes();
                        TLog.i(TAG, "--------->favor");
                        token = mUser.getResult().getToken();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id", mMessageDetailBean.getResult().getId());
                            jsonObject.put("favor_type", "4");
                            String url = ApiManager.URL_FAVORADD + "?token=" + token + "&t=" + t + "&sign=" + sign;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, MESSAGEADD, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showDialog(getString(R.string.null_login));

                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        switch (type){
            case MESSAGEADD:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mFavorImageView.setSelected(true);
                            //    mFavorImageView.setBackgroundResource(R.drawable.icon_detail_collect_select);
                                showToastMsg(message);
                            } else {
                                closeDialog();
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
            case MESSAGEDEL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mFavorImageView.setSelected(false);
                             //   mFavorImageView.setBackgroundResource(R.drawable.icon_detail_collect_normal);
                                showToastMsg(message);
                            } else {
                                closeDialog();
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
            case MESSAGEDETIAL :
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                mMessageDetailBean = gson.fromJson(response.toString(),MessageDetailBean.class);
                                if (mMessageDetailBean.getResult().getIs_favored().equals("0")){
                                    mFavorImageView.setSelected(false);
                                }else {
                                    mFavorImageView.setSelected(true);
                                }
                                mTitleText.setText(mMessageDetailBean.getResult().getTitle());
                                mResourceText.setText(mMessageDetailBean.getResult().getResource());
                                mTimeText.setText(mMessageDetailBean.getResult().getUpdate_time());
                                getWebview();
                            } else {
                                closeDialog();
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
}
