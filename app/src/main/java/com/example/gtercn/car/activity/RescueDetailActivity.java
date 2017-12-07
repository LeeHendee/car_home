package com.example.gtercn.car.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.RescueListBean;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MyLocationUtil;
import com.example.gtercn.car.utils.ShareUtil;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.widget.overlay.DrivingRouteOverlay;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/11/16.
 * description:
 * 救援详情
 */
public class RescueDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RescueDetailActivity";

    private BDLocation mBDLocation = AppLocation.newInstance().getBDLocation();

    private Toolbar mBar;

    private ImageView mTel;

    private ImageView mRoundPortrait;

    private TextView mName;

    private TextView mAddress;

    private List<String> phoneList;

    private RescueListBean.ResultBean mBean;

    private MapView mMapView = null;

    private MyLocationUtil mLocationUtil;

    private RoutePlanSearch rps;

    private LatLng route_end;

    private LatLng route_start;

    private CarApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_detail);
        SDKInitializer.initialize(getApplicationContext());
        mApp = (CarApplication) getApplication();
        initView();
        initData();
        mMapView = (MapView) findViewById(R.id.baidu_mapView);
        mLocationUtil = new MyLocationUtil(mMapView, true, mApp);
    }

    private void initView() {
        mBar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
        barTitle.setText("紧急救援");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTel = (ImageView) findViewById(R.id.rescue_detail_phone_iv);
        mTel.setOnClickListener(this);
        mBar.setTitle("");
        mName = (TextView) findViewById(R.id.name);
        mAddress = (TextView) findViewById(R.id.address);
        mRoundPortrait = (ImageView) findViewById(R.id.round_portrait);
        setSupportActionBar(mBar);
        mBar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_share));
    }

    private void initData() {
        phoneList = new ArrayList<>();
        Bundle bundle = getIntent().getBundleExtra("bdl");
        if (bundle == null) {
            return;
        }
        mBean = (RescueListBean.ResultBean) bundle.getSerializable("rescue_bean");
        if (mBean == null) {
            return;
        }
        mName.setText(mBean.getShop_name());
        mAddress.setText(mBean.getCity() + mBean.getDetail_address());
        String url = mBean.getHead_portrait_url();
        if (url != null) {
            if (url.contains("\\")) {
                url = TAppUtils.formatUrl(url);
            }
        }
        THttpOpenHelper.newInstance().setImageBitmap(mRoundPortrait, url,(int)(60*density),(int)(60*density),
                R.drawable.icon_default,
                R.drawable.icon_default);
        if (mBean.getTel_number_list() != null) {
            String[] arr = mBean.getTel_number_list().split(",");
            phoneList = Arrays.asList(arr);
        }
        initRoutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initRoutePath() {
        double end_latitude = Double.valueOf(mBean.getLatitude());
        double end_longitude = Double.valueOf(mBean.getLongitude());
        if (mBDLocation == null) {
            showSnack(mAddress, "定位失败,请返回上一页,手动定位!");
            return;
        }
        double start_latitude = mBDLocation.getLatitude();
        double start_longitude = mBDLocation.getLongitude();
        route_end = new LatLng(end_latitude, end_longitude);
        route_start = new LatLng(start_latitude, start_longitude);
        rps = RoutePlanSearch.newInstance();
        drivingSearch();
        rps.setOnGetRoutePlanResultListener(routePlanResultListener);
    }

    private void drivingSearch() {
        DrivingRoutePlanOption drivingOption = new DrivingRoutePlanOption();
        drivingOption.from(PlanNode.withLocation(route_start));
        drivingOption.to(PlanNode.withLocation(route_end));
        rps.drivingSearch(drivingOption);
    }

    OnGetRoutePlanResultListener routePlanResultListener = new OnGetRoutePlanResultListener() {

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                return;
            }

            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(RescueDetailActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                DrivingRouteLine drivingRouteLine = drivingRouteResult.getRouteLines().get(0);
                if (mMapView == null)
                    return;
                BaiduMap baiduMap = mMapView.getMap();
                MyDrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(drivingRouteLine);
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

    };


    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {
        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public int getLineColor() {
            return Color.RED;
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.myloc);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.myloc);
        }
    }

    @Override
    protected void onStart() {
        mLocationUtil.startLocation();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mLocationUtil.stopLocation();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(rps != null){
            rps.destroy();
            rps = null;
        }
        if(mMapView != null){
            mMapView.onDestroy();
            mMapView = null;
        }

    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.urgent_rescue_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_this_rescue:
                if (mBean != null) {
                    UMImage img = new UMImage(RescueDetailActivity.this, R.drawable.ic_launcher);
                    ShareUtil.GeneralizeShare(RescueDetailActivity.this,
                            mBean.getShop_name(), mBean.getDetail_address(), img);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rescue_detail_phone_iv:
                showPhoneList();
                break;
        }
    }

    private void showPhoneList() {
        if (phoneList == null) {
            return;
        }
        String[] items = new String[phoneList.size()];
        for (int i = 0; i < phoneList.size(); i++) {
            items[i] = phoneList.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择电话号码:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String phoneNo = phoneList.get(which);
                TAppUtils.getPhone(RescueDetailActivity.this, phoneNo);
            }
        });
        builder.create().show();
    }
}
