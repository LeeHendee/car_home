package com.example.gtercn.car.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.ReplyAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.ExpertQuestionDetailBean;
import com.example.gtercn.car.bean.ReplyBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : LiHang.
 * data : 2016/12/19.
 * description:
 *    问题墙详情
 */
public class ExpertQuestionDetailActivity extends BaseActivity implements ResponseJSONObjectListener, IRecyclerItemListener<Integer> {

    private static final String TAG = "ExpertQuestionDetailActivity";

    private static final int SEND_REPLY = 0x002;
    private static final int QUERY_TYPE = 0x003;

    @BindView(R.id.question_detail_user_nickname)
    TextView mNickname;

    @BindView(R.id.question_detail_commit_time)
    TextView mPublishTime;

    @BindView(R.id.question_detail_content)
    TextView mQuestionContent;

    @BindView(R.id.question_detail_number_of_reply)
    TextView mReplyNumber;

    @BindView(R.id.question_detail_portrait)
    RoundedImageView mHeadPortrait;

    @BindView(R.id.question_detail_recy)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mBarTitle;

    @BindView(R.id.answer_the_question_ll)
    LinearLayout mAnswerQuestionLayout;

    @BindView(R.id.write_reply_view)
    LinearLayout mWriteReplyLayout;

    @BindView(R.id.send_reply_tv)
    TextView mSendReply;

    @BindView(R.id.write_reply_et)
    EditText mWriteReply;

    @BindView(R.id.dynamic_img_view)
    LinearLayout mDynamicView;

    private ExpertQuestionDetailBean.ResultBean mQuestionBean;

    private List<ReplyBean.ResultBean> mReplyList;

    private ReplyAdapter mAdapter;

    private Handler mHandler;

    private String mItemId = null;

    private CarApplication mApp;

    private User mUser;

    private boolean isFirst = true;

    private float density = 1.5f;

    private String mID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        ButterKnife.bind(this);
        density = getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onStart() {
        initView();
        initData();
        super.onStart();
    }

