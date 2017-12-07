package com.example.gtercn.car.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.bean.RescueListBean;
import com.example.gtercn.car.db.RescueService;
import com.example.gtercn.car.db.RescueServiceContentProvider;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017-3-3.
 *  紧急救援数据加载loader
 */

public class RescueListCursorLoader extends AsyncTaskLoader<List<RescueListBean.ResultBean>> {
    private static final String TAG = RescueListCursorLoader.class.getSimpleName();
    private static double RADIUS = 200000 * 1000;
    private List<RescueListBean.ResultBean> mData;
    private RescueDynamicDataReceiver mDynamicDataReceiver;
    private BDLocation mBDLocation = AppLocation.newInstance().getBDLocation();

    public RescueListCursorLoader(Context context){
        super(context);
    }

    @Override
    public List<RescueListBean.ResultBean> loadInBackground() {

        //从百度定位读取当前位置
        LatLng currentPoint =  null;
        if(mBDLocation != null){
            double currentLatitude = mBDLocation.getLatitude();
            double currentLongitude = mBDLocation.getLongitude();
            currentPoint =  new LatLng(currentLatitude, currentLongitude);
        }else {
            String appLatitude = SharedPreferenceHelper.getValue(ApiManager.LAT);
            String appLongitude = SharedPreferenceHelper.getValue(ApiManager.LNG);
            currentPoint = new LatLng(Double.valueOf(appLatitude), Double.valueOf(appLongitude));
        }

        List<RescueListBean.ResultBean> appData = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(RescueService.Rescue.CONTENT_URI,
                RescueServiceContentProvider.RESCUE_PROJECTION, null, null, null);

        if(cursor != null && cursor.moveToFirst()){

            do{
                String longitude = cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_LONGITUDE));
                String latitude = cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_LATITUDE));
                LatLng p = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                double distance = DistanceUtil.getDistance(currentPoint, p);
                double   f1   =   Math.floor(distance);
                if(distance < RADIUS){
                    RescueListBean.ResultBean bean = new RescueListBean.ResultBean();
                    bean.setDistance(f1);

                    bean.setId(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_ID)));
                    bean.setCity_code(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_CITY_CODE)));
                    bean.setType_value(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_TYPE_VALUE)));
                    bean.setHead_portrait_url(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_HEAD_PORTRAIT_URL)));
                    bean.setShop_name(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_SHOP_NAME)));
                    bean.setShop_score(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_SHOP_SCORE)));
                    bean.setLongitude(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_LONGITUDE)));
                    bean.setLatitude(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_LATITUDE)));
                    bean.setCategory(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_CATEGORY)));
                    bean.setCity(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_CITY)));
                    bean.setDetail_address(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_DETAIL_ADDRESS)));
                    bean.setTel_number_list(cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_TEL_NUMBER_LIST)));

                    String distanceList = cursor.getString(cursor.getColumnIndex(RescueService.Rescue.COLUMN_NAME_DISTANCE_LIST));
                    String[] arr = distanceList.split(",");

                    List<String> sList = Arrays.asList(arr);

                    bean.setDistance_list(sList);
                    appData.add(bean);
                }
            }while (cursor.moveToNext());

            cursor.close();

        }

        Collections.sort(appData, new Comparator<RescueListBean.ResultBean>() {
            @Override
            public int compare(RescueListBean.ResultBean lhs, RescueListBean.ResultBean rhs) {
                if(lhs.getDistance() > rhs.getDistance())
                    return 1;
                else if( lhs.getDistance() < rhs.getDistance()){
                    return -1;
                }else {
                    return 0;
                }
            }
        });

        return appData;
    }

    @Override
    public boolean cancelLoad() {
        return super.cancelLoad();
    }

    @Override
    public void onCanceled(List<RescueListBean.ResultBean> data) {
        super.onCanceled(data);
        onReleaseResources(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    public void deliverResult(List<RescueListBean.ResultBean> data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List<RescueListBean.ResultBean> oldData = data;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null) {
            onReleaseResources(oldData);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();

        if (mData != null) {
            onReleaseResources(mData);
            mData = null;
        }

        if (mDynamicDataReceiver != null) {
            getContext().unregisterReceiver(mDynamicDataReceiver);
            mDynamicDataReceiver = null;
        }

    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        if (mDynamicDataReceiver == null) {
            mDynamicDataReceiver = new RescueDynamicDataReceiver(this);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    protected void onReleaseResources(List<RescueListBean.ResultBean> data) {

    }

}
