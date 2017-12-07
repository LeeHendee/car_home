package com.example.gtercn.car.utils;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.lang.ref.WeakReference;

/**
 * author : LiHang.
 * data : 2017/2/14.
 * description:
 */
public class ShareUtil {

    private static Activity mActivity;

    private static final SHARE_MEDIA[] displayList = new SHARE_MEDIA[]{
            SHARE_MEDIA.SINA,
            SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.WEIXIN_CIRCLE,
//            SHARE_MEDIA.QQ,
//            SHARE_MEDIA.QZONE
    };

    /**
     * 推广分享
     *
     *  UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
     *
     *
     *
     UMImage image = new UMImage(ShareActivity.this, file);//本地文件
     UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
     UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
     UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流

     *
     * @param activity
     * @param subject sina 不支持；QQ/QZone 最大20个字符；微信支持。
     * @param txt sina 最多140个字符；QQ/QZone 最大30个字符；微信支持超长字符
     * @param img sina 开头文字+url;QQ/QZone ； 微信？
     */
    public static void  GeneralizeShare(Activity activity, String subject, String txt,
                                        UMImage img){

        mActivity = (Activity)(new WeakReference(activity)).get();
        new ShareAction(activity)
                .setDisplayList(displayList)
                .withSubject(subject)
                .withText(txt)
                .withMedia(img)
                .setCallback(umShareListener)
                .open();

    }

    /**
     *  产品分享
     *
     * UMWeb  web = new UMWeb(Defaultcontent.url);
     web.setTitle("This is music title");//标题
     web.setThumb(thumb);  //缩略图
     web.setDescription("my description");//描述
     *
     * @param activity
     * @param title
     * @param txt
     * @param web sina jpg/png/gif,url及本地图片; qq/qqZone ;微信
     *
     *
     */
    public static void ProductShare(Activity activity, String title, String txt,
                                    UMWeb web){
        mActivity = (Activity)(new WeakReference(activity)).get();
        new ShareAction(activity).setDisplayList(displayList)
                .withSubject(title)
                .withText(txt)
                .withMedia(web)
                .setCallback(umShareListener)
                .open();

    }

    private static  UMShareListener umShareListener = new UMShareListener(){

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(mActivity,share_media + " 分享取消了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            TLog.d("plat","platform"+share_media);
            Toast.makeText(mActivity, share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

            Toast.makeText(mActivity,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(throwable !=null){
                TLog.d("throw","throw:" + throwable.getMessage());
            }
        }
    };

}
