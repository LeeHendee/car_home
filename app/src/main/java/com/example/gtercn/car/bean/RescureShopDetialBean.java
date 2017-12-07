package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public class RescureShopDetialBean {


    /**
     * err_code : 0
     * err_message : OK
     * result : {"id":"3eb069c09cad4def82878cf3cb53a0d3","tel_number_list":"13470322651,0418-2902855","tel_num_list":["13470322651","0418-2902855"],"province":"辽宁省","city":"阜新市","detail_address":"金羽小区南门","display_pic_url_list":"\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04972.JPG,\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04973.JPG,\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04974.JPG,\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04975.JPG,\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04976.JPG","display_pic_list":["http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04972.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04973.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04974.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04975.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04976.JPG"],"product_description":"精修各种汽车、专业维修技术为您的车进行大中小修","is_favored":0,"type_value":"1,2,3,4,5","head_portrait_url":"http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\thumbnail\\DSC04972.JPG","shop_name":"中航汽车维修服务有限公司","shop_score":"5","longitude":"121.647656","latitude":"42.03132"}
     * message : 查询救援服务店铺成功
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

    public static class ResultBean implements Serializable{
        /**
         * id : 3eb069c09cad4def82878cf3cb53a0d3
         * tel_number_list : 13470322651,0418-2902855
         * tel_num_list : ["13470322651","0418-2902855"]
         * province : 辽宁省
         * city : 阜新市
         * detail_address : 金羽小区南门
         * display_pic_url_list : \carhome\shop\f4fdc40cc59142f9b527b9377b7079da\detail\DSC04972.JPG,\carhome\shop\f4fdc40cc59142f9b527b9377b7079da\detail\DSC04973.JPG,\carhome\shop\f4fdc40cc59142f9b527b9377b7079da\detail\DSC04974.JPG,\carhome\shop\f4fdc40cc59142f9b527b9377b7079da\detail\DSC04975.JPG,\carhome\shop\f4fdc40cc59142f9b527b9377b7079da\detail\DSC04976.JPG
         * display_pic_list : ["http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04972.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04973.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04974.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04975.JPG","http://192.168.1.71\\carhome\\shop\\f4fdc40cc59142f9b527b9377b7079da\\detail\\DSC04976.JPG"]
         * product_description : 精修各种汽车、专业维修技术为您的车进行大中小修
         * is_favored : 0
         * type_value : 1,2,3,4,5
         * head_portrait_url : http://192.168.1.71\carhome\shop\f4fdc40cc59142f9b527b9377b7079da\thumbnail\DSC04972.JPG
         * shop_name : 中航汽车维修服务有限公司
         * shop_score : 5
         * longitude : 121.647656
         * latitude : 42.03132
         */

        private String id;
        private String tel_number_list;
        private String province;
        private String city;
        private String detail_address;
        private String display_pic_url_list;
        private String product_description;
        private int is_favored;
        private String type_value;
        private String head_portrait_url;
        private String shop_name;
        private String shop_score;
        private String longitude;
        private String latitude;
        private List<String> tel_num_list;
        private List<String> display_pic_list;
        private String shop_description;

        public String getShop_description(){
            return  shop_description;
        }

        public void  setShop_description(String shop_description){
            this.shop_description = shop_description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel_number_list() {
            return tel_number_list;
        }

        public void setTel_number_list(String tel_number_list) {
            this.tel_number_list = tel_number_list;
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

        public String getDetail_address() {
            return detail_address;
        }

        public void setDetail_address(String detail_address) {
            this.detail_address = detail_address;
        }

        public String getDisplay_pic_url_list() {
            return display_pic_url_list;
        }

        public void setDisplay_pic_url_list(String display_pic_url_list) {
            this.display_pic_url_list = display_pic_url_list;
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
