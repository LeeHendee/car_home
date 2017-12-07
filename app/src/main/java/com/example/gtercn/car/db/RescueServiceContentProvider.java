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
 * 紧急救援 内容提供器
 */

public class RescueServiceContentProvider extends ContentProvider {
    private static final String TAG = RescueServiceContentProvider.class.getSimpleName();
    private static HashMap<String, String> sRescueProjectionMap;

    public static final String[] RESCUE_PROJECTION = new String[]{
            RescueService.Rescue.COLUMN_NAME_ID,
            RescueService.Rescue._ID,
            RescueService.Rescue.COLUMN_NAME_CITY_CODE,
            RescueService.Rescue.COLUMN_NAME_TYPE_VALUE,
            RescueService.Rescue.COLUMN_NAME_HEAD_PORTRAIT_URL,
            RescueService.Rescue.COLUMN_NAME_SHOP_NAME,
            RescueService.Rescue.COLUMN_NAME_SHOP_SCORE,
            RescueService.Rescue.COLUMN_NAME_LONGITUDE,
            RescueService.Rescue.COLUMN_NAME_LATITUDE,
            RescueService.Rescue.COLUMN_NAME_CATEGORY,
            RescueService.Rescue.COLUMN_NAME_DISTANCE_LIST,
            RescueService.Rescue.COLUMN_NAME_UPDATE_TIME,
            RescueService.Rescue.COLUMN_NAME_INSERT_TIME,
            RescueService.Rescue.COLUMN_NAME_DELETE_FLAG,
            RescueService.Rescue.COLUMN_NAME_CITY,
            RescueService.Rescue.COLUMN_NAME_DETAIL_ADDRESS,
            RescueService.Rescue.COLUMN_NAME_TEL_NUMBER_LIST
    };

    private static final int RESCUES = 1;
    private static final int RESCUES_ID = 2;
    private static final UriMatcher sUriMatcher;

    private CarDataBaseHelper mOpenHelper;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(RescueService.AUTHORITY, "Rescue", RESCUES);
        sUriMatcher.addURI(RescueService.AUTHORITY, "Rescue/#", RESCUES_ID);

        sRescueProjectionMap = new HashMap<>();
        sRescueProjectionMap.put(RescueService.Rescue._ID, RescueService.Rescue._ID);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_ID, RescueService.Rescue.COLUMN_NAME_ID);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_CITY_CODE, RescueService.Rescue.COLUMN_NAME_CITY_CODE);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_TYPE_VALUE, RescueService.Rescue.COLUMN_NAME_TYPE_VALUE);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_HEAD_PORTRAIT_URL, RescueService.Rescue.COLUMN_NAME_HEAD_PORTRAIT_URL);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_SHOP_NAME, RescueService.Rescue.COLUMN_NAME_SHOP_NAME);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_SHOP_SCORE, RescueService.Rescue.COLUMN_NAME_SHOP_SCORE);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_LONGITUDE, RescueService.Rescue.COLUMN_NAME_LONGITUDE);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_LATITUDE, RescueService.Rescue.COLUMN_NAME_LATITUDE);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_CATEGORY, RescueService.Rescue.COLUMN_NAME_CATEGORY);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_DISTANCE_LIST, RescueService.Rescue.COLUMN_NAME_DISTANCE_LIST);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_UPDATE_TIME, RescueService.Rescue.COLUMN_NAME_UPDATE_TIME);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_INSERT_TIME, RescueService.Rescue.COLUMN_NAME_INSERT_TIME);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_DELETE_FLAG, RescueService.Rescue.COLUMN_NAME_DELETE_FLAG);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_CITY, RescueService.Rescue.COLUMN_NAME_CITY);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_DETAIL_ADDRESS, RescueService.Rescue.COLUMN_NAME_DETAIL_ADDRESS);
        sRescueProjectionMap.put(RescueService.Rescue.COLUMN_NAME_TEL_NUMBER_LIST, RescueService.Rescue.COLUMN_NAME_TEL_NUMBER_LIST);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case RESCUES:
                count = db.delete(RescueService.Rescue.TABLE_NAME, selection, selectionArgs);
                break;
            case RESCUES_ID:
                String id = uri.getPathSegments().get(RescueService.Rescue.RESCUE_ID_PATH_POSITION);
                if (TextUtils.isEmpty(selection)) {
                    selection = "_id=" + id;
                } else {
                    selection = "_id=" + id + " AND ( " + selection + " )";
                }
                count = db.delete(RescueService.Rescue.TABLE_NAME, selection, selectionArgs);
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
        qb.setTables(RescueService.Rescue.TABLE_NAME);
        boolean distinct = false;
        switch (sUriMatcher.match(uri)) {
            case RESCUES:
                qb.setProjectionMap(sRescueProjectionMap);
                distinct = true;
                break;
            case RESCUES_ID:
                qb.setProjectionMap(sRescueProjectionMap);
                qb.appendWhere(
                        RescueService.Rescue._ID + "="
                                + uri.getPathSegments().get(RescueService.Rescue.RESCUE_ID_PATH_POSITION)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = RescueService.Rescue.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        qb.setDistinct(distinct);
        Cursor c = qb.query(db, projection, selection, selectionArgs, RescueService.Rescue.COLUMN_NAME_ID, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case RESCUES:
                return RescueService.CONTENT_TYPE;
            case RESCUES_ID:
                return RescueService.CONTENT_ITEM_TYPE;
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
        switch (sUriMatcher.match(uri)) {
            case RESCUES:
                rowId = db.insert(RescueService.Rescue.TABLE_NAME, null, values);
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
        switch (sUriMatcher.match(uri)) {
            case RESCUES:
                count = db.update(RescueService.Rescue.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RESCUES_ID:
                String id = uri.getPathSegments().get(RescueService.Rescue.RESCUE_ID_PATH_POSITION);
                if (TextUtils.isEmpty(selection)) {
                    selection = "_id=" + id;
                } else {
                    selection = "_id=" + id + " AND ( " + selection + " )";
                }
                count = db.update(RescueService.Rescue.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
