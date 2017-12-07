package com.example.gtercn.car.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gtercn.car.api.ApiManager;


/**
 * @author Administrator
 * @date 2016-5-18
 * SharePreference
 *
 */
public class SharedPreferenceHelper {

    public static void setValue(String key, String value){
        SharedPreferences guide = ContextService
                .getInstance()
                .getContext()
                .getSharedPreferences(ApiManager.SHARE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = guide.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getValue(String key){
        SharedPreferences guide = ContextService
                .getInstance()
                .getContext()
                .getSharedPreferences(ApiManager.SHARE_NAME, Context.MODE_PRIVATE);

        return guide.getString(key, "");
    }

    public static int getIntValue(String key){
        SharedPreferences guide = ContextService
                .getInstance()
                .getContext()
                .getSharedPreferences(ApiManager.SHARE_NAME, 0);
        return guide.getInt(key, 0);
    }

    public static void setValue(Context context, String key, String value){
        SharedPreferences guide = context.getSharedPreferences(
                ApiManager.SHARE_NAME, 0);
        SharedPreferences.Editor editor = guide.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setValue(Context context, String key, int value){
        SharedPreferences guide = context.getSharedPreferences(
                ApiManager.SHARE_NAME, 0);
        SharedPreferences.Editor editor = guide.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static String getValue(Context context, String key){
        SharedPreferences guide = context.getSharedPreferences(
                ApiManager.SHARE_NAME, 0);
        return guide.getString(key, "");
    }



    public static void setBoolean(String key, boolean value){
        SharedPreferences guide = ContextService
                .getInstance()
                .getContext()
                .getSharedPreferences(ApiManager.SHARE_NAME, 0);
        SharedPreferences.Editor editor = guide.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key){
        SharedPreferences guide = ContextService
                .getInstance()
                .getContext()
                .getSharedPreferences(ApiManager.SHARE_NAME, 0);
        return guide.getBoolean(key, false);
    }

    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences guide = context.getSharedPreferences(
                ApiManager.SHARE_NAME, 0);
        SharedPreferences.Editor editor = guide.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key){
        SharedPreferences guide = context.getSharedPreferences(
                ApiManager.SHARE_NAME, 0);
        return guide.getBoolean(key, false);
    }
    //清除记录
    public static void clearAll(Context context) {
        SharedPreferences guide = context.getSharedPreferences(
                ApiManager.SHARE_NAME, 0);
        SharedPreferences.Editor editor = guide.edit();
        editor.clear();
        editor.apply();
    }


}
