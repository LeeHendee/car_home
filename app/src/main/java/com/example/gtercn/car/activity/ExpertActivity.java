package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.fragment.ExpertCircleFragment;
import com.example.gtercn.car.fragment.ExpertQuestionFragment;
import com.example.gtercn.car.fragment.ExpertTopFragment;
import com.example.gtercn.car.utils.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * 达人页面
 */
public class ExpertActivity extends BaseActivity {

    private static final String TAG = "ExpertActivity";

    private CommonTabLayout mCommonTabLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);
        mCommonTabLayout = (CommonTabLayout) findViewById(R.id.expert_tablayout);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("达人");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getFragments();
        mCommonTabLayout.setCurrentTab(0);
    }

    private void initData() {

    }

    private void getFragments() {
        ExpertTopFragment mNoticeFragment = new ExpertTopFragment();
        mFragments.add(mNoticeFragment);
        mTabEntities.add(new TabEntity(getString(R.string.expert_notice)));
        ExpertCircleFragment mCircleFragment = new ExpertCircleFragment();
        mFragments.add(mCircleFragment);
        mTabEntities.add(new TabEntity(getString(R.string.expert_circle)));
        ExpertQuestionFragment mQuestionFragment = new ExpertQuestionFragment();
        mFragments.add(mQuestionFragment);
        mTabEntities.add(new TabEntity(getString(R.string.expert_question)));
        mCommonTabLayout.setTabData(mTabEntities, ExpertActivity.this, R.id.expert_framelayout, mFragments);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (intent != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment;
            switch (intent.getIntExtra("flag", 0)) {
                case 1:
                    mCommonTabLayout.setCurrentTab(0);
                    break;
                case 2:
                    fragment = mFragments.get(1);
                    ft.show(fragment);
                    ft.commitAllowingStateLoss();
                    break;
                case 3:
                    fragment = mFragments.get(2);
                    ft.show(fragment);
                    ft.commitAllowingStateLoss();
                    break;
            }
        }
        super.onNewIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

}
