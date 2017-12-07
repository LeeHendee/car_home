package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/11/16.
 * description:
 * 救援列表
 *
 */
public class FourServerListBean implements Serializable {


    /**
     * result : [{"id":"0085d2df49b14ef695a8c1cb8ab33524","rescue_service":null,"repair_service":null,"clean_service":null,"maintain_service":null,"tyre_service":null,"type_value":"1,2,5","shop_pic_url":"http://192.168.1.71/null","shop_name":"三合元汽车修理厂","shop_id":"0821a7c3de9546339cec9667ab8584be","shop_score":"0","experience":"7","longitude":"121.625454","latitude":"41.974991","distance":11591.9,"province":"辽宁省","city":"阜新市","district":"海州区","detail_address":"平安西部小学东一百米","tel_number_list":"13904980201","head_portrait_url":"http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14","display_pic_url_list":null,"shop_description":"桑塔纳、捷达配件齐全。维修奥迪、皮卡、海狮、红旗、中华、微型、本田、丰田等各种小型车辆，疑难车辆","product_description":"汽车现场救援，专业拖车为实体汽车服务","delete_flag":null,"update_time":null,"insert_time":null,"type_list":["现场抢修","拖车","紧急加水","紧急送油","配钥匙"],"distance_list":["附近","1","3","5","10"],"district_list":null,"tel_num_list":["13904980201"],"display_pic_list":[],"service_brands_url":null,"service_brands_url_list":null,"service_type_list":null,"type_value_list":["现场抢修","拖车","配钥匙"],"is_favored":1},{"id":"0085d2df49b14ef695a8c1cb8ab33123","rescue_service":null,"repair_service":null,"clean_service":null,"maintain_service":null,"tyre_service":null,"type_value":"1,2,3","shop_pic_url":"http://192.168.1.71/null","shop_name":"天意轮胎广场","shop_id":"00724edf0cdc4db688b44b18ed19acef","shop_score":"0","experience":"10","longitude":"121.675203","latitude":"42.0377","distance":11592,"province":"辽宁省","city":"阜新市","district":"细河区","detail_address":"海鑫国际东门北二百米","tel_number_list":"0418-3880808,13470356318","head_portrait_url":"http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14","display_pic_url_list":null,"shop_description":"品牌轮胎、优质机油、四轮定位、更换三滤、动平衡、充氮气","product_description":"汽车现场救援，专业拖车为实体汽车服务","delete_flag":null,"update_time":null,"insert_time":null,"type_list":null,"distance_list":null,"district_list":null,"tel_num_list":["0418-3880808","13470356318"],"display_pic_list":[],"service_brands_url":null,"service_brands_url_list":null,"service_type_list":null,"type_value_list":["现场抢修","拖车","紧急加水"],"is_favored":0}]
     * err_code : 0
     * err_message : OK
     * message : 查询救援公司成功
     */

    private String err_code;
    private String err_message;
    private String message;
    /**
     * id : 0085d2df49b14ef695a8c1cb8ab33524
     * rescue_service : null
     * repair_service : null
     * clean_service : null
     * maintain_service : null
     * tyre_service : null
     * type_value : 1,2,5
     * shop_pic_url : http://192.168.1.71/null
     * shop_name : 三合元汽车修理厂
     * shop_id : 0821a7c3de9546339cec9667ab8584be
     * shop_score : 0
     * experience : 7
     * longitude : 121.625454
     * latitude : 41.974991
     * distance : 11591.9
     * province : 辽宁省
     * city : 阜新市
     * district : 海州区
     * detail_address : 平安西部小学东一百米
     * tel_number_list : 13904980201
     * head_portrait_url : http://192.168.1.71/\078d29ae03b54f06be3ffc0cf701dfa5\14
     * display_pic_url_list : null
     * shop_description : 桑塔纳、捷达配件齐全。维修奥迪、皮卡、海狮、红旗、中华、微型、本田、丰田等各种小型车辆，疑难车辆
     * product_description : 汽车现场救援，专业拖车为实体汽车服务
     * delete_flag : null
     * update_time : null
     * insert_time : null
     * type_list : ["现场抢修","拖车","紧急加水","紧急送油","配钥匙"]
     * distance_list : ["附近","1","3","5","10"]
     * district_list : null
     * tel_num_list : ["13904980201"]
     * display_pic_list : []
     * service_brands_url : null
     * service_brands_url_list : null
     * service_type_list : null
     * type_value_list : ["现场抢修","拖车","配钥匙"]
     * is_favored : 1
     */

