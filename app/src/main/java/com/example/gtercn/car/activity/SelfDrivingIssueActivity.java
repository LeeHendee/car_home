package com.example.gtercn.car.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.RoadStateAdapter;
import com.example.gtercn.car.api.ApiManager;
import com.example.gtercn.car.base.BaseActivity;
import com.example.gtercn.car.base.CarApplication;
import com.example.gtercn.car.bean.SlefIssueBean;
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.GetTimeData;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.PhotoSave;
import com.example.gtercn.car.utils.SavePhoto;
import com.example.gtercn.car.utils.SharedPreferenceHelper;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.PopCaptureMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
/**
 * 自驾游发布界面
 */
public class SelfDrivingIssueActivity extends BaseActivity implements View.OnTouchListener, ResponseStringListener {
    private static final String TAG = "SelfDrivingIssueActivity";

    private static final int ISSUE = 40991580;
    private GridView mGridView;
    private List<Bitmap> mList = new ArrayList<>();
    private RoadStateAdapter mAdapter;
    private int mMaxCount = 3;
    private PopCaptureMenu mPopMenu;
    private LinearLayout mLinearLayout;
    private List<String> mImgList = new ArrayList<>();
    private int mCount = 0;
    private Uri mPictureUri;
    //系统相册请求码
    private final static int REQUESTCODE_GALLERY = 100;
    //系统相机请求码
    private final static int REQUESTCODE_CAMERA = 101;
    //系统剪切请求码
    private final static int REQUESTCODE_CROP = 0x04;
    private EditText mContextEdittext;
    private EditText mTitleEdittext;
    private Button mButton;
    private User mUser;
    private CarApplication mTApplication;
    private List<SlefIssueBean.ResultBean> list;
    private Bitmap mBitmap;
    private TextView mClaabackText;
    private EditText mTimeText;
    private Bitmap bmp;

    private final static String HEADER_URLSTR = SavePhoto.DIR + "tupian.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_driving_issue);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Re);
//        }
        mGridView = (GridView) findViewById(R.id.activity_self_driving_issue_gridview);
        mPopMenu = new PopCaptureMenu(getApplicationContext(), popMenuListener);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_self_driving_issue);
        mContextEdittext = (EditText) findViewById(R.id.activity_self_driving_issue_content_edittext);
        mTitleEdittext = (EditText) findViewById(R.id.activity_self_driving_issue_title_edittext);
        mButton = (Button) findViewById(R.id.activity_self_driving_issue_button);
        mClaabackText = (TextView) findViewById(R.id.activity_self_driving_issue_callback_textview);
        mTimeText = (EditText) findViewById(R.id.activity_self_driving_issue_time_edittext);
        SimpleDateFormat formatter    =   new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        //获取当前时间
        Date curDate    =   new    Date(System.currentTimeMillis());
        String    str    =    formatter.format(curDate);
        new GetTimeData(this,mTimeText ,str);
        mClaabackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTApplication = (CarApplication) getApplication();
                mUser = mTApplication.getUser();
                if (mTitleEdittext.getText().toString().trim().equals("")) {
                    mTitleEdittext.setError("别忘了标题哦！");
                } else if (mContextEdittext.getText().toString().trim().equals("")) {
                    mContextEdittext.setError("内容，内容哦！");
                }else if(mTimeText.getText().toString().trim().equals("")){
                    mTimeText.setError("亲，活动时间哦！");
                }
                else {
                    showDialog(getString(R.string.dialog_commit));
                    Map map = new HashMap();
                    Map fileMap = new HashMap();
                    map.put("title", mTitleEdittext.getText().toString());
                    map.put("activity_time",mTimeText.getText().toString());
                    map.put("content", mContextEdittext.getText().toString());
                    map.put("city_code", SharedPreferenceHelper.getValue(ApiManager.CITY_CODE));
                    if (mImgList.size() == 1) {
                        fileMap.put("arra", mImgList.get(0));
                        String token = mUser.getResult().getToken();
                        String sign = MD5.getSign(ApiManager.URL_SELF_ISSUE, mUser);
                        String t = MD5.gettimes();
                        String url = ApiManager.URL_SELF_ISSUE + "?token=" + token + "&t=" + t + "&sign=" + sign;
                        THttpOpenHelper.newInstance().requestFormDataFilePost(url, map, fileMap, SelfDrivingIssueActivity.this, ISSUE, TAG);
                    } else if (mImgList.size() == 2) {
                        fileMap.put("arra", mImgList.get(0));
                        fileMap.put("arra1", mImgList.get(1));
                        String token = mUser.getResult().getToken();
                        String sign = MD5.getSign(ApiManager.URL_SELF_ISSUE, mUser);
                        String t = MD5.gettimes();
                        String url = ApiManager.URL_SELF_ISSUE + "?token=" + token + "&t=" + t + "&sign=" + sign;
                        THttpOpenHelper.newInstance().requestFormDataFilePost(url, map, fileMap, SelfDrivingIssueActivity.this, ISSUE, TAG);
                    } else {
                        String token = mUser.getResult().getToken();
                        String sign = MD5.getSign(ApiManager.URL_SELF_ISSUE, mUser);
                        String t = MD5.gettimes();
                        String url = ApiManager.URL_SELF_ISSUE + "?token=" + token + "&t=" + t + "&sign=" + sign;
                        THttpOpenHelper.newInstance().requestFormDataFilePost(url, map, null, SelfDrivingIssueActivity.this, ISSUE, TAG);
                    }
                }
            }
        });
        //防止刚打开activity就弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getGridView();
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
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

    /**
     * GridView的相关逻辑
     */
    private void getGridView() {
        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_plus);
        mList.add(bp);

        mAdapter = new RoadStateAdapter(this, mList, mGridView);

        mGridView.setAdapter(mAdapter);
