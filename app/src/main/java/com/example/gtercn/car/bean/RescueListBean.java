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
public class RescueListBean implements Serializable {

    private String err_code;
    private String err_message;
    private String message;
    /**
     * id : 274390062209451fa1954619e8e7b925
     * city_code : 210900
     * type_value : 2,3,4
     * head_portrait_url : http://192.168.1.71
     * shop_name : 河口科技园
     * shop_score : 8
     * longitude : 121.507122
     * latitude : 38.855172
     * category : [{"key":"1","value":"现场抢修"},{"key":"2","value":"拖车"},{"key":"3","value":"紧急加水"},{"key":"4","value":"紧急送油"},{"key":"5","value":"配钥匙"}]
     * distance_list : ["附近","1","3","5","10"]
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

    public static class ResultBean implements Serializable {
        private String id;
        private String city_code;
        private String type_value;
        private String head_portrait_url;
        private String shop_name;
        private String shop_score;
        private String longitude;
        private String latitude;
        private String category;
        private List<String> distance_list;
        private String city;
        private String detail_address;
        private String tel_number_list;


        private double distance = 0;


        public ResultBean(String id, String city_code, String type_value, String head_portrait_url, String shop_name, String shop_score, String longitude, String latitude,String city,String detail_address,String tel_number_list) {
            this.id = id;
            this.city_code = city_code;
            this.type_value = type_value;
            this.head_portrait_url = head_portrait_url;
            this.shop_name = shop_name;
            this.shop_score = shop_score;
            this.longitude = longitude;
            this.latitude = latitude;
            this.city=city;
            this.detail_address = detail_address;
            this.tel_number_list = tel_number_list;
        }

        public ResultBean() {
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getType_value() {
            return type_value;
        }

        public void setType_value(String type_value) {
            this.type_value = type_value;
        }

        public String getHead_portrait_url() {
            return head_portrait_url;
        }

        public void setHead_portrait_url(String head_portrait_url) {
            this.head_portrait_url = head_portrait_url;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_score() {
            return shop_score;
        }

        public void setShop_score(String shop_score) {
            this.shop_score = shop_score;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<String> getDistance_list() {
            return distance_list;
        }

        public void setDistance_list(List<String> distance_list) {
            this.distance_list = distance_list;
        }

        public String getCity(){
            return city;
        }

        public void setCity(String city){
            this.city = city;
        }

        public String getDetail_address(){
            return detail_address;
        }

        public void setDetail_address(String detail_address){
            this.detail_address =detail_address;
        }

        public String getTel_number_list(){
            return tel_number_list;
        }

        public void  setTel_number_list(String tel_number_list){
            this.tel_number_list = tel_number_list;
        }
        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", city_code='" + city_code + '\'' +
                    ", type_value='" + type_value + '\'' +
                    ", head_portrait_url='" + head_portrait_url + '\'' +
                    ", shop_name='" + shop_name + '\'' +
                    ", shop_score='" + shop_score + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", category='" + category + '\'' +
                    ", distance_list=" + distance_list +
                    '}';
        }
    }
}
