package com.example.gtercn.car.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.ExpertTopTitleAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseFragment;
import com.example.gtercn.car.bean.ExpertTopTypeBean;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *达人榜
 */
public class ExpertTopFragment extends BaseFragment implements ResponseJSONObjectListener {
    private static final String TAG = "ExpertTopFragment";
    private ListView mListView;
    private List<ExpertTopTypeBean.ResultBean> mTypeList;
    private Context mContext;
    private FragmentManager fm;
    private ExpertTopTypeBean mBean;
    private ExpertTopTitleAdapter mAdapter;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100) {
                ExpertTopContentFragment expertTopContentFragment = new ExpertTopContentFragment();
                Bundle bundle = new Bundle();
                String type_id = mTypeList.get(0).getId();
                bundle.putString("type_id", type_id);
                expertTopContentFragment.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.expert_notice_framelayout, expertTopContentFragment, "0")
                        .commit();
            }
            return true;
        }
    });

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_notice, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mListView = (ListView) view.findViewById(R.id.expert_notice_listview);
        fm = getFragmentManager();
        mTypeList = new ArrayList<>();
        mAdapter = new ExpertTopTitleAdapter(mContext, mTypeList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ExpertTopContentFragment expertTopContentFragment = new ExpertTopContentFragment();
                Bundle bundle = new Bundle();
                String type_id = mTypeList.get(position).getId();
                bundle.putString("type_id", type_id);
                expertTopContentFragment.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.expert_notice_framelayout, expertTopContentFragment, "0")
                        .addToBackStack("0")
                        .commit();

                mAdapter.changeSelected(position);
            }
        });

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        try {
            String url = ApiManager.URL_EXPERT_TYPE;
            JSONObject params = new JSONObject();
            params.put("t", "t");
            params.put("sign", "sign");
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, TAG);
            showDialog(getString(R.string.loading_data));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        if (response != null) {
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    if (TextUtils.equals(code, "0")) {
                        Gson gson = new Gson();
                        mBean = gson.fromJson(response.toString(), ExpertTopTypeBean.class);
                        if (mBean != null) {
                            List<ExpertTopTypeBean.ResultBean> list = mBean.getResult();
                            if (list != null) {
                                mTypeList.addAll(list);
                                mAdapter.notifyDataSetChanged();
                                mHandler.sendEmptyMessage(100);
                            }
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

}
