package com.example.gtercn.car.base;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gtercn.car.cache.CacheManager;
import com.example.gtercn.car.utils.MD5;


/**
 * Created by Administrator on 2016-5-31.
 * 存缓存任务类
 */
public class SaveCacheTask extends AsyncTask<Void, Void, Void>{

    private Context mContext;

    private String mObject;

    private String mFile;

    public SaveCacheTask(Context context, String object, String file){
        this.mContext = context;
        this.mObject = object;
        this.mFile = file;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String file = MD5.hashKeyForDisk(mFile);
        CacheManager.saveObject(mContext, mObject, file);
        return null;
    }

}
