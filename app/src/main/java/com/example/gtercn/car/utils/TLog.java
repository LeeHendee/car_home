package com.example.gtercn.car.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016-5-18.
 * 输出logcat控制类
 */
public class TLog {

    public static final String TAG = "TLogTravel";

    /**
     * release false
     * debug true;
     */
    public static boolean isDebug = false;

    /**
     * common debug
     * @param msg
     */
    public static void d(String msg){
        if(isDebug){
            Log.d(TAG,msg);
        }
    }

    /**
     * info level
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg){
        if(isDebug){
            Log.i(tag,msg);
        }
    }

    /**
     * debug level
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg){
        if(isDebug){
            Log.d(tag,msg);
        }
    }

    /**
     * warn level
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg){
        if(isDebug){
            Log.w(tag,msg);
        }
    }

    /**
     * error level
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg){
        if(isDebug){
            Log.e(tag,msg);
        }
    }
}
