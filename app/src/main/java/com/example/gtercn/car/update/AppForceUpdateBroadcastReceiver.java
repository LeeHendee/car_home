package com.example.gtercn.car.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.example.gtercn.car.R;
import com.example.gtercn.car.base.AppManager;
import com.example.gtercn.car.bean.UpdateBean;

/**
 * 强制更新窗口，
 * 更新或退出应用。
 */
public class AppForceUpdateBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		UpdateBean bean = (UpdateBean) intent.getSerializableExtra("bean");
		String url = bean.getUrl();
		if(!TextUtils.isEmpty(url)){
			if(Build.VERSION.SDK_INT >= 23){
				if(Settings.canDrawOverlays(context)){
					appPopWindow(context, bean);
				}else {
					Intent intent_window = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
					intent_window.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent_window);
				}
			}else {
				appPopWindow(context, bean);
			}
		}
	}

	private void appPopWindow(Context context, UpdateBean bean){
		final View layout = LayoutInflater.from(context).inflate(R.layout.app_update_pop, null);
		float density = context.getResources().getDisplayMetrics().density;
		int screenW = context.getResources().getDisplayMetrics().widthPixels;
		int width =  screenW - (int)(40*density); 
		int height = (int)(width * 0.75f);
		final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		LayoutParams layoutParams;
		layoutParams = new LayoutParams(width, height,
										LayoutParams.TYPE_SYSTEM_ERROR,
										LayoutParams.FLAG_DIM_BEHIND,
										PixelFormat.TRANSPARENT);
		layoutParams.gravity = Gravity.CENTER;
		windowManager.addView(layout, layoutParams); 
		Button ok = (Button) layout.findViewById(R.id.app_pop_ok);
		Button quit = (Button) layout.findViewById(R.id.app_pop_cancel);
		quit.setText("退出");

		TextView versionTv = (TextView) layout.findViewById(R.id.favorities_header_resume);
		TextView textView = (TextView) layout.findViewById(R.id.favorities_header_content);
		String minVersion = bean.getMin_version();
		versionTv.setText("最低可用版本" + minVersion + "");

		String content = bean.getMin_content();
		if(!TextUtils.isEmpty(content)){
			textView.setText(content);
		}

		final String url = bean.getUrl();

		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				windowManager.removeView(layout);
				AppDownloadManager.getInstance().downloadApp(url);
				AppManager.getInstance().finishAllActivity();
			}
		});
		
		quit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				windowManager.removeView(layout);
				AppManager.getInstance().finishAllActivity();
			}
		});
		
	}
	
}
