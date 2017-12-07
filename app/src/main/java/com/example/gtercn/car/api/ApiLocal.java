package com.example.gtercn.car.api;


import android.content.res.AssetManager;

import com.example.gtercn.car.interfaces.ResponseCallbackHandler;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.utils.ContextService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Administrator on 2016-5-24.
 * 仿真数据接口类，数据源在本地，解析加载
 */
public class ApiLocal {
    private static final String TAG = "ApiLocal";

    /**
     * 测试登陆的返回信息:
     * 数据存在asset文件夹的login_test文件中:
     *
     * @param map
     * @param handler
     * @param type
     * @param tag
     */
    public static void login(Map<String, String> map, ResponseCallbackHandler handler, int type, String tag) {

        AssetManager assetManager = ContextService.getInstance().getContext().getAssets();
        try {
            InputStream is = assetManager.open("login_test");

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];

            int line = 0;

            while ((line = is.read(buffer)) != -1) {

                os.write(buffer, 0, line);
            }

            String str = os.toString();

            handler.onSuccessResponse(str, type);
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void SelfDriving(ResponseCallbackHandler handler, int type, String tag) {
        /**
         * 测试返回信息，正式删除
         *自驾游
         */
        try {
            AssetManager assetManager = ContextService.getInstance().getContext().getAssets();
            InputStream is = assetManager.open("self_travel");
            StringBuilder sb = new StringBuilder();
            final char[] buffer = new char[1024];
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            int line = 0;
            while ((line = isr.read(buffer)) != -1) {
                sb.append(buffer, 0, line);
            }
            String str = sb.toString();
            handler.onSuccessResponse(str, type);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // login_test end
    }

    public static void CarWash(ResponseJSONObjectListener handler, int type, String tag) {

    }

    public static void Promotion(ResponseCallbackHandler handler, int type, String tag) {
        /**
         * 测试返回信息，正式删除
         *促销
         */
        try {
            AssetManager assetManager = ContextService.getInstance().getContext().getAssets();
            InputStream is = assetManager.open("promotion_data");
            StringBuilder sb = new StringBuilder();
            final char[] buffer = new char[1024];
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            int line = 0;
            while ((line = isr.read(buffer)) != -1) {
                sb.append(buffer, 0, line);
            }
            String str = sb.toString();
            handler.onSuccessResponse(str, type);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // login_test end
    }


    public static void HomeAd(ResponseCallbackHandler handler, int type, String tag) {
        /**
         * 测试返回信息，正式删除
         *广告
         */
        try {
            AssetManager assetManager = ContextService.getInstance().getContext().getAssets();
            InputStream is = assetManager.open("message_data");
            StringBuilder sb = new StringBuilder();
            final char[] buffer = new char[1024];
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            int line = 0;
            while ((line = isr.read(buffer)) != -1) {
                sb.append(buffer, 0, line);
            }
            String str = sb.toString();
            handler.onSuccessResponse(str, type);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // login_test end
    }

    public static void Message(ResponseCallbackHandler handler, int type, String tag) {
        /**
         * 测试返回信息，正式删除
         *咨询列表
         */
        try {
            AssetManager assetManager = ContextService.getInstance().getContext().getAssets();
            InputStream is = assetManager.open("message_data");
            StringBuilder sb = new StringBuilder();
            final char[] buffer = new char[1024];
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            int line = 0;
            while ((line = isr.read(buffer)) != -1) {
                sb.append(buffer, 0, line);
            }
            String str = sb.toString();
            handler.onSuccessResponse(str, type);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // login_test end
    }
}
