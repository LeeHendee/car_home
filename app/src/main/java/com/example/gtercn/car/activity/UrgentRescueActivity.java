package com.example.gtercn.car.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.RescueRecyclerViewAdapter;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.RescueCategoryBean;
import com.example.gtercn.car.bean.RescueListBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.loader.RescueListCursorLoader;
import com.example.gtercn.car.location.AppLocation;
import com.example.gtercn.car.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 紧急救援List
 */
public class UrgentRescueActivity extends BaseActivity implements
        View.OnClickListener,
        LoaderManager.LoaderCallbacks<List<RescueListBean.ResultBean>> {

    private static final String TAG = "UrgentRescueActivity";

    private RecyclerView mRecyclerView;

    private List<RescueListBean.ResultBean> mRescueList;

    private RescueRecyclerViewAdapter mAdapter;

    private Toolbar mToolbar;

    private View mRescueType;

    private View mDistanceSelect;

    private TextView mCurrentCity;

    private ImageView mLocationIv;

    private PopupWindow mPopupWindow;

    private ImageView mTypeArrow;
    private ImageView mDistanceArrow;
    private TextView mTypeTextView;
    private TextView mDistanceTextView;

    @BindView(R.id.empty_urgent_view)
    View mEmptyView;

    @BindView(R.id.custom_empty_view_textview)
    TextView mEmptyViewText;

    private int mFlag = 0;

    private List<RescueListBean.ResultBean> mFilterList = new ArrayList<>();

    private List<Object> mCategoryList = new ArrayList<>();
    private List<Object> mDistanceList = new ArrayList<>();

    private CarApplication mApp;

    private BDLocation mBDLocation;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_rescue);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public void initView() {
        mApp = (CarApplication) getApplication();
        mUser = mApp.getUser();
        mRecyclerView = (RecyclerView) findViewById(R.id.rescue_recycler);
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        mTypeArrow = (ImageView)findViewById(R.id.rescue_type_arrow);
        mDistanceArrow=(ImageView)findViewById(R.id.rescue_distance_arrow);
        mTypeTextView=(TextView)findViewById(R.id.rescue_type_textview);
        mDistanceTextView = (TextView)findViewById(R.id.rescue_distance_textview);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("紧急救援");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRescueList = new ArrayList<>();
        mAdapter = new RescueRecyclerViewAdapter(this, mRescueList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRescueType = findViewById(R.id.rescue_type);
        mDistanceSelect = findViewById(R.id.rescue_distance);

        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRescueType.setOnClickListener(this);
        mDistanceSelect.setOnClickListener(this);

        mCurrentCity = (TextView) findViewById(R.id.current_location_tv);
        mLocationIv = (ImageView) findViewById(R.id.urgent_list_locate_iv);

        mLocationIv.setOnClickListener(this);
    }

    private void loadCurrentLocation(){
        mBDLocation = AppLocation.newInstance().getBDLocation();
        if (mBDLocation != null) {
            mCurrentCity.setText( "当前位置 ：" + mBDLocation.getProvince() + mBDLocation.getCity() + mBDLocation.getDistrict());
        }
    }

    public void initData() {
        loadCurrentLocation();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rescue_type:
                mFlag = 1;
                if (mRescueList != null && mRescueList.size() > 0){
                    showTypeWindow(v, mCategoryList, false);
                }
                break;
            case R.id.rescue_distance:
                mFlag = 2;
                if (mRescueList != null && mRescueList.size() > 0){
                    showTypeWindow(v, mDistanceList, true);
                }
                break;
            case R.id.type_tv:
            case R.id.item_type_view_lyt:
                if (mRescueList == null || mRescueList.size() == 0)
                    return;
                Object obj = v.getTag();
                if(obj instanceof RescueCategoryBean){
                    RescueCategoryBean rb = (RescueCategoryBean) obj;
                    mFilterList = filterData(mRescueList, rb.getKey());
                }else if(obj instanceof String){
                    String vl = (String) obj;
                    double dis = 1000;
                    if(TextUtils.equals(vl, "附近")){
                        dis = Integer.MAX_VALUE;
                    }else {
                        dis = Double.valueOf(vl)*1000;
                    }
                    mFilterList = filterDataByDistance(mRescueList, dis);
                }

                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }

                if(mFilterList.size() > 0){
                    mEmptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mAdapter.setChange(mFilterList);
                }else {
                    mEmptyViewText.setText("没有相关救援信息");
                    mEmptyView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
                break;
            case R.id.urgent_list_locate_iv:
                loadCurrentLocation();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<RescueListBean.ResultBean> filterData(List<RescueListBean.ResultBean> beans, String type) {
        List<RescueListBean.ResultBean> list = new ArrayList<>();
        for(RescueListBean.ResultBean bean: beans){
            String strType = bean.getType_value();
            if (strType.contains(type)) {
                list.add(bean);
            }
        }

        return list;
    }

    private List<RescueListBean.ResultBean> filterDataByDistance(List<RescueListBean.ResultBean> beans, double distance) {

        List<RescueListBean.ResultBean> list = new ArrayList<>();

        for(RescueListBean.ResultBean bean : beans){
            double d = bean.getDistance();
            if(d < distance){
                list.add(bean);
            }
        }

        return list;
    }

    /**
     * 数据过滤
     * @param v
     * @param list
     * @param isDistance
     */
    private void showTypeWindow(View v, List<Object> list, boolean isDistance) {
        if (list == null) {
            return;
        }

        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return;
        }

        final View view = LayoutInflater.from(this).inflate(R.layout.rescue_type_view, null);
        LinearLayout typeLayout = (LinearLayout) view.findViewById(R.id.type_ll);
        typeLayout.removeAllViews();

        for (int i = 0; i < list.size(); i++) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_type_view, null);
            itemView.setBackgroundColor(Color.WHITE);
            TextView typeTv = (TextView) itemView.findViewById(R.id.type_tv);

            if(list.get(i) instanceof RescueCategoryBean){
                RescueCategoryBean bean = (RescueCategoryBean) list.get(i);
                typeTv.setText(bean.getValue());
                typeTv.setTag(bean);
            }else if(list.get(i) instanceof String){
                String value = (String) list.get(i);
                if(i>0) {
                    typeTv.setText(value + "km");
                }else {
                    typeTv.setText(value);
                }
                typeTv.setTag(value);
            }

            typeTv.setTextColor(Color.BLACK);
            typeTv.setBackgroundResource(R.drawable.backcolor_callback);
            typeLayout.addView(itemView);
            typeTv.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.color.white));
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(v);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus && mFlag == 2) {
            mDistanceTextView.setTextColor(ContextCompat.getColor(mContext, R.color.blue2));
            mDistanceArrow.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_shop_list_shang_arrow));

        } else if (!hasFocus && mFlag == 1) {
            mTypeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.blue2));
            mTypeArrow.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_shop_list_shang_arrow));

        } else {
            mTypeTextView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            mDistanceTextView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            mDistanceArrow.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_shop_list_xia_arrow));
            mTypeArrow.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.icon_shop_list_xia_arrow));
        }
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public Loader<List<RescueListBean.ResultBean>> onCreateLoader(int id, Bundle args) {
        return new RescueListCursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<RescueListBean.ResultBean>> loader, List<RescueListBean.ResultBean> data) {
        if(data != null && data.size() > 0){
            synchronized (mRescueList){
                mRescueList.clear();
                mRescueList.addAll(data);
                mAdapter.addData(mRescueList);
                String category = data.get(0).getCategory();
                Gson gson = new Gson();
                mCategoryList.addAll((List<RescueCategoryBean>)gson.fromJson(category, new TypeToken<List<RescueCategoryBean>>(){}.getType()));
                List<String> distanceList = data.get(0).getDistance_list();
                mDistanceList.addAll(distanceList);

            }
        }else {
            mEmptyViewText.setText("没有相关救援信息");
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<RescueListBean.ResultBean>> loader) {
        mRescueList.clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
