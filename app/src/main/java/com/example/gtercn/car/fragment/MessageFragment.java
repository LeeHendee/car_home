package com.example.gtercn.car.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.MessageAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseFragment;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.MessageBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 发现的咨询页面
 */
public class MessageFragment extends BaseFragment implements ResponseStringListener ,ResponseJSONObjectListener{
    private static final String TAG = "MessageFragment";
    private static final int MESSAGE = 63276061;
    private static final int MSEARCH = 47922282;

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private ImageView mSearchimage;
    private EditText mSearchEditText;
    private ArrayList<MessageBean.ResultBean> list = new ArrayList<>();
    private ArrayList<MessageBean.ResultBean> list2 = new ArrayList<>();
    private CarApplication mApplication;
    private User mUser;
    private RelativeLayout mCustomLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);
        mApplication = (CarApplication) getActivity().getApplication();
        mUser = mApplication.getUser();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_promotion_recyclerview);
        mSearchEditText = (EditText) view.findViewById(R.id.fragment_promotion_edittext);
        mSearchimage = (ImageView) view.findViewById(R.id.fragment_promotion_search_image);
        mCustomLayout = (RelativeLayout)view.findViewById(R.id.fragment_promotion_relativelayout);

        mSearchimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                showDialog(getResources().getString(R.string.dialog_data));
                if(mUser !=null && mUser.getResult().getToken() != null){
                    HashMap<String, String> map = new HashMap<>();
                    map.put("keyword", mSearchEditText.getText().toString());
                    map.put("token",mUser.getResult().getToken());
                    ApiManager.MessageSearch(map,null, MessageFragment.this, MSEARCH, TAG);
                }else{
                    HashMap<String, String> map = new HashMap<>();
                    map.put("keyword", mSearchEditText.getText().toString());
                    ApiManager.MessageSearch(map,null, MessageFragment.this, MSEARCH, TAG);
                }
            }
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    //TODO回车键按下时要执行的操作
                    showDialog(getResources().getString(R.string.dialog_data));
                    if(mUser !=null  && mUser.getResult().getToken() != null){
                        HashMap<String, String> map = new HashMap<>();
                        map.put("keyword", mSearchEditText.getText().toString());
                        map.put("token",mUser.getResult().getToken());
                        ApiManager.MessageSearch(map,null, MessageFragment.this, MSEARCH, TAG);
                    }else{
                        HashMap<String, String> map = new HashMap<>();
                        map.put("keyword", mSearchEditText.getText().toString());
                        ApiManager.MessageSearch(map, null,MessageFragment.this, MSEARCH, TAG);
                    }
                }
                return false;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    private void getData() {
       //  showDialog(getString(R.string.dialog_data));
        String url = ApiManager.URL_MESSAGE;
        if(mUser!= null){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token",mUser.getResult().getToken());
                THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,MESSAGE,TAG);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token","");
                THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,MESSAGE,TAG);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = mApplication.getUser();
        getData();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //pause

        } else {
            //resume
            if (list2.size()!=0) {
                mCustomLayout.setVisibility(View.GONE);
                mAdapter.refresh(list2);
                mSearchEditText.setText("");
            } else {
                getData();
            }

        }
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
    }

    @Override
    public void onSuccessResponse(String response, int type) {
        closeDialog();
        switch (type) {
            case MSEARCH:
                if (!TextUtils.isEmpty(response)) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.has("err_code")) {
                            String returnCode = obj.getString("err_code");
                            TAppUtils.logout(mApplication,returnCode);
                            if (TextUtils.equals(returnCode, "0")) {
                                JSONArray obj2 = obj.getJSONArray("result");
                                String message = obj.getString("message");
                                if (message.equals("null")) {
                                    mCustomLayout.setVisibility(View.GONE);
                                    Gson gson = new Gson();
                                    list.clear();
                                    list = gson.fromJson(obj2.toString(),
                                            new TypeToken<List<MessageBean.ResultBean>>() {
                                            }.getType());
                                    mAdapter.refresh(list);
                                } else {
                                    mCustomLayout.setVisibility(View.VISIBLE);
                                    list.clear();
                                    mAdapter.refresh(list);
                                }

                            } else {

                                mCustomLayout.setVisibility(View.VISIBLE);
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
    public void onSuccessResponse(JSONObject response, int type) {
        if (response != null) {
            try {

                if (response.has("err_code")) {
                    String returnCode = response.getString("err_code");
                    TAppUtils.logout(mApplication,returnCode);
                    if (TextUtils.equals(returnCode, "0")) {
                        JSONArray obj2 = response.getJSONArray("result");
                        String message = response.getString("message");
                        if (message.equals("null")) {
                            mCustomLayout.setVisibility(View.GONE);
                            Gson gson = new Gson();
                            list.clear();
                            list = gson.fromJson(obj2.toString(),
                                    new TypeToken<List<MessageBean.ResultBean>>() {
                                    }.getType());
                            list2= (ArrayList<MessageBean.ResultBean>) list.clone();
                            mAdapter = new MessageAdapter(getActivity(), list);
                            mRecyclerView.setAdapter(mAdapter);

                        } else {

                            mCustomLayout.setVisibility(View.VISIBLE);
                        }

                    } else {
                        mCustomLayout.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
