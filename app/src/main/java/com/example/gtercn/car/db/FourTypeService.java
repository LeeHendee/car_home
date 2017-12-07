package com.example.gtercn.car.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2017-3-2.
 * 4类服务数据表接口定义
 */

public final class FourTypeService {
    public static final String AUTHORITY = "com.example.gtercn.car.provider.four";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.example.gtercn.car";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.example.gtercn.car";

    private FourTypeService(){}

    public static final class Four implements BaseColumns{

        private Four(){}

        public static final String TABLE_NAME = "Four";

        private static final String SCHEME = "content://";

        private static final String PATH = "/Four";

        private static final String PATH_ID = "/Four/";

        public static final int FOUR_ID_PATH_POSITION = 1;

        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH);

        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_ID);

        public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + PATH_ID + "/#");

        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String COLUMN_NAME_ID = "id";

        public static final String COLUMN_NAME_SHOP_ID = "shop_id";

        public static final String COLUMN_NAME_CITY = "city";

        public static final String COLUMN_NAME_CITY_CODE = "city_code";

        public static final String COLUMN_NAME_SHOP_NAME = "shop_name";

        public static final String COLUMN_NAME_SHOP_DESCIPTION = "shop_desciption";

        public static final String COLUMN_NAME_HEAD_PORTRAIT_URL = "head_portrait_url";

        public static final String COLUMN_NAME_SHOP_SCORE = "shop_score";

        public static final String COLUMN_NAME_LONGTITUDE = "longitude";

        public static final String COLUMN_NAME_LATITUDE = "latitude";

        public static final String COLUMN_NAME_UPDATE_TIME = "update_time";

        public static final String COLUMN_NAME_INSERT_TIME = "insert_time";

        public static final String COLUMN_NAME_DELETE_FLAG = "delete_flag";

        public static final String COLUMN_NAME_RESCUE_SERVICE = "rescue_service";

        public static final String COLUMN_NAME_REPAIR_SERVICE = "repair_service";

        public static final String COLUMN_NAME_CLEAN_SERVICE = "clean_service";

        public static final String COLUMN_NAME_MAINTAIN_SERVICE = "maintain_service";

        public static final String COLUMN_NAME_TYRE_SERVICE = "tyre_service";

        public static final String COLUMN_NAME_TYPE_SERVICE = "type_value";

        public static final String COLUMN_NAME_SHOP_PIC_URL = "shop_pic_url";

        public static final String COLUMN_NAME_EXPERIENCE = "experience";

        public static final String COLUMN_NAME_DISTANCE = "distance";

        public static final String COLUMN_NAME_PROVINCE = "province";

        public static final String COLUMN_NAME_DISTRICT = "district";

        public static final String COLUMN_NAME_DETAIL_ADDRESS = "detail_address";

        @Deprecated
        public static final String COLUMN_NAME_X = "tel_number_list";

        public static final String COLUMN_NAME_DISPLAY_PIC_URL_LIST = "display_pic_url_list";

        public static final String COLUMN_NAME_PRODUCT_DESCRIPTION = "product_description";

        public static final String COLUMN_NAME_TYPE_LIST = "type_list";

        public static final String COLUMN_NAME_DISTANCE_LIST = "distance_list";

        public static final String COLUMN_NAME_DISTRICT_LIST = "district_list";

        public static final String COLUMN_NAME_TEL_NUM_LIST = "tel_num_list";

        public static final String COLUMN_NAME_DISPLAY_PIC_LIST = "display_pic_list";

        public static final String COLUMN_NAME_SERVICE_BRANDS_URL = "service_brands_url";

        public static final String COLUMN_NAME_SERVICE_BRANDS_URL_LIST = "service_brands_url_list";

        public static final String COLUMN_NAME_SERVICE_TYPE_LIST = "service_type_list";

        public static final String COLUMN_NAME_TYPE_VALUE_LIST = "type_value_list";

        public static final String COLUMN_NAME_IS_FAVORED = "is_favored";

        public static final String COLUMN_NAME_SCORE = "score";

        public static final String COLUMN_NAME_SERVICE_LIST = "service_list";

        public static final String COLUMN_NAME_TYPE_VALUE = "type_value";

    }

}
