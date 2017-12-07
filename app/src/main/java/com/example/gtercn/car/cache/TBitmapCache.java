package com.example.gtercn.car.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.example.gtercn.car.utils.ContextService;


import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016-5-24.
 */
public class TBitmapCache implements ImageLoader.ImageCache{
    private static final String TAG = TBitmapCache.class.getSimpleName();
    private LruCache<String,Bitmap> mMemoryCache;

    private DiskLruCache mDiskLruCache;

    private final Object mDiskCacheLock = new Object();

    private static int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);

    private static int cacheSize = maxMemory/8;

    private boolean mDiskCacheStarting = true;
    private static  int DISK_CACHE_SIZE = cacheSize * 2;
    public static final String DISK_CACHE_SUBDIR = "thumbnails";
    private static final int DISK_CACHE_INDEX = 0;

    static{
        maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);

        cacheSize = maxMemory/8;

         DISK_CACHE_SIZE = cacheSize * 2;
    }

    public TBitmapCache(){
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };

        File cacheDir = getDiskCacheDir(ContextService.getInstance().getContext(),
                DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);

    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bmp = null;
        if(url != null){
            bmp = mMemoryCache.get(url);
            if(bmp == null){
                //get bitmap from diskCache 从磁盘获取图片.
                bmp = getBitmapFromDiskCache(url);

                if(bmp != null){
                    if (getBitmapFromMemCache(url) == null) {
                        mMemoryCache.put(url, bmp);
                    }
                }
            }
        }
        return bmp;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if(getBitmapFromMemCache(url) == null){
            mMemoryCache.put(url, bitmap);
            //写图片到磁盘缓存
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(url,bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String url){
        return mMemoryCache.get(url);
    }

    public  File getDiskCacheDir(Context context, String uniqueName){
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !Environment.isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取程序版本号
     * @param context
     * @return
     */
    public static int getAppVersion(Context context){
        try{
            PackageInfo info = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        }catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
        }
        return 1;
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        // Add to memory cache as before
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }

        // Also add to disk cache
        synchronized (mDiskCacheLock) {
            // Add to disk cache
            if (mDiskLruCache != null) {
                final String keyk = hashKeyForDisk(key);
                OutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(keyk);
                    if (snapshot == null) {
                        final DiskLruCache.Editor editor = mDiskLruCache.edit(keyk);
                        if (editor != null) {
                            out = editor.newOutputStream(DISK_CACHE_INDEX);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            editor.commit();
                            out.close();
                        }
                    } else {
                        snapshot.getInputStream(DISK_CACHE_INDEX).close();
                    }
                } catch (final IOException e) {
                } catch (Exception e) {
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {}
                }
            }
        }
        //END_INCLUDE(add_bitmap_to_cache)
    }

    public Bitmap getBitmapFromDiskCache(String url) {
        //BEGIN_INCLUDE(get_bitmap_from_disk_cache)
        final String key = hashKeyForDisk(url);
        Bitmap bitmap = null;

        synchronized (mDiskCacheLock) {
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {}
            }
            if (mDiskLruCache != null) {
                InputStream inputStream = null;
                try {
                    final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(DISK_CACHE_INDEX);
                        if (inputStream != null) {
                            FileDescriptor fd = ((FileInputStream) inputStream).getFD();
                            bitmap = BitmapFactory.decodeFileDescriptor(fd);
                        }
                    }
                } catch (final IOException e) {
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {}
                }
            }
            return bitmap;
        }
        //END_INCLUDE(get_bitmap_from_disk_cache)
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                File cacheDir = params[0];
                try{
                    mDiskLruCache = DiskLruCache.open(cacheDir,
                            getAppVersion(ContextService.getInstance().getContext()),1,
                            DISK_CACHE_SIZE);
                    mDiskCacheStarting = false; // Finished initialization
                    mDiskCacheLock.notifyAll(); // Wake any waiting threads
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {
        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Object... params) {
            final String imageKey =(String)(params[0]);
            final Bitmap bitmap = (Bitmap)(params[1]);
            // Check disk cache in background thread
//            Bitmap bitmap = getBitmapFromDiskCache(imageKey);

//            if (bitmap == null) { // Not found in disk cache
//                // Process as normal
//                final Bitmap bitmap = decodeSampledBitmapFromResource(
//                        getResources(), params[0], 100, 100));
//            }

            // Add final bitmap to caches
            addBitmapToCache(imageKey, bitmap);

            return bitmap;
        }
    }


    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
     * disk filename.
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public void cleanDiskCache(){
        try {
            if(mDiskLruCache != null)
                mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getSizeDiskCache(){

        if(mDiskLruCache != null)
            return mDiskLruCache.size();
        else
            return 0l;
    }

}

