package com.example.gtercn.car.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.bean.ExpertTopBean;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.PopPhoneMenu;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : LiHang.
 * data : 2016/11/16.
 * description:
 * 达人信息页面;
 */
public class ExpertInfoActivity extends BaseActivity {

    private static final String TAG = "ExpertInfoActivity";

    private Toolbar mBar;

    private ExpertTopBean.ResultBean mExpertInfo;
    private PopPhoneMenu mPopMenu;
    private   String telNumber;

    @BindView(R.id.expert_info_linearlayout)
    LinearLayout mLinearLayout;

    @BindView(R.id.more_imgs)
    TextView mMorePictures;

    @BindView(R.id.expert_info_name)
    TextView mName;

    @BindView(R.id.expert_info_experience)
    TextView mExperience;

    @BindView(R.id.expert_info_type)
    TextView mType;

    @BindView(R.id.expert_info_description)
    TextView mDescription;

    @BindView(R.id.top_detail_wechat)
    LinearLayout mWeChat;

    @BindView(R.id.title_of_top_album)
    RelativeLayout mAlbumTitleView;

    @BindView(R.id.display_img_ll)
    LinearLayout mDisplayImgView;

    @BindView(R.id.make_call_layout)
    LinearLayout mMakeCallLayout;

    @BindView(R.id.expert_info_headportrait)
    ImageView mHeadPortrait;

    private List<String> mImgList;

    private float density = 1.5f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_info);
        ButterKnife.bind(this);

        density = getResources().getDisplayMetrics().density;

        initView();
        initData();
    }

    private void initView() {
        mBar = (Toolbar) findViewById(R.id.toolbar);
        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
        barTitle.setText("达人信息");
        TextView backTv = (TextView) findViewById(R.id.toolbar_back_tv);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initData() {
        Intent intent = getIntent();
        mExpertInfo = (ExpertTopBean.ResultBean) intent.getSerializableExtra("expert_info");
        if (mExpertInfo != null) {
            TLog.i(TAG, "---->>initData mExpertInfo" + mExpertInfo);
            mName.setText(mExpertInfo.getExpert_name());
            mExperience.setText(mExpertInfo.getExpert_experience());
            mType.setText(mExpertInfo.getTop_title());
            mDescription.setText((CharSequence) mExpertInfo.getExpert_discription_detail());
            String avatarUrl = mExpertInfo.getExpert_portrait_url();
            if (!TextUtils.isEmpty(avatarUrl)) {
                if(avatarUrl.contains("\\")){
                    avatarUrl = TAppUtils.formatUrl(avatarUrl);
                }
                THttpOpenHelper.newInstance().setImageBitmap( mHeadPortrait, avatarUrl,
                        (int)(100 * density), (int)(100 * density),
                        R.drawable.icon_expert_avatar,
                        R.drawable.icon_expert_avatar);
            } else {
                mHeadPortrait.setImageResource(R.drawable.icon_expert_avatar);
            }
            getImgList();


            telNumber  = (String) mExpertInfo.getExpert_tel_number();
            if(telNumber != null) {
                String[] sourceStrArray = telNumber.split(",");
                List<String> tel_num_list = Arrays.asList(sourceStrArray);
//                for (String str: sourceStrArray) {
//                    tel_num_list.add(str);
//                }
                mPopMenu = new PopPhoneMenu(mContext.getApplicationContext(), tel_num_list);
            }
        }
    }

    @OnClick({
            R.id.top_detail_wechat,
            R.id.display_img_ll,
            R.id.more_imgs,
            R.id.make_call_layout,
            R.id.expert_info_headportrait
    })
    void onInfoClick(View v) {
        switch (v.getId()) {
            case R.id.make_call_layout:
                if (telNumber == null) {
                    showSnack(mExperience, "此达人没有留下电话号码!");
                    return;
                }else{
                    mPopMenu.showAtLocation(mLinearLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                            0, 0);
                }
                break;

            case R.id.more_imgs:
                goToAlbum();
                break;

            case R.id.top_detail_wechat:
                getWeChatNumber();
                break;

            case R.id.display_img_ll:
                goToAlbum();
                break;

            case R.id.expert_info_headportrait:
                if (mExpertInfo == null) {
                    return;
                }
//                Intent intent = new Intent(this, AlbumDetailActivity.class);
                Intent intent = new Intent(this, PhotoViewActivity.class);
                intent.putExtra("url", mExpertInfo.getExpert_portrait_url());
                startActivity(intent);
                break;
        }
    }

    private void goToAlbum() {
        if (mImgList == null) {
            showSnack(mMakeCallLayout, "此达人没有相册!");
            return;
        }
//        Intent intent = new Intent(this, AlbumActivity.class);
        Intent intent = new Intent(this, AlbumGalleryActivity.class);
        intent.putExtra("imgList", mImgList.toArray());
        startActivity(intent);
    }

    private void getWeChatNumber() {
        final View view = LayoutInflater.from(this).inflate(R.layout.custom_add_wechat, null);
        final TextView weChatNumber = (TextView) view.findViewById(R.id.wechat_number);
        weChatNumber.setText((CharSequence) mExpertInfo.getExpert_wechat_number());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        builder.setTitle("点击确定,复制微信号到剪切板!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("微信号",weChatNumber.getText().toString().trim());
                cm.setPrimaryClip(clipData);
                showSnack(view, "成功复制微信号");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(true);
        builder.create().show();
    }

    private void getImgList() {
        String imageStr = (String) mExpertInfo.getExpert_display_pic_list();
        if (imageStr == null) {
            TLog.i(TAG, "----->>expert_display_pic_list is null !");
            mAlbumTitleView.setVisibility(View.GONE);
            mDisplayImgView.setVisibility(View.GONE);
            return;
        }
        String[] image = imageStr.split(",");
        mImgList = Arrays.asList(image);
        TLog.i(TAG, "--->>mImgList.size is " + mImgList.toString());
        if (mImgList != null && mImgList.size() > 0) {
            TLog.i(TAG, "--->>mImgList.size is " + mImgList.size());
            initDynamicView(mImgList);
        }
    }

    private void initDynamicView(List<String> urls) {
        TLog.i(TAG, "---->>urls.size = " + urls.size());
        mDisplayImgView.removeAllViews();
        int width = getResources().getDisplayMetrics().widthPixels;
        int padding = (int)(6 * 2 * getResources().getDisplayMetrics().density);
        int w1 = (width - padding)/3;
        for (int i = 0; i < (urls.size() > 3 ? 3 : urls.size()); i++) {
            TLog.i(TAG, "---->>i = " + i);
            View dynamicView = LayoutInflater.from(this).inflate(R.layout.item_display_imgs, null);
            ImageView imageView = (ImageView) dynamicView.findViewById(R.id.img_one);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = w1;
            layoutParams.height = w1;
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(layoutParams);
            String url = urls.get(i);
            if(url != null && url.contains("\\")){
                url = TAppUtils.formatUrl(url);
            }
            THttpOpenHelper.newInstance().setImageBitmap(imageView, url,w1,w1,
                    R.drawable.icon_default,
                    R.drawable.icon_default);
            mDisplayImgView.addView(dynamicView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }
}
