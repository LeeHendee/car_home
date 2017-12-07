package com.example.gtercn.car.location;

import com.example.gtercn.car.interfaces.IAppLocation;
import com.example.gtercn.car.interfaces.IAppLocationListener;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Administrator on 2017-3-3.
 * 定位观测器
 */

public class AppLocationImpl implements IAppLocation {
    private static final String TAG = AppLocationImpl.class.getSimpleName();

    private Vector<IAppLocationListener> vector = new Vector<>();

    private static  AppLocationImpl instance = null;

    private AppLocationImpl(){}

    public synchronized static AppLocationImpl newInstance(){
        if(instance == null){
            instance = new AppLocationImpl();
        }
        return instance;
    }

    @Override
    public void attchAppLocation(IAppLocationListener listener) {
        vector.add(listener);
    }

    @Override
    public void detachAppLocation(IAppLocationListener listener) {
        vector.remove(listener);
    }

    @Override
    public void notifyAppLocation() {
        if (vector != null) {
            Iterator<IAppLocationListener> iterator = vector.iterator();
            while (iterator.hasNext()) {
                iterator.next().updateLocation();
            }
        }
    }
}
