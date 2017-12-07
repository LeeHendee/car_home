package com.example.gtercn.car.task;

import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.Constants;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-7-4.
 * 自动登陆
 */
public class AutoLoginTask implements Runnable, ResponseJSONObjectListener {
    private static final String TAG = AutoLoginTask.class.getSimpleName();
    private static final int TYPE = 0x1;
    private CarApplication mApplication;
    private User mUser;

    public AutoLoginTask(CarApplication app) {
        mApplication = app;
    }

    @Override
    public void run() {
        try {
            String token = SharedPreferenceHelper.getValue("token");
            if(TextUtils.isEmpty(token)){
                return;
            }else {
                JSONObject params = new JSONObject();
                params.put("token", token);
                THttpOpenHelper.newInstance().requestJsonObjectPost(ApiManager.URL_AUTO_LOGIN, params, this, TYPE, TAG);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void  clearPersonalData(){
        mApplication.resetUser();
        boolean isFirst = SharedPreferenceHelper.getBoolean(Constants.APP_LAUNCH_FLAG);

        SharedPreferenceHelper.clearAll(mApplication.getApplicationContext());

        SharedPreferenceHelper.setBoolean(Constants.APP_LAUNCH_FLAG, isFirst);

    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        clearPersonalData();
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        switch (type) {
            case TYPE:
                if (response != null) {
                    try {
                        if (response.has("returnCode")) {
                            String code = response.getString("returnCode");
                            String message = response.getString("message");
                            Toast.makeText(mApplication.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                mUser = gson.fromJson(String.valueOf(response), User.class);
                                if (mUser != null) {
                                    mApplication.setUser(mUser);
                                    saveToken();
                                }
                            } else {
                                clearPersonalData();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    clearPersonalData();
                }
                break;
            default:
                break;
        }
    }

    private void saveToken() {
        String token = mUser.getResult().getToken();
        if (token != null) {
            SharedPreferenceHelper.setValue("token", token);
        }
    }

}
