package com.example.gtercn.car.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.SelfSubCommentAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.SelfDriveCommentBean;
import com.example.gtercn.car.bean.SelfSubCommentBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 全部评论
 */
public class SelfSubClassCommentActivity extends BaseActivity implements ResponseJSONObjectListener, View.OnClickListener {
    private static final String TAG = "SelfSubClassCommentActivity";
    private static final int SUBCOMMENT = 13116097;
    private static final int COMMENTSUBREPLY = 17886097;
    private SelfSubCommentBean selfSubCommentBean;
    private SelfDriveCommentBean.ResultBean resultBean;
    private String self_id;
    private ListView mListView;
    private TextView mNameText;
    private TextView mContextText;
    private TextView mDayText;
    private EditText mEditText;
    private RelativeLayout mEditRelativelayout;
    private TextView mSendTextView;
    private User mUser;
    private CarApplication mCarApplication;
    private boolean iswho;
    private String to_user_id;
    private String item_id;
    private TextView mSumTextView;
    private TextView mCallbackText;
    private ImageView mHeaderImageview;
    private float density = 1.5f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_sub_class_comment);
        mCarApplication = (CarApplication) getApplication();
        mUser = mCarApplication.getUser();
        mHeaderImageview = (ImageView)findViewById(R.id.activity_self_subclass_header_imageview);
        mCallbackText = (TextView)findViewById(R.id.self_subclass_avtivity_toolbar_callback_textview);
        mCallbackText.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.self_subclass_avtivity_listview);
        mNameText = (TextView) findViewById(R.id.self_subclass_avtivity_nameText);
        mContextText = (TextView) findViewById(R.id.self_subclass_avtivity_context);
        mDayText = (TextView) findViewById(R.id.self_subclass_avtivity_daytext);
        mSumTextView = (TextView)findViewById(R.id.self_subclass_avtivity_sumtext);
        mSendTextView = (TextView) findViewById(R.id.self_sub_class_edittext_sendtext);
        mEditText = (EditText) findViewById(R.id.self_sub_class_comment_edittext);

        mSendTextView.setOnClickListener(this);
        mEditRelativelayout = (RelativeLayout) findViewById(R.id.self_sub_class_edittext_relativelayout);
        //监听软键盘是否显示或隐藏
        mEditRelativelayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        mEditRelativelayout.getWindowVisibleDisplayFrame(r);
                        int screenHeight = mEditRelativelayout.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            //软键盘显示
                            if(mUser==null){
                                Intent intent = new Intent(SelfSubClassCommentActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            mEditRelativelayout.setVisibility(View.GONE);
                            mSendTextView.setVisibility(View.VISIBLE);
                            // changeKeyboardHeight(heightDifference);
                        } else {
                            //软键盘隐藏
                            mSendTextView.setVisibility(View.GONE);
                            mEditRelativelayout.setVisibility(View.VISIBLE);
                            mEditText.setFocusable(false);
                            mEditText.setText("");
                        }
                    }

                });
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (mUser != null) {
                    mEditText.setFocusable(true);
                    mEditText.setFocusableInTouchMode(true);
                    iswho = true;
//                } else {
//                    Intent intent = new Intent(SelfSubClassCommentActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
                return false;
            }

        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                to_user_id = selfSubCommentBean.getResult().get(position).getUser_id();
                item_id = selfSubCommentBean.getResult().get(position).getId();
                TLog.i(TAG,"-------->"+selfSubCommentBean.getResult().get(position));
                TLog.i(TAG,"--to_user_id----->"+to_user_id);
                TLog.i(TAG,"--item_id----->"+item_id);
                mEditText.setFocusable(true);
                mEditText.setFocusableInTouchMode(true);
                mEditText.requestFocus();
                mEditText.findFocus();
                iswho = false;
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);// 显示输入法


            }
        });
        Bundle bundle = new Bundle();
        bundle = getIntent().getBundleExtra("bundle");
        self_id = bundle.getString("self_id");
        TLog.e(TAG,"---self_id--->"+self_id);
        resultBean = (SelfDriveCommentBean.ResultBean) bundle.getSerializable("resultBean");
        mContextText.setText(resultBean.getContent());
        mDayText.setText(resultBean.getInsert_time());
        mNameText.setText(resultBean.getNickname());
        String url = resultBean.getAvatar_url();
        if(url!=null) {
            if (url.contains("\\")) {
                url = TAppUtils.formatUrl(url);
            }
        }
        THttpOpenHelper.newInstance().setImageBitmap(mHeaderImageview,url,(int)(60*density),(int)(60*density),R.drawable.icon_setting_header_compile,R.drawable.icon_setting_header_compile);
        getData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCarApplication = (CarApplication) getApplication();
        mUser = mCarApplication.getUser();

    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    private void getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("self_driving_id", self_id);
            jsonObject.put("item_id", resultBean.getId());
            TLog.i(TAG,"--------id-------->"+resultBean.getId());
            showDialog(getString(R.string.dialog_data));
            ApiManager.SelfSubComment(jsonObject, this, SUBCOMMENT, TAG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case SUBCOMMENT:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mCarApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                selfSubCommentBean = gson.fromJson(String.valueOf(response), SelfSubCommentBean.class);
                                SelfSubCommentAdapter mSelfSubCommentAdapter = new SelfSubCommentAdapter(this, selfSubCommentBean, mEditText);
                                mListView.setAdapter(mSelfSubCommentAdapter);
                                String sum = selfSubCommentBean.getResult().size()+"";
                                mSumTextView.setText(sum);
                               // mSumTextView.setText(selfSubCommentBean.getResult().size());
                            } else {
                                //非正常返回码
                                if (response.has("message")) {
                                    TLog.i(TAG, "-----------TAG---->" + code);
                                    String errMsg = response.getString("message");
                                    showToastMsg(errMsg);
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
            case COMMENTSUBREPLY:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mCarApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mEditText.setText("");
                                getData();
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
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.self_sub_class_edittext_sendtext:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                mSendTextView.setVisibility(View.GONE);
                mEditRelativelayout.setVisibility(View.VISIBLE);
                Map<String, String> map = new HashMap<>();
                map.put("token", mUser.getResult().getToken());
                String sign = MD5.getSign(ApiManager.URL_SELF_SUBCLASS_COMMENT_REPLY, mUser);
                map.put("t", MD5.gettimes());
                map.put("sign", sign);
                if (iswho == true) {
                    JSONObject jsonObject2 = new JSONObject();
                    try {
                        jsonObject2.put("self_driving_id", self_id);
                        jsonObject2.put("content", mEditText.getText());
                        jsonObject2.put("to_user_id", resultBean.getUser_id());
                        jsonObject2.put("item_id", resultBean.getId());
                        showDialog(getString(R.string.dialog_data));
                        ApiManager.SelfSubCommentReply(map, jsonObject2, this, COMMENTSUBREPLY, TAG);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    JSONObject jsonObject2 = new JSONObject();
                    try {
                        jsonObject2.put("self_driving_id", self_id);
                        jsonObject2.put("content", mEditText.getText());
                        TLog.i(TAG,"--to_user_id----->"+to_user_id);
                        TLog.i(TAG,"--item_id----->"+resultBean.getId());
                        TLog.i(TAG,"------self_driving_id--->"+self_id);
                        jsonObject2.put("to_user_id", to_user_id);
                        jsonObject2.put("item_id", resultBean.getId());
                        showDialog(getString(R.string.dialog_data));
                        ApiManager.SelfSubCommentReply(map, jsonObject2, this, COMMENTSUBREPLY, TAG);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.self_subclass_avtivity_toolbar_callback_textview:
                finish();
                break;
        }
    }
}
