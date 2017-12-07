package com.example.gtercn.car.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-3-3.
 * 4类服务 洗车、修车、轮胎、保养 列表数据结构
 */

public class FourServiceBean implements Serializable {
    private static final long serialVersionUID = 171253128861408637L;

    private String id;

    private String shop_id;

    private String shop_name;

    private String shop_description;

    private String detail_address;

    private String shop_pic_url;

    private String shop_score;

    //接口拼写错误
    private String longitude;

    private String latitude;

    private String city_code;

    private String repair_service;

    private String clean_service;

    private String tyre_service;

    private String maintain_service;

    private double distance;

    private String tel_num_list;

    private String score;

    private String update_time;

    private String insert_time;

    private String delete_flag;

    private String service_list;

    private String type_value;
    public String getType_value() {
        return type_value;
    }

    public void setType_value(String type_value) {
        this.type_value = type_value;
    }

    public String getShop_id(){
        return shop_id;
    }

    public void setShop_id(String shop_id){
        this.shop_id =shop_id;
    }

    public String getService_list(){
        return service_list;
    }

    public void setService_list(String service_list){
        this.service_list =service_list;
    }

    public String getScore(){
        return score;
    }

    public void setScore(String score){
        this.score =score;
    }

    public String getClean_service() {
        return clean_service;
    }

    public void setClean_service(String clean_service) {
        this.clean_service = clean_service;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getMaintain_service() {
        return maintain_service;
    }

    public void setMaintain_service(String maintain_service) {
        this.maintain_service = maintain_service;
    }

    public String getRepair_service() {
        return repair_service;
    }

    public void setRepair_service(String repair_service) {
        this.repair_service = repair_service;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTel_num_list() {
        return tel_num_list;
    }

    public void setTel_num_list(String tel_num_list) {
        this.tel_num_list = tel_num_list;
    }

    public String getTyre_service() {
        return tyre_service;
    }

    public void setTyre_service(String tyre_service) {
        this.tyre_service = tyre_service;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getShop_description() {
        return shop_description;
    }

    public void setShop_description(String shop_description) {
        this.shop_description = shop_description;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_pic_url() {
        return shop_pic_url;
    }

    public void setShop_pic_url(String shop_pic_url) {
        this.shop_pic_url = shop_pic_url;
    }

    public String getShop_score() {
        return shop_score;
    }

    public void setShop_score(String shop_score) {
        this.shop_score = shop_score;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "FourServiceBean{" +
                "city_code='" + city_code + '\'' +
                ", id='" + id + '\'' +
                ", shop_name='" + shop_name + '\'' +
                ", shop_description='" + shop_description + '\'' +
                ", detail_address='" + detail_address + '\'' +
                ", shop_pic_url='" + shop_pic_url + '\'' +
                ", shop_score='" + shop_score + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", repair_service='" + repair_service + '\'' +
                ", clean_service='" + clean_service + '\'' +
                ", tyre_service='" + tyre_service + '\'' +
                ", maintain_service='" + maintain_service + '\'' +
                ", distance='" + distance + '\'' +
                ", tel_num_list='" + tel_num_list + '\'' +
                ", update_time='" + update_time + '\'' +
                ", insert_time='" + insert_time + '\'' +
                ", delete_flag='" + delete_flag + '\'' +
                '}';
    }
}
