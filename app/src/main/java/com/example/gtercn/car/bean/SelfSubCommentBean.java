package com.example.gtercn.car.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class SelfSubCommentBean {

    /**
     * result : [{"id":"C27DC65BC19CE492F619BEB2870EEC76","user_id":"3ca8cd3b67cb4a518367c9d7700eabf4","to_user_id":"a57348bc5608401fa9f7c11563e7c7b6","type_id":null,"reference_id":null,"item_id":null,"content":"我也挺想去，可惜最近比较忙，没时间","delete_flag":null,"insert_time":"2017-01-04 10:12","update_time":null,"nickname":"test","to_nickname":"那年冬天","reply_num":null},{"id":"7CC041D5A32027BEA42D9081F1F44D28","user_id":"a57348bc5608401fa9f7c11563e7c7b6","to_user_id":"3ca8cd3b67cb4a518367c9d7700eabf4","type_id":null,"reference_id":null,"item_id":null,"content":"那就等着闲下来的时候去吧","delete_flag":null,"insert_time":"2017-01-09  -1378分钟前","update_time":null,"nickname":"那年冬天","to_nickname":"test","reply_num":null},{"id":"311581C138EB97A2B6388BD6C0BC0D3A","user_id":"3ca8cd3b67cb4a518367c9d7700eabf4","to_user_id":"a57348bc5608401fa9f7c11563e7c7b6","type_id":null,"reference_id":null,"item_id":null,"content":"这个主意不错哟！","delete_flag":null,"insert_time":"2017-01-09  -1379分钟前","update_time":null,"nickname":"test","to_nickname":"那年冬天","reply_num":null}]
     * err_code : 0
     * err_message : OK
     * message : 查询自驾游评论回复成功
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

    public static class ResultBean {
        /**
         * id : C27DC65BC19CE492F619BEB2870EEC76
         * user_id : 3ca8cd3b67cb4a518367c9d7700eabf4
         * to_user_id : a57348bc5608401fa9f7c11563e7c7b6
         * type_id : null
         * reference_id : null
         * item_id : null
         * content : 我也挺想去，可惜最近比较忙，没时间
         * delete_flag : null
         * insert_time : 2017-01-04 10:12
         * update_time : null
         * nickname : test
         * to_nickname : 那年冬天
         * reply_num : null
         */

        private String id;
        private String user_id;
        private String to_user_id;
        private Object type_id;
        private Object reference_id;
        private Object item_id;
        private String content;
        private Object delete_flag;
        private String insert_time;
        private Object update_time;
        private String nickname;
        private String to_nickname;
        private Object reply_num;

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

        public String getTo_user_id() {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id) {
            this.to_user_id = to_user_id;
        }

        public Object getType_id() {
            return type_id;
        }

        public void setType_id(Object type_id) {
            this.type_id = type_id;
        }

        public Object getReference_id() {
            return reference_id;
        }

        public void setReference_id(Object reference_id) {
            this.reference_id = reference_id;
        }

        public Object getItem_id() {
            return item_id;
        }

        public void setItem_id(Object item_id) {
            this.item_id = item_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(Object delete_flag) {
            this.delete_flag = delete_flag;
        }

        public String getInsert_time() {
            return insert_time;
        }

        public void setInsert_time(String insert_time) {
            this.insert_time = insert_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTo_nickname() {
            return to_nickname;
        }

        public void setTo_nickname(String to_nickname) {
            this.to_nickname = to_nickname;
        }

        public Object getReply_num() {
            return reply_num;
        }

        public void setReply_num(Object reply_num) {
            this.reply_num = reply_num;
        }
    }
}
