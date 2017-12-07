package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.utils.TAppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-3-9.
 *  广告详情
 */

public class AdvertisementActivity extends BaseActivity {
    private static final String TAG = "AdvertisementActivity";

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @BindView(R.id.adert_webView)
    WebView mWebView;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        ButterKnife.bind(this);
        mTitle.setText("信息详情");

        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        url = getIntent().getStringExtra("url");
        if (url != null) {
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
        }
        if(!TextUtils.isEmpty(url)){
            mWebView.getSettings().setJavaScriptEnabled(true);
            //设置是否支持缩放
            mWebView.getSettings().setSupportZoom(true);
            //取消滚动条
            mWebView.setVerticalScrollBarEnabled(false);
            mWebView.loadUrl(url);

        }else {
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }
}
