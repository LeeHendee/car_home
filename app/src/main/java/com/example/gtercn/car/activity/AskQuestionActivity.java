package com.example.gtercn.car.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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
import com.example.gtercn.car.bean.User;
import com.example.gtercn.car.interfaces.ResponseStringListener;
import com.example.gtercn.car.net.THttpOpenHelper;
import com.example.gtercn.car.utils.MD5;
import com.example.gtercn.car.utils.PhotoSave;
import com.example.gtercn.car.utils.TAppUtils;
import com.example.gtercn.car.utils.TLog;
import com.example.gtercn.car.widget.PopCaptureMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static vi.com.gdi.bgl.android.java.EnvDrawText.bmp;

/**
 * 我要提问的页面
 */
public class AskQuestionActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener, ResponseStringListener {

    private static final String TAG = "AskQuestionActivity";

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

    private EditText mContentEt;

    private TextView mBack;

    private Button mSubmit;

    private CarApplication mApplication;

    private CarApplication mApp;

    @BindView(R.id.toolbar)
    Toolbar mBar;

    @BindView(R.id.toolbar_title)
    TextView mTitle;

    @BindView(R.id.toolbar_back_tv)
    TextView mBackTv;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_issue);
        ButterKnife.bind(this);
        mApp = (CarApplication) getApplication();
        mGridView = (GridView) findViewById(R.id.activity_announce_issue_gridview);
        mPopMenu = new PopCaptureMenu(mContext.getApplicationContext(), popMenuListener);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_announce_issue);
        mContentEt = (EditText) findViewById(R.id.activity_announce_issue_content_edittext);
        //防止刚打开activity就弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getGridView();
        mApplication = (CarApplication) getApplication();

        mSubmit = (Button) findViewById(R.id.ask_question_submit);
        mSubmit.setOnClickListener(this);

        mTitle.setText("我要提问");

        mBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ask_question_submit:
                submitQuestion();
                break;
        }
    }

    /**
     * 将问题提交到服务器
     * 参数:token,t,sign QueryString
     * files,title,type = 1(问题墙);
     */
    private void submitQuestion() {
        User user = mApp.getUser();
        if (user == null) {
            TLog.i(TAG, "---->>login info is error!! please check it out");
            return;
        }
        String token = user.getResult().getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        String sign = MD5.getSign(ApiManager.URL_QUESTION_SUBMIT, user);
        String t = MD5.gettimes();
        String url = getUrl(ApiManager.URL_QUESTION_SUBMIT, token, t, sign);

        Map picMap = new HashMap<>();

        if (mImgList != null && mImgList.size() > 0) {
            TLog.i(TAG, "---->>commitQuestion : " + mImgList.toString());
            if (mImgList.size() == 1) {
                picMap.put("files", mImgList.get(0));
                TLog.i(TAG, "---->>files.key " + mImgList.get(0));
            } else if (mImgList.size() == 2) {
                picMap.put("files", mImgList.get(0));
                picMap.put("files1", mImgList.get(1));
            } else
                picMap.put("files", "");
        }
        String content = mContentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToastMsg("请填写提问内容!");
            return;
        }
        Map params = new HashMap<>();
        params.put("content", content);
        params.put("type", "1");
        THttpOpenHelper.newInstance().requestFormDataFilePost(url, params, picMap, this, 1, TAG);
        showDialog(getString(R.string.dialog_commit));
    }

    /**
     * GridView的相关逻辑
     */
    private void getGridView() {
        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_take_photo);
        mList.add(bp);

        mAdapter = new RoadStateAdapter(getApplicationContext(), mList, mGridView);

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
                    } else {
                        //浏览大图，在新窗口中打开。
                    }
                }
            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AskQuestionActivity.this);
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
                            Bitmap bp = BitmapFactory.decodeResource(AskQuestionActivity.this.getResources(), R.drawable.icon_take_photo);
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
                        //这种方式只能传小图片 ,URI可以传大图片
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
                    Bitmap bp2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_plus);
                    mList.add(bp2);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

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

    //获得文件的Uri
    private Uri getUriFormFile(String filePath) {
        return Uri.fromFile(new File(filePath));
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
                    //已经将预置的uri传递过去,会导致onResult中返回的intent为空;
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

    //解决editText和scrollview滑动冲突
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.activity_announce_issue_content_edittext && canVerticalScroll(mContentEt))) {
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
        if (response != null) {
            TLog.i(TAG, "---->>response is " + response);
            try {
                JSONObject obj = new JSONObject(response);
                if (obj.has("err_code")) {
                    try {
                        String code = obj.getString("err_code");
                        TAppUtils.logout(mApplication,code);
                        String message = obj.getString("message");
                        showToastMsg(message);
                        if (TextUtils.equals(code, "0")) {
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

    @Override
    public void onErrorResponse(VolleyError error, int type) {
        closeDialog();
        showToastMsg(error);
    }


    @Override
    protected void onExecuteSuccess(String result, int type) {

    }

    @Override
    protected void onExecuteFailure(int type) {

    }

}
