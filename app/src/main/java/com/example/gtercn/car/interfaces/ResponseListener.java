package com.example.gtercn.car.interfaces;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2016-5-23.
 * 网络访问回调 错误处理数据接口
 */
public interface ResponseListener {

    /**
     * 访问数据错误时候的处理
     *
     * @param type
     */
    void onErrorResponse(VolleyError error, int type);

}
