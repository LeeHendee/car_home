package com.example.gtercn.car.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.SelfCommentAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.SelfDetailBean;
import com.example.gtercn.car.bean.SelfDriveCommentBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.ShareUtil;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;
import com.umeng.socialize.media.UMImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 自驾游详情页
 */
public class SelfDrivingTravelDetailActivity extends BaseActivity implements View.OnClickListener, ResponseJSONObjectListener {
    private static final String TAG = "SelfDrivingTravelDetailActivity";
    private static final int COMMENT = 60976158;
    private static final int COLLECTADD = 56612392;
    private static final int COLLECTDEL = 4442392;
    private static final int COMMENTREPLY = 9326097;
    private static final int COMMENTSUBREPLY = 17886097;
    private static final int SELFENROL = 52611032;
    private static final int SELFREMOVEENROL = 10320648;
    private static final int SELFDETIAL = 52616116;
    private static final int SELFCANCEL =  52613194;

    private RelativeLayout mRelativeLayout;
    private TextView mApplyTextView;
    private LinearLayout mPictureLinearLayout;
    private static final int TOOL_BAR_WIDTH = 50;
    private SelfDetailBean mSelfDetailBean;

    private ImageView mHeadImageView;
    private SelfDriveCommentBean selfDriveCommentBean;
    private ImageView mShareImageview;
    private RelativeLayout mReplayRelativeLayout;
    private EditText mReplayEditText;
    private TextView mSendTextView;
    private TextView mContentTextview;
    private TextView mTimeTextView;
    private ListView mListView;
    private SelfCommentAdapter selfCommentAdapter;
    private ImageView mFavorImageView;
    private User mUser;
    private CarApplication mApplication;
    public static boolean isComment;
    public static String item_id;
    public static String to_user_id;
    private TextView mSumTextView;
    private TextView mNameTextView;
    private String id;
    private RelativeLayout mPeopleLayout;
    private TextView mPeopleNumText;
    private TextView mPeopleListText;
    private TextView mCancelTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_driving_travel_detail);

        density = getResources().getDisplayMetrics().density;
        mApplication = (CarApplication) getApplication();
        mUser = mApplication.getUser();
        //防止刚打开activity就弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.self_driving_detail_activity_toolbar_backcall_relativelayout);
        mApplyTextView = (TextView) findViewById(R.id.self_driving_detail_activity_apply_textview);
        mPictureLinearLayout = (LinearLayout) findViewById(R.id.self_driving_detail_activity_imageview_linearlayout);
        mPeopleLayout = (RelativeLayout)findViewById(R.id.self_driving_detail_activity_people_layout);
        mPeopleNumText = (TextView) findViewById(R.id.self_driving_detail_activity_people_sum);
        mPeopleListText = (TextView) findViewById(R.id.self_driving_detail_activity_people_roll);
        mCancelTextView = (TextView)findViewById(R.id.self_driving_detail_activity_cancel_textview);
        mListView = (ListView) findViewById(R.id.self_driving_detail_activity_expandlistview);
        mHeadImageView = (ImageView) findViewById(R.id.self_driving_detail_activity_headimg);
        mReplayEditText = (EditText) findViewById(R.id.self_driving_detail_activity_reply_relativelayout_edittext);
        mReplayRelativeLayout = (RelativeLayout) findViewById(R.id.self_driving_detail_activity_reply_relativelayout_renlayout);
        mSendTextView = (TextView) findViewById(R.id.self_driving_detail_activity_reply_relativelayout_sendTextView);
        mContentTextview = (TextView) findViewById(R.id.self_driving_detail_activity_content_textview);
        mTimeTextView = (TextView) findViewById(R.id.self_driving_detail_activity_day_textview);
        mFavorImageView = (ImageView) findViewById(R.id.self_driving_detail_activity_collect_imageview);
        mSumTextView = (TextView) findViewById(R.id.self_driving_detail_activity_reply_rennumtext);
        mNameTextView = (TextView) findViewById(R.id.self_driving_detail_activity_name_Textview);
        mShareImageview = (ImageView)findViewById(R.id.self_driving_detail_activity_toolbar_shareimg);
        mShareImageview.setOnClickListener(this);
        mFavorImageView.setOnClickListener(this);
        mPeopleListText.setOnClickListener(this);
        //监听软键盘是否显示或隐藏
        mReplayRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        mReplayRelativeLayout.getWindowVisibleDisplayFrame(r);
                        int screenHeight = mReplayRelativeLayout.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            //软键盘显示
                            if (mUser == null) {
                                showToastMsg(getString(R.string.null_login));
                                Intent intent = new Intent(SelfDrivingTravelDetailActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            mReplayRelativeLayout.setVisibility(View.GONE);
                            mSendTextView.setVisibility(View.VISIBLE);
                            // changeKeyboardHeight(heightDifference);
                        } else {
                            //软键盘隐藏
                            mSendTextView.setVisibility(View.GONE);
                            mReplayRelativeLayout.setVisibility(View.VISIBLE);
                            mReplayEditText.setFocusable(false);
                            mReplayEditText.setText("");
                        }
                    }

                });
        mReplayEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (mUser != null) {
                mReplayEditText.setFocusable(true);
                mReplayEditText.setFocusableInTouchMode(true);
                isComment = true;
//                } else {
//                    Intent intent = new Intent(SelfDrivingTravelDetailActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
                return false;
            }

        });
        mSendTextView.setOnClickListener(this);
        mApplyTextView.setOnClickListener(this);
        mCancelTextView.setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);

    }

    private void getComment() {
        showDialog(getString(R.string.dialog_data));

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("self_driving_id", id);
            ApiManager.SelfComment(jsonObject, this, COMMENT, TAG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();
        mApplication = (CarApplication) getApplication();
        mUser = mApplication.getUser();
        id = getIntent().getStringExtra("id");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(mUser!= null){
            if(mUser.getResult().getToken()!=null) {
                String sign = MD5.getSign(ApiManager.URL_SELF_DETAIL, mUser);
                String t = MD5.gettimes();
                String url = ApiManager.URL_SELF_DETAIL + "?token=" + mUser.getResult().getToken() + "&t=" + t + "&sign=" + sign;
                THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, SELFDETIAL, TAG);
            }else{
                String url = ApiManager.URL_SELF_DETAIL;
                THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,SELFDETIAL,TAG);
            }
        }else {
            String url = ApiManager.URL_SELF_DETAIL;
            THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,SELFDETIAL,TAG);
        }
        getComment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void getIsJudge(){
        if(mUser != null) {
            if (mSelfDetailBean.getResult().getPublic_flag().equals("0") == true) {//是否为发布者0不是，1是
                //用户
                if (mSelfDetailBean.getResult().getAvailable_flag() == 1) { //是否有效1有效  ， 0无效
                    //活动有效
                    mApplyTextView.setVisibility(View.VISIBLE);
                    mCancelTextView.setVisibility(View.GONE);
                    if (mSelfDetailBean.getResult().getEnroll_flag() == null || mSelfDetailBean.getResult().getEnroll_flag().equals("0") == true) {//报名flag(1:能,0:不能)

                        mCancelTextView.setVisibility(View.GONE);
                        mApplyTextView.setVisibility(View.GONE);
                    } else {
                        if (mSelfDetailBean.getResult().getSign_flag() == 0) {//(1：报名，0：、未报名)
                            mCancelTextView.setVisibility(View.GONE);
                            mApplyTextView.setVisibility(View.VISIBLE);
                            mApplyTextView.setSelected(false);
                            mApplyTextView.setText("马上报名");

                        } else {
                            mCancelTextView.setVisibility(View.GONE);
                            mApplyTextView.setVisibility(View.VISIBLE);
                            mApplyTextView.setSelected(true);
                            mApplyTextView.setText("已报名");
                        }
                    }
                } else {

                    mCancelTextView.setVisibility(View.GONE);
                    mApplyTextView.setVisibility(View.GONE);
                }
            } else {
                mCancelTextView.setVisibility(View.VISIBLE);
                mApplyTextView.setVisibility(View.GONE);
                mPeopleLayout.setVisibility(View.VISIBLE);
                mPeopleNumText.setText(mSelfDetailBean.getResult().getCount() + "");
                //发布者
                if (mSelfDetailBean.getResult().getAvailable_flag() == 1) {
                    //活动有效


                    mCancelTextView.setSelected(false);
                    mCancelTextView.setText("取消活动");
                    mCancelTextView.setClickable(true);
                } else {

                    mCancelTextView.setVisibility(View.GONE);
                    mApplyTextView.setVisibility(View.GONE);

                }
            }
        }else{
            if(mSelfDetailBean.getResult().getAvailable_flag() == 1) {
                mCancelTextView.setVisibility(View.GONE);
                mApplyTextView.setVisibility(View.VISIBLE);
                mApplyTextView.setSelected(false);
                mApplyTextView.setText("马上报名");
            }else{

                mCancelTextView.setVisibility(View.GONE);
                mApplyTextView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.self_driving_detail_activity_people_roll:
                Intent goPeople = new Intent(this,PeopleRollActivity.class);
                goPeople.putExtra("id",id);
                startActivity(goPeople);
            break;
            case R.id.self_driving_detail_activity_cancel_textview:
                if (mUser != null) {
                    String token = mUser.getResult().getToken();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("self_driving_id",id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (mCancelTextView.isSelected() == false) {
                        String sign = MD5.getSign(ApiManager.URL_SELF_CANCEL, mUser);
                        String time = MD5.gettimes();
                        String url = ApiManager.URL_SELF_CANCEL +"?token="+token+"&t="+time+"&sign="+sign;
                        THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,SELFCANCEL,TAG);

                    }
                } else {
                    showToastMsg(getString(R.string.null_login));

                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.self_driving_detail_activity_toolbar_backcall_relativelayout:
                finish();
                break;
            case R.id.self_driving_detail_activity_toolbar_shareimg:
                UMImage image;
                if(mSelfDetailBean.getResult().getPic_urls_list() != null){
                    String imgUrl = mSelfDetailBean.getResult().getPic_urls_list().get(0);
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
                if(TextUtils.isEmpty(mSelfDetailBean.getResult().getTitle())){
                    name = "顺驾天下";
                }else {
                    name = mSelfDetailBean.getResult().getTitle();
                }

                ShareUtil.GeneralizeShare(this, name, "", image);

                break;
            case R.id.self_driving_detail_activity_apply_textview:
                if (mUser != null) {
                    String token = mUser.getResult().getToken();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("self_driving_id",id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (mApplyTextView.isSelected()) {
                        String sign = MD5.getSign(ApiManager.URL_SELF_REMOVE_ENROL, mUser);
                        String time = MD5.gettimes();
                        String url = ApiManager.URL_SELF_REMOVE_ENROL +"?token="+token+"&t="+time+"&sign="+sign;
                        ApiManager.SelfRemoveEnrol(url,jsonObject,SelfDrivingTravelDetailActivity.this,SELFREMOVEENROL,TAG);
                    } else {
                        String sign = MD5.getSign(ApiManager.URL_SELF_ENROL, mUser);
                        String time = MD5.gettimes();
                        String url = ApiManager.URL_SELF_ENROL +"?token="+token+"&t="+time+"&sign="+sign;
                        ApiManager.SelfEnrol(url, jsonObject,SelfDrivingTravelDetailActivity.this,SELFENROL, TAG);
                    }
                } else {
                    showToastMsg(getString(R.string.null_login));

                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.self_driving_detail_activity_reply_relativelayout_sendTextView:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                mSendTextView.setVisibility(View.GONE);
                mReplayRelativeLayout.setVisibility(View.VISIBLE);
                Map<String, String> map = new HashMap<>();
                if(mUser != null) {

                    if (isComment == true) {
                        map.put("token", mUser.getResult().getToken());
                        String sign = MD5.getSign(ApiManager.URL_SELF_COMMENT_REPLY, mUser);
                        map.put("t", MD5.gettimes());
                        map.put("sign", sign);
                        JSONObject jsonObject2 = new JSONObject();
                        try {
                            jsonObject2.put("self_driving_id", id);
                            jsonObject2.put("content", mReplayEditText.getText());
                            showDialog(getString(R.string.dialog_data));
                            ApiManager.SelfCommentReply(map, jsonObject2, this, COMMENTREPLY, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        map.put("token", mUser.getResult().getToken());
                        String sign = MD5.getSign(ApiManager.URL_SELF_SUBCLASS_COMMENT_REPLY, mUser);
                        map.put("t", MD5.gettimes());
                        map.put("sign", sign);
                        JSONObject jsonObject2 = new JSONObject();
                        try {
                            jsonObject2.put("self_driving_id", id);
                            jsonObject2.put("content", mReplayEditText.getText());
                            jsonObject2.put("to_user_id", to_user_id);
                            jsonObject2.put("item_id", item_id);
                            showDialog(getString(R.string.dialog_data));
                            ApiManager.SelfSubCommentReply(map, jsonObject2, this, COMMENTSUBREPLY, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
//                mReplayEditText.setText("");
                break;
            case R.id.self_driving_detail_activity_collect_imageview:
                if (mFavorImageView.isSelected()) {
                    String token = null;
                    if (mUser != null) {
                        String signs = MD5.getSign(ApiManager.URL_FAVORDEL, mUser);
                        String t =  MD5.gettimes();
                        TLog.i(TAG, "--------->favor");
                        token = mUser.getResult().getToken();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id", id);
                            jsonObject.put("favor_type", "1");
                            String url = ApiManager.URL_FAVORDEL + "?token=" + token + "&t=" + t + "&sign=" + signs;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, COLLECTDEL, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastMsg(getString(R.string.null_login));
                        Intent intent = new Intent(SelfDrivingTravelDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    String token = null;
                    if (mUser!= null) {
                        String signs = MD5.getSign(ApiManager.URL_FAVORADD, mUser);
                        String t =  MD5.gettimes();
                        TLog.i(TAG, "--------->favor");
                        token = mUser.getResult().getToken();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id", id);
                            jsonObject.put("favor_type", "1");
                            String url = ApiManager.URL_FAVORADD + "?token=" + token + "&t=" + t + "&sign=" + signs;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, COLLECTADD, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastMsg(getString(R.string.null_login));

                        Intent intent = new Intent(SelfDrivingTravelDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
                }
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case COMMENT:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                selfDriveCommentBean = gson.fromJson(String.valueOf(response), SelfDriveCommentBean.class);
                                selfCommentAdapter = new SelfCommentAdapter(this, selfDriveCommentBean, mReplayEditText, id, mListView);
                                mListView.setAdapter(selfCommentAdapter);
                                String sum = selfDriveCommentBean.getResult().size() + "";
                                mSumTextView.setText(sum);
                            } else {
                                closeDialog();
                                //非正常返回码
                                if (response.has("message")) {
                                    String errMsg = response.getString("message");
                                }
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
            case COLLECTADD:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
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
            case COLLECTDEL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
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
            case COMMENTREPLY:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mReplayEditText.setText("");
                                getComment();
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
            case COMMENTSUBREPLY:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mReplayEditText.setText("");
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
            case SELFENROL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mApplyTextView.setSelected(true);
                                mApplyTextView.setText("已报名");

                            } else {

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
            case SELFREMOVEENROL :
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mApplyTextView.setSelected(false);
                                mApplyTextView.setText("马上报名");
                            } else {

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
            case SELFDETIAL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                 Gson gson = new Gson();
                                 mSelfDetailBean = gson.fromJson(response.toString(),SelfDetailBean.class);
                                getActiviView();
                                //判读是否报名
                                getIsJudge();
                                //判断是否收藏
                                if (mSelfDetailBean.getResult().getCollection_flag() == 0) {
                                    mFavorImageView.setSelected(false);
                                } else {
                                    mFavorImageView.setSelected(true);
                                }

                                String time = mSelfDetailBean.getResult().getActivity_time();
                                //String[] temp = null;
                                //temp = time.split(" ");
                                // mTimeTextView.setText(temp[1].toString());
                                mTimeTextView.setText(time);
                                mContentTextview.setText(mSelfDetailBean.getResult().getContent());
                                mNameTextView.setText(mSelfDetailBean.getResult().getNickname());
                                if(mSelfDetailBean.getResult().getAvatar_url() != null) {
                                    String url = mSelfDetailBean.getResult().getAvatar_url();
                                    if(url.contains("\\")){
                                        url = TAppUtils.formatUrl(url);
                                    }
                                    THttpOpenHelper.newInstance().setImageBitmap(mHeadImageView, url,
                                            (int)(50 * density), (int) (50 * density),
                                            R.drawable.personal_info_portrait,
                                            R.drawable.personal_info_portrait);
                                }
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
            case SELFCANCEL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
//                                mCancelTextView.setSelected(true);
//                                mCancelTextView.setClickable(false);
//                                mCancelTextView.setText("活动结束");
                                mCancelTextView.setVisibility(View.GONE);
                                mApplyTextView.setVisibility(View.GONE);
                                showToastMsg(message);
                            } else {

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
        closeDialog();
    }


    /**
     * 动态加载活动的图片
     */
    private void getActiviView() {
        /**
         * 高/宽 比率
         */
        final float ratio = 0.75f;
        final int width = mContext.getResources().getDisplayMetrics().widthPixels - (int) (density * TOOL_BAR_WIDTH);
        final int height = (int) (ratio * width);
        mPictureLinearLayout.removeAllViews();
        if(mSelfDetailBean.getResult().getPic_urls_list() != null) {
            for (int i = 0; i < mSelfDetailBean.getResult().getPic_urls_list().size(); i++) {
                //通过代码创建ImageView
                final int j = i;
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
                //设置图片拉伸方式
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(10, 10, 10, 0);
                //给图片指定资源
                if(mSelfDetailBean.getResult().getPic_urls_list()!=null) {
                    String url = mSelfDetailBean.getResult().getPic_urls_list().get(i);
                    if (url != null && url.contains("\\")) {
                        url = TAppUtils.formatUrl(url);
                    }

                    THttpOpenHelper.newInstance().setImageBitmap(imageView, url,
                            width, height,
                            R.drawable.icon_default,
                            R.drawable.icon_default);

                    mPictureLinearLayout.addView(imageView);
                }
            }
        }
    }

//    /**
//     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
//     *
//     * @param listView
//     */
//    public void setListViewHeight(ListView listView) {
//
//// 获取ListView对应的Adapter
//
//        ListAdapter listAdapter = listView.getAdapter();
//
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0); // 计算子项View 的宽高
//            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//
//        listView.setLayoutParams(params);
//    }

//
}
