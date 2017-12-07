package com.example.gtercn.car.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.gtercn.car.cache.CacheManager;
import com.example.gtercn.car.utils.Constants;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.SnackbarUtil;
import com.example.gtercn.car.utils.TAppUtils;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by Administrator on 2016-5-18.

 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    protected Context mContext;

    protected ProgressDialog mDialog;
    protected float density = 1.5f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        versionMarshmallow();
        mContext = getApplicationContext();
        AppManager.getInstance().addActivity(this);

    }

    public String getUrl(String api, String token, String timestamp, String sign) {
        String url = api + "?token=" + token + "&t=" + timestamp + "&sign=" + sign;
        return url;
    }

    private void versionMarshmallow(){
        if(Build.VERSION.SDK_INT >= 23){
            TAppUtils.versifyStoragePermission(this);
            TAppUtils.verifyCameraPermission(this);
            TAppUtils.verifyLocationPermission(this);
        }
    }

    public void showSnack(View view, String content){
        SnackbarUtil.ShortSnackbar(view, content, Color.WHITE, Color.BLACK).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnTop();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnTop() {
        finish();
    }

    protected boolean isLogin() {
        return ((CarApplication) (getApplication())).getUser() == null ? false : true;
    }

    protected void showDialog(final String msg) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }

    protected void showToastMsg(int res) {
        Toast.makeText(getApplication(), getString(res), Toast.LENGTH_SHORT).show();
    }

    protected void showToastMsg(String msg) {
        Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToastMsg(VolleyError error) {
        if (error instanceof TimeoutError) {
            showToastMsg("网络连接超时");
        } else if (error instanceof NoConnectionError) {
            showToastMsg("网络未连接");
        } else if (error instanceof NetworkError) {
            showToastMsg("网络错误");
        } else if (error instanceof ServerError) {
            showToastMsg("服务器响应错误");
        } else if (error instanceof ParseError) {
            showToastMsg("数据格式错误");
        }
    }

    protected void readCacheData(String cacheKey, int type) {
        new ReadCacheTask(mContext, type).execute(cacheKey);
    }
    protected void saveCacheData(String cacheKey, String object) {
        new SaveCacheTask(mContext, object, cacheKey).execute();
    }

    private class ReadCacheTask extends AsyncTask<String, Void, String> {
        private Context context;
        private int type;

        public ReadCacheTask(Context context, int type) {
            this.context = context;
            this.type = type;
        }

        @Override
        protected String doInBackground(String... params) {
            String file = MD5.hashKeyForDisk(params[0]);
            String object = CacheManager.readObject(context, file);
            return object;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                onExecuteSuccess(result, type);
            } else {
                onExecuteFailure(type);
            }
        }
    }

    protected abstract void onExecuteSuccess(String result, int type);

    protected abstract void onExecuteFailure(int type);

    protected boolean hasPermissions(String... permissions) {

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    protected void requestPermissions(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.WRITE_SD:
                doSDWritePermission();
                break;
            case Constants.CALL_PHONE:
                doCallPermission();
                break;
            case Constants.LOCATION:
                doLocation();
                break;
        }
    }

    protected void doLocation() {

    }

    protected void doCallPermission() {

    }

    protected void doSDWritePermission() {

    }

    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
