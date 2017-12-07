package com.example.gtercn.car.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : LiHang.
 * data : 2017/3/3.
 * description:
 * 联系我们
 */
public class ContactUsActivity extends BaseActivity {

    private static final String TAG = "ContactUsActivity";

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @BindView(R.id.contact_us_phone)
    TextView mPhoneTv;

    @BindView(R.id.contact_us_phone_rl)
    RelativeLayout mPhoneRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        mTitle.setText("联系我们");

        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPhoneRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"04183330400"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
