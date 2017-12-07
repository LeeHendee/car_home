package com.example.gtercn.car;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.umeng.socialize.media.WBShareCallBackActivity;


public class WBShareActivity extends WBShareCallBackActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Toast.makeText(WBShareActivity.this, "新浪微博的回调页面!", Toast.LENGTH_SHORT).show();
    }
}
