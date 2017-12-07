package com.example.gtercn.car.update;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class UpdateUtils {

	/**
	 * normal update
	 */
	public static final String APP_UPDATE = "com.example.gtercn.car.APP_UPDATE";

	/**
	 * force update
	 */
	public static final String APP_FORCE = "com.example.gtercn.car.APP_FORCE";

	public static int getVerCode(Context context) {
		int vercode = -1;
		try {
			vercode = context.getPackageManager().getPackageInfo("com.example.gtercn.car", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return vercode;
		}
		return vercode;
	}

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo("com.example.gtercn.car", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return verName;
		}
		return verName;
	}
}
