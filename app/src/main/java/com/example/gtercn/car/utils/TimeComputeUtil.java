package com.example.gtercn.car.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author : LiHang.
 * data : 2017/1/23.
 * description:
 * 计算时间差工具类;
 */
public class TimeComputeUtil {

    private static final String TAG = "TimeComputeUtil";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String getInterval(String date) {

        Date clientDate = new Date(System.currentTimeMillis());

        try {
            Date oldDate = sdf.parse(date);
            String nowDate = sdf.format(clientDate);
            Date newDate = sdf.parse(nowDate);
            long time_1 = oldDate.getTime();
            long time_2 = newDate.getTime();
            long interval = (time_2 - time_1) / 1000 / 60;
            if (interval <= 60) {
                return String.valueOf(interval) + "分钟前";
            } else if (interval > 60 && interval <= 1440) {
                return String.valueOf((int) (Math.ceil(interval / 60 + 0.5))) + "小时前";
            } else if (interval > 1440) {
                return nowDate.substring(0, 10);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "has an exception";
    }
}
