package com.example.gtercn.car.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.LoginActivity;
import com.example.gtercn.car.adapter.ExpertCircleAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseFragment;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.ExpertCircleListBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 达人圈
 */
public class ExpertCircleFragment extends BaseFragment implements ResponseJSONObjectListener, ResponseStringListener,IRecyclerItemListener {

    private static final String TAG = "ExpertCircleFragment";

    private RecyclerView mRecyclerView;

    private Context mContext;

    private List<ExpertCircleListBean.ResultBean> mList;

    private ExpertCircleAdapter mAdapter;

    private CarApplication mApp;

    private User mUser;

    private String token = null;

    private RelativeLayout mEmptyView;

    private Paint mPaint = new Paint();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_circle, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.expert_circle_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mList = new ArrayList<>();
        mEmptyView = (RelativeLayout) view.findViewById(R.id.expert_circle_empty_layout);
        mEmptyView.setVisibility(View.GONE);

    }

    private void drawBottomLine() {
        mPaint.setColor(Color.rgb(215, 215, 215));
        mPaint.setStrokeWidth(1);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                for (int i = 0, size = parent.getChildCount(); i < size; i++) {
                    View child = parent.getChildAt(i);
                    c.drawLine(child.getLeft() + 10, child.getBottom(), child.getRight() - 10, child.getBottom(), mPaint);
                }
            }
        });
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        String url = null;
        mApp = (CarApplication) getActivity().getApplication();
        mUser = mApp.getUser();
        HashMap<String, String> map = new HashMap<>();
        if (mUser != null && mUser.getResult().getToken()!=null) {
            token = mUser.getResult().getToken();
            url = ApiManager.URL_EXPERT_CIRCLE_LIST + "?token=" + token;
            map.put("token", token);
        } else {
            url = ApiManager.URL_EXPERT_CIRCLE_LIST;
        }
        TLog.i(TAG, "initData url is " + url);
        map.put("city_code", SharedPreferenceHelper.getValue(ApiManager.CITY_CODE));
         THttpOpenHelper.newInstance().requestFormDataFilePost(url,map,null,this,2,TAG);
        showDialog(getString(R.string.loading_data));
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case 0:
                if (response != null) {
                    TLog.i(TAG, "--->>success" + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            if (TextUtils.equals(code, "0")) {
                                showToastMsg("成功收藏");
                                initData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 1:
                if (response != null) {
                    TLog.i(TAG, "--->>success" + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            if (TextUtils.equals(code, "0")) {
                                showToastMsg("取消收藏");
                                initData();
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
    public void onResume() {
        initData();
        super.onResume();
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
    public void callback(Object o) {
        /**
         * 点击收藏图标:
         *    如果未收藏则调用添加收藏接口; "0"
         *    如果已经收藏则调用删除收藏接口; "1"
         */

        ExpertCircleListBean.ResultBean bean = mList.get((Integer) o);
        if (token == null) {
            showToastMsg("请先登录!");
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            return;
        }
        mApp = (CarApplication) getActivity().getApplication();
        mUser = mApp.getUser();
        if(mUser != null) {
            if (TextUtils.equals(bean.getIs_favored(), "0")) {
                String sign = MD5.getSign(ApiManager.URL_FAVORADD, mUser);
                String t = MD5.gettimes();
                String url = ApiManager.URL_FAVORADD + "?token=" + token + "&t=" + t + "&sign=" + sign;
                String favor_id = bean.getId();
                String favor_type = "3";
                JSONObject params = new JSONObject();
                try {
                    params.put("favor_id", favor_id);
                    params.put("favor_type", favor_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 0, "favor_add");
                showDialog("正在添加收藏,请稍等...");
            } else {
                String sign = MD5.getSign(ApiManager.URL_FAVOR_DEL, mUser);
                String t = MD5.gettimes();
                String url = ApiManager.URL_FAVOR_DEL + "?token=" + token + "&t=" + t + "&sign=" + sign;
                String favor_id = bean.getId();
                String favor_type = "3";
                JSONObject params = new JSONObject();
                try {
                    params.put("favor_id", favor_id);
                    params.put("favor_type", favor_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, "favor_del");
                showDialog("正在取消收藏,请稍等...");
            }
        }
    }

    @Override
    public void onSuccessResponse(String response, int type) {
        if (!TextUtils.isEmpty(response)) {
            closeDialog();
            try {
                JSONObject obj = new JSONObject(response);

                if (obj.has("err_code")) {
                    String returnCode = obj.getString("err_code");
                    TAppUtils.logout(mApp,returnCode);
                    if (TextUtils.equals(returnCode, "0")) {
                        JSONArray obj2 = obj.getJSONArray("result");
                        String message = obj.getString("message");
                        if (message.equals("null")) {
                                Gson gson = new Gson();
                                List<ExpertCircleListBean.ResultBean>  list = gson.fromJson(obj2.toString(),
                                    new TypeToken<List<ExpertCircleListBean.ResultBean>>() {
                                    }.getType());
                                if (list != null && list.size() > 0) {
                                    mList.clear();
                                    mList.addAll(list);
                                    mAdapter = new ExpertCircleAdapter(mContext, mList);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mAdapter.setItemListener(this);
                                    drawBottomLine();
                                } else if (list == null || list.size() == 0) {
                                    mEmptyView.setVisibility(View.VISIBLE);
                                }
                        } else {
                            mEmptyView.setVisibility(View.VISIBLE);
                        }

                    } else {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
