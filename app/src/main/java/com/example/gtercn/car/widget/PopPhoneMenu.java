package com.example.gtercn.car.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.gtercn.car.R;
import com.example.gtercn.car.adapter.PopWindowPhoneAdapter;

import java.util.List;


/**
 * Created by Administrator on 2016-7-15.
 * 弹出电话菜单
 */
public class PopPhoneMenu extends PopupWindow {
    private static final String TAG = PopPhoneMenu.class.getSimpleName();

    private Button mCamera;

    private Button mAlbum;

    private Button mCancel;
    private ListView listView;
    private View mView;

    public PopPhoneMenu(Context context, List<String> mphoneList) {
        super();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.pop_phone_menu, null);
        mCancel = (Button) mView.findViewById(R.id.pop_phone_cancel);
        listView = (ListView) mView.findViewById(R.id.pop_phone_listview);
        PopWindowPhoneAdapter popWindowPhoneAdapter = new PopWindowPhoneAdapter(context, mphoneList, this);
        listView.setAdapter(popWindowPhoneAdapter);
        /**
         * 关闭Pop
         */
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


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
