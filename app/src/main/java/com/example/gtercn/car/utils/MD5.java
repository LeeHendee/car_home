package com.example.gtercn.car.utils;


import com.example.gtercn.car.bean.User;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2016-6-23.
 */
public class MD5 {

    private static String TAG = "MD5";

    private static String times;
    /**
     * md5 encrypt
     *
     * @param url
     * @param user
     * @return
     */
    public static String getSign(String url, User user) {
        times =   AppTimeStamp.getInstance().getCurrentTimeStampString();
        String tokens = user.getResult().getToken();
//        String str = url + "?userid=" + user.getResult().getUser_info().getUser_id() + "&token=" + tokens + "&timestamp=" + times;
        String str = url + user.getResult().getUser_info().getUser_id() + tokens + times;
        String cacheKey = "";
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("md5");
            mDigest.update(str.getBytes("utf-8"));
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cacheKey;

    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
     * disk filename.
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes("utf-8"));
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (Exception e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

public static String gettimes(){
    return times;
}

}
