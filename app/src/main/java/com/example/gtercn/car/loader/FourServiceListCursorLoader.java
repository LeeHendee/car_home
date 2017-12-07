package com.example.gtercn.car.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.bean.FourServiceBean;
import com.example.gtercn.car.db.FourServiceContentProvider;
import com.example.gtercn.car.db.FourTypeService;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017-3-3.
 *  4 类服务数据加载 loader
 */

public class FourServiceListCursorLoader extends AsyncTaskLoader<List<FourServiceBean>> {
    private static final String TAG = FourServiceListCursorLoader.class.getSimpleName();
    private static double RADIUS = 200000 * 1000;

    private BDLocation mBDLocation = AppLocation.newInstance().getBDLocation();

    private List<FourServiceBean> mData;

    private FourServiceDynamicDataReceiver mDynamicDataReceiver;

    /**
     * 1 修车
     * 2 洗车
     * 3 轮胎
     * 4 保养
     */
    private int mServiceType;

    public FourServiceListCursorLoader(Context context, int serviceType){
        super(context);
        this.mServiceType = serviceType;
    }

    @Override
    public List<FourServiceBean> loadInBackground() {
        LatLng currentPoint =  null;
        if(mBDLocation != null){
            double currentLatitude = mBDLocation.getLatitude();
            double currentLongitude = mBDLocation.getLongitude();
            currentPoint =  new LatLng(currentLatitude, currentLongitude);
        }else {
            //读取缺省值，
            String appLatitude = SharedPreferenceHelper.getValue(ApiManager.LAT);
            String appLongitude = SharedPreferenceHelper.getValue(ApiManager.LNG);
            currentPoint = new LatLng(Double.valueOf(appLatitude), Double.valueOf(appLongitude));
        }
        String selection;
        String type;
        switch (mServiceType){
            case 5100:
                type = "5100";
                selection = FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE + "=? ";
                break;
            case 4100:
                type = "4100";
                selection = FourTypeService.Four.COLUMN_NAME_CLEAN_SERVICE + "=? " ;
                break;
            case 7100:
                type = "7100";
                selection = FourTypeService.Four.COLUMN_NAME_TYRE_SERVICE + "=? " ;
                break;
            case 6100:
                type = "6100";
                selection = FourTypeService.Four.COLUMN_NAME_MAINTAIN_SERVICE + "=? ";
                break;
            default:
                type = "5100";
                selection = FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE + "=? " ;
        }

        List<FourServiceBean> appData = new ArrayList<>();

        Cursor cursor = null;

        String[] selectionArgs = {type};//无删除标记

        cursor = getContext().getContentResolver().query(FourTypeService.Four.CONTENT_URI,
                FourServiceContentProvider.FOUR_PROJECTION, selection, selectionArgs, null);

        if(cursor != null && cursor.moveToFirst()){
            do{
                String longitude = cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_LONGTITUDE));
                String latitude = cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_LATITUDE));
                LatLng p = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                double distance = DistanceUtil.getDistance(currentPoint, p);
                double   f1   =   Math.floor(distance);
                if(distance < RADIUS){
                    FourServiceBean bean = new FourServiceBean();
                    bean.setDistance(f1);
                    bean.setId(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_ID)));
                    bean.setShop_id(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_SHOP_ID)));
                    bean.setRepair_service(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE)));
                    bean.setClean_service(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_CLEAN_SERVICE)));
                    bean.setMaintain_service(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_MAINTAIN_SERVICE)));
                    bean.setTyre_service(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_TYRE_SERVICE)));
                    bean.setShop_pic_url(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_SHOP_PIC_URL)));
                    bean.setShop_name(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_SHOP_NAME)));
                    bean.setScore(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_SCORE)));
                    bean.setDetail_address(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_DETAIL_ADDRESS)));
                    bean.setTel_num_list(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_TEL_NUM_LIST)));
                    bean.setLongitude(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_LONGTITUDE)));
                    bean.setLatitude(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_LATITUDE)));
                    bean.setCity_code(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_CITY_CODE)));
                    bean.setType_value(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_TYPE_VALUE)));
                    bean.setShop_description(cursor.getString(cursor.getColumnIndex(FourTypeService.Four.COLUMN_NAME_SHOP_DESCIPTION)));
                    appData.add(bean);
                }
            }while (cursor.moveToNext());

            cursor.close();

        }

        Collections.sort(appData, new Comparator<FourServiceBean>() {
            @Override
            public int compare(FourServiceBean lhs, FourServiceBean rhs) {
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
    public void onCanceled(List<FourServiceBean> data) {
        super.onCanceled(data);
        onReleaseResources(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    public void deliverResult(List<FourServiceBean> data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List<FourServiceBean>  oldData = data;
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
            mDynamicDataReceiver = new FourServiceDynamicDataReceiver(this);
        }

        if (takeContentChanged() || mData == null ) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    protected void onReleaseResources(List<FourServiceBean> data) {

    }
    
}
