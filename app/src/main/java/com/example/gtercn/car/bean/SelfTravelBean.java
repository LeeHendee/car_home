package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SelfTravelBean implements Serializable {


    /**
     * result : [{"id":"0085d2df43b14ef695a8c1cb8ab33123","title":"越野自驾大峡谷+羚羊谷","content":"我组织去越野自驾大峡谷+羚羊谷，6天5夜深度自驾游，专业自驾领队护航，快来参加报名吧！名额有限哦！联系电话：13752552336","pic_urls":"\\078d29ae03b54f06be3ffc0cf701dfa5\\14","pic_urls_list":["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14"],"available_flag":1,"delete_flag":null,"update_time":"2016-12-28 09:17","insert_time":null,"sign_flag":0,"collection_flag":0},{"id":"0085d2df43b14yf695a8c1cb8ab33123","title":"龙庆峡2日自助游","content":"龙庆峡2日自助游+伪河 浪漫自驾游爱在乡村游","pic_urls":"\\078d29ae03b54f06be3ffc0cf701dfa5\\14","pic_urls_list":["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14"],"available_flag":1,"delete_flag":null,"update_time":"1小时前  2016-12-29","insert_time":null,"sign_flag":0,"collection_flag":0},{"id":"0085d2df43b14yf6a5a8c1cb8ab33123","title":"西藏至青海自驾游","content":"11月8日广州出发 自驾西藏至青海开团马上出发","pic_urls":"\\078d29ae03b54f06be3ffc0cf701dfa5\\14","pic_urls_list":["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14"],"available_flag":1,"delete_flag":null,"update_time":"38分钟前  2016-12-29","insert_time":null,"sign_flag":0,"collection_flag":0}]
     * err_code : 0
     * err_message : OK
     * message : 查询自驾游成功
     */

    private String err_code;
    private String err_message;
    private String message;
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
        /**
         * id : 0085d2df43b14ef695a8c1cb8ab33123
         * title : 越野自驾大峡谷+羚羊谷
         * content : 我组织去越野自驾大峡谷+羚羊谷，6天5夜深度自驾游，专业自驾领队护航，快来参加报名吧！名额有限哦！联系电话：13752552336
         * pic_urls : \078d29ae03b54f06be3ffc0cf701dfa5\14
         * pic_urls_list : ["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14"]
         * available_flag : 1
         * delete_flag : null
         * update_time : 2016-12-28 09:17
         * insert_time : null
         * sign_flag : 0
         * collection_flag : 0
         */

        private String id;
        private String title;
        private String content;
        private String pic_urls;
        private int available_flag;//有效flag(1:有效,0:取消)
        private Object delete_flag;
        private String update_time;
        private Object insert_time;
        private int sign_flag;//是否报名(1：报名，0：、未报名)
        private int collection_flag;//是否收藏(1：收藏，0：未收藏)
        private String nickname;
        private List<String> pic_urls_list;
        private String avatar_url ;
        private String enroll_flag;//报名flag(1:能,0:不能)
        private String public_flag;//用户flag(1:发布,0:报名)

        public String getPublic_flag(){
            return public_flag;
        }

        public void  setPublic_flag(String public_flag){
            this.public_flag = public_flag;
        }

        public String getEnroll_flag(){
            return enroll_flag;
        }

        public void setEnroll_flag(String enroll_flag){
            this.enroll_flag = enroll_flag;
        }


        public String getAvatar_url(){
            return avatar_url;
        }
        public void setAvatar_url(String avatar_url){
            this.avatar_url = avatar_url;
        }
        public String getNickname(){
            return nickname;
        }

        public void setNickname(String nickname){
            this.nickname = nickname;
        }

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

        public String getPic_urls() {
            return pic_urls;
        }

        public void setPic_urls(String pic_urls) {
            this.pic_urls = pic_urls;
        }

        public int getAvailable_flag() {
            return available_flag;
        }

        public void setAvailable_flag(int available_flag) {
            this.available_flag = available_flag;
        }

        public Object getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(Object delete_flag) {
            this.delete_flag = delete_flag;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public Object getInsert_time() {
            return insert_time;
        }

        public void setInsert_time(Object insert_time) {
            this.insert_time = insert_time;
        }

        public int getSign_flag() {
            return sign_flag;
        }

        public void setSign_flag(int sign_flag) {
            this.sign_flag = sign_flag;
        }

        public int getCollection_flag() {
            return collection_flag;
        }

        public void setCollection_flag(int collection_flag) {
            this.collection_flag = collection_flag;
        }

        public List<String> getPic_urls_list() {
            return pic_urls_list;
        }

        public void setPic_urls_list(List<String> pic_urls_list) {
            this.pic_urls_list = pic_urls_list;
        }
    }
}
