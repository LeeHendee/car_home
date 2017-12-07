package com.example.gtercn.car.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.gtercn.car.R;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.location.MyOrientationListener;


/**
 * author : LiHang.
 * data : 2016/12/29.
 * description:
 * BaiDuMap Util for location;
 */
public class MyLocationUtil {

    private static final String TAG = "MyLocationUtil";

    private Context context;

    private MapView mMapView;

    /**
     * 地图实例
     */
    private BaiduMap mBaiduMap;
    /**
     * 定位的客户端
     */
    private LocationClient mLocationClient;
    /**
     * 定位的监听器
     */
    public MyLocationListener mMyLocationListener;
    /**
     * 当前定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    /***
     * 是否是第一次定位
     */
    private volatile boolean isFirstLocation = true;

    /**
     * 最新一次的经纬度
     */
    private double mCurrentLatitude;

    private double mCurrentLongtitude;
    /**
     * 当前的精度
     */
    private float mCurrentAccuracy;
    /**
     * 方向传感器的监听器
     */
    private MyOrientationListener myOrientationListener;
    /**
     * 方向传感器X方向的值
     */
    private int mXDirection;

    /**
     * 地图定位的模式
     */
    private String[] mStyles = new String[]{"地图模式【正常】", "地图模式【跟随】", "地图模式【罗盘】"};

    private int mCurrentStyle = 0;

    private BDLocation mBDLocation;

    private boolean isOnlyInfo = false;

    private boolean isReady = false;

    private CarApplication carApplication;


    public MyLocationUtil(MapView mapView, boolean isFirstLocation, CarApplication application) {
        this.context = application.getApplicationContext();
        this.mMapView = mapView;
        this.isFirstLocation = isFirstLocation;
        this.carApplication = application;
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        initMyLocation();
        initOrientationListener();
    }

    /**
     * 定位信息构造方法
     *
     * @param isOnlyInfo
     */
    public MyLocationUtil(boolean isOnlyInfo, CarApplication application) {

        this.isOnlyInfo = isOnlyInfo;
        this.carApplication = application;
        this.context = application.getApplicationContext();
        initMyLocation();
    }

    public BDLocation getBDLocation() {
        return mBDLocation;
    }

    /**
     * 初始化方向传感器
     */
    private void initOrientationListener() {
        myOrientationListener = new MyOrientationListener(context);

        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mXDirection = (int) x;
                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccuracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection)
                                .latitude(mCurrentLatitude)
                                .longitude(mCurrentLongtitude).build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                        // 设置自定义图标
                        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                                .fromResource(R.drawable.navi_map_gps_locked);
                        MyLocationConfiguration config = new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker);
                        mBaiduMap.setMyLocationConfigeration(config);
                    }
                });
    }

    /**
     * 初始化定位相关代码
     */
    public void initMyLocation() {

        mLocationClient = new LocationClient(context);
        // 创建定位回调监听;
        if (isOnlyInfo) {
            TLog.i(TAG, "----->>initMyLocation  isOnlyInfo is = " + isOnlyInfo);
            LocationInfoListener infoListener = new LocationInfoListener();
            mLocationClient.registerLocationListener(infoListener);
        } else {
            TLog.i(TAG, "----->>initMyLocation  isOnlyInfo is = " + isOnlyInfo);
            mMyLocationListener = new MyLocationListener();
            // 注册回调监听;
            mLocationClient.registerLocationListener(mMyLocationListener);
        }

        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setNeedDeviceDirect(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        option.setScanSpan(8000);
        mLocationClient.setLocOption(option);
    }


    public class LocationInfoListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            }
            mBDLocation = bdLocation;
            carApplication.setLocationInfo(mBDLocation);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 实现定位回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;


            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    // 设置定位信息的精度;
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mXDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 精确度;
            mCurrentAccuracy = location.getRadius();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 设置当前经纬度
            mCurrentLatitude = location.getLatitude();
            mCurrentLongtitude = location.getLongitude();
            // 设置自定义图标
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.navi_map_gps_locked);
            MyLocationConfiguration config = new MyLocationConfiguration(
                    mCurrentMode, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);
            // 第一次定位时，将地图位置移动到当前位置
            if (isFirstLocation) {
                isFirstLocation = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
            TLog.i(TAG, "--->>in the end !!! --->>>");
            carApplication.setLocationInfo(location);

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 地图移动到我的位置,此处可以重新发定位请求，然后定位；
     * 直接拿最近一次经纬度，如果长时间没有定位成功，可能会显示效果不好
     */
    public void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLatitude, mCurrentLongtitude);
        TLog.i(TAG, "----->>center2myLoc latitude = " + mCurrentLatitude + "--->> longtitude = " + mCurrentLongtitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    public void startLocation() {
        // 开启图层定位
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        if (!isOnlyInfo) {
            mBaiduMap.setMyLocationEnabled(true);
            // 开启方向传感器
            myOrientationListener.start();
        }
    }

    public void stopLocation() {
        // 开启图层定位
        if (!mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        if (!isOnlyInfo) {
            mBaiduMap.setMyLocationEnabled(true);
            // 开启方向传感器
            myOrientationListener.stop();
        }
    }
}
