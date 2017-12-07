package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016-7-15.
 * 图片放大
 */
public class PhotoViewActivity extends BaseActivity {

    private static final String TAG = PhotoViewActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @BindView(R.id.photoview_img)
    ImageView mImageView;

    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public void initView() {

        mTitle.setText("相册查看");

        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initData() {
        String url = getIntent().getStringExtra("url");
        if(url != null && url.contains("\\")){
            url = TAppUtils.formatUrl(url);
        }

        if(!TextUtils.isEmpty(url)){
            THttpOpenHelper.newInstance().setImageBitmap(mImageView,url,
                    R.drawable.icon_default,
                    R.drawable.icon_default);
        }

        mAttacher = new PhotoViewAttacher(mImageView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAttacher.cleanup();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
    protected void onExecuteFailure(int type) {

    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

}
