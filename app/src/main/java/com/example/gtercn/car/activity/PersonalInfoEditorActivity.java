package com.example.gtercn.car.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.net.THttpsOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : LiHang.
 * data : 2017/2/21.
 * description:
 *    个人信息
 */
public class PersonalInfoEditorActivity extends BaseActivity implements ResponseStringListener {

    private static final String TAG = "PersonalInfoEditorActivity";

    private User mUser;

    private CarApplication mApp;

    @BindView(R.id.person_info_portrait)
    RoundedImageView mPortrait;

    @BindView(R.id.person_info_nickname)
    TextInputEditText mNickname;

    @BindView(R.id.person_info_realname)
    TextInputEditText mRealname;

//    @BindView(R.id.person_info_description)
//    EditText mDescription;

    @BindView(R.id.person_info_sex_man)
    RadioButton mSexMan;

    @BindView(R.id.person_info_sex_woman)
    RadioButton mSexWoman;

    @BindView(R.id.person_info_save_btn)
    Button mSave;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    @BindView(R.id.sex_rg)
    RadioGroup mSexGroup;

    private String mSex = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_editor);
        ButterKnife.bind(this);
        mApp = (CarApplication) getApplication();
        mUser = mApp.getUser();
        mTitle.setText("编辑信息");
        mSex = "0";
        mSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.person_info_sex_man) {
                    mSex = "0";
                    TLog.i(TAG, "----->>>mSex = " + mSex);
                } else {
                    mSex = "1";
                    TLog.i(TAG, "----->>>mSex = " + mSex);
                }
            }
        });

        if(mUser.getResult().getUser_info().getAvatar_url()!=null) {

            String url = mUser.getResult().getUser_info().getAvatar_url();
            if(url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }

            THttpOpenHelper.newInstance().setImageBitmap(mPortrait,
                    url,(int)(80*density),(int)(80*density),
                    R.drawable.personal_head_portrait,
                    R.drawable.personal_head_portrait);
        }

    }

    @OnClick({
            R.id.person_info_portrait,
            R.id.person_info_save_btn,
            R.id.toolbar_back_tv,
    })
    void onPersonalClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back_tv:
                finish();
                break;
            case R.id.person_info_save_btn:
                commitData();
                break;
            case R.id.person_info_portrait:

                break;
        }
    }

    private void commitData() {
        String token = mUser.getResult().getToken();
        String sign = MD5.getSign(ApiManager.URL_PERSONAL_INFO_CHANGE, mUser);
        String t = MD5.gettimes();
        String url = getUrl(ApiManager.URL_PERSONAL_INFO_CHANGE, token, t, sign);

        String nickname = mNickname.getText().toString().trim();
        String realname = mRealname.getText().toString().trim();
//        String description = mDescription.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            mNickname.setError("请填写昵称!");
            return;
        }
//        if (TextUtils.isEmpty(realname)) {
//            mRealname.setError("请填写昵称!");
//            return;
//        }
        Map params = new HashMap();
        params.put("nickname", nickname);
        params.put("real_name", realname);
        params.put("sex", mSex);

        Map picMap = new HashMap<>();
        if (mUser.getResult().getUser_info().getAvatar_url() != null){
            picMap.put("avatar", mUser.getResult().getUser_info().getAvatar_url());
        }else {
            picMap.put("avatar", "");
        }
        THttpsOpenHelper.newInstance().requestFormDataPost(url, params,picMap,this, 1, TAG);
        showDialog(getString(R.string.dialog_commit));
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }

    @Override
    public void onSuccessResponse(String response, int type) {
        closeDialog();
        if (response != null) {
            TLog.i(TAG, "---->>response is " + response);
            try {
                JSONObject obj = new JSONObject(response);
                if (obj.has("err_code")) {
                    try {
                        String code = obj.getString("err_code");
                        TAppUtils.logout(mApp,code);
                        String message = obj.getString("message");
                        showToastMsg(message);
                        if (TextUtils.equals(code, "0")) {
                            mUser.getResult().getUser_info().setNickname(mNickname.getText().toString().trim());
                            mUser.getResult().getUser_info().setReal_name(mRealname.getText().toString().trim());
                            mUser.getResult().getUser_info().setSex(Integer.valueOf(mSex));
                            mApp.setUser(mUser);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
