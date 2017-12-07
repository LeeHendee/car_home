package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.utils.Constants;
import com.example.gtercn.car.utils.SharedPreferenceHelper;

/**
 * author : LiHang.
 * data : 2017/2/8.
 * description:
 *    1.需要显示的功能,延时3秒;
 *    2.延时过后判断是否是首次加载app,如果是首次,则跳转到GuideActivity,如果不是首次,跳转到MainActivity;
 */
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int DELAY_TIME = 2000;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //延迟2s开启app
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, DELAY_TIME);
    }

    private void startApp() {
        //配置默认值 ： 阜新市
        String cityCode = SharedPreferenceHelper.getValue(ApiManager.CITY_CODE);
        if (TextUtils.isEmpty(cityCode)) {
            SharedPreferenceHelper.setValue(ApiManager.CITY_CODE, "210900");
            SharedPreferenceHelper.setValue(ApiManager.LAT, "42.027365");
            SharedPreferenceHelper.setValue(ApiManager.LNG, "121.675898");
        }

        Intent startApp;
        if (isFirst()) {
            startApp = new Intent(this, MainActivity.class);
        } else {
            startApp = new Intent(this, GuideActivity.class);
        }
        startActivity(startApp);
        finish();
    }

    private boolean isFirst() {
        return SharedPreferenceHelper.getBoolean(Constants.APP_LAUNCH_FLAG);
    }
}
