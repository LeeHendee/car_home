package com.example.gtercn.car.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.FourViewPagerAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.FourServeDetailBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseCallbackHandler;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.ShareUtil;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.widget.PopPhoneMenu;
import com.google.gson.Gson;
import com.umeng.socialize.media.UMImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FourServeDetailActivity extends BaseActivity implements View.OnClickListener, ResponseCallbackHandler, ResponseJSONObjectListener {
    private static final String TAG = "FourServeDetailActivity";
    private BDLocation mBDLocation = AppLocation.newInstance().getBDLocation();
    private static final int FAVORADD = 32400502;
    private static final int FAVORDEL = 4447110;
    private static final int FOURDETAIL = 9346116;
    private static int mPosition = 0;
    protected float density = 1.5f;
    private View mPhoneRelativeLayout;
    private View mRelativeLayout;
    private PopPhoneMenu mPopMenu;
    private TextView mToolBarTextView;
    private ImageView mADImageview;
    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mDetailTextView;
    private ImageView mWashTextView;
    private ImageView mRepairTextView;
    private ImageView mMaintainTextView;
    private ImageView mTyreTextView;
    private TextView mDistanceTextView;
    private ImageView mFavorImageView;
    private ImageView mShareImg;
    private TextView mProductTextView;

    private List<String> mAdList;
    private List<ImageView> mDotViews;
    private ViewPager mViewPager;
    private FourViewPagerAdapter mViewPagerAdapter;
    private ScheduledExecutorService mTimer;
    private Future mFuture;
    private static final int TIME = 2000;
    private FourServeDetailBean fourServeDetailBean;
    private User mUser;
    private CarApplication mCarApplication;
    private RelativeLayout mNavigationKey;
    public static List<Activity> activityList = new LinkedList<>();
    private static final String APP_FOLDER_NAME = "BDNavigationActivity";
    private String mSDCardPath = null;

    private final static String authBaseArr[] =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

    private final static String authComArr[] = {Manifest.permission.READ_PHONE_STATE};

    private final static int authBaseRequestCode = 1;

    private final static int authComRequestCode = 2;

    private boolean hasInitSuccess = false;

    private boolean hasRequestComAuth = false;
    private String shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_repairing_detail);
        mCarApplication = (CarApplication) getApplication();
        mRepairTextView = (ImageView) findViewById(R.id.repairing_detail_activity_repair);
        mADImageview = (ImageView) findViewById(R.id.repairing_detail_activity_vp_imageview);
        mWashTextView = (ImageView) findViewById(R.id.repairing_detail_activity_washcar);
        mMaintainTextView = (ImageView) findViewById(R.id.repairing_detail_activity_maintain);
        mTyreTextView = (ImageView) findViewById(R.id.repairing_detail_activity_tyre);
        mFavorImageView = (ImageView) findViewById(R.id.repairing_detail_activity_collect_imageview);
        mNavigationKey = (RelativeLayout) findViewById(R.id.one_key_navigation_rl);
        mShareImg = (ImageView) findViewById(R.id.share_img);
        mProductTextView = (TextView) findViewById(R.id.repairing_detail_activity_product_description);
        mFavorImageView.setOnClickListener(this);
        mShareImg.setOnClickListener(this);

        mToolBarTextView = (TextView) findViewById(R.id.repairing_detail_activity_callback_textview);
        mPhoneRelativeLayout = findViewById(R.id.repairing_detail_activity_phone_relativelayout);
        mRelativeLayout = findViewById(R.id.repairing_detail_activity_relativelayout);
        mViewPager = (ViewPager) findViewById(R.id.repairing_detail_activity_vp);
        mTimer = Executors.newSingleThreadScheduledExecutor();

        mPhoneRelativeLayout.setOnClickListener(this);
        mToolBarTextView.setOnClickListener(this);

        mAddressTextView = (TextView) findViewById(R.id.repairing_detail_activity_address_textview);
        mNameTextView = (TextView) findViewById(R.id.repairing_detail_activity_name_textview);
        mDetailTextView = (TextView) findViewById(R.id.repairing_detail_activity_detail_textview);
        mDistanceTextView = (TextView) findViewById(R.id.repairing_detail_activity_distance);
        Bundle bundle = getIntent().getExtras();
        shop_id = bundle.getString("shop_id");
        mNavigationKey.setOnClickListener(this);
        if (initDirs()) {
            initNavi();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = mCarApplication.getUser();
        getData();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFuture != null) {
            mFuture.cancel(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.shutdownNow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 判断服务项目
     */
    private void isService() {
        if (fourServeDetailBean.getResult().getRepair_service() != null) {
            if (fourServeDetailBean.getResult().getRepair_service().equals("5100")) {
                mRepairTextView.setSelected(true);
            } else {
                mRepairTextView.setSelected(false);
            }
        }
        if (fourServeDetailBean.getResult().getClean_service() != null) {
            if (fourServeDetailBean.getResult().getClean_service().equals("4100")) {
                mWashTextView.setSelected(true);
            } else {
                mWashTextView.setSelected(false);
            }
        }
        if (fourServeDetailBean.getResult().getTyre_service() != null) {
            if (fourServeDetailBean.getResult().getTyre_service().equals("7100")) {
                mTyreTextView.setSelected(true);
            } else {
                mTyreTextView.setSelected(false);

            }
        }
        if (fourServeDetailBean.getResult().getMaintain_service() != null) {
            if (fourServeDetailBean.getResult().getMaintain_service().equals("6100")) {
                mMaintainTextView.setSelected(true);
            } else {
                mMaintainTextView.setSelected(false);
            }
        }
    }

    /**
     * 数据的加载
     * @param
     */
    private void getData() {
        THttpOpenHelper tHttpOpenHelper = THttpOpenHelper.newInstance();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("shop_id", shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mUser != null) {
            String url = ApiManager.URL_FOUR_SERVE_DETAIL + "?token=" + mUser.getResult().getToken();
            tHttpOpenHelper.requestJsonObjectPost(url, jsonObject, this, FOURDETAIL, TAG);
        } else {
            tHttpOpenHelper.requestJsonObjectPost(ApiManager.URL_FOUR_SERVE_DETAIL, jsonObject, this, FOURDETAIL, TAG);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repairing_detail_activity_phone_relativelayout:
                mPopMenu.showAtLocation(mRelativeLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.repairing_detail_activity_callback_textview:
                finish();
                break;
            case R.id.repairing_detail_activity_collect_imageview:

                if (mFavorImageView.isSelected() == false) {
                    String token = null;
                    if (mCarApplication.getUser() != null) {
                        token = mCarApplication.getUser().getResult().getToken();
                        String sign = MD5.getSign(ApiManager.URL_FAVORADD, mUser);
                        String t =  MD5.gettimes();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id", shop_id);
                            jsonObject.put("favor_type", "7");
                            String url = ApiManager.URL_FAVORADD + "?token=" + token + "&t=" + t + "&sign=" + sign;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, FAVORADD, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastMsg(getString(R.string.null_login));
                        Intent intent = new Intent(FourServeDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    String token = null;
                    if (mCarApplication.getUser() != null) {
                        token = mCarApplication.getUser().getResult().getToken();
                        String sign = MD5.getSign(ApiManager.URL_FAVORDEL, mUser);
                        String t =  MD5.gettimes();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("favor_id", shop_id);
                            jsonObject.put("favor_type", "7");
                            String url = ApiManager.URL_FAVORDEL + "?token=" + token + "&t=" + t + "&sign=" + sign;
                            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, FAVORDEL, TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToastMsg(getString(R.string.null_login));
                        Intent intent = new Intent(FourServeDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                break;
            case R.id.one_key_navigation_rl:
                if (BaiduNaviManager.isNaviInited()) {
                    routeplanToNavi();
                }
                break;
            case R.id.share_img:
                UMImage image;
                String name;
                String address;
                if (fourServeDetailBean.getResult().getShop_pic_url() != null) {
                    String imgUrl = fourServeDetailBean.getResult().getShop_pic_url();
                    if(imgUrl.contains("\\")){
                        imgUrl = TAppUtils.formatUrl(imgUrl);
                    }
                    image = new UMImage(this, imgUrl);
                }else {
                    image = new UMImage(this, R.drawable.ic_launcher);
                }

                if(TextUtils.isEmpty(fourServeDetailBean.getResult().getShop_name())){
                    name = address = "顺驾天下";
                }else {
                    name = fourServeDetailBean.getResult().getShop_name();
                    address = fourServeDetailBean.getResult().getDetail_address();
                }

                ShareUtil.GeneralizeShare(this, name, address, image);
                break;
        }
    }

    @Override
    public void onSuccessResponse(String response, int type) {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        switch (type) {
            case FAVORADD:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mCarApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mFavorImageView.setSelected(true);
                                showToastMsg(message);
                            } else {
                                closeDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                }
                break;
            case FAVORDEL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mCarApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mFavorImageView.setSelected(false);
                                showToastMsg(message);
                            } else {
                                closeDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                }
                break;
            case FOURDETAIL:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mCarApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                fourServeDetailBean = gson.fromJson(response.toString(), FourServeDetailBean.class);

                                mAdList = new ArrayList<>();
                                mDotViews = new ArrayList<>();
                                mAdList = fourServeDetailBean.getResult().getDisplay_pic_list();
                                if (mAdList != null) {
                                    mADImageview.setVisibility(View.GONE);
                                    initViewPager();
                                    addPictures();
                                    showPicture();
                                }else{
                                    mADImageview.setVisibility(View.VISIBLE);
                                    mADImageview.setBackgroundResource(R.drawable.icon_default);
                                }

                                isService();
                                mAddressTextView.setText(fourServeDetailBean.getResult().getCity() + fourServeDetailBean.getResult().getDetail_address());
                                mNameTextView.setText(fourServeDetailBean.getResult().getShop_name());
                                mDetailTextView.setText(fourServeDetailBean.getResult().getShop_description());
                                String longitude = fourServeDetailBean.getResult().getLongitude();
                                String latitude = fourServeDetailBean.getResult().getLatitude();
                                LatLng p = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                                LatLng currentPoint = null;
                                if (mBDLocation != null) {
                                    double currentLatitude = mBDLocation.getLatitude();
                                    double currentLongitude = mBDLocation.getLongitude();
                                    currentPoint = new LatLng(currentLatitude, currentLongitude);
                                } else {
                                    String appLatitude = SharedPreferenceHelper.getValue(ApiManager.LAT);
                                    String appLongitude = SharedPreferenceHelper.getValue(ApiManager.LNG);
                                    currentPoint = new LatLng(Double.valueOf(appLatitude), Double.valueOf(appLongitude));
                                }
                                double distance = DistanceUtil.getDistance(currentPoint, p);
                                double f1 = Math.floor(distance);
                                if (f1 < 1 * 1000) {
                                    mDistanceTextView.setText((new DecimalFormat("0").format(f1) + "m"));
                                } else {
                                    mDistanceTextView.setText(new DecimalFormat("0.00").format(f1 / 1000) + "km");
                                }
                                mProductTextView.setText(fourServeDetailBean.getResult().getProduct_description());

                                if (fourServeDetailBean.getResult().getTel_num_list() != null) {
                                    mPopMenu = new PopPhoneMenu(mContext.getApplicationContext(), fourServeDetailBean.getResult().getTel_num_list());
                                }
                                int is_favored = fourServeDetailBean.getResult().getIs_favored();
                                if (is_favored == 0) {
                                    mFavorImageView.setSelected(false);
                                } else {
                                    mFavorImageView.setSelected(true);
                                }
                            } else {
                                closeDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                }
                break;
        }
    }

    @Override
    public void onSuccessResponse(JSONArray response, int type) {

    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
    }

    /**
     * 广告条方法逻辑
     */
    private void addPictures() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.repairing_detail_activity_dots);
        linearLayout.removeAllViews();
        for (int k = 0; k < mAdList.size(); k++) {
            ImageView imageViewDot = new ImageView(this);
            //设置圆点的样式和布局
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = (int) (5 * density);
            layoutParams.rightMargin = (int) (5 * density);
            imageViewDot.setLayoutParams(layoutParams);
            if (k == 0) {
                imageViewDot.setImageResource(R.drawable.circular_point_shape_selected);
            } else {
                imageViewDot.setImageResource(R.drawable.circular_point_shape);
            }
            mDotViews.add(imageViewDot);
            linearLayout.addView(imageViewDot);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotViews.size(); i++) {
                    if (i != position) {
                        mDotViews.get(i).setImageResource(R.drawable.circular_point_shape);
                    } else {
                        mDotViews.get(i).setImageResource(R.drawable.circular_point_shape_selected);
                        mPosition = position;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void initViewPager() {
        FragmentManager fm = getSupportFragmentManager();
        mViewPagerAdapter = new FourViewPagerAdapter(mAdList, this, fm);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    private void showPicture() {
        mFuture = mTimer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(0);
                }
            }
        }, 0, TIME, TimeUnit.MILLISECONDS);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mViewPager.setCurrentItem(mPosition, false);
                    if (mPosition > mAdList.size() - 1) {
                        mViewPager.setCurrentItem(0, false);
                        mPosition = 0;
                    }
                    mPosition++;
                    break;
            }
        }
    };

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    String authinfo = null;
    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
        }

        @Override
        public void playStart() {
        }
    };

    public void showToastMsg(final String msg) {
        FourServeDetailActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(FourServeDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean hasBasePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCompletePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void initNavi() {
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {

            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                FourServeDetailActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                    }
                });
            }

            public void initSuccess() {
                hasInitSuccess = true;
                initSetting();

            }

            public void initStart() {
            }

            public void initFailed() {
            }
        }, mTTSCallback, ttsHandler, ttsPlayStateListener);
    }


    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void routeplanToNavi() {
        if (!hasInitSuccess) {
            return;
        }
        // 权限申请
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasCompletePhoneAuth()) {
                if (!hasRequestComAuth) {
                    hasRequestComAuth = true;
                    this.requestPermissions(authComArr, authComRequestCode);
                    return;
                } else {
                    Toast.makeText(FourServeDetailActivity.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;

        if (mBDLocation == null) {
            showSnack(mProductTextView, "定位失败,请稍后重试!");
            return;
        }

        double descLongitude = Double.valueOf(fourServeDetailBean.getResult().getLongitude());
        double descLatitude = Double.valueOf(fourServeDetailBean.getResult().getLatitude());
        sNode = new BNRoutePlanNode(mBDLocation.getLongitude(), mBDLocation.getLatitude(), "当前位置", null, BNRoutePlanNode.CoordinateType.BD09LL);
        eNode = new BNRoutePlanNode(descLongitude, descLatitude, fourServeDetailBean.getResult().getShop_name(), null, BNRoutePlanNode.CoordinateType.BD09LL);
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode, eNode));
        }
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mStartNode = null;
        private BNRoutePlanNode mEndNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode sNode, BNRoutePlanNode eNode) {
            mStartNode = sNode;
            mEndNode = eNode;

        }

        @Override
        public void onJumpToNavigator() {

            for (Activity ac : activityList) {
                if (ac.getClass().getName().endsWith("BDNaviGuideActivity")) {
                    return;
                }
            }
            Intent intent = new Intent(FourServeDetailActivity.this, BDNaviGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("startNode", mStartNode);
            bundle.putSerializable("endNote", mEndNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(FourServeDetailActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSetting() {
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
        }

        @Override
        public void resumeTTS() {
        }

        @Override
        public void releaseTTSPlayer() {
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            return 1;
        }

        @Override
        public void phoneHangUp() {
        }

        @Override
        public void phoneCalling() {
        }

        @Override
        public void pauseTTS() {
        }

        @Override
        public void initTTSPlayer() {
        }

        @Override
        public int getTTSState() {
            return 1;
        }
    };


    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(FourServeDetailActivity.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        } else if (requestCode == authComRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                }
            }
            routeplanToNavi();
        }
    }
}
