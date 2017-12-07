package com.example.gtercn.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.ExpertInfoActivity;
import com.example.gtercn.car.adapter.ExpertTopContentAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseFragment;
import com.example.gtercn.car.bean.ExpertTopBean;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 * 达人榜
 */

public class ExpertTopContentFragment extends BaseFragment implements ExpertTopContentAdapter.IRecyclerViewItemListener, ResponseJSONObjectListener {

    private static final String TAG = "ExpertTopContentFragment";

    private RecyclerView mRecyclerView;

    private ExpertTopContentAdapter mAdapter;

    private List<ExpertTopBean.ResultBean> mTopList;

    private String mTypeId = null;

    private Bundle bundle;

    private RelativeLayout mEmptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_notice_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTopList = new ArrayList<>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.expert_top_recy);
        GridLayoutManager gm = new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(gm);
        mAdapter = new ExpertTopContentAdapter(mContext, mTopList);
        mRecyclerView.setAdapter(mAdapter);
        mEmptyView = (RelativeLayout) view.findViewById(R.id.expert_top_empty_view);
        bundle = getArguments();
        if (bundle != null) {
            mTypeId = bundle.getString("type_id");
        }
        super.onViewCreated(view, savedInstanceState);
        mAdapter.setListener(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        try {

            if (mTypeId == null) {
                return;
            }

            String url = ApiManager.URL_EXPERT_TOP;
            JSONObject params = new JSONObject();
            params.put("expert_type", mTypeId);
            params.put("city_code", SharedPreferenceHelper.getValue(ApiManager.CITY_CODE));
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, TAG);
            showDialog(getString(R.string.loading_data));
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
    public void listener(int position) {
        Intent intent = new Intent(mContext,ExpertInfoActivity.class);
        ExpertTopBean.ResultBean bean = mTopList.get(position);
        intent.putExtra("expert_info", bean);
        startActivity(intent);
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        if (response != null) {
            TLog.i(TAG, "--->>response is = " + response.toString());
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");

                    if (TextUtils.equals(code, "0")) {
                        JSONArray array = response.getJSONArray("result");
                        if (array.length() == 0) {
                            mEmptyView.setVisibility(View.VISIBLE);
                            return;
                        }
                        Gson gson = new Gson();
                        ExpertTopBean bean = gson.fromJson(response.toString(), ExpertTopBean.class);
                        List<ExpertTopBean.ResultBean> list = bean.getResult();

                        mTopList.clear();
                        if (list != null) {
                            mTopList.addAll(list);
                            mAdapter.notifyDataSetChanged();
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


}
