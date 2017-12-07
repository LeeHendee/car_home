package com.example.gtercn.car.bean;

/**
 * Created by Administrator on 2017/3/14.
 */

public class MessageDetailBean {

    /**
     * err_code : 0
     * err_message : OK
     * result : {"id":"test0120001455iilppop","title":"吉利博越对比荣威RX5养车成本","content":"近两年的中国品牌中，一些SUV车型在市场上关注度非常高，而其中比较突出的就要算吉利博越（以下简称博越）和荣威RX5（以下简称RX5）了。其中，博越的1.8T车型是其主销车型，而RX5的1.5T车型则卖的比较好，今天我们就用这两款车型作为对比，来看看它们的养车成本如何。通过这次的对比我们可以看出，在后期养车成本方面，博越要比RX5贵一些，但总体差距不算太大。总花费方面，博越比RX5贵了两千元左右，其实这两款车的主要差距在于油耗，博越要贵了一千多元。其次是保养方面，虽然保养价格、保养周期都差不多，但RX5更便宜一些，主要原因在于RX5使用的昆仑全合成机油价格便宜。另外，RX5除了手动变速箱外，还搭配了DCT（7速干式双离合），而博越则搭载的是6AT（6速自动变速箱），这两款变速箱在保养、油耗方面都会影响养车成本，这些都是影响消费者挑选、购买车辆的因素。","resource":"汽车之家","picture_list":null,"delete_flag":0,"insert_time":"2017-01-25 09:22","update_time":"2017-03-13 16:19","html_url":"http://192.168.1.71/carhome/car_home_html/information2017-1-9.html","is_favored":"0","introduction":"近两年的中国品牌中，一些SUV车型在市场上关注度非常高"}
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
         * id : test0120001455iilppop
         * title : 吉利博越对比荣威RX5养车成本
         * content : 近两年的中国品牌中，一些SUV车型在市场上关注度非常高，而其中比较突出的就要算吉利博越（以下简称博越）和荣威RX5（以下简称RX5）了。其中，博越的1.8T车型是其主销车型，而RX5的1.5T车型则卖的比较好，今天我们就用这两款车型作为对比，来看看它们的养车成本如何。通过这次的对比我们可以看出，在后期养车成本方面，博越要比RX5贵一些，但总体差距不算太大。总花费方面，博越比RX5贵了两千元左右，其实这两款车的主要差距在于油耗，博越要贵了一千多元。其次是保养方面，虽然保养价格、保养周期都差不多，但RX5更便宜一些，主要原因在于RX5使用的昆仑全合成机油价格便宜。另外，RX5除了手动变速箱外，还搭配了DCT（7速干式双离合），而博越则搭载的是6AT（6速自动变速箱），这两款变速箱在保养、油耗方面都会影响养车成本，这些都是影响消费者挑选、购买车辆的因素。
         * resource : 汽车之家
         * picture_list : null
         * delete_flag : 0
         * insert_time : 2017-01-25 09:22
         * update_time : 2017-03-13 16:19
         * html_url : http://192.168.1.71/carhome/car_home_html/information2017-1-9.html
         * is_favored : 0
         * introduction : 近两年的中国品牌中，一些SUV车型在市场上关注度非常高
         */

        private String id;
        private String title;
        private String content;
        private String resource;
        private Object picture_list;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private String html_url;
        private String is_favored;
        private String introduction;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
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

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(String is_favored) {
            this.is_favored = is_favored;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