    private List<ResultBean> result;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RescueListBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public static class ResultBean implements Serializable {
        private String id;
        private Object rescue_service;
        private String repair_service;
        private String clean_service;
        private String maintain_service;
        private String tyre_service;
        private String type_value;
        private String shop_pic_url;
        private String shop_name;
        private String shop_id;
        private String shop_score;
        private String experience;
        private String longitude;
        private String latitude;
        private double distance;
        private String province;
        private String city;
        private String district;
        private String detail_address;
        private String tel_number_list;
        private String head_portrait_url;
        private Object display_pic_url_list;
        private String shop_description;
        private String product_description;
        private Object delete_flag;
        private Object update_time;
        private Object insert_time;
        private Object district_list;
        private Object service_brands_url;
        private Object service_brands_url_list;
        private Object service_type_list;
        private int is_favored;
        private String city_code;
        private String score;
        private List<String> type_list;
        private List<String> distance_list;
        private List<String> tel_num_list;
        private List<String> display_pic_list;
        private List<String> type_value_list;
        private List<String> service_list;

        public String getCity_code(){
            return city_code;
        }

        public void setCity_code(String city_code){
            this.city_code = city_code;
        }

        public String getScore(){
            return score;
        }

        public void setScore(String score){
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getRescue_service() {
            return rescue_service;
        }

        public void setRescue_service(Object rescue_service) {
            this.rescue_service = rescue_service;
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

        public String getType_value() {
            return type_value;
        }

        public void setType_value(String type_value) {
            this.type_value = type_value;
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

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_score() {
            return shop_score;
        }

        public void setShop_score(String shop_score) {
            this.shop_score = shop_score;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
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

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
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

        public String getHead_portrait_url() {
            return head_portrait_url;
        }

        public void setHead_portrait_url(String head_portrait_url) {
            this.head_portrait_url = head_portrait_url;
        }

        public Object getDisplay_pic_url_list() {
            return display_pic_url_list;
        }

        public void setDisplay_pic_url_list(Object display_pic_url_list) {
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

        public Object getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(Object delete_flag) {
            this.delete_flag = delete_flag;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public Object getInsert_time() {
            return insert_time;
        }

        public void setInsert_time(Object insert_time) {
            this.insert_time = insert_time;
        }

        public Object getDistrict_list() {
            return district_list;
        }

        public void setDistrict_list(Object district_list) {
            this.district_list = district_list;
        }

        public Object getService_brands_url() {
            return service_brands_url;
        }

        public void setService_brands_url(Object service_brands_url) {
            this.service_brands_url = service_brands_url;
        }

        public Object getService_brands_url_list() {
            return service_brands_url_list;
        }

        public void setService_brands_url_list(Object service_brands_url_list) {
            this.service_brands_url_list = service_brands_url_list;
        }

        public Object getService_type_list() {
            return service_type_list;
        }

        public void setService_type_list(Object service_type_list) {
            this.service_type_list = service_type_list;
        }

        public int getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(int is_favored) {
            this.is_favored = is_favored;
        }

        public List<String> getType_list() {
            return type_list;
        }

        public void setType_list(List<String> type_list) {
            this.type_list = type_list;
        }

        public List<String> getDistance_list() {
            return distance_list;
        }

        public void setDistance_list(List<String> distance_list) {
            this.distance_list = distance_list;
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

        public List<String> getType_value_list() {
            return type_value_list;
        }

        public void setType_value_list(List<String> type_value_list) {
            this.type_value_list = type_value_list;
        }

        public List<String> getService_list(){
            return service_list;
        }

        public void setService_list(List<String> service_list){
            this.service_list = service_list;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", rescue_service=" + rescue_service +
                    ", repair_service=" + repair_service +
                    ", clean_service=" + clean_service +
                    ", maintain_service=" + maintain_service +
                    ", tyre_service=" + tyre_service +
                    ", type_value='" + type_value + '\'' +
                    ", shop_pic_url='" + shop_pic_url + '\'' +
                    ", shop_name='" + shop_name + '\'' +
                    ", shop_id='" + shop_id + '\'' +
                    ", shop_score='" + shop_score + '\'' +
                    ", experience='" + experience + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", distance=" + distance +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", detail_address='" + detail_address + '\'' +
                    ", tel_number_list='" + tel_number_list + '\'' +
                    ", head_portrait_url='" + head_portrait_url + '\'' +
                    ", display_pic_url_list=" + display_pic_url_list +
                    ", shop_description='" + shop_description + '\'' +
                    ", product_description='" + product_description + '\'' +
                    ", delete_flag=" + delete_flag +
                    ", update_time=" + update_time +
                    ", insert_time=" + insert_time +
                    ", district_list=" + district_list +
                    ", service_brands_url=" + service_brands_url +
                    ", service_brands_url_list=" + service_brands_url_list +
                    ", service_type_list=" + service_type_list +
                    ", is_favored=" + is_favored +
                    ", type_list=" + type_list +
                    ", distance_list=" + distance_list +
                    ", tel_num_list=" + tel_num_list +
                    ", display_pic_list=" + display_pic_list +
                    ", type_value_list=" + type_value_list +
                    '}';
        }
    }
}