    private void initView() {
        mApp = (CarApplication) getApplication();
        mUser = mApp.getUser();
        mReplyList = new ArrayList<>();
        mBarTitle.setText("详情");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReplyAdapter(mReplyList, this);
        mRecyclerView.setAdapter(mAdapter);
        mHandler = new Handler(new MyHandlerCallback());
        mAdapter.setItemListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mID = intent.getStringExtra("id");
            TLog.i(TAG, "---->>mID = " + mID);
            String url;
            if (mUser != null) {
                String token = mUser.getResult().getToken();
                url = ApiManager.URL_QUESTION_WALL_DETAIL + "?token=" + token;
            } else
                url = ApiManager.URL_QUESTION_WALL_DETAIL;

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

    private void initDynamicView() {

        if (mQuestionBean.getRes_url_list() != null) {
            TLog.i(TAG, "---->>initDynamicView--->>url_list is not null");
            mDynamicView.removeAllViews();
            String[] array = mQuestionBean.getRes_url_list().split(",");
            List<String> imgList = Arrays.asList(array);
            if (imgList != null) {
                mDynamicView.setVisibility(View.VISIBLE);
                TLog.i(TAG, "--->>imgList.size = " + imgList.size());
                //将图片封装在list集合中,接下来根据list.size数量,动态创建布局;
                for (String s : imgList) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_dynamic_img, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.dynamic_img);

                    String url = s;
                    if (s.contains("\\")) {
                        url = TAppUtils.formatUrl(url);
                    }
                    THttpOpenHelper.newInstance().setImageBitmap(imageView, url,
                            (int) (90 * density), (int) (90 * density),
                            R.drawable.icon_default,
                            R.drawable.icon_default);

                    final String xxUrl = url;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, PhotoViewActivity.class);
                            intent.putExtra("url", xxUrl);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    });
                    mDynamicView.addView(view);
                }
            }
        } else
            TLog.i(TAG, "---->>initDynamicView--->>url_list is null !");
    }

    @OnClick({
            R.id.answer_the_question_ll,
            R.id.send_reply_tv
    })
    void onQuestionDetailClick(View v) {
        if (mQuestionBean == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.answer_the_question_ll:
                if (mUser != null)
                    mWriteReplyLayout.setVisibility(View.VISIBLE);
                else {
                    showToastMsg("请先登录!");
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.send_reply_tv:
                mWriteReplyLayout.setVisibility(View.GONE);
                sendReply(mItemId);
                break;
        }
    }

    /**
     * 参数: token--必须为真是的token值;
     * t,sign
     * JsonObject中封装
     * "question_id": "1",
     * "type_id": "0",
     * "id": "",
     * "content": "abcd"
     */
    private void sendReply(String itemId) {

        if (mUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            showToastMsg("登录用户才可以恢复帖子,请先登录!");
        }
        String token = mUser.getResult().getToken();
        String sign = MD5.getSign(ApiManager.URL_REPLY_SUBMIT, mUser);
        String t = MD5.gettimes();
        String url = getUrl(ApiManager.URL_REPLY_SUBMIT, token, t, sign);
        String content = mWriteReply.getText().toString();
        JSONObject params = new JSONObject();

        String questionId = mQuestionBean.getId();
        TLog.i(TAG, "--->>sendReply itemId is " + itemId);

        try {
            params.put("question_id", questionId);
            params.put("type_id", "0");
            params.put("id", itemId);
            params.put("content", content);
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, SEND_REPLY, TAG);
            showDialog(getString(R.string.submit_reply));
            hideKeyboard(mWriteReply);
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
                    TLog.i(TAG, "---->>response is " + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                ReplyBean bean = gson.fromJson(response.toString(), ReplyBean.class);
                                if (bean == null) {
                                    return;
                                }
                                List<ReplyBean.ResultBean> list = bean.getResult();
                                if (list != null && list.size() > 0) {
                                    TLog.i(TAG, "---->>list.size is " + list.size());
                                    mReplyList.clear();
                                    mReplyList.addAll(list);
                                    mAdapter.notifyDataSetChanged();
                                    mItemId = null;
                                    mReplyNumber.setText((String) list.get(0).getReply_num());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case SEND_REPLY:
                if (response != null) {
                    TLog.i(TAG, "---->>response is " + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            String message = response.getString("message");
                            if (message != null)
                                showToastMsg("提交成功!");
                            if (TextUtils.equals(code, "0")) {
                                mHandler.sendEmptyMessage(2);
                                isFirst = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case QUERY_TYPE:
                if (response != null) {
                    TLog.i(TAG, "---->>response is " + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            String message = response.getString("message");
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                ExpertQuestionDetailBean bean = gson.fromJson(response.toString(), ExpertQuestionDetailBean.class);
                                if (bean != null && bean.getResult() != null) {
                                    mQuestionBean = bean.getResult();
                                    if (mQuestionBean != null) {
                                        mHandler.sendEmptyMessage(100);
                                    }
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

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void callback(Integer integer) {
        mItemId = mReplyList.get(integer).getItem_id();
        TLog.i(TAG, "---->>itemId is " + mItemId);
        mAnswerQuestionLayout.performClick();
    }

    class MyHandlerCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 2) {
                mWriteReply.setText(null);
                mWriteReplyLayout.setVisibility(View.GONE);
                initData();
            } else if (msg.what == 100) {
                settingView();
            }
            return true;
        }
    }

    private void settingView() {

        String questionId = "";
        if (mQuestionBean != null) {
            mNickname.setText(mQuestionBean.getNickname());
//            String interval = TimeComputeUtil.getInterval(mQuestionBean.getInsert_time());
            mPublishTime.setText(mQuestionBean.getPublish_time());
            mReplyNumber.setText(mQuestionBean.getReply_num());
            if (mQuestionBean.getContent() == null)
                mQuestionContent.setVisibility(View.GONE);
            else
                mQuestionContent.setText(mQuestionBean.getContent());
            questionId = mQuestionBean.getId();
            String portraitUrl = mQuestionBean.getUser_portrait();
            if (!TextUtils.isEmpty(portraitUrl)) {
                portraitUrl = TAppUtils.formatUrl(portraitUrl);
                /**
                 * 缺省图片需要更换
                 */
                THttpOpenHelper.newInstance().setImageBitmap(mHeadPortrait,
                        portraitUrl, (int) (50 * density), (int) (50 * density),
                        R.drawable.icon_expert_avatar,
                        R.drawable.icon_expert_avatar);
            } else {
                mHeadPortrait.setImageResource(R.drawable.icon_expert_avatar);
            }
            initDynamicView();
        }

        String token = mUser.getResult().getToken();
        String sign = MD5.getSign(ApiManager.URL_REPLY_LIST, mUser);
        String t = MD5.gettimes();
        String url = getUrl(ApiManager.URL_REPLY_LIST, token, t, sign);
        JSONObject params = new JSONObject();
        try {
            params.put("question_id", questionId);
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, TAG);
            showDialog(getString(R.string.loading_data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
