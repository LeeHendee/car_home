package com.example.gtercn.car.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-5-18.
 * 用户数据结构
 */
public class User implements Serializable {

    /**
     * err_code : 0
     * err_message : OK
     * result : {"user_info":{"user_id":"a57348bc5608401fa9f7c11563e7c7b6","login_phone":"18642690086","password":"e10adc3949ba59abbe56e057f20f883e","nickname":"那年冬天","avatar_url":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/b80b7ff05a8a4968bfd77313d71c1657.jpg","device_token":"1","device_type":2,"update_time":"2016-12-28 17:25:21.0","insert_time":"2016-12-12 10:47:05.0","delete_flag":0},"token":"0351B79A9DC05135263BF2BC08C992B02A00C510AC0E61282C471CFA25A644B72C16F01235A1B1157FF9D7B3F9E1372807653A05FE6F1C3E"}
     * message : 欢迎回来
     */

    private String err_code;
    private String err_message;
    /**
     * user_info : {"user_id":"a57348bc5608401fa9f7c11563e7c7b6","login_phone":"18642690086","password":"e10adc3949ba59abbe56e057f20f883e","nickname":"那年冬天","avatar_url":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/b80b7ff05a8a4968bfd77313d71c1657.jpg","device_token":"1","device_type":2,"update_time":"2016-12-28 17:25:21.0","insert_time":"2016-12-12 10:47:05.0","delete_flag":0}
     * token : 0351B79A9DC05135263BF2BC08C992B02A00C510AC0E61282C471CFA25A644B72C16F01235A1B1157FF9D7B3F9E1372807653A05FE6F1C3E
     */

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

    @Override
    public String toString() {
        return "User{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", result=" + result +
                ", message='" + message + '\'' +
                '}';
    }

    public static class ResultBean implements Serializable {
        /**
         * user_id : a57348bc5608401fa9f7c11563e7c7b6
         * login_phone : 18642690086
         * password : e10adc3949ba59abbe56e057f20f883e
         * nickname : 那年冬天
         * avatar_url : http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/b80b7ff05a8a4968bfd77313d71c1657.jpg
         * device_token : 1
         * device_type : 2
         * update_time : 2016-12-28 17:25:21.0
         * insert_time : 2016-12-12 10:47:05.0
         * delete_flag : 0
         */

        private UserInfoBean user_info;
        private String token;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "user_info=" + user_info +
                    ", token='" + token + '\'' +
                    '}';
        }

        public static class UserInfoBean implements Serializable {
            private String user_id;
            private String login_phone;
            private String password;
            private String nickname;
            private String real_name;
            private int sex;
            private String avatar_url;
            private String device_token;
            private int device_type;
            private String update_time;
            private String insert_time;
            private int delete_flag;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getLogin_phone() {
                return login_phone;
            }

            public void setLogin_phone(String login_phone) {
                this.login_phone = login_phone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public String getDevice_token() {
                return device_token;
            }

            public void setDevice_token(String device_token) {
                this.device_token = device_token;
            }

            public int getDevice_type() {
                return device_type;
            }

            public void setDevice_type(int device_type) {
                this.device_type = device_type;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getInsert_time() {
                return insert_time;
            }

            public void setInsert_time(String insert_time) {
                this.insert_time = insert_time;
            }

            public int getDelete_flag() {
                return delete_flag;
            }

            public void setDelete_flag(int delete_flag) {
                this.delete_flag = delete_flag;
            }

            @Override
            public String toString() {
                return "UserInfoBean{" +
                        "user_id='" + user_id + '\'' +
                        ", login_phone='" + login_phone + '\'' +
                        ", password='" + password + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", real_name='" + real_name + '\'' +
                        ", sex='" + sex + '\'' +
                        ", avatar_url='" + avatar_url + '\'' +
                        ", device_token='" + device_token + '\'' +
                        ", device_type=" + device_type +
                        ", update_time='" + update_time + '\'' +
                        ", insert_time='" + insert_time + '\'' +
                        ", delete_flag=" + delete_flag +
                        '}';
            }
        }
    }
}
