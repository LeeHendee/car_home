package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Gallery;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.GalleryAdapter;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.utils.TLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : LiHang.
 * data : 2016/12/13.
 * description:
 * 达人榜信息详情下的相册;
 */
public class AlbumGalleryActivity extends BaseActivity{

    private static final String TAG = "AlbumActivity";

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @BindView(R.id.gallery_gallery)
    Gallery mGallery;

    private GalleryAdapter mAdapter;

    private List<String> mList;

    private int mGalleryWidth;

    private int mGalleryHeight;

    private volatile  boolean isGlobal = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTitle.setText("达人画册");

        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mList = new ArrayList<>();
        mGallery.setVerticalFadingEdgeEnabled(false);
        mGallery.setHorizontalFadingEdgeEnabled(false);


    }

    private void initData() {
        String[] imgArray = getIntent().getStringArrayExtra("imgList");
        if (imgArray == null) {
            TLog.i(TAG, "---->>imgArray is null !");
            return;
        }

        mList.addAll(Arrays.asList(imgArray));

        mGallery.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setGalleryAdapter();
            }
        });

    }

    private void setGalleryAdapter(){
        if(!isGlobal){
            mGalleryWidth = mGallery.getMeasuredWidth();
            mGalleryHeight = mGallery.getMeasuredHeight();
            mAdapter = new GalleryAdapter(getApplicationContext(), mList, mGalleryWidth, mGalleryHeight);
            mGallery.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            isGlobal = true;
        }
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

}