/**
 * GridView的点击事件
 */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (mList.size() == mMaxCount) {
                    //已经有了2张，
                } else {
                    if (position == mList.size() - 1) {
                        mPopMenu.showAtLocation(mLinearLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
                                0, 0);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    } else {
                        //浏览大图，在新窗口中打开。
                    }
                }
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SelfDrivingIssueActivity.this);
                builder.setTitle("提示信息");
                builder.setMessage("确定删除此图片么？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int size = mImgList.size();
                        if (size > 0 && position < size) {
                            mList.remove(position);
                            mImgList.remove(position);
                        } else {
                            return;
                        }
                        /**
                         * 如果删除空了，就没了，需要补充一个缺省的添加图片
                         */
                        if (mList.size() == 0) {
                            Bitmap bp = BitmapFactory.decodeResource(SelfDrivingIssueActivity.this.getResources(), R.drawable.icon_plus);
                            mList.add(bp);

                        }
                        mAdapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        mGridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE ? true : false;
            }
        });

    }

    /**
     * 显示传的图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUESTCODE_GALLERY:
                    //获得uri
                    Uri galleryUri = data.getData();
                    gotoCrop(galleryUri);
                    break;

                case REQUESTCODE_CAMERA:
                    if (mPictureUri != null) {
                        String imagepath = mPictureUri.getPath().toString();
                        Uri cameraUri = getUriFormFile(imagepath);
                        gotoCrop(cameraUri);
                    }
                    break;
                case REQUESTCODE_CROP:
                    //系统剪切图片
                    bmp = data.getParcelableExtra("data");
                    String imgpath =   PhotoSave.saveBitmap(this,bmp);
                    mImgList.add(imgpath);
                    //更新图片
                    mList.remove(mList.size() - 1);

                    mList.add(bmp);
                    Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_plus);
                    mList.add(bp);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    /**
     * 牌照或者相册截图 操作
     * 相关参数需要参考camera 类裁剪部分
     */
    //启动图片剪切
    private void gotoCrop(Uri inputRri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputRri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);  //设置剪切框的比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);  //图片输出大小
        intent.putExtra("outputY", 300);  //图片输出大小
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CROP);
    }

    //获取bitmap的方法
    public Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bmp;
    }

    private View.OnClickListener popMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopMenu.dismiss();
            switch (v.getId()) {
                case R.id.pop_capture_camera:
                    ++mCount;
                    //指定一个文件路径,以及文件名;
                    File file = new File(Environment.getExternalStorageDirectory().getPath(), "image" + String.valueOf(mCount) + ".jpg");
                    //获取该图片的uri
                    mPictureUri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //将图片的uri传递进去;
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mPictureUri);
                    //把拍照生成图片的路径传过去
                    startActivityForResult(intent, REQUESTCODE_CAMERA);
                    mPopMenu.dismiss();
                    break;
                case R.id.pop_capture_album:
                    Intent intent2 = new Intent(Intent.ACTION_PICK);
                    intent2.setType("image/*");
                    startActivityForResult(intent2, REQUESTCODE_GALLERY);
                    mPopMenu.dismiss();
                    break;

            }
        }
    };
    //获得文件的Uri
    private Uri getUriFormFile(String filePath) {
        return Uri.fromFile(new File(filePath));
    }
    //解决edittext和scrollview滑动冲突
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.activity_self_driving_issue_content_edittext && canVerticalScroll(mContextEdittext))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        if ((view.getId() == R.id.activity_self_driving_issue_title_edittext && canVerticalScroll(mTitleEdittext))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }

        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;
        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }


    @Override
    public void onSuccessResponse(String response, int type) {
        closeDialog();
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject obj = new JSONObject(response);
                if (obj.has("err_code")) {
                    String returnCode = obj.getString("err_code");
                    TAppUtils.logout(mTApplication,returnCode);
                    if (TextUtils.equals(returnCode, "0")) {
                        JSONObject obj2 = obj.getJSONObject("result");
                        String message = obj.getString("message");
                        String pic = obj2.getString("pic_urls");
                        TLog.i(TAG, "------------>" + pic);
                        showToastMsg(message);
                        for(String path : mImgList) {
                            PhotoSave.deletePhoto(path);
                        }
                        finish();
                    } else {
                        String message = obj.getString("message");
                        showToastMsg(message);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
    }
}
