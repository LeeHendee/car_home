package com.example.gtercn.car.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * 保存图片
 */
public class SavePhoto {

    public static final String DIR = Environment.getExternalStorageDirectory() + File.separator + "carhome" + File.separator;

    private static String filename;

    private static String filepath;

    public static String getFilename() {

        return filename;
    }

    public static String getFilepath() {

        return filepath;
    }

    public static void getPhoto(Bitmap bitmap){
        new DateFormat();
        String name= DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+".jpg";
        FileOutputStream fout = null;
        filename = DIR + name;
        filepath = DIR + "/" + name;
        File file = new File(filepath,filename);
        try {
            fout = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(fout!=null) {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  保存bmp 为文件
     * @param filename
     * @param bmp
     * @return
     */
    public static boolean saveBitmap(String filename, Bitmap bmp) {

        //删除文件，防止复写
        deleteFile(filename);

        File f =  null;
        boolean isOk = getDir(filename);
        if(isOk){
            if(TextUtils.isEmpty(filename))
                return false;
            else
                f = new File(filename);
        }else {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(f));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bos != null)
                    bos.close();
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    public static Bitmap getBitmapFromFile(String filePath){
        return BitmapFactory.decodeFile(filePath);
    }

    public static  void saveDrawableToBitmap(String filePath, Bitmap drawable){
//        RoundedDrawable bitmapDrawable = (RoundedDrawable) drawable;
//        Bitmap bitmap = bitmapDrawable.getSourceBitmap();
        saveBitmap(filePath, drawable);
    }

    public static boolean deleteFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
            return true;
        }
        return false;
    }

    public static boolean deleteAllFile(){
        File file = new File(DIR);
        if(!file.exists()){
            return false;
        }else if(!file.isDirectory()){
            return false;
        }else {
            String[] tmpList = file.list();
            File temp = null;
            for(String str: tmpList){
                if(DIR.endsWith(File.separator)){
                    temp = new File(DIR + str);
                }else {
                    temp = new File(DIR + File.separator + str);
                }
                if(temp.exists()){
                    TLog.i("SavePhoto", "--->>>> temp is " + temp.getAbsolutePath());
                    boolean isDone = temp.delete();
                    if(isDone)
                        TLog.i("SavePhoto", "--->>>> file is deleted!!!!!!!!!!! <<<<----");
                    else
                        TLog.i("SavePhoto", "--->>>> deleted fail !!!!!!!!!!! <<<<----");
                }

            }
        }
        //删除目录
        file.delete();

        return true;
    }

    /**
     * 指定了固定的头像目录。
     * @param filePath
     * @return
     */
    private static boolean getDir(String filePath){
        File dir = new File(DIR);
        if(!dir.exists()){
            boolean isCreate = dir.mkdir();
            if(isCreate)
                return true;
            else
                return false;
        }
        return true;
    }




}
