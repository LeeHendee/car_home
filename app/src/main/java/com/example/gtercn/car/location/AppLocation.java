package com.example.gtercn.car.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.example.gtercn.car.utils.ContextService;
import com.example.gtercn.car.utils.TLog;

/**
 * Created by Administrator on 2017-3-3.
 * 百度定位
 */

public class AppLocation {

    private  static final String  TAG = AppLocation.class.getSimpleName();

    private static AppLocation instance = new AppLocation();

    private Context mContext = ContextService.getInstance().getContext();

    private MyLocationListener mMyListener = new MyLocationListener();

    private LocationClient mLocationClient = null;

    private LocationClientOption mOption = null;

    private volatile BDLocation mLocation;

    private int mXDirection;

    /**
     * 最新一次的经纬度
     */
    private double mCurrentLatitude;

    private double mCurrentLongtitude;

    /**
     * 当前定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    /**
     * 当前的精度
     */
    private float mCurrentAccuracy;

    private AppLocation(){ }

    public static AppLocation newInstance(){
        return instance;
    }

    private void initManagerLocation(){
        mLocationClient = new LocationClient(mContext);
        mLocationClient.registerLocationListener(mMyListener);
        initLocationOption();
    }

    public void start(){
        initManagerLocation();
        mLocationClient.start();
    }

    public void stop(){
//        mLocationClient.unRegisterLocationListener(mMyListener);
        mLocationClient.stop();
    }

    private void initLocationOption(){
        mOption = new LocationClientOption();
        mOption.setCoorType("bd09ll");
        mOption.setScanSpan(2000);
        mOption.setIsNeedAddress(true);
        mOption.setOpenGps(true);
        mOption.setIsNeedLocationDescribe(true);
        mOption.setNeedDeviceDirect(true);
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(mOption);
    }

    private void setBDLocation(BDLocation location){
        mLocation = location;
    }

    public BDLocation getBDLocation(){
        return mLocation;
    }

    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation != null) {
                setBDLocation(bdLocation);
                AppLocationImpl.newInstance().notifyAppLocation();
                TLog.i(TAG, "-->> 通知百度的定位数据更新！");
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

}
