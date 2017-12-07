package com.example.gtercn.car.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.AskQuestionActivity;
import com.example.gtercn.car.activity.ExpertQuestionDetailActivity;
import com.example.gtercn.car.activity.LoginActivity;
import com.example.gtercn.car.adapter.ExpertQuestionAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseFragment;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.ExpertQuestionBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.IRecyclerItemListener;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题墙
 */
public class ExpertQuestionFragment extends BaseFragment implements View.OnClickListener,
        ResponseJSONObjectListener, IRecyclerItemListener<Integer> {

    private static final String TAG = "ExpertQuestionFragment";

    private Context mContext;

    private ExpertQuestionAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private List<ExpertQuestionBean.ResultBean> mList;

    private FloatingActionButton mFab;

    private View mView;

    private CarApplication mApp;

    private User mUser;

    private RelativeLayout mEmptyView;

    private Paint mPaint = new Paint();

    private float density = 1.5f;

    private int mScreenWidth = 480;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        density = getResources().getDisplayMetrics().density;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;

    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_expert_question, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApp = (CarApplication) getActivity().getApplication();
        mList = new ArrayList<>();
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.expert_question_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ExpertQuestionAdapter(mContext, mList);
        mRecyclerView.setAdapter(mAdapter);
        drawBottomLine();
        mAdapter.setItemListener(this);

        mFab.setOnClickListener(this);
        mFab.show();

        mEmptyView =(RelativeLayout) view.findViewById(R.id.expert_question_empty_layout);
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
        String url = ApiManager.URL_QUESTION_WALL_LIST;//getUrl(ApiManager.URL_QUESTION_WALL_LIST, "u", "t", "s");
        JSONObject params = new JSONObject();
        try {
            params.put("u", "u");
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, TAG);
            showDialog(getString(R.string.loading_data));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:

                mUser = mApp.getUser();
                if (mUser == null) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    showToastMsg("请登录应用");
                } else {
                    Intent intent = new Intent(getActivity(), AskQuestionActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        if (response != null) {
            TLog.i(TAG, "---->>response is " + response.toString());
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    if (TextUtils.equals(code, "0")) {
                        Gson gson = new Gson();
                        ExpertQuestionBean bean = gson.fromJson(response.toString(), ExpertQuestionBean.class);
                        List<ExpertQuestionBean.ResultBean> list = bean.getResult();
                        if (list != null) {
                            mList.clear();
                            mList.addAll(list);
                            TLog.i(TAG, "----->>mlist.size = " + mList.size());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mEmptyView.setVisibility(View.VISIBLE);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
        ExpertQuestionBean.ResultBean bean = mList.get(integer);
        mUser = mApp.getUser();
        if (bean == null) {
            TLog.i(TAG, "------>>callback bean is null!");
            return;
        }
        if (mUser == null) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            showToastMsg("请登录应用");
        } else {
            Intent intent = new Intent(mContext, ExpertQuestionDetailActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        closeDialog();
        super.onDestroyView();
    }
}
