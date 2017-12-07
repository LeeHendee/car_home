package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2017/2/13.
 * description:
 */
public class SearchBean implements Serializable {

    /**
     * result : [{"id":"71443e967466427380b6d3f4dd81a055","rescue_service":"2000","repair_service":"5100","clean_service":"0","maintain_service":"6100","tyre_service":"0","type_value":null,"shop_pic_url":"http://192.168.1.71/\\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\thumbnail\\DSC03932.JPG","shop_name":"焱顺修车行","shop_id":null,"shop_score":"0","experience":null,"longtitude":"121.670178","latitude":"42.008112","distance":6386.8,"province":"辽宁省","city":"阜新市","district":"太平区","detail_address":"建设广场振兴路（广场西走十米）","tel_number_list":"13795016609,13841833338","head_portrait_url":"http://192.168.1.71/null","display_pic_url_list":"\\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03932.JPG, \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03934.JPG, \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03936.JPG, \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03937.JPG","shop_description":"阜新市炎顺修车行创建于2011年，公司位于阜新市振兴路，地理位置优越，主要经营汽车保养，快修，钣金，喷漆。师傅从业20年，现有员工6人，经验丰富，技术精湛。愿为您提供满意的服务","product_description":null,"delete_flag":null,"update_time":null,"insert_time":null,"type_list":null,"distance_list":null,"district_list":null,"tel_num_list":["13795016609","13841833338"],"display_pic_list":["http://192.168.1.71/\\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03932.JPG","http://192.168.1.71/ \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03934.JPG","http://192.168.1.71/ \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03936.JPG","http://192.168.1.71/ \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03937.JPG"],"service_brands_url":null,"service_brands_url_list":null,"service_type_list":null,"type_value_list":null,"is_favored":0}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 71443e967466427380b6d3f4dd81a055
     * rescue_service : 2000
     * repair_service : 5100
     * clean_service : 0
     * maintain_service : 6100
     * tyre_service : 0
     * type_value : null
     * shop_pic_url : http://192.168.1.71/\carhome\shop\71443e967466427380b6d3f4dd81a055\thumbnail\DSC03932.JPG
     * shop_name : 焱顺修车行
     * shop_id : null
     * shop_score : 0
     * experience : null
     * longtitude : 121.670178
     * latitude : 42.008112
     * distance : 6386.8
     * province : 辽宁省
     * city : 阜新市
     * district : 太平区
     * detail_address : 建设广场振兴路（广场西走十米）
     * tel_number_list : 13795016609,13841833338
     * head_portrait_url : http://192.168.1.71/null
     * display_pic_url_list : \carhome\shop\71443e967466427380b6d3f4dd81a055\detail\DSC03932.JPG, \carhome\shop\71443e967466427380b6d3f4dd81a055\detail\DSC03934.JPG, \carhome\shop\71443e967466427380b6d3f4dd81a055\detail\DSC03936.JPG, \carhome\shop\71443e967466427380b6d3f4dd81a055\detail\DSC03937.JPG
     * shop_description : 阜新市炎顺修车行创建于2011年，公司位于阜新市振兴路，地理位置优越，主要经营汽车保养，快修，钣金，喷漆。师傅从业20年，现有员工6人，经验丰富，技术精湛。愿为您提供满意的服务
     * product_description : null
     * delete_flag : null
     * update_time : null
     * insert_time : null
     * type_list : null
     * distance_list : null
     * district_list : null
     * tel_num_list : ["13795016609","13841833338"]
     * display_pic_list : ["http://192.168.1.71/\\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03932.JPG","http://192.168.1.71/ \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03934.JPG","http://192.168.1.71/ \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03936.JPG","http://192.168.1.71/ \\carhome\\shop\\71443e967466427380b6d3f4dd81a055\\detail\\DSC03937.JPG"]
     * service_brands_url : null
     * service_brands_url_list : null
     * service_type_list : null
     * type_value_list : null
     * is_favored : 0
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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
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
        return "SearchBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message=" + message +
                ", result=" + result +
                '}';
    }

    public static class ResultBean implements Serializable {
        private String id;
        private String rescue_service;
        private String repair_service;
        private String clean_service;
        private String maintain_service;
        private String tyre_service;
        private Object type_value;
        private String shop_pic_url;
        private String shop_name;
        private Object shop_id;
        private String shop_score;
        private Object experience;
        private String longtitude;
        private String latitude;
        private double distance;
        private String province;
        private String city;
        private String district;
        private String detail_address;
        private String tel_number_list;
        private String head_portrait_url;
        private String display_pic_url_list;
        private String shop_description;
        private Object product_description;
        private Object delete_flag;
        private Object update_time;
        private Object insert_time;
        private Object type_list;
        private Object distance_list;
        private Object district_list;
        private Object service_brands_url;
        private Object service_brands_url_list;
        private Object service_type_list;
        private Object type_value_list;
        private int is_favored;
        private List<String> tel_num_list;
        private List<String> display_pic_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRescue_service() {
            return rescue_service;
        }

        public void setRescue_service(String rescue_service) {
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

        public Object getType_value() {
            return type_value;
        }

        public void setType_value(Object type_value) {
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

        public Object getShop_id() {
            return shop_id;
        }

        public void setShop_id(Object shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_score() {
            return shop_score;
        }

        public void setShop_score(String shop_score) {
            this.shop_score = shop_score;
        }

        public Object getExperience() {
            return experience;
        }

        public void setExperience(Object experience) {
            this.experience = experience;
        }

        public String getLongtitude() {
            return longtitude;
        }

        public void setLongtitude(String longtitude) {
            this.longtitude = longtitude;
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

        public Object getProduct_description() {
            return product_description;
        }

        public void setProduct_description(Object product_description) {
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

        public Object getType_list() {
            return type_list;
        }

        public void setType_list(Object type_list) {
            this.type_list = type_list;
        }

        public Object getDistance_list() {
            return distance_list;
        }

        public void setDistance_list(Object distance_list) {
            this.distance_list = distance_list;
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

        public Object getType_value_list() {
            return type_value_list;
        }

        public void setType_value_list(Object type_value_list) {
            this.type_value_list = type_value_list;
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

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", rescue_service='" + rescue_service + '\'' +
                    ", repair_service='" + repair_service + '\'' +
                    ", clean_service='" + clean_service + '\'' +
                    ", maintain_service='" + maintain_service + '\'' +
                    ", tyre_service='" + tyre_service + '\'' +
                    ", type_value=" + type_value +
                    ", shop_pic_url='" + shop_pic_url + '\'' +
                    ", shop_name='" + shop_name + '\'' +
                    ", shop_id=" + shop_id +
                    ", shop_score='" + shop_score + '\'' +
                    ", experience=" + experience +
                    ", longtitude='" + longtitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", distance=" + distance +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", detail_address='" + detail_address + '\'' +
                    ", tel_number_list='" + tel_number_list + '\'' +
                    ", head_portrait_url='" + head_portrait_url + '\'' +
                    ", display_pic_url_list='" + display_pic_url_list + '\'' +
                    ", shop_description='" + shop_description + '\'' +
                    ", product_description=" + product_description +
                    ", delete_flag=" + delete_flag +
                    ", update_time=" + update_time +
                    ", insert_time=" + insert_time +
                    ", type_list=" + type_list +
                    ", distance_list=" + distance_list +
                    ", district_list=" + district_list +
                    ", service_brands_url=" + service_brands_url +
                    ", service_brands_url_list=" + service_brands_url_list +
                    ", service_type_list=" + service_type_list +
                    ", type_value_list=" + type_value_list +
                    ", is_favored=" + is_favored +
                    ", tel_num_list=" + tel_num_list +
                    ", display_pic_list=" + display_pic_list +
                    '}';
        }
    }
}
