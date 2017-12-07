package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.MainViewPagerAdapter;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.fragment.FindFragment;
import com.example.gtercn.car.fragment.HomeFragment;
import com.example.gtercn.car.fragment.StoreFragment;
import com.example.gtercn.car.service.AppService;
import com.example.gtercn.car.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private List<Fragment> mFragments;
    private MainViewPagerAdapter mAdapter;
    public User mUser;
    private ViewPager mViewPager;
    private TabLayout mTab;
    private long mQuitTime = 1l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.main_vp);
        mTab = (TabLayout) findViewById(R.id.main_tab);
        mTab.setTabTextColors(R.color.text_title_color, R.color.blue2);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (!hasPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
             requestPermissions(Constants.LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        startAppService();

        initView();

        initData();
    }

    @Override
    protected void doLocation() {
        super.doLocation();
    }

    public void initView() {

    }

    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new FindFragment());
        mFragments.add(new StoreFragment());
        FragmentManager fm = getSupportFragmentManager();
        mAdapter = new MainViewPagerAdapter(fm, mFragments, this);
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTab.getTabCount(); i++) {
            TabLayout.Tab tab = mTab.getTabAt(i);
            tab.setCustomView(mAdapter.getView(i));
        }
        mTab.getTabAt(1).select();
        mTab.getTabAt(0).select();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    private void startAppService() {
        Intent intent = new Intent(this, AppService.class);
        startService(intent);
    }

    private void stopAppService() {
        Intent intent = new Intent(this, AppService.class);
        stopService(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mQuitTime > 2000) {
                showToastMsg(R.string.click_exit_app);
                mQuitTime = System.currentTimeMillis();
            } else {
//                AppManager.getInstance().finishAllActivity();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        if (!hasPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            requestPermissions(Constants.LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopAppService();
        super.onDestroy();
    }
}
