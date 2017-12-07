package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.FourViewPagerAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.RescueListBean;
import com.example.gtercn.car.bean.RescureShopDetialBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.ShareUtil;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.google.gson.Gson;
import com.umeng.socialize.media.UMImage;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 救援店家详情页面
 */
public class RescureShopDetailActivity extends BaseActivity implements View.OnClickListener, ResponseJSONObjectListener {

    private static final String TAG = "RescureShopDetailActivity";

    private static final int RESCUREDETAUL = 20456116;
    private BDLocation mBDLocation = AppLocation.newInstance().getBDLocation();
    private TextView back;
    private TextView mAddress;
    private TextView mDistance;
    private TextView mCallRescue;
    private TextView mDiscription;
    private TextView mProduct;
    private TextView mTitle;
    private ImageView mDisplayPic;
    private ViewPager mViewPager;
    private FourViewPagerAdapter mViewPagerAdapter;
    private List<String> mAdList;
    private List<ImageView> mDotViews;
    private ScheduledExecutorService mTimer;
    private Future mFuture;
    private static final int TIME = 2000;
    private static int mPosition = 0;

    private ImageView mShareIv;

    private ImageView mFavorIv;
    private RescureShopDetialBean rescureShopDetialBean;
    private CarApplication mApp;

    private User mUser;

