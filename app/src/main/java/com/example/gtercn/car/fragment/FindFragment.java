package com.example.gtercn.car.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.gtercn.car.R;
import com.example.gtercn.car.activity.SelfDrivingTravelActivity;
import com.example.gtercn.car.base.BaseFragment;
import com.example.gtercn.car.utils.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import java.util.ArrayList;

public class FindFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "FindFragment";
    private CommonTabLayout mCommonTabLayout;
    private LinearLayout mSelfDriverLinearLayout;
    private LinearLayout mMeetLinearLayout;
    private  View view;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    //http://www.shunjiatianxia.com/
    private ImageView mImgLink;

    public FindFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_layout, container, false);
        mImgLink = (ImageView) view.findViewById(R.id.find_fragment_img_link);
        mCommonTabLayout = (CommonTabLayout) view.findViewById(R.id.find_tablayout);
        mSelfDriverLinearLayout = (LinearLayout) view.findViewById(R.id.find_framelyout_self_driving_linearlayout);
        mMeetLinearLayout = (LinearLayout)view.findViewById(R.id.find_fragment_car_meets);
        mImgLink.setOnClickListener(this);
        mMeetLinearLayout.setOnClickListener(this);
        mSelfDriverLinearLayout.setOnClickListener(this);
        getFragements();
        return view;
    }

    private void getFragements() {
        PromotionFragment mPromotionFragment = new PromotionFragment();
        mFragments.add(mPromotionFragment);
        mTabEntities.add(new TabEntity(getString(R.string.promotion)));
        MessageFragment mMessageFragment = new MessageFragment();
        mFragments.add(mMessageFragment);
        mTabEntities.add(new TabEntity(getString(R.string.message)));
        mCommonTabLayout.setTabData(mTabEntities, getActivity(), R.id.find_framelyout, mFragments);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

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
            case R.id.find_fragment_img_link:
                Intent intentLink = new Intent();
                intentLink.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse("http://www.shunjiatianxia.com/");
                intentLink.setData(uri);
                startActivity(intentLink);
                break;
            case R.id.find_framelyout_self_driving_linearlayout:
                Intent intent = new Intent(getActivity(), SelfDrivingTravelActivity.class);
                startActivity(intent);
                break;
            case R.id.find_fragment_car_meets:
                 showToastMsg(R.string.jian_she_zhong);
                break;
        }
    }

}
