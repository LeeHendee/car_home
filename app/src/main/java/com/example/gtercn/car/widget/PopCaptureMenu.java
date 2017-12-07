package com.example.gtercn.car.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.gtercn.car.R;


/**
 * Created by Administrator on 2016-7-15.
 * 弹出电话菜单
 */
public class PopCaptureMenu extends PopupWindow {
    private static final String TAG = PopCaptureMenu.class.getSimpleName();

    private Button mCamera;

    private Button mAlbum;

    private Button mCancel;

    private View mView;

    public PopCaptureMenu(Context context, View.OnClickListener listener) {
        super();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.pop_capture_menu, null);
        mCamera = (Button) mView.findViewById(R.id.pop_capture_camera);
        mAlbum = (Button) mView.findViewById(R.id.pop_capture_album);
        mCancel = (Button) mView.findViewById(R.id.pop_capture_cancel);
        mCamera.setText("相机");
        mAlbum.setText("相册");
        /**
         * 关闭Pop
         */
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mCamera.setOnClickListener(listener);
        mAlbum.setOnClickListener(listener);
        /**
         * 设置当前的POP的 View
         */
        setContentView(mView);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        /**
         * 设置Pop 可点击
         */
        setFocusable(true);
        setAnimationStyle(R.style.pop_menu);

        /**
         * 配置背景 半透明颜色
         */
        ColorDrawable drawable = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(drawable);

        /**
         * 点击pop 外部消失
         */
        setOutsideTouchable(true);

    }

}
