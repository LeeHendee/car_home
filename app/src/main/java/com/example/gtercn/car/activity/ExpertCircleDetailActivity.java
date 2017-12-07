package com.example.gtercn.car.activity;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.ExpertCircleDetailBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.PopPhoneMenu;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : LiHang.
 * data : 2016/12/19.
 * description:
 *    达人圈详情
 */
public class ExpertCircleDetailActivity extends BaseActivity implements ResponseStringListener, ResponseJSONObjectListener {

    private static final String TAG = "ExpertCircleDetailActivity";
    private static final int ARTICLENUM = 24292422;
    private static final int QUERY_TYPE = 0x01;
    private PopPhoneMenu mPopMenu;
    private String telNumber;
    @BindView(R.id.expert_circle_layout)
    LinearLayout mLinearLayout;

    @BindView(R.id.circle_detail_name)
    TextView mExpertName;

    @BindView(R.id.circle_detail_motto)
    TextView mExpertMotto;

    @BindView(R.id.circle_detail_wechat)
    LinearLayout mWeChat;

    @BindView(R.id.circle_detail_make_call_layout)
    LinearLayout mCallView;

    @BindView(R.id.circle_detail_time)
    TextView mArticleTime;

    @BindView(R.id.circle_detail_glance_number)
    TextView mGlanceNumber;

    @BindView(R.id.circle_article_wb)
    WebView mArticleView;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mBarTitle;

    @BindView(R.id.circle_detail_portrait)
    RoundedImageView mHeadPortrait;

    @BindView(R.id.expert_circle_detail_title)
    TextView mTitle;

    private float density = 1.5f;

    private String mID;

    private CarApplication mApp;

    private User mUser;

    private Handler mHandler;

    private ExpertCircleDetailBean.ResultBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_circle_detail);
        ButterKnife.bind(this);
        density = getResources().getDisplayMetrics().density;
        initView();
        initData();
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 100) {
                    settingView();
                }
                return true;
            }
        });
    }

    private void settingView() {
        mExpertName.setText(mBean.getExpert_name());
        mExpertMotto.setText(mBean.getMotto());
        mArticleTime.setText(mBean.getPublish_time());
        mTitle.setText("标题：" + mBean.getTitle());

        mArticleView.getSettings().setJavaScriptEnabled(true);
        mArticleView.getSettings().setSupportZoom(true);
        mArticleView.getSettings().setBuiltInZoomControls(false);
        mArticleView.setVerticalScrollBarEnabled(false);
        mArticleView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mArticleView.getSettings().setDefaultFontSize(72);
        mArticleView.loadUrl(URLUtil.guessUrl(String.valueOf(mBean.getHtml_url())));
        if (mBean.getExpert_portrait() == null)
            mHeadPortrait.setImageResource(R.drawable.icon_expert_avatar);
        else{
            TLog.i(TAG,"--->>expert_portrait is "+mBean.getExpert_portrait());
            String head_url = TAppUtils.formatUrl(mBean.getExpert_portrait());
            TLog.i(TAG,"--->>expert_portrait after trim "+head_url);
            THttpOpenHelper.newInstance().setImageBitmap(mHeadPortrait,head_url,(int)(100*density),(int)(100*density),
                    R.drawable.icon_expert_avatar, R.drawable.icon_expert_avatar);
        }
        telNumber = mBean.getTelephone_number();
        if (telNumber != null) {
            String[] sourceStrArray = telNumber.split(",");
            List<String> tel_num_list = Arrays.asList(sourceStrArray);
//            for (String str: sourceStrArray) {
//                tel_num_list.add(str);
//            }
            mPopMenu = new PopPhoneMenu(mContext.getApplicationContext(), tel_num_list);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 浏览数增加,调用接口;
     */
    private void setData() {
        Map map = new HashMap();
        map.put("article_id",mID);
        THttpOpenHelper.newInstance().requestFormDataFilePost(ApiManager.URL_ARTICLE_GLANCENUM,map,null,this,ARTICLENUM,"articlenum");
    }

    private void initView() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
        barTitle.setText("达人文章详情");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mApp = (CarApplication) getApplication();
        mUser = mApp.getUser();
        Intent intent = getIntent();
        if (intent != null) {
            mID = intent.getStringExtra("id");
            //调用帖子浏览数接口;
            setData();
            TLog.i(TAG, "---->>mID get from intent--->>" + mID);
            String url;
            if (mUser != null) {
                String token = mUser.getResult().getToken();
                url = ApiManager.URL_ARTICLE_DETAIL_QUERY + "?token=" + token;
            } else {
                url = ApiManager.URL_ARTICLE_DETAIL_QUERY;
            }
            JSONObject params = new JSONObject();
            try {
                params.put("id", mID);
                THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, QUERY_TYPE, TAG);
                showDialog(getString(R.string.loading_data));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({
            R.id.circle_detail_wechat,
            R.id.circle_detail_make_call_layout,
    })
    void onCircleDetailClick(View v) {
        if (mBean == null) {
            showSnack(mCallView, "该达人没有留下电话号码!");
            return;
        }
        switch (v.getId()) {
            case R.id.circle_detail_wechat:
                getWeChatNumber();
                break;
            case R.id.circle_detail_make_call_layout:
                if (telNumber == null) {
                    showSnack(mArticleTime, "此达人没有留下电话号码!");
                    return;
                } else {
                    mPopMenu.showAtLocation(mLinearLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                            0, 0);
                }
                break;
        }
    }

    private void getWeChatNumber() {
        final View view = LayoutInflater.from(this).inflate(R.layout.custom_add_wechat, null);
        final TextView weChatNumber = (TextView) view.findViewById(R.id.wechat_number);
        weChatNumber.setText(mBean.getExpert_wechat_number());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        builder.setTitle("点击确定,复制微信号到剪切板!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cm.setText(weChatNumber.getText().toString().trim());
                showSnack(view, "成功复制微信号:" + cm.getText());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(true);
        builder.create().show();
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void onSuccessResponse(String response, int type) {
        switch (type) {
            case ARTICLENUM:
                if (!TextUtils.isEmpty(response)) {
                    try {
                        JSONObject obj = new JSONObject(response);

                        if (obj.has("err_code")) {
                            String returnCode = obj.getString("err_code");
                            if (TextUtils.equals(returnCode, "0")) {
                                JSONObject obj2 = obj.getJSONObject("result");
                                String message = obj2.getString("glance_number");
                                mGlanceNumber.setText(message);
                                TLog.i(TAG, "------message-->" + message);
                            } else {
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        TLog.i(TAG, "----->>response is " + response.toString());
        if (response != null) {
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    TAppUtils.logout(mApp,code);
                    if (TextUtils.equals(code, "0")) {
                        Gson gson = new Gson();
                        ExpertCircleDetailBean bean = gson.fromJson(response.toString(), ExpertCircleDetailBean.class);
                        mBean = bean.getResult();
                        if (mBean != null) {
                            mHandler.sendEmptyMessage(100);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
