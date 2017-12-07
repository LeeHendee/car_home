package com.example.gtercn.car.interfaces;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016-5-23.
 * 网络访问回调 JSONObject 数据接口
 */
public interface ResponseJSONObjectListener extends ResponseListener {
    /**
     * 成功返回JsonObject数据的处理
     *
     * @param type
     */
    void onSuccessResponse(JSONObject response, int type);


}
