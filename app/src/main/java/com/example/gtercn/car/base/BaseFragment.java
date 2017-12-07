package com.example.gtercn.car.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.gtercn.car.cache.CacheManager;
import com.example.gtercn.car.utils.SnackbarUtil;
import com.example.gtercn.car.utils.TLog;

/**
 * Created by Administrator on 2016-5-18.
 * fragment 基础类
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private ConnectivityManager mConnectManager;

    protected Context mContext;

    protected float density = 1.5f;

    protected ProgressDialog mDialog;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mConnectManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        density = context.getResources().getDisplayMetrics().density;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       //抽象接口 调用入口，在View创建之后调用数据或其他
        initView();
        initData();
    }

    public void showSnack(View v, String s) {
        SnackbarUtil.ShortSnackbar(v, s, Color.WHITE, Color.BLACK).show();
    }

    public abstract void initView();

    public abstract void initData();

    public String getUrl(String api, String token, String timestamp, String sign) {
        String url = api + "?token=" + token + "&t=" + "t" + "&sign=" + sign;
        return url;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    protected boolean isLogin(){

        boolean b = ((CarApplication) (getActivity().getApplication())).getUser() != null ? true : false;
        return b;
    }

    protected void showDialog(final String msg){
        if(mDialog == null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage(msg);
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(false);
        }
        mDialog.show();
    }

    protected void closeDialog(){
        if (mDialog!=null){
            mDialog.dismiss();
            mDialog = null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void showToastMsg(int res){
        Toast.makeText(mContext,getString(res),Toast.LENGTH_SHORT).show();
    }

    protected void showToastMsg(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    protected void showToastMsg(VolleyError error){
        if(error instanceof TimeoutError){
            //网络连接超时
            showToastMsg("网络连接超时");
        }else if(error instanceof NoConnectionError){
            //网络未连接
            showToastMsg("网络未连接");
        }else if(error instanceof NetworkError){
            //网络错误
            showToastMsg("网络错误");
        }else if(error instanceof ServerError){
            //服务器响应错误
            showToastMsg("服务器响应错误");
        }else if (error instanceof ParseError){
            //解析错误，数据格式错误
            showToastMsg("数据格式错误");
        }

        TLog.i(TAG,  "response statusCode : " + error.getMessage());

    }

    protected boolean isNetWorkState(){
        if(mConnectManager != null){
            NetworkInfo info = mConnectManager.getActiveNetworkInfo();
            if(info != null && info.isConnectedOrConnecting()){
                if(info.getState() == NetworkInfo.State.CONNECTED || info.getState() == NetworkInfo.State.CONNECTING){
                    return true;
                }
            }else
                return false;
        }
        return false;
    }

    protected void readCacheData(String cacheKey,int type){
        new ReadCacheTask(mContext, type).execute(cacheKey);
    }

    protected void saveCacheData(String object, String cacheKey){
        new SaveCacheTask(mContext, object, cacheKey).execute();
    }

    private class ReadCacheTask extends AsyncTask<String, Void, String> {
        private Context context;
        private int type;
        public ReadCacheTask(Context context, int type){
            this.context = context;
            this.type = type;
        }

        @Override
        protected String doInBackground(String... params) {
            String file = params[0];
            String object = CacheManager.readObject(context, file);
            return object;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result != null){
                onExecuteSuccess(result, type);
            }else{
                onExecuteFailure(type);
            }
        }
    }

    protected abstract void onExecuteSuccess(String result, int type);

    protected abstract void onExecuteFailure(int type);

}
