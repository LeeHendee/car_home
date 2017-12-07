package com.example.gtercn.car.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.MyFavouriteAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.MyFavouriteBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseJSONObjectListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.recyclerview.SwapRecyclerView;
import com.example.gtercn.car.widget.recyclerview.SwipeMenu;
import com.example.gtercn.car.interfaces.SwipeMenuBuilder;
import com.example.gtercn.car.widget.recyclerview.SwipeMenuItem;
import com.example.gtercn.car.widget.recyclerview.SwipeMenuView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : LiHang.
 * data : 2016/12/30.
 * description:
 * 我的收藏
 */
public class MyFavoriteActivity extends BaseActivity implements SwipeMenuBuilder,
        MyFavouriteAdapter.itemClickListener, ResponseJSONObjectListener {

    private static final String TAG = MyFavoriteActivity.class.getSimpleName();

    private MyFavouriteAdapter mAdapter;

    private boolean isEdited = false;

    private List<MyFavouriteBean.ResultBean> mBeans;

    @BindView(R.id.my_favourite_recy)
    SwapRecyclerView mRecyclerView;

    private static final int GET_DATA = 0x01;

    private static final int DELETE_DATA = 0x02;

    private User mUser;

    private CarApplication mApplication;

    private Paint mPaint = new Paint();

    @BindView(R.id.empty_favourite_view)
    View mEmptyView;

    @BindView(R.id.custom_empty_view_textview)
    TextView mEmptyViewText;


    private Toolbar mBar;

    private String id;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);
        ButterKnife.bind(this);
        mEmptyView.setVisibility(View.GONE);
        mBar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("我的收藏");
        mBar.setTitle("");
        setSupportActionBar(mBar);
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBeans = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setEnable(true);
        mAdapter = new MyFavouriteAdapter(this, mBeans);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(this);

        drawBottomLine();

        mRecyclerView.setOnSwipeListener(new SwapRecyclerView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
        initData();

    }

    private void drawBottomLine() {
        mPaint.setColor(Color.rgb(170, 170, 170));
        mPaint.setStrokeWidth(1);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                for (int i = 0, size = parent.getChildCount(); i < size; i++) {
                    View child = parent.getChildAt(i);
                    c.drawLine(child.getLeft() + 10, child.getBottom(), child.getRight() - 10, child.getBottom(), mPaint);
                }
            }
        });
    }

    private void initData() {
        mApplication = (CarApplication) getApplication();
        mUser = mApplication.getUser();
    }

    @Override
    public SwipeMenuView create() {

        SwipeMenu menu = new SwipeMenu(this);

        SwipeMenuItem item = new SwipeMenuItem(MyFavoriteActivity.this);
        item.setTitle("删除")
                .setTitleColor(Color.WHITE)
                .setBackground(new ColorDrawable(Color.RED))
                .setWidth(dp2px(80))
                .setTitleSize(16);
        menu.addMenuItem(item);

        SwipeMenuView menuView = new SwipeMenuView(menu);

        menuView.setOnMenuItemClickListener(mOnSwipeItemClickListener);

        return menuView;
    }

    private SwipeMenuView.OnMenuItemClickListener mOnSwipeItemClickListener = new SwipeMenuView.OnMenuItemClickListener() {

        @Override
        public void onMenuItemClick(int pos, SwipeMenu menu, int index) {

            cancelFavorite(pos);
//            mAdapter.remove(pos);
//            if (mAdapter.getItemCount() == 0) {
//                mEmptyView.setVisibility(View.VISIBLE);
//            }
        }
    };

    private void submitDelete(int position) {
        if (mUser == null) {
            return;
        } else {
            String url = getUrl(ApiManager.URL_MY_FAVORADD, mUser.getResult().getToken(), "t", "sign");

        }
    }

    private void cancelFavorite(int pos) {
        if (pos == 0 && mAdapter.getPos() != 0) {
            id = mBeans.get(mAdapter.getPos()).getFavor_id();
            type = mBeans.get(mAdapter.getPos()).getFavor_type();
        } else {
            id = mBeans.get(pos).getFavor_id();
            type = mBeans.get(pos).getFavor_type();
        }
        String sign = MD5.getSign(ApiManager.URL_FAVOR_DEL, mUser);
        String t = MD5.gettimes();
        String url = ApiManager.URL_FAVOR_DEL + "?token=" + mUser.getResult().getToken() + "&t=" + t + "&sign=" + sign;
//        String id = mBeans.get(pos).getFavor_id();
//        int type = mBeans.get(pos).getFavor_type();
        JSONObject params = new JSONObject();
        try {
            params.put("favor_id", id);
            params.put("favor_type",String.valueOf(type));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, DELETE_DATA, "favor_del");
        showDialog("正在取消收藏,请稍等...");
        if (pos == 0 && mAdapter.getPos() != 0) {
            mAdapter.remove(mAdapter.getPos());
        } else {
            mAdapter.remove(pos);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_item) {
            if (!isEdited) {
                mAdapter.setDisplayed(true);
                item.setTitle("完成");
                isEdited = true;
//                mRecyclerView.setEnable(true);

            } else {
//                mRecyclerView.setEnable(false);
                mAdapter.setDisplayed(false);
                item.setTitle("编辑");
                isEdited = false;
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
    }

    private void getInfo() {
        if (mUser == null) {
            return;
        } else {
            String token = mUser.getResult().getToken();
            String sign = MD5.getSign(ApiManager.URL_MY_FAVORADD, mUser);
            String t = MD5.gettimes();
            String url = getUrl(ApiManager.URL_MY_FAVORADD, token, t, sign);
            JSONObject params = new JSONObject();
            try {
                params.put("token", token);
                params.put("t", t);
                params.put("sign", sign);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            THttpOpenHelper.newInstance().requestJsonObjectPost(url, params, this, GET_DATA, TAG);
            showDialog(getString(R.string.loading_data));
        }
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    protected boolean isLogin() {
        return super.isLogin();
    }

    @Override
    public void itemClick(MyFavouriteBean.ResultBean bean) {
        String id = bean.getFavor_id();
        int type = bean.getFavor_type();
        if (type == 1){
            //自驾游
            Intent goSelfDriving = new Intent(mContext, SelfDrivingTravelDetailActivity.class);
            goSelfDriving.putExtra("id",id);
            startActivity(goSelfDriving);
            }else if (type == 2){
                //车友会（建设中）
            }else if (type == 3){
                //达人圈
                Intent goExpertCircle = new Intent(mContext, ExpertCircleDetailActivity.class);
                goExpertCircle.putExtra("id",id);
                startActivity(goExpertCircle);
            }else if (type == 4){
                //资讯
                Intent goMessage = new Intent(mContext, MessageActivity.class);
                goMessage.putExtra("id",id);
                startActivity(goMessage);
            }else if (type == 5){
                //促销
                Intent goPromotion = new Intent(mContext, PromotionActivity.class);
                goPromotion.putExtra("promotionId",id);
                startActivity(goPromotion);
            }else if (type == 6){
                //紧急救援
                Intent goRescueDetail = new Intent(mContext, RescureShopDetailActivity.class);
                goRescueDetail.putExtra("shopId",id);
                startActivity(goRescueDetail);
            }else if (type == 7){
                //四类服务
                Intent goFourServeDetail = new Intent(mContext, FourServeDetailActivity.class);
                goFourServeDetail.putExtra("shop_id",id);
                startActivity(goFourServeDetail);
        }
    }

    @Override
    public void onSuccessResponse(JSONObject response, int type) {
        closeDialog();
        switch (type) {
            case GET_DATA:
                if (response != null) {
                    TLog.i(TAG, "---->>onSuccessResponse response is " + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String err_code = response.getString("err_code");
                            TAppUtils.logout(mApplication,err_code);
                            String message = response.getString("message");
//                            showToastMsg(message);
                            if (TextUtils.equals(err_code, "0")) {
                                Gson gson = new Gson();
                                MyFavouriteBean bean = gson.fromJson(response.toString(), MyFavouriteBean.class);
                                if (bean == null) {
                                    return;
                                }
                                List<MyFavouriteBean.ResultBean> list = bean.getResult();
                                if (list != null && list.size() > 0) {
                                    mBeans.clear();
                                    mBeans.addAll(list);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    mEmptyViewText.setText("没有相关收藏信息");
                                    mEmptyView.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
            case DELETE_DATA:
                if (response != null) {
                    TLog.i(TAG, "--->>success" + response.toString());
                    if (response.has("err_code")) {
                        try {
                            String code = response.getString("err_code");
                            TAppUtils.logout(mApplication,code);
                            if (TextUtils.equals(code, "0")) {
//                                getInfo();
                                mAdapter.setPos();
                                Toast.makeText(mContext, "删除收藏!", Toast.LENGTH_SHORT).show();
                                if (mAdapter.getItemCount() == 0) {
                                    mEmptyViewText.setText("没有相关收藏信息");
                                    mEmptyView.setVisibility(View.VISIBLE);
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

    private void showErrMessage(JSONObject response) {
        if (response.has("message")) {
            try {
                String message = response.getString("message");
                if (!TextUtils.isEmpty(message)) {
                    showToastMsg(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMessage(JSONObject response) {
        if (response.has("message")) {
            try {
                String message = response.getString("message");
                if (!TextUtils.isEmpty(message)) {
                    showToastMsg(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }
}
