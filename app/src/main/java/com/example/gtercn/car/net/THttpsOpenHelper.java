package com.example.gtercn.car.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gtercn.car.interfaces.ResponseJSONArrayListener;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.utils.ContextService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Administrator on 2016-5-18.
 *  Https 网络访问操作类 主要针对REST ful 服务接口速度快
 * 1、获取网络数据
 */
public class THttpsOpenHelper {
    private static final String TAG = THttpsOpenHelper.class.getSimpleName();
    private static THttpsOpenHelper instance;
    private RequestQueue mRequestQueue;

    private static final int TIMEOUT = 10 * 1000;

    private static final int MAX_RETRIES = 1;

    private THttpsOpenHelper() {
        initVolley();
    }

    public static THttpsOpenHelper newInstance(){
        if(instance == null){
            instance = new THttpsOpenHelper();
        }
        return instance;
    }

    /**
     * init Volley method.
     *
     */
    private  void initVolley(){
        HurlStack hurlStack = new HurlStack(){
            @Override
            protected HttpsURLConnection createConnection(URL url) throws IOException {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                try {
                    httpsURLConnection.setSSLSocketFactory(getSocketFactoryCertificate());
                    httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return httpsURLConnection;
            }
        };
        mRequestQueue = Volley.newRequestQueue(ContextService.getInstance().getContext(), hurlStack);
    }

    /**
     *
     * @return
     */
    private SSLSocketFactory getSocketFactoryCertificate(){
        try {
            TrustManager[] trustManagers = new TrustManager[]{new HttpsTrustManager()};
            SSLContext ssLContext = SSLContext.getInstance("TLS");
            ssLContext.init(null, trustManagers, new SecureRandom());
            return ssLContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }

    // Let's assume your server app is hosting inside a server machine
    // which has a server certificate in which "Issued to" is "localhost",for example.
    // Then, inside verify method you can verify "localhost".
    // If not, you can temporarily return true
    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //return true; // verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return true;//hv.verify("localhost", session);
            }
        };
    }

    /**
     * 获取数据请求队列
     * @return
     */
    public RequestQueue getRequestQueue(){
        if (mRequestQueue != null)
            return mRequestQueue;
        else
            return null;
    }

    /**
     * Volley headers,配置Cookies
     * @return
     */
    public Map<String, String> getVolleyHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("Charset", "UTF-8");
        map.put("Content-Type", "application/json");
//        map.put("Content-Type", "application/x-www-form-urlencoded");
//        map.put("api-version","1.0");
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

    public Map<String, String> getVolleyHeadersPay() {
        Map<String, String> map = new HashMap<>();
        map.put("Charset", "UTF-8");
//        map.put("Content-Type", "application/json");
        map.put("Content-Type", "application/x-www-form-urlencoded");
//        map.put("api-version","1.0");
        return map;
    }

    /**
     * get method, request data of String from net.
     *
     * @param url url address
     * @param handler callback interface
     * @param type type of callback param
     * @return Object,this is used to cancel task
     */
    public Object requestGet(String url, final ResponseStringListener handler,
                             final int type, final String tag){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        handler.onSuccessResponse(response,type);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error,type);
                    }
        }){
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
     * {@link #requestGet(String, ResponseStringListener, int, String)}
     * @param url
     * @param map
     * @param handler
     * @param type
     * @return
     */
    public Object requestPost(final String url, final Map map,
                              final ResponseStringListener handler,
                              final int type, final String tag){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        handler.onSuccessResponse(response,type);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error,type);
                    }
                }){

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
    public Object requestJsonObjectGet(String url, final ResponseJSONObjectListener handler,
                                       final int type, final String tag){
        JsonObjectRequest stringRequest = new JsonObjectRequest (Request.Method.GET,url,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccessResponse(response,type);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error,type);
                    }
                }){
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


    public Object requestPayPost(final String url, final String body,
                                 final ResponseJSONObjectListener handler,
                                 final int type, final String tag) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, body,
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
                return getVolleyHeadersJson();
            }

        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }

    /**
     * post method, request data of JSONObject from net.
     * {@link #requestGet(String, ResponseStringListener, int, String)}
     * @param url
     * @param jsonObject
     * @param handler
     * @param type
     * @return
     */

    public Object requestJsonObjectPost(final String url, final JSONObject jsonObject,
                                        final ResponseJSONObjectListener handler,
                                        final int type, final String tag){
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccessResponse(response,type);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error,type);
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getVolleyHeaders();
            }

//            @Override
//            protected JsonObject<String, String> getParams() throws AuthFailureError {
//                return map;
//            }
        };
        stringRequest.setTag(tag);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return mRequestQueue.add(stringRequest);
    }


    /**
     * 订单支付信息调用接口
     *
     * @param url
     * @param map
     * @param handler
     * @param type
     * @param tag
     * @return
     */
    public Object requestJsonObjectPostPay(final String url, final Map map,
                                           final ResponseJSONObjectListener handler,
                                           final int type, final String tag) {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,
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
                return getVolleyHeadersPay();
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
     * get method, request data of JSONArray from net.
     * {@link #requestGet(String, ResponseStringListener, int, String)}
     * @param url
     * @param handler
     * @param type
     * @return
     */
    public Object requestJsonArrayGet(String url, final ResponseJSONArrayListener handler,
                                      final int type, final String tag){
        JsonArrayRequest stringRequest = new JsonArrayRequest (Request.Method.GET,url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        handler.onSuccessResponse(response,type);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error,type);
                    }
                }){
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
     * post method, request data of JSONArray from net.
     * params {@link #requestGet(String, ResponseStringListener, int, String)}
     * @param url
     * @param map
     * @param handler
     * @param type
     * @return
     */
    public Object requestJsonArrayPost(final String url, final Map map,
                                       final ResponseJSONArrayListener handler,
                                       final int type, final String tag) {
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.POST,url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        handler.onSuccessResponse(response,type);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onErrorResponse(error,type);
                    }
                }){

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
     * formdata格式数据:参数为map类型;
     * @param url

     * @param
     * @param handler
     * @param type
     * @param tag
     * @return
     */
    public Object requestFormDataPost(final String url, final Map bodyMap, final Map fileMap,
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
