package com.example.gtercn.car.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.location.CityCodeChangeImpl;
import com.example.gtercn.car.interfaces.ICityCodeChangeListener;
import com.example.gtercn.car.loader.FourServiceDynamicDataReceiver;
import com.example.gtercn.car.loader.RescueDynamicDataReceiver;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.location.AppLocationImpl;
import com.example.gtercn.car.interfaces.IAppLocationListener;
import com.example.gtercn.car.task.AppUpdateTask;
import com.example.gtercn.car.task.AutoLoginTask;
import com.example.gtercn.car.task.LoadFourServiceListTask;
import com.example.gtercn.car.task.LoadRescueListTask;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TLog;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * author : LiHang.
 * data : 2016/12/27.
 * description:
 * 整个项目Service类
 *   需要处理的业务逻辑:
 *      1.自动登录
 *      2.获取当前定位信息
 */
public class AppService extends Service implements ICityCodeChangeListener, IAppLocationListener{
    private static final String TAG = "AppService";
    /**
     * 缺省定位信息变化调整距离 米
     */
    private static final double DISTANCE_CHG = 1000;

    private AppBinder mBinder = new AppBinder();

    private ScheduledExecutorService mExecutor;

    private static int CPU_COUNT = 2;

    private CarApplication mApplication;

    private AppLocation mAppLocation;

    @Override
    public void onCreate() {
        super.onCreate();
        TLog.i(TAG, "-->>> AppService Start.");
        mApplication = (CarApplication) getApplication();
        CPU_COUNT = Runtime.getRuntime().availableProcessors();
        mExecutor = Executors.newScheduledThreadPool(CPU_COUNT + 1);
        init();
    }

    private void init(){
        mAppLocation = AppLocation.newInstance();
        mAppLocation.start();

        AppLocationImpl.newInstance().attchAppLocation(this);

        CityCodeChangeImpl.newInstance().attchCityCode(this);
        loadRescueAndServiceData();

        mExecutor.schedule(new AutoLoginTask(mApplication), 100, TimeUnit.MILLISECONDS);
        mExecutor.schedule(new AppUpdateTask(mApplication), 200, TimeUnit.MILLISECONDS);
    }

    private void loadRescueAndServiceData() {
        mExecutor.schedule(new LoadRescueListTask(mApplication), 1, TimeUnit.MILLISECONDS);
        mExecutor.schedule(new LoadFourServiceListTask(mApplication), 2, TimeUnit.MILLISECONDS);
    }

    @Override
    public void updateCityCode() {
        loadRescueAndServiceData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void updateLocation() {
        BDLocation bdLocation = mAppLocation.getBDLocation();
        double currentLatitude =bdLocation .getLatitude();
        double currentLongitude = bdLocation.getLongitude();

        String appLatitude = SharedPreferenceHelper.getValue(ApiManager.LAT);
        String appLongitude = SharedPreferenceHelper.getValue(ApiManager.LNG);
        LatLng currentPoint = new LatLng(currentLatitude, currentLongitude);
        LatLng appPoint = new LatLng(Double.valueOf(appLatitude), Double.valueOf(appLongitude));

        double distance = DistanceUtil.getDistance(currentPoint, appPoint);
        if(distance > DISTANCE_CHG){
            SharedPreferenceHelper.setValue(ApiManager.LAT, String.valueOf(currentLatitude));
            SharedPreferenceHelper.setValue(ApiManager.LNG, String.valueOf(currentLongitude));

            Intent fourServiceIntent = new Intent(FourServiceDynamicDataReceiver.FOUR_ACTION);
            Intent RescueServiceIntent = new Intent(RescueDynamicDataReceiver.RESCUE_ACTION);
            sendBroadcast(fourServiceIntent);
            sendBroadcast(RescueServiceIntent);
        }
    }

    @Override
    public void onDestroy() {
        CityCodeChangeImpl.newInstance().detachCityCode(this);

        if(mAppLocation != null){
            mAppLocation.stop();
        }

        AppLocationImpl.newInstance().detachAppLocation(this);


        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
        }

        super.onDestroy();
        TLog.i(TAG, "-->>> AppService Stop.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class AppBinder extends Binder {

    }
}
