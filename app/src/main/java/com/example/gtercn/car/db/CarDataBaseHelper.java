package com.example.gtercn.car.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017-3-2.
 * 定义数据库
 */

public class CarDataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = CarDataBaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "car.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_FOUR = "CREATE TABLE IF NOT EXISTS "
            + FourTypeService.Four.TABLE_NAME + " ( "
            + FourTypeService.Four._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + FourTypeService.Four.COLUMN_NAME_ID + " VARCHAR NOT NULL ,"
            + FourTypeService.Four.COLUMN_NAME_SHOP_ID + " VARCHAR '',"
            + FourTypeService.Four.COLUMN_NAME_SHOP_NAME + " VARCHAR NOT NULL ,"
            + FourTypeService.Four.COLUMN_NAME_SCORE + " VARCHAR '' ,"
            + FourTypeService.Four.COLUMN_NAME_SHOP_DESCIPTION + " VARCHAR '' ,"
            + FourTypeService.Four.COLUMN_NAME_DETAIL_ADDRESS + " VARCHAR '' ,"
            + FourTypeService.Four.COLUMN_NAME_SHOP_PIC_URL + " VARCHAR '' ,"
            + FourTypeService.Four.COLUMN_NAME_LONGTITUDE + " VARCHAR NOT NULL ,"
            + FourTypeService.Four.COLUMN_NAME_LATITUDE + " VARCHAR NOT NULL ,"
            + FourTypeService.Four.COLUMN_NAME_CITY_CODE + " VARCHAR NOT NULL ,"
            + FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE + " VARCHAR '0' ,"
            + FourTypeService.Four.COLUMN_NAME_CLEAN_SERVICE + " VARCHAR '0' ,"
            + FourTypeService.Four.COLUMN_NAME_MAINTAIN_SERVICE + " VARCHAR '0' ,"
            + FourTypeService.Four.COLUMN_NAME_TYRE_SERVICE + " VARCHAR '0' ,"
            + FourTypeService.Four.COLUMN_NAME_SERVICE_LIST+" VARCHAR '',"
            + FourTypeService.Four.COLUMN_NAME_TEL_NUM_LIST + " VARCHAR '' ,"

            + FourTypeService.Four.COLUMN_NAME_TYPE_VALUE + " VARCHAR '' )";

    private static final String CREATE_TABLE_RESCUE = "CREATE TABLE IF NOT EXISTS "
            + RescueService.Rescue.TABLE_NAME + " ( "
            + RescueService.Rescue._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + RescueService.Rescue.COLUMN_NAME_ID + " VARCHAR NOT NULL ,"
            + RescueService.Rescue.COLUMN_NAME_CITY_CODE + " VARCHAR NOT NULL ,"
            + RescueService.Rescue.COLUMN_NAME_TYPE_VALUE + " VARCHAR '' ,"
            + RescueService.Rescue.COLUMN_NAME_HEAD_PORTRAIT_URL + " VARCHAR '' ,"
            + RescueService.Rescue.COLUMN_NAME_SHOP_NAME + " VARCHAR NOT NULL ,"
            + RescueService.Rescue.COLUMN_NAME_SHOP_SCORE + " VARCHAR '1' ,"
            + RescueService.Rescue.COLUMN_NAME_LONGITUDE + " VARCHAR NOT NULL ,"
            + RescueService.Rescue.COLUMN_NAME_LATITUDE + " VARCHAR NOT NULL ,"

            + RescueService.Rescue.COLUMN_NAME_CATEGORY + " VARCHAR NOT NULL ,"
            + RescueService.Rescue.COLUMN_NAME_DISTANCE_LIST + " VARCHAR NOT NULL ,"
            + RescueService.Rescue.COLUMN_NAME_CITY+" VARCHAR '' ,"
            + RescueService.Rescue.COLUMN_NAME_DETAIL_ADDRESS+" VARCHAR '' ,"
            + RescueService.Rescue.COLUMN_NAME_TEL_NUMBER_LIST+" VARCHAR '' ,"
            + RescueService.Rescue.COLUMN_NAME_UPDATE_TIME + " VARCHAR '' ,"
            + RescueService.Rescue.COLUMN_NAME_INSERT_TIME + " VARCHAR '' ,"
            + RescueService.Rescue.COLUMN_NAME_DELETE_FLAG + " VARCHAR '0' )";


    CarDataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOUR);
        db.execSQL(CREATE_TABLE_RESCUE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + FourTypeService.Four.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RescueService.Rescue.TABLE_NAME);
        onCreate(db);
    }
}
