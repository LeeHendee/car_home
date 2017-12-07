package com.example.gtercn.car.utils;

import android.text.TextUtils;


import com.example.gtercn.car.api.ApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Administrator on 2016-6-23.
 * 获取服务器时间戳
 */
public class AppTimeStamp {
    private static final String TAG = AppTimeStamp.class.getSimpleName();
    private static AppTimeStamp instance ;
    private static final int TYPE = 1;

    /**
     * 服务器与手机时间差
     */
    private long mIntervalTime;

    private AppTimeStamp(){
        fetchServerTime();
    }

    public synchronized static AppTimeStamp getInstance(){
        if(instance == null){
            instance = new AppTimeStamp();
        }
        return  instance;
    }

    /**
     * 设置时间差
     * @param interval
     */
    public void setServerTimeStamp(long interval){
        mIntervalTime = interval;
    }

    /**
     * 获取当前服务器大约时间
     * @return
     */
    public long getCurrentTimeStamp(){
        long current = System.currentTimeMillis();
        return current + mIntervalTime;
    }

    public String getCurrentTimeStampString(){
        long current = System.currentTimeMillis();
        return  String.valueOf(current + mIntervalTime);
    }

    /**
     * 取服务器时间戳
     */
    private void fetchServerTime(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //取服务器时间戳
                        long serverTime = getServerTime();
                        long currentTime = System.currentTimeMillis();
                        long interval = serverTime - currentTime;
                        setServerTimeStamp(interval);
                    }

                    /**
                     * 具体从服务器取时间方法
                      * @return
                     */
                    private long getServerTime(){
                        //访问网络服务器，获取服务器时间，返回long
                        String response = httpGet(ApiManager.URL_TIMESTAMP + "?system_bj=1",null);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (obj.has("returnCode")) {
                                    String returnCode = obj.getString("returnCode");
                                    if (TextUtils.equals(returnCode, "0")) {
                                        JSONObject sub = obj.getJSONObject("result");
                                        String servertime = sub.getString("server_time");
                                        return Long.valueOf(servertime);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return System.currentTimeMillis();
                            }
                        }
                        return System.currentTimeMillis();
                    }

                    public String httpGet(String httpUrl, HashMap<String, String> map){
                        URL url;
                        BufferedReader read = null;
                        InputStream is = null;
                        HttpURLConnection con = null;
                        try {
                            url = new URL(httpUrl);
                            con = (HttpURLConnection)url.openConnection();
                            con.setConnectTimeout(20000);
                            con.setDoInput(true);
                            con.setRequestMethod("POST");
                            con.setRequestProperty("Accept", "*/*");
                            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            con.setRequestProperty("Connection", "Keep-Alive");
                            con.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
                            con.setRequestProperty("Charset", "UTF-8");
                            con.setRequestProperty("api-version", "1.0");
                            con.setUseCaches(false);
                            con.setInstanceFollowRedirects(false);

                            is = con.getInputStream();
                            read = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                            String line = read.readLine();
                            StringBuilder temp = new StringBuilder();
                            String crlf = System.getProperty("line.separator");
                            while(line != null){
                                temp.append(line);
                                temp.append(crlf);
                                line = read.readLine();
                            }
                            read.close();
                            is.close();
                            con.disconnect();
                            return temp.toString();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }finally{
                            try {
                                if(read != null)
                                    read.close();
                                if(is != null)
                                    is.close();
                                if(con != null)
                                    con.disconnect();

                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    private StringBuffer getParams(HashMap<String, String> map){
                        StringBuffer buf = new StringBuffer();
                        if(map != null)
                            for(String key : map.keySet()) {
                                buf.append(key);
                                buf.append("=");
                                buf.append(map.get(key));
                                buf.append("&");
                            }
                        if(buf.length() > 0)
                            buf.deleteCharAt(buf.length() - 1);
                        return buf;

                    }

                }
        ).start();
    }

}
