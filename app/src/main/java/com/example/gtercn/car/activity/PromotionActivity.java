package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.PromotionDetailBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.ShareUtil;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.MyDialog;
import com.google.gson.Gson;
import com.umeng.socialize.media.UMImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 促销的详情界面
 */
public class PromotionActivity extends BaseActivity implements View.OnClickListener ,ResponseJSONObjectListener{
    private static final String TAG="PromotionActivity";
    private static final int PROMOTIONADD = 1910502;
    private static final int PROMOTIONDEL = 1918096;
    private static final int PROMOTIONDETAIL = 1916116;
    private TextView mCallbackText;
    private TextView mTitleTextView;
    private PromotionDetailBean mPromotionDetailBean;
    private ImageView imageView;
    private RelativeLayout mPhoneLayout;
    private MyDialog myDialog;
    private ImageView mFavorImageView;
    private String phones;
    private TextView mNameText;
    private TextView mAddressText;
    private TextView mStartTime;
    private TextView mEndTime;
    private WebView mWebView;
    private List list;
    private CarApplication mApplication;
    private ImageView mImageview;
    private ImageView mShareImageview;
    private User mUser;
    private String id;
    private float density = 1.5f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        id = getIntent().getStringExtra("promotionId");
        mApplication = (CarApplication) getApplication();
        mCallbackText = (TextView) findViewById(R.id.promotion_activity_callback_textview);
        mWebView = (WebView)findViewById(R.id.activity_promotion_webview);
      //  mTitleTextView = (TextView) findViewById(R.id.activity_promotion_shopname);
        imageView = (ImageView) findViewById(R.id.promotion_activity_imageview);
        mPhoneLayout = (RelativeLayout) findViewById(R.id.activity_promotion_phone_relativelayout);
        mFavorImageView =(ImageView) findViewById(R.id.activity_promotion_toolbar_favor);
        mNameText=(TextView)findViewById(R.id.activity_promotion_shopname);
        mAddressText = (TextView) findViewById(R.id.activity_promotion_shopaddress);
        mEndTime=(TextView)findViewById(R.id.activity_promotion_valid_time_end_textview);
        mStartTime=(TextView)findViewById(R.id.activity_promotion_valid_time_start_textview);
        mImageview = (ImageView)findViewById(R.id.activity_promotion_headimg);
        mShareImageview =(ImageView)findViewById(R.id.activity_promotion_toolbar_share);
        mFavorImageView.setOnClickListener(this);
        mPhoneLayout.setOnClickListener(this);
        mShareImageview.setOnClickListener(this);
       // THttpOpenHelper.newInstance().setImageBitmap(imageView, getIntent().getStringExtra("url"), R.drawable.ic_launcher, R.drawable.ic_launcher);
        mCallbackText.setOnClickListener(this);
    }
    //webview
    private void getWebview() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置是否支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //取消滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        String url = mPromotionDetailBean.getResult().getHtml_url();
        if( url.contains("\\")) {
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
        if(mUser!=null && mUser.getResult().getToken()!= null) {
            String url = ApiManager.URL_PROMOTION_DETAIL+"?token="+mUser.getResult().getToken();
            THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,PROMOTIONDETAIL,TAG);
        }else {
            THttpOpenHelper.newInstance().requestJsonObjectPost(ApiManager.URL_PROMOTION_DETAIL,jsonObject,this,PROMOTIONDETAIL,TAG);
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
        switch (v.getId()) {
            case R.id.promotion_activity_callback_textview:
                finish();
                break;
            case R.id.activity_promotion_phone_relativelayout:
                if(phones!=null) {
                    myDialog = new MyDialog(PromotionActivity.this, R.style.MyDialog, new MyDialog.LeaveMyDialogListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.mydialog_clear:
                                    myDialog.dismiss();
                                    break;

                            }
                        }
                    }, list, myDialog);
                    myDialog.show();
                }else {
                    showToastMsg("没有电话！！");
                }
                break;
            case R.id.activity_promotion_toolbar_favor:
                mApplication = (CarApplication) getApplication();
                mUser = mApplication.getUser();
                if (mFavorImageView.isSelected() == true) {
                    String token = null;
                    if (mUser != null) {
                        String sign = MD5.getSign(ApiManager.URL_FAVORDEL, mUser);
                        String t =  MD5.gettimes();
                        TLog.i(TAG, "--------->favor del");
                        token = mUser.getResult().getToken();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id", mPromotionDetailBean.getResult().getId());
                            jsonObject.put("favor_type", "5");
                            String url = ApiManager.URL_FAVORDEL + "?token=" + token + "&t=" + t + "&sign=" + sign;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, PROMOTIONDEL, TAG);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastMsg(getString(R.string.null_login));
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    String token = null;
                    if (mUser != null) {
                        String sign = MD5.getSign(ApiManager.URL_FAVORADD, mUser);
                        String t =  MD5.gettimes();
                        TLog.i(TAG, "--------->favor add");
                        token = mUser.getResult().getToken();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id", mPromotionDetailBean.getResult().getId());
                            jsonObject.put("favor_type", "5");
                            String url = ApiManager.URL_FAVORADD + "?token=" + token + "&t=" + t + "&sign=" + sign;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, PROMOTIONADD, TAG);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastMsg(getString(R.string.null_login));
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.activity_promotion_toolbar_share:

                UMImage image;
                if(mPromotionDetailBean.getResult().getBackground_image() != null){
                    String imgUrl = mPromotionDetailBean.getResult().getBackground_image();
                    if(imgUrl.contains("\\")){
                        imgUrl = TAppUtils.formatUrl(imgUrl);
                    }
                    if(TextUtils.isEmpty(imgUrl)){
                        image = new UMImage(this, R.drawable.ic_launcher);
                    }else {
                        image = new UMImage(this, imgUrl);
                    }
                }else {
                    image = new UMImage(this, R.drawable.ic_launcher);
                }

                String name;
                if(TextUtils.isEmpty(mPromotionDetailBean.getResult().getShop_name())){
                    name = "顺驾天下";
                }else {
                    name = mPromotionDetailBean.getResult().getShop_name();
                }

                ShareUtil.GeneralizeShare(this, name, "", image);
                break;
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        switch (type){
            case PROMOTIONADD:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                TLog.e(TAG,"---PROMOTIONADD--->");
                                mFavorImageView.setSelected(true);
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
            case PROMOTIONDEL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                TLog.e(TAG,"---PROMOTIONDEL--->");

                                mFavorImageView.setSelected(false);


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
            case PROMOTIONDETAIL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                mPromotionDetailBean = gson.fromJson(response.toString(),PromotionDetailBean.class);
                                phones = mPromotionDetailBean.getResult().getShop_phone();
                                if(phones !=null) {
                                    String[] phonesArray = phones.split(",");
                                    list = new ArrayList(Arrays.asList(phonesArray));
                                }
                                if(mPromotionDetailBean.getResult().getHtml_url()!=null) {
                                    getWebview();
                                }
                                mStartTime.setText(mPromotionDetailBean.getResult().getStart_date());
                                mEndTime.setText(mPromotionDetailBean.getResult().getEnd_date());
                                mNameText.setText(mPromotionDetailBean.getResult().getShop_name());
                                mAddressText.setText(mPromotionDetailBean.getResult().getShop_address());
                                if(mPromotionDetailBean.getResult().getPicture_list()!=null) {
                                    String url = mPromotionDetailBean.getResult().getPicture_list();
                                    if( url.contains("\\")) {
                                        url = TAppUtils.formatUrl(url);
                                    }
                                    THttpOpenHelper.newInstance().setImageBitmap(mImageview, url, (int) (50 * density), (int) (50 * density), R.drawable.icon_default, R.drawable.icon_default);

                                }

                                if(mPromotionDetailBean.getResult().getBackground_image()!=null){
                                    String back_url = mPromotionDetailBean.getResult().getBackground_image();
                                    if( back_url.contains("\\")) {
                                        back_url = TAppUtils.formatUrl(back_url);
                                    }
                                    THttpOpenHelper.newInstance().setImageBitmap(imageView, back_url, (int) (300 * density), (int) (180 * density), R.drawable.icon_default, R.drawable.icon_default);

                                }

                                if (mPromotionDetailBean.getResult().getIs_favored().equals("0")){

                                    mFavorImageView.setSelected(false);
                                }else {
                                    mFavorImageView.setSelected(true);
                                }
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
