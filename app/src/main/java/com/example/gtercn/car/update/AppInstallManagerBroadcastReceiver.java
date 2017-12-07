package com.example.gtercn.car.update;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class AppInstallManagerBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		long myDownloadReference = AppDownloadManager.getInstance().getDownloadAppId();
		long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
		String fileUri = null;
		if(myDownloadReference == reference){
			Query myDownloadQuery = new Query();
			myDownloadQuery.setFilterById(reference);
			String serviceString = Context.DOWNLOAD_SERVICE;
			DownloadManager downloadManager = (DownloadManager) context.getSystemService(serviceString);
			Cursor myDownload = downloadManager.query(myDownloadQuery);
			if(myDownload.moveToFirst()){
				int fileUriId = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
				fileUri = myDownload.getString(fileUriId);
			}
			myDownload.close();
			if(fileUri != null && !TextUtils.isEmpty(fileUri)){
				Intent install = new Intent(Intent.ACTION_VIEW);
				install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				install.setDataAndType(Uri.parse(fileUri),
						"application/vnd.android.package-archive");
				context.startActivity(install);
			}
		}
	}

}
