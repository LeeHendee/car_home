package com.example.gtercn.car.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016-5-23.
 * 网络访问回调数据接口
 */
public interface ResponseCallbackHandler {

    /**
     *成功返回String数据的处理
     * @param type
     */
    void onSuccessResponse(String response, int type);

    /**
     *成功返回JsonObject数据的处理
     * @param type
     */
    void onSuccessResponse(JSONObject response, int type);

    /**
     *成功返回JsonArray数据的处理
     * @param type
     */
    void onSuccessResponse(JSONArray response, int type);

    /**
     * 访问数据错误时候的处理
     * @param type
     */
    void onErrorResponse(VolleyError error, int type);

}
