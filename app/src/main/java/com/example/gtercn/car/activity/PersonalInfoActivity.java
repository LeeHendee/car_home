package com.example.gtercn.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : LiHang.
 * data : 2017/2/21.
 * description:
 * 个人信息
 */
public class PersonalInfoActivity extends BaseActivity {

    private static final String TAG = "PersonalInfoActivity";

    @BindView(R.id.person_info_portrait_display)
    RoundedImageView mPortrait;

    @BindView(R.id.person_info_nickname_tv)
    TextView mNickname;

    @BindView(R.id.person_info_realname_tv)
    TextView mRealname;

//    @BindView(R.id.person_info_description_tv)
//    TextView mDescription;

    @BindView(R.id.person_info_sex_tv)
    TextView mSexTv;

    @BindView(R.id.person_info_editor_btn)
    Button mEditorBtn;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        mTitle.setText("个人信息");

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @OnClick({
            R.id.person_info_editor_btn,
            R.id.toolbar_back_tv,
    })
    void onPersonalInfoClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_tv:
                finish();
                break;
            case R.id.person_info_editor_btn:
                Intent intent = new Intent(this, PersonalInfoEditorActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initData() {
        CarApplication app = (CarApplication) getApplication();
        User user = app.getUser();
        if (user != null) {
            mNickname.setText(user.getResult().getUser_info().getNickname());
            mRealname.setText(user.getResult().getUser_info().getReal_name());
            if (user.getResult().getUser_info().getSex() == 0){
                mSexTv.setText("男");
            }else {
                mSexTv.setText("女");
            }
            if(user.getResult().getUser_info().getAvatar_url()!=null) {

                String url = user.getResult().getUser_info().getAvatar_url();
                if(url.contains("\\")){
                    url = TAppUtils.formatUrl(url);
                }

                THttpOpenHelper.newInstance().setImageBitmap(mPortrait,
                        url,(int)(80*density),(int)(80*density),
                        R.drawable.personal_head_portrait,
                        R.drawable.personal_head_portrait);
            }
//            mRealname.setText(user.getResult().getUser_info().);
//            mSexTv.setText(user.getResult().getUser_info().ge);
        }
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }


}
