package com.example.gtercn.car.db;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017-3-2.
 * 4 类服务 内容提供器
 */

public class FourServiceContentProvider extends ContentProvider {
    private static final String TAG = FourServiceContentProvider.class.getSimpleName();
    private static HashMap<String, String> sFourProjectionMap;

    public static final String[] FOUR_PROJECTION = new String[]{
            FourTypeService.Four.COLUMN_NAME_ID,
            FourTypeService.Four._ID,
            FourTypeService.Four.COLUMN_NAME_SHOP_ID,
            FourTypeService.Four.COLUMN_NAME_SHOP_NAME,
            FourTypeService.Four.COLUMN_NAME_SHOP_DESCIPTION,
            FourTypeService.Four.COLUMN_NAME_DETAIL_ADDRESS,
            FourTypeService.Four.COLUMN_NAME_SHOP_PIC_URL,
            FourTypeService.Four.COLUMN_NAME_LONGTITUDE,
            FourTypeService.Four.COLUMN_NAME_LATITUDE,
            FourTypeService.Four.COLUMN_NAME_CITY_CODE,
            FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE,
            FourTypeService.Four.COLUMN_NAME_CLEAN_SERVICE,
            FourTypeService.Four.COLUMN_NAME_MAINTAIN_SERVICE,
            FourTypeService.Four.COLUMN_NAME_TYRE_SERVICE,
            FourTypeService.Four.COLUMN_NAME_TEL_NUM_LIST,
            FourTypeService.Four.COLUMN_NAME_SCORE,
            FourTypeService.Four.COLUMN_NAME_TYPE_VALUE
    };

    private static final int FOURS = 1;
    private static final int FOURS_ID = 2;

    private static final UriMatcher sUriMatcher;

    private CarDataBaseHelper mOpenHelper;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(FourTypeService.AUTHORITY, "Four", FOURS);
        sUriMatcher.addURI(FourTypeService.AUTHORITY, "Four/#", FOURS_ID);

        sFourProjectionMap = new HashMap<>();
        sFourProjectionMap.put(FourTypeService.Four._ID, FourTypeService.Four._ID);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_ID, FourTypeService.Four.COLUMN_NAME_ID);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_SHOP_ID, FourTypeService.Four.COLUMN_NAME_SHOP_ID);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_SHOP_NAME, FourTypeService.Four.COLUMN_NAME_SHOP_NAME);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_SCORE,FourTypeService.Four.COLUMN_NAME_SCORE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_SHOP_DESCIPTION, FourTypeService.Four.COLUMN_NAME_SHOP_DESCIPTION);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_DETAIL_ADDRESS, FourTypeService.Four.COLUMN_NAME_DETAIL_ADDRESS);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_SHOP_PIC_URL, FourTypeService.Four.COLUMN_NAME_SHOP_PIC_URL);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_LONGTITUDE, FourTypeService.Four.COLUMN_NAME_LONGTITUDE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_LATITUDE, FourTypeService.Four.COLUMN_NAME_LATITUDE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_CITY_CODE, FourTypeService.Four.COLUMN_NAME_CITY_CODE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE, FourTypeService.Four.COLUMN_NAME_REPAIR_SERVICE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_CLEAN_SERVICE, FourTypeService.Four.COLUMN_NAME_CLEAN_SERVICE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_MAINTAIN_SERVICE, FourTypeService.Four.COLUMN_NAME_MAINTAIN_SERVICE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_TYRE_SERVICE, FourTypeService.Four.COLUMN_NAME_TYRE_SERVICE);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_TEL_NUM_LIST, FourTypeService.Four.COLUMN_NAME_TEL_NUM_LIST);
        sFourProjectionMap.put(FourTypeService.Four.COLUMN_NAME_TYPE_VALUE,FourTypeService.Four.COLUMN_NAME_TYPE_VALUE);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)){
            case FOURS:
                count = db.delete(FourTypeService.Four.TABLE_NAME, selection, selectionArgs);
                break;
            case FOURS_ID:
                String id = uri.getPathSegments().get(FourTypeService.Four.FOUR_ID_PATH_POSITION);
                if(TextUtils.isEmpty(selection)){
                    selection = "_id=" + id;
                }else {
                    selection = "_id=" + id + " AND ( " + selection + " )";
                }
                count = db.delete(FourTypeService.Four.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CarDataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(FourTypeService.Four.TABLE_NAME);
        boolean distinct =false;
        switch (sUriMatcher.match(uri)){
            case FOURS:
                qb.setProjectionMap(sFourProjectionMap);
                distinct = true;
                break;
            case FOURS_ID:
                qb.setProjectionMap(sFourProjectionMap);
                qb.appendWhere(
                        FourTypeService.Four._ID + "="
                                + uri.getPathSegments().get(FourTypeService.Four.FOUR_ID_PATH_POSITION)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        String orderBy;
        if(TextUtils.isEmpty(sortOrder)){
            orderBy = FourTypeService.Four.DEFAULT_SORT_ORDER;
        }else {
            orderBy = sortOrder;
        }
        qb.setDistinct(distinct);
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(
                db,
                projection,
                selection,
                selectionArgs,
                FourTypeService.Four.COLUMN_NAME_ID,
                null,
                orderBy
        );
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case FOURS:
                return FourTypeService.CONTENT_TYPE;
            case FOURS_ID:
                return FourTypeService.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = 0;
        Uri newUri = null;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case FOURS:
                rowId = db.insert(FourTypeService.Four.TABLE_NAME, null, values);
                newUri = ContentUris.withAppendedId(uri, rowId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }


    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(operations);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)){
            case FOURS:
                count = db.update(FourTypeService.Four.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FOURS_ID:
                String id = uri.getPathSegments().get(FourTypeService.Four.FOUR_ID_PATH_POSITION);

                if(TextUtils.isEmpty(selection)){
                    selection = "_id=" + id;
                }else {
                    selection = "_id=" + id + " AND ( " + selection + " )";
                }
                count = db.update(FourTypeService.Four.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw  new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
