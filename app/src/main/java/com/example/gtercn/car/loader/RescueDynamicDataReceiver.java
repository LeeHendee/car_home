package com.example.gtercn.car.loader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2017-3-3.
 * 紧急救援数据动态更新，当坐标点变化时 通知更新数据
 */

public class RescueDynamicDataReceiver extends BroadcastReceiver {
    public static final String RESCUE_ACTION = "com.example.gtercn.car.rescue.ACTION";
    public RescueListCursorLoader mLoader;

    public RescueDynamicDataReceiver(RescueListCursorLoader loader){
        mLoader = loader;
        IntentFilter filter = new IntentFilter(RESCUE_ACTION);
        mLoader.getContext().registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mLoader.onContentChanged();
    }
}
