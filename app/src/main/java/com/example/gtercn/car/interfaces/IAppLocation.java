package com.example.gtercn.car.interfaces;

/**
 * Created by Administrator on 2017-3-3.
 * 应用定位接口
 */

public interface IAppLocation {
    void attchAppLocation(IAppLocationListener listener);

    void detachAppLocation(IAppLocationListener listener);

    void notifyAppLocation();
}
