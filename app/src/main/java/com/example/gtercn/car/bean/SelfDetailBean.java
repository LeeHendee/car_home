package com.example.gtercn.car.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class SelfDetailBean {

    /**
     * err_code : 0
     * err_message : OK
     * result : {"id":"a08c3096d82b41a18a351585a96358d2","user_id":null,"title":"测试","content":"测试内容","pic_urls":"\\carhome\\evaluation\\a08c3096d82b41a18a351585a96358d2\\1489393606976.jpg","pic_urls_list":["http://192.168.1.71\\carhome\\evaluation\\a08c3096d82b41a18a351585a96358d2\\1489393606976.jpg"],"available_flag":1,"delete_flag":null,"update_time":"19小时前  2017-03-13","insert_time":null,"sign_flag":0,"collection_flag":0,"nickname":"多重人格","avatar_url":"http://192.168.1.71/carhome/users/avatar/0e038e3d4ef149df9515fcde3abe4a86/75859f9a71bc492dab1deb01c075ef40.jpg","city_code":"210900"}
     * message : 查询自驾游成功
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
         * id : a08c3096d82b41a18a351585a96358d2
         * user_id : null
         * title : 测试
         * content : 测试内容
         * pic_urls : \carhome\evaluation\a08c3096d82b41a18a351585a96358d2\1489393606976.jpg
         * pic_urls_list : ["http://192.168.1.71\\carhome\\evaluation\\a08c3096d82b41a18a351585a96358d2\\1489393606976.jpg"]
         * available_flag : 1
         * delete_flag : null
         * update_time : 19小时前  2017-03-13
         * insert_time : null
         * sign_flag : 0
         * collection_flag : 0
         * nickname : 多重人格
         * avatar_url : http://192.168.1.71/carhome/users/avatar/0e038e3d4ef149df9515fcde3abe4a86/75859f9a71bc492dab1deb01c075ef40.jpg
         * city_code : 210900
         */

        private String id;
        private Object user_id;
        private String title;
        private String content;
        private String pic_urls;
        private int available_flag;
        private Object delete_flag;
        private String update_time;
        private Object insert_time;
        private int sign_flag;
        private int collection_flag;
        private String nickname;
        private String avatar_url;
        private String city_code;
        private List<String> pic_urls_list;
        private String enroll_flag;//报名flag(1:能,0:不能)
        private String public_flag;//用户flag(1:发布,0:报名)
        private int count;
        private String activity_time;

        public String getActivity_time(){
            return activity_time;
        }

        public void setActivity_time(String activity_time){
            this.activity_time = activity_time;
        }

        public int getCount(){
            return count;
        }

        public void setCount(int count){
            this.count = count;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public List<String> getPic_urls_list() {
            return pic_urls_list;
        }

        public void setPic_urls_list(List<String> pic_urls_list) {
            this.pic_urls_list = pic_urls_list;
        }

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
    }
}
