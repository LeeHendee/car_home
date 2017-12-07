package com.example.gtercn.car.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public class FourServeDetailBean {


    /**
     * err_code : 0
     * err_message : OK
     * result : {"id":null,"city_code":null,"repair_service":"5100","clean_service":"4100","maintain_service":"6100","tyre_service":"0","shop_pic_url":"http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\thumbnail\\DSC04951.JPG","shop_name":"F1汽车用品","score":"5","detail_address":"东梁镇转盘南一百五十米","tel_number_list":"15042513037","tel_num_list":["15042513037"],"longitude":"121.568768","latitude":"41.911622","province":"辽宁省","city":"阜新市","display_pic_url_list":"\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04950.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04951.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04952.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04953.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04954.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04955.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04956.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04957.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04958.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04960.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04961.JPG,\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04962.JPG","display_pic_list":["http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04950.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04951.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04952.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04953.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04954.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04955.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04956.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04957.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04958.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04960.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04961.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04962.JPG"],"shop_description":"F1汽车用品创建于2010年，位于东梁镇转盘南一百五十米，主要经营汽车美容、装饰等，现有员工十人，新店开业，全新设备，愿为您提供满意的服务","product_description":"洗车、打蜡、封釉、镀膜等汽车美容，装饰、保养等","is_favored":0}
     * message : 查询服务店铺成功
     */

    private String err_code;
    private String err_message;
    private ResultBean result;
    private String message;

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResultBean {
        /**
         * id : null
         * city_code : null
         * repair_service : 5100
         * clean_service : 4100
         * maintain_service : 6100
         * tyre_service : 0
         * shop_pic_url : http://192.168.1.71\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\thumbnail\DSC04951.JPG
         * shop_name : F1汽车用品
         * score : 5
         * detail_address : 东梁镇转盘南一百五十米
         * tel_number_list : 15042513037
         * tel_num_list : ["15042513037"]
         * longitude : 121.568768
         * latitude : 41.911622
         * province : 辽宁省
         * city : 阜新市
         * display_pic_url_list : \carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04950.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04951.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04952.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04953.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04954.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04955.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04956.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04957.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04958.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04960.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04961.JPG,\carhome\shop\2123d0ad995e48ea87b9a6fc807b400f\detail\DSC04962.JPG
         * display_pic_list : ["http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04950.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04951.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04952.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04953.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04954.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04955.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04956.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04957.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04958.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04960.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04961.JPG","http://192.168.1.71\\carhome\\shop\\2123d0ad995e48ea87b9a6fc807b400f\\detail\\DSC04962.JPG"]
         * shop_description : F1汽车用品创建于2010年，位于东梁镇转盘南一百五十米，主要经营汽车美容、装饰等，现有员工十人，新店开业，全新设备，愿为您提供满意的服务
         * product_description : 洗车、打蜡、封釉、镀膜等汽车美容，装饰、保养等
         * is_favored : 0
         */

        private Object id;
        private Object city_code;
        private String repair_service;
        private String clean_service;
        private String maintain_service;
        private String tyre_service;
        private String shop_pic_url;
        private String shop_name;
        private String score;
        private String detail_address;
        private String tel_number_list;
        private String longitude;
        private String latitude;
        private String province;
        private String city;
        private String display_pic_url_list;
        private String shop_description;
        private String product_description;
        private int is_favored;
        private List<String> tel_num_list;
        private List<String> display_pic_list;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getCity_code() {
            return city_code;
        }

        public void setCity_code(Object city_code) {
            this.city_code = city_code;
        }

        public String getRepair_service() {
            return repair_service;
        }

        public void setRepair_service(String repair_service) {
            this.repair_service = repair_service;
        }

        public String getClean_service() {
            return clean_service;
        }

        public void setClean_service(String clean_service) {
            this.clean_service = clean_service;
        }

        public String getMaintain_service() {
            return maintain_service;
        }

        public void setMaintain_service(String maintain_service) {
            this.maintain_service = maintain_service;
        }

        public String getTyre_service() {
            return tyre_service;
        }

        public void setTyre_service(String tyre_service) {
            this.tyre_service = tyre_service;
        }

        public String getShop_pic_url() {
            return shop_pic_url;
        }

        public void setShop_pic_url(String shop_pic_url) {
            this.shop_pic_url = shop_pic_url;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getDetail_address() {
            return detail_address;
        }

        public void setDetail_address(String detail_address) {
            this.detail_address = detail_address;
        }

        public String getTel_number_list() {
            return tel_number_list;
        }

        public void setTel_number_list(String tel_number_list) {
            this.tel_number_list = tel_number_list;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDisplay_pic_url_list() {
            return display_pic_url_list;
        }

        public void setDisplay_pic_url_list(String display_pic_url_list) {
            this.display_pic_url_list = display_pic_url_list;
        }

        public String getShop_description() {
            return shop_description;
        }

        public void setShop_description(String shop_description) {
            this.shop_description = shop_description;
        }

        public String getProduct_description() {
            return product_description;
        }

        public void setProduct_description(String product_description) {
            this.product_description = product_description;
        }

        public int getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(int is_favored) {
            this.is_favored = is_favored;
        }

        public List<String> getTel_num_list() {
            return tel_num_list;
        }

        public void setTel_num_list(List<String> tel_num_list) {
            this.tel_num_list = tel_num_list;
        }

        public List<String> getDisplay_pic_list() {
            return display_pic_list;
        }

        public void setDisplay_pic_list(List<String> display_pic_list) {
            this.display_pic_list = display_pic_list;
        }
    }
}
