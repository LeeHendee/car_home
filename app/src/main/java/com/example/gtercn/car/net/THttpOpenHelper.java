package com.example.gtercn.car.net;

import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.cache.TBitmapCache;
import com.example.gtercn.car.interfaces.ResponseCallbackHandler;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.utils.ContextService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-5-18.
 * 网络访问操作类 主要针对REST ful 服务接口速度快
 * 1、获取网络数据
 * 2、加载网络图片
 */
public class THttpOpenHelper {
    private static final String TAG = THttpOpenHelper.class.getSimpleName();
    private static THttpOpenHelper instance;
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    private static int TIMEOUT = 10 * 1000;

    private static int MAX_RETRIES = 2;

    /**
     * 用户管理
     */
    private static User mUser;

    private THttpOpenHelper() {
    }

    public static THttpOpenHelper newInstance() {
        if (instance == null) {
            instance = new THttpOpenHelper();
            mRequestQueue = Volley.newRequestQueue(ContextService.getInstance().getContext());
            mImageLoader = new ImageLoader(mRequestQueue, new TBitmapCache());
        }
        return instance;
    }

    /**
     * 获取数据请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue != null)
            return mRequestQueue;
        else
            return null;
    }

    /**
     * 设置用户
     *
     * @param user
     */
    public void setUser(User user) {
        mUser = user;
    }

    public void resetUser() {
        mUser = null;
    }

    /**
     * 网络加载图片 以原图大小加载
     *
     * @param imageView
     * @param url
     * @param defaultImageResId
     * @param errorImageResId
     */
    public void setImageBitmap(final ImageView imageView, final String url,
                               int defaultImageResId, final int errorImageResId) {
        ImageLoader.ImageListener listener =
                ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);

        mImageLoader.get(url, listener);
    }

    /**
     * 网络图片加载
     *
     * @param imageView         ImageView控件
     * @param url               图片网络地址
     * @param width             图片宽度
     * @param height            图片高度
     * @param defaultImageResId 缺省图标
     * @param errorImageResId   网络访问错误时加载的图标
     */
    public void setImageBitmap(final ImageView imageView, final String url,
                               int width, int height, int defaultImageResId,
                               final int errorImageResId) {
        ImageLoader.ImageListener listener =
                ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        mImageLoader.get(url, listener, width, height);
    }

    /**
     * 网络图片加载
     *
     * @param imageView
     * @param url
     * @param width
     * @param height
     * @param scaleType         图片缩放类型
     * @param defaultImageResId
     * @param errorImageResId
     */
    public void setImageBitmap(final ImageView imageView, final String url,
                               int width, int height, ImageView.ScaleType scaleType,
                               int defaultImageResId, final int errorImageResId) {
        ImageLoader.ImageListener listener =
                ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        mImageLoader.get(url, listener, width, height, scaleType);
    }

    /**
     * Volley headers,配置Cookies
     *
     * @return
     */

    public Map<String, String> getVolleyHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("Charset", "UTF-8");
        map.put("Content-Type", "application/json");
//        map.put("Content-Type", "application/x-www-form-urlencoded");
        return map;
    }

    /**
     * Volley headers,配置Cookies
     *
     * @return
     */
    public Map<String, String> getVolleyHeadersJson() {
        Map<String, String> map = new HashMap<>();
        map.put("Charset", "UTF-8");
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("Accept", "application/json");
        return map;
    }

    /**
     * get method, request data of String from net.
     *
     * @param url     url address
     * @param handler callback interface
     * @param type    type of callback param
     * @return Object, this is used to cancel task
     */
    public Object requestGet(String url, final ResponseCallbackHandler handler,
                             final int type, final String tag) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handler.onSuccessResponse(response, type);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error, type);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getVolleyHeaders();
            }

        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }

    /**
     * get method, request data of String from net.
     *
     * @param url     url address
     * @param handler callback interface
     * @param type    type of callback param
     * @return Object, this is used to cancel task
     */
    public Object requestPostNo(String url, final ResponseCallbackHandler handler,
                                final int type, final String tag) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handler.onSuccessResponse(response, type);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error, type);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getVolleyHeaders();
            }

        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }

    /**
     * post method, request data of String from net.
     * {@link #requestGet(String, ResponseCallbackHandler, int, String)}
     *
     * @param url
     * @param map
     * @param handler
     * @param type
     * @return
     */
    public Object requestPost(final String url, final Map map,
                              final ResponseCallbackHandler handler,
                              final int type, final String tag) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handler.onSuccessResponse(response, type);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error, type);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getVolleyHeaders();
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }

        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }


    /**
     * get method, request data of JSONObject from net.
     * @param url
     * @param handler
     * @param type
     * @return
     */
    public Object requestJsonObjectGet(String url, final ResponseCallbackHandler handler,
                                       final int type, final String tag) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccessResponse(response, type);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error, type);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getVolleyHeaders();
            }

        };
        stringRequest.setTag(tag);
        return mRequestQueue.add(stringRequest);
    }

    public Object requestJsonObjectGet(String url, final ResponseJSONObjectListener handler,
                                       final int type, final String tag) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccessResponse(response, type);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error, type);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getVolleyHeaders();
            }

        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }

    /**
     *
     * @param url
     * @param jsonObject
     * @param handler
     * @param type
     * @param tag
     * @return
     */
    public Object requestJsonObjectPost(final String url, final JSONObject jsonObject,
                                        final ResponseJSONObjectListener handler,
                                        final int type, final String tag) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccessResponse(response, type);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error, type);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return getVolleyHeaders();
            }

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return map;
//            }
        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }


    /**
     * formdata格式数据:参数为map类型;
     * @param url
     * @param bodyMap
     * @param fileMap
     * @param handler
     * @param type
     * @param tag
     * @return
     */
    public Object requestFormDataFilePost(final String url,  final Map bodyMap, final Map fileMap,
                                      final ResponseStringListener handler,
                                      final int type, final String tag) {
        MultipartFileRequest stringRequest2 = new MultipartFileRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handler.onSuccessResponse(response, type);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error, type);
                    }
                }, bodyMap, fileMap) {
        };

        stringRequest2.setTag(tag);
        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest2);
    }




}
