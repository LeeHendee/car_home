package com.example.gtercn.car.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.AboutUsActivity;
import com.example.gtercn.car.activity.CityPickerActivity;
import com.example.gtercn.car.activity.ExpertActivity;
import com.example.gtercn.car.activity.ForgetPasswordStepOneActivity;
import com.example.gtercn.car.activity.FourServeActivity;
import com.example.gtercn.car.activity.HomeSearchActivity;
import com.example.gtercn.car.activity.LoginActivity;
import com.example.gtercn.car.activity.MyActivityActivity;
import com.example.gtercn.car.activity.MyFavoriteActivity;
import com.example.gtercn.car.activity.MyQuestionActivity;
import com.example.gtercn.car.activity.PersonalInfoActivity;
import com.example.gtercn.car.activity.UrgentRescueActivity;
import com.example.gtercn.car.adapter.ViewPagerAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseFragment;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.CityListBean;
import com.example.gtercn.car.bean.HomeAdBean;
import com.example.gtercn.car.bean.UpdateBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.IAppLocationListener;
import com.example.gtercn.car.interfaces.ICityCodeChangeListener;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.location.AppLocationImpl;
import com.example.gtercn.car.location.CityCodeChangeImpl;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.net.THttpsOpenHelper;
import com.example.gtercn.car.update.UpdateUtils;
import com.example.gtercn.car.utils.ContextService;
import com.example.gtercn.car.utils.GetPath;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.SavePhoto;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.widget.PopCaptureMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements
        View.OnClickListener, ResponseJSONObjectListener, ResponseStringListener, IAppLocationListener,
        ICityCodeChangeListener {
    private static final String TAG = "HomeFragment";
    private static final String CITY_KEY = "city_list_key";
    private static final int CITY_TYPE = 0x01;
    private static final int AD = 16390707;
    private static final int LOGOUT = 66220427;
    private final static int REQUESTCODE_CAMERA = 0x02;
    private final static int REQUESTCODE_GALLERY = 0x03;
    private final static int REQUESTCODE_CROP = 0x04;
    private final static String HEADER_URLSTR = SavePhoto.DIR + "header.jpg";
    private static int mPosition = 0;
    private View mSpecialRelativeLayout;

    private View mExpertRelativeLayout;

    private View mWashRelativeLayout;

    private View mTyreRelativeLayout;

    private View mMaintainRelativeLayout;

    private View mRepairRelativeLayout;

    private View mLogoutRelayout;

    private View mLinearLayout;

    private View mFavoriteLayout;

    private View mQuestionLayout;

    private ProgressBar mProgressBar;

    private TextView mCityLocation;

    private ImageView mPersonal;

    private DrawerLayout mDrawer;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private PopCaptureMenu mPopMenu;
    private Bitmap bmp;
    private String avatarUrl;

    private List<HomeAdBean> mAdList = new ArrayList<>();

    private List<ImageView> mDotViews;
    private ScheduledExecutorService mTimer;
    private Future mFuture;
    private static final int TIME = 3000;

    private RoundedImageView mHeadPortrait;

    private View mChangePwd;
    private View mChangePwdLine;

    private View mAppUpdate;

    private View mLoginLayout;

    private View mAboutUsLayout;

    private View mSearchLayout;

    private View mPersonalInfoLayout;

    private View mMyActivityLayout;

    private String headerPath;

    private View mView;

    private String mLocateCity;

    private User mUser;

    private CarApplication mApplication;

    private TextView mNickname;

    private EditText mSearchEt;

    private boolean isFirst = true;

    private List<CityListBean.ResultBean> mCityList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        mApplication = (CarApplication) getActivity().getApplication();
        mLinearLayout = mView.findViewById(R.id.fragment_home_linearlayout);
        mSpecialRelativeLayout = mView.findViewById(R.id.home_special_relief_relativelayout);
        mExpertRelativeLayout = mView.findViewById(R.id.home_expert_relativelayout);
        mMaintainRelativeLayout =  mView.findViewById(R.id.home_maintain_relativelayout);
        mWashRelativeLayout = mView.findViewById(R.id.home_car_wash_relativelayout);
        mTyreRelativeLayout = mView.findViewById(R.id.home_tyre_relativelayout);
        mRepairRelativeLayout = mView.findViewById(R.id.home_car_repairing_relativelayout);
        mLogoutRelayout = mView.findViewById(R.id.click_logout);
        mNickname = (TextView) mView.findViewById(R.id.user_nickname_tv);
        mSearchLayout = mView.findViewById(R.id.home_search_ll);
        mAppUpdate =  mView.findViewById(R.id.app_update_layout);
        mLoginLayout = mView.findViewById(R.id.login_layout);

        mChangePwd = mView.findViewById(R.id.change_pwd_layout);
        mChangePwdLine = mView.findViewById(R.id.change_pwd_line);

        mAboutUsLayout = mView.findViewById(R.id.about_us_layout);
        mSearchEt = (EditText) mView.findViewById(R.id.search_et);
        mPersonalInfoLayout = mView.findViewById(R.id.personal_info_rl);
        mProgressBar = (ProgressBar)mView.findViewById(R.id.home_ad_progressbar);
        mHeadPortrait = (RoundedImageView) mView.findViewById(R.id.head_portrait);
        mMyActivityLayout = mView.findViewById(R.id.click_my_activity);
        mFavoriteLayout = mView.findViewById(R.id.click_my_favorite);
        mQuestionLayout = mView.findViewById(R.id.click_my_question);

        mFavoriteLayout.setOnClickListener(this);
        mMyActivityLayout.setOnClickListener(this);
        mQuestionLayout.setOnClickListener(this);
        mHeadPortrait.setOnClickListener(this);
        mSearchLayout.setOnClickListener(this);
        mSearchEt.setOnClickListener(this);
        mAboutUsLayout.setOnClickListener(this);
        mPersonalInfoLayout.setOnClickListener(this);

        mViewPager = (ViewPager) mView.findViewById(R.id.home_vp);
        mDrawer = (DrawerLayout) mView.findViewById(R.id.home_drawer);
        mDrawer.addDrawerListener(new CarSimpleDrawerListener());

        mChangePwd.setOnClickListener(this);
        mAppUpdate.setOnClickListener(this);
        mLoginLayout.setOnClickListener(this);
        mSpecialRelativeLayout.setOnClickListener(this);
        mExpertRelativeLayout.setOnClickListener(this);
        mMaintainRelativeLayout.setOnClickListener(this);
        mRepairRelativeLayout.setOnClickListener(this);
        mTyreRelativeLayout.setOnClickListener(this);
        mWashRelativeLayout.setOnClickListener(this);
        mLogoutRelayout.setOnClickListener(this);

        mDotViews = new ArrayList<>();

        mCityLocation = (TextView) mView.findViewById(R.id.home_toolbar_address_textview);
        mCityLocation.setOnClickListener(this);

        mPersonal = (ImageView) mView.findViewById(R.id.home_personal);
        mPersonal.setOnClickListener(this);
        mPopMenu = new PopCaptureMenu(mContext.getApplicationContext(), popMenuListener);

        AppLocationImpl.newInstance().attchAppLocation(this);

        readCityList();
        CityCodeChangeImpl.newInstance().attchCityCode(this);
        getAdView();

        initViewPager();

        return mView;
    }

    @Override
    public void updateLocation() {
        initLocationInfo();
    }

    private class CarSimpleDrawerListener extends DrawerLayout.SimpleDrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppLocationImpl.newInstance().detachAppLocation(this);
        CityCodeChangeImpl.newInstance().detachCityCode(this);
        if (mTimer != null) {
            mTimer.shutdownNow();
        }

        if(bmp != null){
            bmp.recycle();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setNickname();
        showPicture();
    }

    private void initLocationInfo() {
        if (getActivity().getIntent().getStringExtra("city") != null)
            mLocateCity = getActivity().getIntent().getStringExtra("city");

        if (!TextUtils.isEmpty(mLocateCity) && !isFirst) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCityLocation.setText(mLocateCity);
                }
            });
        } else {
            if(isFirst){
                BDLocation bdLocation = AppLocation.newInstance().getBDLocation();
                if (bdLocation == null) {
                    return;
                }
                String city = bdLocation.getCity();
                if (TextUtils.isEmpty(city)) {
                    return;
                }
                final String newCity = city;
                mLocateCity = newCity;
                initCityCode(newCity);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCityLocation.setText(newCity);
                    }
                });
            }
        }
    }

    private void setNickname() {
        mUser = mApplication.getUser();
        if (mUser != null) {
            mNickname.setText(mUser.getResult().getUser_info().getNickname());
            mNickname.invalidate();
            mLogoutRelayout.setVisibility(View.VISIBLE);
            mChangePwd.setVisibility(View.VISIBLE);
            mChangePwdLine.setVisibility(View.VISIBLE);
            new LoadHeaderTask().execute(mUser.getResult().getUser_info().getAvatar_url());
        } else {
            mNickname.setText("戳我登录");
            mHeadPortrait.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.personal_head_portrait));
            mLogoutRelayout.setVisibility(View.GONE);
            mChangePwd.setVisibility(View.GONE);
            mChangePwdLine.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFuture != null) {
            mFuture.cancel(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initLocationInfo();
        mTimer = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCityList();
    }

    private void initCityList() {
        String url = ApiManager.URL_CITY_LIST;
        JSONObject params = new JSONObject();
        THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 0x01, TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_special_relief_relativelayout:
                Intent intent = new Intent(getActivity(), UrgentRescueActivity.class);
                startActivity(intent);
                break;
            case R.id.home_expert_relativelayout:
                Intent intent2 = new Intent(getActivity(), ExpertActivity.class);
                startActivity(intent2);
                break;
            case R.id.home_car_repairing_relativelayout:
                Intent intent3 = new Intent(getActivity(), FourServeActivity.class);
                intent3.putExtra("type", "5100");
                startActivity(intent3);
                break;
            case R.id.home_car_wash_relativelayout:
                Intent intent4 = new Intent(getActivity(), FourServeActivity.class);
                intent4.putExtra("type", "4100");
                startActivity(intent4);
                break;
            case R.id.home_tyre_relativelayout:
                Intent intent5 = new Intent(getActivity(), FourServeActivity.class);
                intent5.putExtra("type", "7100");
                startActivity(intent5);
                break;
            case R.id.home_maintain_relativelayout:
                Intent intent6 = new Intent(getActivity(), FourServeActivity.class);
                intent6.putExtra("type", "6100");
                startActivity(intent6);
                break;
            case R.id.home_toolbar_address_textview:
                Intent cityList = new Intent(mContext, CityPickerActivity.class);
                cityList.putExtra("city_list", (Serializable) mCityList);
                startActivity(cityList);
                break;
            case R.id.search_et:
            case R.id.home_search_ll:
                Intent goSearch = new Intent(mContext, HomeSearchActivity.class);
                startActivity(goSearch);
                break;
            case R.id.home_personal:
                mDrawer.openDrawer(Gravity.RIGHT);
                mUser = mApplication.getUser();
                if (mUser != null) {
                    new LoadHeaderTask().execute(mUser.getResult().getUser_info().getAvatar_url());
                }else {
                    mHeadPortrait.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.personal_head_portrait));
                }
                break;
            case R.id.login_layout:
            case R.id.head_portrait:
                mUser = mApplication.getUser();
                if (mUser == null) {
                    showToastMsg("请先登录!");
                    mDrawer.closeDrawers();
                    Intent goLogin = new Intent(mContext, LoginActivity.class);
                    startActivity(goLogin);
                } else {
                    mPopMenu.showAtLocation(mLinearLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            case R.id.click_my_activity:
                mDrawer.closeDrawers();
                if (mUser == null) {
                    showToastMsg("请先登录!");
                    Intent goLogin = new Intent(mContext, LoginActivity.class);
                    startActivity(goLogin);
                } else {
                    Intent goMyActivity = new Intent(mContext, MyActivityActivity.class);
                    startActivity(goMyActivity);
                }
                break;
            case R.id.app_update_layout:
                update();
                break;

            case R.id.click_logout:
                mDrawer.closeDrawers();
                mUser = mApplication.getUser();
                if (mUser != null) {
                    showDialog(getString(R.string.dialog_data));
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("token", mUser.getResult().getToken());
                        ApiManager.Logout(jsonObject, this, LOGOUT, TAG);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.click_my_favorite:
                mDrawer.closeDrawers();
                if (mUser == null) {
                    showToastMsg("请先登录!");
                    Intent goLogin = new Intent(mContext, LoginActivity.class);
                    startActivity(goLogin);
                } else {
                    Intent goMyFavor = new Intent(mContext, MyFavoriteActivity.class);
                    startActivity(goMyFavor);
                }
                break;
            case R.id.change_pwd_layout:
                mDrawer.closeDrawers();
                Intent changePwd = new Intent(mContext, ForgetPasswordStepOneActivity.class);
                startActivity(changePwd);
                break;
            case R.id.click_my_question:
                mDrawer.closeDrawers();
                if (mUser == null) {
                    showToastMsg("请先登录!");
                    Intent goLogin = new Intent(mContext, LoginActivity.class);
                    startActivity(goLogin);
                } else {
                    Intent myQuestion = new Intent(mContext, MyQuestionActivity.class);
                    startActivity(myQuestion);
                }
                break;

            case R.id.about_us_layout:
                mDrawer.closeDrawers();
                Intent goAboutUs = new Intent(mContext, AboutUsActivity.class);
                startActivity(goAboutUs);
                break;

            case R.id.personal_info_rl:
                mDrawer.closeDrawers();
                if (mUser == null) {
                    showToastMsg("请先登录!");
                    Intent goLogin = new Intent(mContext, LoginActivity.class);
                    startActivity(goLogin);
                } else {
                    Intent personInfoIntent = new Intent(mContext, PersonalInfoActivity.class);
                    startActivity(personInfoIntent);
                }
                break;
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case LOGOUT:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
                                String message = response.getString("message");
                                mApplication.resetUser();
                                mHeadPortrait.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.personal_head_portrait));
                                mHandler.sendEmptyMessageDelayed(101, 1000);
                                SavePhoto.deleteAllFile();
                                SharedPreferenceHelper.setValue("token", "");
                                showToastMsg(message);
                            } else {
                                closeDialog();
                                String message = response.getString("message");
                                showToastMsg(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                }
                break;
            case AD:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            if (TextUtils.equals(code, "0")) {
                                JSONArray obj2 = response.getJSONArray("result");
                                Gson gson = new Gson();
                                List<HomeAdBean> list = gson.fromJson(obj2.toString(),
                                        new TypeToken<List<HomeAdBean>>(){}.getType());
                                if (list != null && list.size() > 0) {
                                    mProgressBar.setVisibility(View.GONE);
                                    mAdList.clear();
                                    mAdList.addAll(list);
                                    mViewPagerAdapter.refresh(mAdList);
                                    addPictures();
                                }
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } else {
                }
                break;
            case 0x01:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                CityListBean cityListBean = gson.fromJson(response.toString(), CityListBean.class);
                                if (cityListBean != null) {
                                    mCityList.clear();
                                    mCityList.addAll(cityListBean.getResult());
                                    sortCityList(mCityList);
                                    saveCacheData(response.toString(), CITY_KEY);
                                }
                            } else {
                                readCityList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        readCityList();
                    }
                } else {
                    readCityList();
                }
                break;
        }
    }

    @Override
    public void onSuccessResponse(String response, int type) {
        closeDialog();
        switch (type) {
            case 1:
                if (response != null) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.has("err_code")) {
                            try {
                                String code = obj.getString("err_code");
                                TAppUtils.logout(mApplication,code);
                                String message = obj.getString("message");
                                showToastMsg(message);
                                if (TextUtils.equals(code, "0")) {
                                    JSONObject jsonObject = obj.getJSONObject("result");
                                    mHeadPortrait.setImageBitmap(bmp);
                                    mHeadPortrait.invalidate();
                                    avatarUrl = jsonObject.getString("avatar_url");
                                    mUser.getResult().getUser_info().setAvatar_url(avatarUrl);
                                    mApplication.setUser(mUser);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }

    private void readCityList() {
        readCacheData(CITY_KEY, CITY_TYPE);
    }

    private void initCityCode(String name) {
        if (mCityList != null) {
            for (CityListBean.ResultBean bean : mCityList) {
                if (bean.getCity_name().contains(name)) {
                    isFirst = false;
                    SharedPreferenceHelper.setValue(ApiManager.CITY_CODE, bean.getCity_code());
                    CityCodeChangeImpl.newInstance().notifyChange();
                    break;
                }
            }
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onExecuteSuccess(String result, int type) {
        switch (type) {
            case CITY_TYPE:
                Gson gson = new Gson();
                CityListBean cityListBean = gson.fromJson(result, CityListBean.class);
                if (cityListBean != null) {
                    mCityList.clear();
                    mCityList.addAll(cityListBean.getResult());
                    sortCityList(mCityList);
                }
                break;
        }

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    /**
     * 广告条加载数据
     */
    private void getAdView() {
        try {
            String url = ApiManager.URL_AD;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("city_code", SharedPreferenceHelper.getValue(ApiManager.CITY_CODE));
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, jsonObject, this, AD, "adv.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 广告条方法逻辑
     */
    private void addPictures() {
        LinearLayout linearLayout = (LinearLayout) mView.findViewById(R.id.wrap_dots_ll);
        mDotViews.clear();
        linearLayout.removeAllViews();
        for (int k = 0; k < mAdList.size(); k++) {
            ImageView imageViewDot = new ImageView(getActivity());

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
                    if (i == position) {
                        mDotViews.get(i).setImageResource(R.drawable.circular_point_shape_selected);
                        mPosition = position;
                    } else {
                        mDotViews.get(i).setImageResource(R.drawable.circular_point_shape);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void initViewPager() {
        FragmentManager fm = getActivity().getSupportFragmentManager();

        mViewPagerAdapter = new ViewPagerAdapter(mAdList, getActivity(), fm);
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
                case 101:
                    setNickname();
                    break;
            }
        }
    };

    private View.OnClickListener popMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopMenu.dismiss();
            switch (v.getId()) {
                case R.id.pop_capture_camera:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri outPutUri = getUriFormFile(HEADER_URLSTR);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
                    startActivityForResult(intent, REQUESTCODE_CAMERA);
                    break;
                case R.id.pop_capture_album:
                    Intent intent2 = new Intent(Intent.ACTION_PICK);
                    intent2.setType("image/*");
                    startActivityForResult(intent2, REQUESTCODE_GALLERY);
                    break;
            }
        }
    };

    private Uri getUriFormFile(String filePath) {
        return Uri.fromFile(new File(filePath));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE_GALLERY:
                    Uri galleryUri = data.getData();
                    gotoCrop(galleryUri);
                    break;
                case REQUESTCODE_CAMERA:
                    Uri cameraUri = getUriFormFile(HEADER_URLSTR);
                    gotoCrop(cameraUri);
                    break;
                case REQUESTCODE_CROP:
                    bmp = data.getParcelableExtra("data");
                    updateHeader();
                    break;
            }
        }
    }

    private void gotoCrop(Uri inputRri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputRri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CROP);
    }

    private void updateHeader() {
        SavePhoto.getPhoto(bmp);
        SavePhoto.saveBitmap(HEADER_URLSTR, bmp);
        Uri mUri = getUriFormFile(HEADER_URLSTR);
        String path = GetPath.getImageAbsolutePath(mContext, mUri);
        headerPath = path;

        String sign = MD5.getSign(ApiManager.URL_PERSONAL_INFO_CHANGE, mUser);
        String t = MD5.gettimes();
        final String Url = ApiManager.URL_PERSONAL_INFO_CHANGE + "?token=" + mUser.getResult().getToken() + "&t=" + t + "&sign=" + sign;
        Map picMap = new HashMap<>();
        picMap.put("avatar", headerPath);
        THttpsOpenHelper.newInstance().requestFormDataPost(Url, null, picMap, this, 1, TAG);
        showDialog(getString(R.string.dialog_data));
    }

    private class LoadHeaderTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            bitmap = SavePhoto.getBitmapFromFile(HEADER_URLSTR);
            if (bitmap != null) {
                return bitmap;
            } else {
                bitmap = loadNetBitmap(url);
                if (bitmap != null) {
                    int w = (int) (78 * density);
                    Bitmap tmp = getBitmapDynamic(bitmap, w, w);
                    SavePhoto.saveBitmap(HEADER_URLSTR, tmp);
                    return tmp;
                }
            }
            return BitmapFactory.decodeResource(getResources(), R.drawable.personal_head_portrait);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                mHeadPortrait.setImageBitmap(bitmap);
            }
        }

        public Bitmap loadNetBitmap(String imgUrl) {
            URL url;
            Bitmap bitmap = null;
            try {
                url = new URL(imgUrl);
                HttpURLConnection con = (HttpURLConnection) url
                        .openConnection();
                con.setConnectTimeout(20 * 1000);
                con.setDoInput(true);
                con.setUseCaches(false);
                con.setInstanceFollowRedirects(true);
                con.setRequestProperty("content_type", "image/*");
                con.connect();
                InputStream is = con.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
                con.disconnect();
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private Bitmap getBitmapDynamic(Bitmap bmp, int width, int height) {
            float w = bmp.getWidth();
            float h = bmp.getHeight();
            Matrix matrix = new Matrix();
            float scaleW = width / w;
            float scaleH = height / h;
            matrix.postScale(scaleW, scaleH);
            Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, (int) w, (int) h, matrix, true);
            return bitmap;
        }
    }

    @Override
    public void updateCityCode() {
        getAdView();
    }

    private void update() {
        UpdateBean updateBean = mApplication.getUpdateBean();
        if (updateBean != null) {
            int currentCode = UpdateUtils.getVerCode(mApplication);
            if (currentCode < updateBean.getVersion_code()) {
                Intent intent = new Intent(UpdateUtils.APP_UPDATE);
                intent.putExtra("bean", updateBean);
                ContextService.getInstance().getContext().sendBroadcast(intent);
            } else {
                showToastMsg(getString(R.string.app_update_info));
            }
        } else {
            showToastMsg(getString(R.string.app_update_info));
        }
    }

    private void sortCityList(List<CityListBean.ResultBean> cityList) {
        Comparator<CityListBean.ResultBean> itemComparator = new Comparator<CityListBean.ResultBean>() {
            @Override
            public int compare(CityListBean.ResultBean lhs, CityListBean.ResultBean rhs) {
                return lhs.getCity_phoneticize().compareTo(rhs.getCity_phoneticize());
            }
        };
        Collections.sort(cityList, itemComparator);
    }

}
