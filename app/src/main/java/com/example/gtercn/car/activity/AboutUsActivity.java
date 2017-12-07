package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : LiHang.
 * data : 2017/2/21.
 * description:
 *    关于我们
 */
public class AboutUsActivity extends BaseActivity {

    private static final String TAG = "AboutUsActivity";

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @BindView(R.id.about_us_vername)
    TextView mVerName;

    @BindView(R.id.contact_us_rl)
    RelativeLayout mContactUs;

    @BindView(R.id.feedback_rl)
    RelativeLayout mFeedBackLayout;

    private CarApplication mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        mTitle.setText("关于我们");
        mApplication = (CarApplication) getApplication();

        mVerName.setText("当前版本 : " + getResources().getString(R.string.app_version));

        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFeedBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, AdviceFeedbackActivity.class);
                startActivity(intent);
            }
        });

        mContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }
}
