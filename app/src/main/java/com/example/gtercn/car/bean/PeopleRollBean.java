package com.example.gtercn.car.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class PeopleRollBean {


    /**
     * message : 查看名单
     * result : [{"nickname":"haha","avatarUrl":"http://192.168.1.71/carhome/users/avatar/11dfa745fc8c4ae6b2ddd331058ad10a/24ecae43e78446f5b572f719cb527b25.jpg"}]
     * err_message : OK
     * err_code : 0
     */

    private String message;
    private String err_message;
    private String err_code;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * nickname : haha
         * avatarUrl : http://192.168.1.71/carhome/users/avatar/11dfa745fc8c4ae6b2ddd331058ad10a/24ecae43e78446f5b572f719cb527b25.jpg
         */

        private String nickname;
        private String avatarUrl;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
