package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class PromotionBean implements Serializable{

    /**
     * err_code : 0
     * err_message : OK
     * result : [{"id":"2","shop_id":"0085d2df49b14ef695a8c1cb8ab34321","shop_name":"一路平安汽车救援服务有限公司","start_date":"2016年12月01日","end_date":"2016年12月31日","title":"圣诞促销","content":"我航母编队24日在东海海空域开展全要素训练和试验任务。上午11时许，两架歼-15舰载战斗机快速准确地滑向辽宁舰舰艏的各自起飞位，在起飞助理唐博\u201c航母STYLE\u201d动作的指引下，战机依次呼啸着直冲云宵。","picture_list":null,"delete_flag":0,"insert_time":"2016-12-25 08:55","update_time":"2016-12-26 09:59","shop_address":"辽宁省大连市西岗区越秀大厦","shop_phone":"0412-5235236,0412-62534120"},{"id":"1","shop_id":"0085d2df49b14ef695a8c1cb8ab33138","shop_name":"奥奔汽车维修公司","start_date":"2016年12月01日","end_date":"2017年01月01日","title":"元旦年会大促销","content":"12月25日中午，中国驻札幌总领事馆救援车队克服交通受阻等困难，携带食品和饮用水赶赴机场，着手开展安抚和救助工作","picture_list":null,"delete_flag":0,"insert_time":"2016-12-26 08:55","update_time":"2016-12-26 09:58","shop_address":"辽宁省大连市高新园区软件园","shop_phone":"0412-5235121,0412-62534120"}]
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
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

    public static class ResultBean implements Serializable{
        /**
         * id : 2
         * shop_id : 0085d2df49b14ef695a8c1cb8ab34321
         * shop_name : 一路平安汽车救援服务有限公司
         * start_date : 2016年12月01日
         * end_date : 2016年12月31日
         * title : 圣诞促销
         * content : 我航母编队24日在东海海空域开展全要素训练和试验任务。上午11时许，两架歼-15舰载战斗机快速准确地滑向辽宁舰舰艏的各自起飞位，在起飞助理唐博“航母STYLE”动作的指引下，战机依次呼啸着直冲云宵。
         * picture_list : null
         * delete_flag : 0
         * insert_time : 2016-12-25 08:55
         * update_time : 2016-12-26 09:59
         * shop_address : 辽宁省大连市西岗区越秀大厦
         * shop_phone : 0412-5235236,0412-62534120
         */

        private String id;
        private String shop_id;
        private String shop_name;
        private String start_date;
        private String end_date;
        private String title;
        private String introduction;
        private String content;
        private Object picture_list;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private String shop_address;
        private String shop_phone;
        private String html_url;
        private String is_favored;

        public String getIntroduction(){
            return introduction;
        }

        public void setIntroduction(String introduction){
            this.introduction = introduction;
        }

        public String getIs_favored(){
            return is_favored;
        }
        public void setIs_favored(String is_favored){
            this.is_favored = is_favored;
        }

        public String getHtml_url(){
            return  html_url;
        }

        public void setHtml_url(String html_url){
            this.html_url =html_url;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getPicture_list() {
            return picture_list;
        }

        public void setPicture_list(Object picture_list) {
            this.picture_list = picture_list;
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
    }
}
