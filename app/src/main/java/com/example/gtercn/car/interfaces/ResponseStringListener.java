package com.example.gtercn.car.interfaces;

/**
 * Created by Administrator on 2016-12-14.
 * 网络访问回调 String数据接口
 */
public interface ResponseStringListener extends ResponseListener {
    /**
     *成功返回String数据的处理
     * @param type
     */
    void onSuccessResponse(String response, int type);
}
