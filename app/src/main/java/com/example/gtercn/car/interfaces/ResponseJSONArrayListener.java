package com.example.gtercn.car.interfaces;

import org.json.JSONArray;

/**
 * Created by Administrator on 2016-5-23.
 * 网络访问回调 JSONArray 数据接口
 */
public interface ResponseJSONArrayListener extends ResponseListener {
    /**
     * 成功返回JsonArray数据的处理
     *
     * @param type
     */
    void onSuccessResponse(JSONArray response, int type);

}
