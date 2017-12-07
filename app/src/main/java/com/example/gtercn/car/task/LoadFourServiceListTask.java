package com.example.gtercn.car.task;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.FourServerListBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.db.FourServiceContentProvider;
import com.example.gtercn.car.db.FourTypeService;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.loader.FourServiceDynamicDataReceiver;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-3-3.
 * 同步 4 类服务数据 到本地数据库
 */

public class LoadFourServiceListTask implements Runnable , ResponseJSONObjectListener{
    private static final String TAG = LoadFourServiceListTask.class.getSimpleName();
    private CarApplication mApplication;
    private Context mContext;
    private User mUser;
    private static final int COUNT = 500;
    private volatile int begin = 0;
    private volatile int end = 500;
    private volatile boolean isOver = true;
    private volatile boolean isOnce = true;
    private final Object mLock = new Object();
    private volatile JSONObject mResponse = null;

    public LoadFourServiceListTask(CarApplication application){
        this.mApplication = application;
        this.mContext = mApplication.getApplicationContext();
        mUser = mApplication.getUser();
    }

    @Override
    public void run() {
        while (isOver){
            processData(mResponse);
            mResponse = null;
            if(isOver){
                loadData();
            }

        }
    }

    private void loadData() {
        try {
            String url = "";
            JSONObject params = new JSONObject();
            params.put("begin_number" , begin);
            params.put("over_number", end);
            params.put("city_code", SharedPreferenceHelper.getValue(ApiManager.CITY_CODE));

            if(mUser != null){
                url = ApiManager.URL_FOUR_SERVE + "?token=" + mUser.getResult().getToken();
            }else {
                url = ApiManager.URL_FOUR_SERVE;
            }
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 1, "loadRescue");

            try {
                synchronized (mLock){
                    mLock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        if (response != null) {
            mResponse = response;
        }else {
            isOver = false;
        }
        unlocked();
    }

    private void processData(JSONObject response) {
        if(response != null) {
            if (response.has("err_code")) {
                try {
                    String code = response.getString("err_code");
                    if (TextUtils.equals(code, "0")) {
                        Gson gson = new Gson();
                        FourServerListBean bean = gson.fromJson(response.toString(), FourServerListBean.class);
                        List<FourServerListBean.ResultBean> list = bean.getResult();
                        if (list != null && list.size() > 0) {
                            saveRescueData(list);
                            sendDynamicDataChange();
                            if (list.size() < COUNT) {
                                isOver = false;
                            } else {
                                begin += list.size();
                                end += COUNT;
                            }
                        } else {
                            isOver = false;
                        }
                    } else {
                        isOver = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    isOver = false;
                }
            } else {
                isOver = false;
            }
        }
    }

    private void unlocked(){
        synchronized (mLock){
            mLock.notify();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        isOver = false;
        unlocked();
    }

    private void deleteTable(FourServerListBean.ResultBean bean){
        if(isOnce){
            mContext.getContentResolver().delete(FourTypeService.Four.CONTENT_URI, null, null);
            isOnce = false;
            return;
        }

        Cursor cursor = mContext.getContentResolver().query(FourTypeService.Four.CONTENT_URI,
                FourServiceContentProvider.FOUR_PROJECTION, null, null, null);
        if(cursor != null && cursor.getCount() != 0){
            if(cursor.moveToFirst()){
                String code = cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_CITY_CODE));
                if(TextUtils.equals(code, bean.getCity_code())){
                }else {
                    mContext.getContentResolver().delete(FourTypeService.Four.CONTENT_URI, null, null);
                }
            }
        }
        if(cursor != null){
            cursor.close();
        }
    }

    private void saveRescueData(List<FourServerListBean.ResultBean>  list){
        FourServerListBean.ResultBean newData = list.get(0);
        deleteTable(newData);
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        for (FourServerListBean.ResultBean bean: list){
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(FourTypeService.Four.CONTENT_URI);
            builder.withValue(FourTypeService.Four.COLUMN_NAME_SHOP_ID, bean.getId());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_ID,bean.getId());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE,bean.getRepair_service());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_CLEAN_SERVICE,bean.getClean_service());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_MAINTAIN_SERVICE,bean.getMaintain_service());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_TYRE_SERVICE,bean.getTyre_service());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_SHOP_PIC_URL,bean.getShop_pic_url());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_SHOP_NAME,bean.getShop_name());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_SCORE,bean.getScore());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_DETAIL_ADDRESS,bean.getDetail_address());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_TEL_NUM_LIST,bean.getTel_number_list());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_LONGTITUDE,bean.getLongitude());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_LATITUDE,bean.getLatitude());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_CITY_CODE,bean.getCity_code());
            builder.withValue(FourTypeService.Four.COLUMN_NAME_TYPE_VALUE,"1");
            builder.withValue(FourTypeService.Four.COLUMN_NAME_SHOP_DESCIPTION,"2");
            builder.withYieldAllowed(true);
            ops.add(builder.build());
        }

        try {
            mContext.getContentResolver().applyBatch(FourTypeService.AUTHORITY,ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private void sendDynamicDataChange(){
        Intent intent = new Intent(FourServiceDynamicDataReceiver.FOUR_ACTION);
        mContext.sendBroadcast(intent);
    }

}
