package com.example.gtercn.car.bean;

/**
 * Created by Administrator on 2017/3/14.
 */

public class PromotionDetailBean {

    /**
     * err_code : 0
     * err_message : OK
     * result : {"id":"aaf4f47f35104e20a433a5f","shop_id":"141d38aec6044c8d82cddf0ed3c9afbc","shop_name":"占东汽车电器","start_date":"2017年01月25日","end_date":"2017年02月25日","title":"新款君越春节大促销","introduction":"","content":"全新一代君越30H全混动，购车享\"1元首付18期0利率\"","picture_list":null,"html_url":"http://192.168.1.71/carhome/car_home_html/promotion2017-1-9.html","delete_flag":0,"insert_time":"2017-01-15 09:14","update_time":"2017-03-03 11:01","shop_address":"辽宁省阜新市四合大街与玉龙路交界东二百米","shop_phone":"15042529249","is_favored":"0","city_code":"210900"}
     * message : null
     */

    private String err_code;
    private String err_message;
    private ResultBean result;
    private Object message;

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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public static class ResultBean {
        /**
         * id : aaf4f47f35104e20a433a5f
         * shop_id : 141d38aec6044c8d82cddf0ed3c9afbc
         * shop_name : 占东汽车电器
         * start_date : 2017年01月25日
         * end_date : 2017年02月25日
         * title : 新款君越春节大促销
         * introduction :
         * content : 全新一代君越30H全混动，购车享"1元首付18期0利率"
         * picture_list : null
         * html_url : http://192.168.1.71/carhome/car_home_html/promotion2017-1-9.html
         * delete_flag : 0
         * insert_time : 2017-01-15 09:14
         * update_time : 2017-03-03 11:01
         * shop_address : 辽宁省阜新市四合大街与玉龙路交界东二百米
         * shop_phone : 15042529249
         * is_favored : 0
         * city_code : 210900
         */

        private String id;
        private String shop_id;
        private String shop_name;
        private String start_date;
        private String end_date;
        private String title;
        private String introduction;
        private String content;
        private String picture_list;
        private String html_url;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private String shop_address;
        private String shop_phone;
        private String is_favored;
        private String city_code;
        private String background_image;

        public String getBackground_image(){
            return background_image;
        }

        public void setBackground_image(String background_image){
            this.background_image = background_image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture_list() {
            return picture_list;
        }

        public void setPicture_list(String picture_list) {
            this.picture_list = picture_list;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public int getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(int delete_flag) {
            this.delete_flag = delete_flag;
        }

        public String getInsert_time() {
            return insert_time;
        }

        public void setInsert_time(String insert_time) {
            this.insert_time = insert_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public String getShop_phone() {
            return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
            this.shop_phone = shop_phone;
        }

        public String getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(String is_favored) {
            this.is_favored = is_favored;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }
    }
}
