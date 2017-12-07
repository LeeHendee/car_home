package com.example.gtercn.car.cache;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2016-5-18.
 * 缓存管理：文件或内存缓存等
 */
public class CacheManager {

    private static final String TAG = CacheManager.class.getSimpleName();

    /**
     * 缓存时间
     */
    private static final long CACAHE_TIME = 5 * 60 * 1000;

    /**
     * 字符串 存储 缓存。
     * @param context
     * @param object
     * @param file
     * @return
     */
    public static boolean saveObject(Context context, String object, String file){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(fos != null)
                    fos.close();
                if(oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 字符串 读取 缓存。
     * @param context
     * @param file
     * @return
     */
    public  static String readObject(Context context, String file){
        if(!isExistDataCache(context,file)){
            return  null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (String)ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(fis != null)
                    fis.close();
                if(ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 处理缓存文件存在问题
     * @param context
     * @param file
     * @return
     */
    public static boolean isExistDataCache(Context context, String file){
        if(context == null)
            return false;
        boolean isExist = false;
        File data = context.getFileStreamPath(file);
        if(data.exists())
            isExist = true;
        return isExist;
    }

    /**
     * 缓存数据有效期
     * @param context
     * @param file
     * @return
     */
    public static boolean isCacheValidate(Context context, String file){
        File data = context.getFileStreamPath(file);
        if(!data.exists()){
            return false;
        }

        long deltaTime = System.currentTimeMillis() - data.lastModified();
        return deltaTime > CACAHE_TIME ? true:false;
    }

    /**
     * 清除缓存文件
     * @param context
     */
    public static void cleanCache(Context context){
        deleteFileByDirectory(context.getCacheDir());
        deleteFileByDirectory(context.getFilesDir());
    }

    /**
     * 清除其它文件夹之下的文件。
     * @param directory
     */
    public static void cleanOthersDirectory(File directory){
        deleteFileByDirectory(directory);
    }


    /**
     * 删除某个文件夹之下的文件。
     * @param directory
     */
    private static void deleteFileByDirectory(File directory){
        if(directory != null && directory.exists() && directory.isDirectory()){
            for(File child : directory.listFiles()){
                if(child.isDirectory()){
                    deleteFileByDirectory(child);
                }
                child.delete();
            }
        }
    }


}