    private int isFavored;
    private  String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescure_shop_detail);
        mApp = (CarApplication) getApplication();
        mTimer = Executors.newSingleThreadScheduledExecutor();

        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFuture != null) {
            mFuture.cancel(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.shutdownNow();
        }
    }

    private void initData() {
        mUser =mApp.getUser();
        shopId = getIntent().getStringExtra("shopId");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rescue_id",shopId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(mUser != null){
            String sign = MD5.getSign(ApiManager.URL_RESCURE_DETAIL, mUser);
            String t = MD5.gettimes();
            String url= ApiManager.URL_RESCURE_DETAIL+"?token="+mUser.getResult().getToken()+"&t="+ t +"&sign=" + sign;
            THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,RESCUREDETAUL,TAG);
        }else {
            String url = ApiManager.URL_RESCURE_DETAIL;
            THttpOpenHelper.newInstance().requestJsonObjectPost(url,jsonObject,this,RESCUREDETAUL,TAG);
        }
    }

    private void initView() {
        back = (TextView) findViewById(R.id.rescure_shop_activity_callback_textview);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAddress = (TextView) findViewById(R.id.rescue_shop_address);
        mDistance = (TextView) findViewById(R.id.rescue_shop_distance);
        mCallRescue = (TextView) findViewById(R.id.rescue_shop_btn);
        mDiscription = (TextView) findViewById(R.id.rescue_shop_discription);
        mProduct = (TextView) findViewById(R.id.rescue_shop_product);
        mTitle = (TextView) findViewById(R.id.rescue_shop_title);
        mDisplayPic = (ImageView) findViewById(R.id.rescue_shop_iv);
        mViewPager = (ViewPager) findViewById(R.id.rescue_shop_vp);
        mShareIv = (ImageView) findViewById(R.id.rescue_shop_share);
        mFavorIv = (ImageView) findViewById(R.id.rescue_shop_favorite);
        mShareIv.setOnClickListener(this);
        mFavorIv.setOnClickListener(this);
        mCallRescue.setOnClickListener(this);
    }

    private void changeIcon(int isFavor) {
        switch (isFavor) {
            case 0:
                mFavorIv.setImageResource(R.drawable.icon_detail_collect_normal);

                break;
            case 1:
                mFavorIv.setImageResource(R.drawable.icon_detail_collect_select);

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rescue_shop_share:
                UMImage image;
                String name;
                String address;
                if (rescureShopDetialBean.getResult().getHead_portrait_url() != null) {
                    String imgUrl = rescureShopDetialBean.getResult().getHead_portrait_url();
                    if(imgUrl.contains("\\")){
                        imgUrl = TAppUtils.formatUrl(imgUrl);
                    }
                    image = new UMImage(this, imgUrl);
                }else {
                    image = new UMImage(this, R.drawable.ic_launcher);
                }

                if(TextUtils.isEmpty(rescureShopDetialBean.getResult().getShop_name())){
                    name = address = "顺驾天下";
                }else {
                    name = rescureShopDetialBean.getResult().getShop_name();
                    address = rescureShopDetialBean.getResult().getDetail_address();
                }

                ShareUtil.GeneralizeShare(this, name, address, image);

                break;
            case R.id.rescue_shop_favorite:
                mUser = mApp.getUser();
                if (mUser != null && mUser.getResult() != null) {
                    addOrCancelFavor(isFavored);
                } else {
                    showToastMsg("登录用户才能收藏哦!");
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rescue_shop_btn:
                RescureShopDetialBean.ResultBean beanDetail = rescureShopDetialBean.getResult();
                if(beanDetail == null){
                    return;
                }
                Intent intent = new Intent(RescureShopDetailActivity.this, RescueDetailActivity.class);
                Bundle bundle = new Bundle();
                RescueListBean.ResultBean bean = new RescueListBean.ResultBean();

                bean.setId(beanDetail.getId());
                bean.setLatitude(beanDetail.getLatitude());
                bean.setLongitude(beanDetail.getLongitude());
                bean.setCity(beanDetail.getCity());
                bean.setDetail_address(beanDetail.getDetail_address());
                bean.setShop_name(beanDetail.getShop_name());
                bean.setTel_number_list(beanDetail.getTel_number_list());
                bean.setHead_portrait_url(beanDetail.getHead_portrait_url());
//                bean.setDistance_list(beanDetail.getDistance);
                bean.setType_value(beanDetail.getType_value());

                bundle.putSerializable("rescue_bean", bean);
                intent.putExtra("bdl", bundle);
                startActivity(intent);
                break;
        }
    }

    private void addOrCancelFavor(int favoriteType) {
        String token = mUser.getResult().getToken();
        switch (favoriteType) {
            case 0:
                String sign = MD5.getSign(ApiManager.URL_FAVORADD, mUser);
                String t = MD5.gettimes();
                String url = ApiManager.URL_FAVORADD + "?token=" + token + "&t=" + t + "&sign=" + sign;
                String favor_id = shopId;
                String favor_type = "6";
                JSONObject params = new JSONObject();
                try {
                    params.put("favor_id", favor_id);
                    params.put("favor_type", favor_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, 0, "favor_add");
                showDialog("正在添加收藏,请稍等...");
                break;
            case 1:
                String sign1 = MD5.getSign(ApiManager.URL_FAVOR_DEL, mUser);
                String t1 = MD5.gettimes();
                String url_delete = ApiManager.URL_FAVOR_DEL + "?token=" + token + "&t=" + t1 + "&sign=" + sign1;
                String favor_id_del = shopId;
                String favor_type_del = "6";
                JSONObject paramsDel = new JSONObject();
                try {
                    paramsDel.put("favor_id", favor_id_del);
                    paramsDel.put("favor_type", favor_type_del);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                THttpOpenHelper.newInstance().requestJsonObjectPost(url_delete, paramsDel, this, 1, "favor_del");
                showDialog("正在取消收藏,请稍等...");
                break;
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case 0:
                if (response != null) {
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            if (TextUtils.equals(code, "0")) {
//                                mFavorIv.setImageResource(R.drawable.icon_detail_collect_select);
                                showSnack(mAddress, "成功收藏");
                                isFavored = 1;
                                changeIcon(1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case 1:
                if (response != null) {
                    TLog.i(TAG, "--->>success" + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            if (TextUtils.equals(code, "0")) {
//                                mFavorIv.setImageResource(R.drawable.icon_detail_collect_normal);
                                showSnack(mAddress, "取消收藏");
                                isFavored = 0;
                                changeIcon(0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case RESCUREDETAUL:
                if(response != null){
                    if(response.has("err_code")){
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApp,code);
                            if (TextUtils.equals(code, "0")) {
                                Gson gson = new Gson();
                                rescureShopDetialBean = gson.fromJson(response.toString(), RescureShopDetialBean.class);
                                mTitle.setText(rescureShopDetialBean.getResult().getShop_name());
                                mAddress.setText(rescureShopDetialBean.getResult().getCity()+rescureShopDetialBean.getResult().getDetail_address());
                                String longitude = rescureShopDetialBean.getResult().getLongitude();
                                String latitude = rescureShopDetialBean.getResult().getLatitude();
                                LatLng p = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                                LatLng currentPoint =  null;
                                if(mBDLocation != null){
                                    double currentLatitude = mBDLocation.getLatitude();
                                    double currentLongitude = mBDLocation.getLongitude();
                                    currentPoint =  new LatLng(currentLatitude, currentLongitude);
                                }else {
                                    //读取缺省值，
                                    String appLatitude = SharedPreferenceHelper.getValue(ApiManager.LAT);
                                    String appLongitude = SharedPreferenceHelper.getValue(ApiManager.LNG);
                                    currentPoint = new LatLng(Double.valueOf(appLatitude), Double.valueOf(appLongitude));
                                }
                                double distance = DistanceUtil.getDistance(currentPoint, p);
                                double   f1   =   Math.floor(distance);
                                if(f1<1*1000) {
                                    mDistance.setText((new DecimalFormat("0").format(f1) + "m"));
                                }else {
                                    mDistance.setText(new DecimalFormat("0.00").format(f1/1000) + "km");
                                }
                                mDiscription.setText(rescureShopDetialBean.getResult().getShop_description());
                                mProduct.setText(rescureShopDetialBean.getResult().getProduct_description());
                                changeIcon(rescureShopDetialBean.getResult().getIs_favored());
                                isFavored = rescureShopDetialBean.getResult().getIs_favored();
                                List<String> list = rescureShopDetialBean.getResult().getDisplay_pic_list();

                                if(list !=null && list.get(0)!=null) {
                                    mAdList = new ArrayList<>();
                                    mDotViews = new ArrayList<>();
                                    mAdList.addAll(list);
                                    if (mAdList != null) {
                                        mDisplayPic.setVisibility(View.GONE);
                                        initViewPager();
                                        addPictures();
                                        showPicture();
                                    }else{
                                        mDisplayPic.setVisibility(View.VISIBLE);
                                        mDisplayPic.setBackgroundResource(R.drawable.icon_default);
                                    }
                                }else{
                                    mDisplayPic.setImageResource(R.drawable.icon_default);
                                    mDisplayPic.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {

    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    /**
     * 广告条方法逻辑
     */
    private void addPictures() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rescue_shop_dots);
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

}
