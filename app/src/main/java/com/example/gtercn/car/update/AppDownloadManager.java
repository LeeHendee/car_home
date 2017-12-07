package com.example.gtercn.car.update;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.example.gtercn.car.R;
import com.example.gtercn.car.utils.ContextService;

import java.io.File;

public class AppDownloadManager {
	private static AppDownloadManager instance = null;
	private static Context mContext;
	private static long reference = 0l;
	private static Uri file;
	
	private AppDownloadManager(){
		mContext = ContextService.getInstance().getContext();
	}
	
	public static synchronized AppDownloadManager getInstance(){
		if(instance == null)
			instance = new AppDownloadManager();
		return instance;
	}
	
	public static synchronized AppDownloadManager getInstance(Context context){
		if(instance == null)
			instance = new AppDownloadManager();
		mContext = context;
		return instance;
	}
	
	public void downloadApp(final String url){
		String serviceString = Context.DOWNLOAD_SERVICE;
		DownloadManager downloadManager;
		downloadManager = (DownloadManager) mContext.getSystemService(serviceString);
		String[] array = url.split("/");
		String name = array[array.length - 1];

		Uri uri = Uri.parse(url);
		if(!TextUtils.equals(uri.getScheme(),"http")){
			return;
		}

		Request request = new Request(uri);
		file = Uri.fromFile(new File(Environment.getExternalStorageDirectory() +"/", name));
		request.setDestinationUri(file);
		request.setMimeType("application/vnd.android.package-archive");
		request.setTitle(mContext.getResources().getText(R.string.app_name));
		request.setDescription(name);
		reference = downloadManager.enqueue(request);
	}
	
	public long getDownloadAppId(){
		return reference;
	}
	
	public Uri getFileUri(){
		return file;
	}
	
}
