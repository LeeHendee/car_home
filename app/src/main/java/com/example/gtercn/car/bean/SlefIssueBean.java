package com.example.gtercn.car.bean;

/**
 * Created by Administrator on 2017/1/10.
 */

public class SlefIssueBean {

    /**
     * err_code : 0
     * err_message : OK
     * result : {"id":"ed5306e749094b488e757ed2affb1a54","user_id":"9c42dc3e16ff455eb5ea9eeff69ed6c4","title":"233123","content":"123","pic_urls":"carhome/evaluation/ed5306e749094b488e757ed2affb1a54/1483944792952.png","pic_urls_list":null,"available_flag":1,"delete_flag":0,"update_time":null,"insert_time":null,"sign_flag":null,"collection_flag":null}
     * message : 发布成功
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
         * id : ed5306e749094b488e757ed2affb1a54
         * user_id : 9c42dc3e16ff455eb5ea9eeff69ed6c4
         * title : 233123
         * content : 123
         * pic_urls : carhome/evaluation/ed5306e749094b488e757ed2affb1a54/1483944792952.png
         * pic_urls_list : null
         * available_flag : 1
         * delete_flag : 0
         * update_time : null
         * insert_time : null
         * sign_flag : null
         * collection_flag : null
         */

        private String id;
        private String user_id;
        private String title;
        private String content;
        private String pic_urls;
        private Object pic_urls_list;
        private int available_flag;
        private int delete_flag;
        private Object update_time;
        private Object insert_time;
        private Object sign_flag;
        private Object collection_flag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
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

        public Object getPic_urls_list() {
            return pic_urls_list;
        }

        public void setPic_urls_list(Object pic_urls_list) {
            this.pic_urls_list = pic_urls_list;
        }

        public int getAvailable_flag() {
            return available_flag;
        }

        public void setAvailable_flag(int available_flag) {
            this.available_flag = available_flag;
        }

        public int getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(int delete_flag) {
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

        public Object getSign_flag() {
            return sign_flag;
        }

        public void setSign_flag(Object sign_flag) {
            this.sign_flag = sign_flag;
        }

        public Object getCollection_flag() {
            return collection_flag;
        }

        public void setCollection_flag(Object collection_flag) {
            this.collection_flag = collection_flag;
        }
    }
}
