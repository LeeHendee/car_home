package com.example.gtercn.car.bean;

import java.io.Serializable;

/**
 * author : LiHang.
 * data : 2017/3/14.
 * description:
 */
public class ExpertQuestionDetailBean implements Serializable {

    /**
     * err_code : 0
     * err_message : OK
     * result : {"id":"92378240b84d4996b3e0deb1b2622ac6","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"不说什么了，收藏有问题啊！","res_url_list":"http://192.168.1.71\\carhome\\question\\92378240b84d4996b3e0deb1b2622ac6\\1489462392545.jpg,http://192.168.1.71\\carhome\\question\\92378240b84d4996b3e0deb1b2622ac6\\1489462392872.jpg","delete_flag":0,"insert_time":"2017-03-13 16:01:23","update_time":"2017-03-14 11:35:52","publish_time":"2017-03-13 16:03","expert_name":null,"expert_portrait":null,"user_portrait":"http://192.168.1.71/carhome/users/avatar/0e038e3d4ef149df9515fcde3abe4a86/75859f9a71bc492dab1deb01c075ef40.jpg","reply_num":"1","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":"多重人格","is_favored":null,"html_url":null,"introduction":null,"city_code":null}
     * message : null
     */

    private String err_code;
    private String err_message;
    /**
     * id : 92378240b84d4996b3e0deb1b2622ac6
     * user_id : null
     * type : 1
     * title : null
     * support_number : 0
     * favor_number : 0
     * glance_number : 0
     * content : 不说什么了，收藏有问题啊！
     * res_url_list : http://192.168.1.71\carhome\question\92378240b84d4996b3e0deb1b2622ac6\1489462392545.jpg,http://192.168.1.71\carhome\question\92378240b84d4996b3e0deb1b2622ac6\1489462392872.jpg
     * delete_flag : 0
     * insert_time : 2017-03-13 16:01:23
     * update_time : 2017-03-14 11:35:52
     * publish_time : 2017-03-13 16:03
     * expert_name : null
     * expert_portrait : null
     * user_portrait : http://192.168.1.71/carhome/users/avatar/0e038e3d4ef149df9515fcde3abe4a86/75859f9a71bc492dab1deb01c075ef40.jpg
     * reply_num : 1
     * motto : null
     * telephone_number : null
     * expert_wechat_number : null
     * nickname : 多重人格
     * is_favored : null
     * html_url : null
     * introduction : null
     * city_code : null
     */

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

    @Override
    public String toString() {
        return "ExpertQuestionDetailBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", result=" + result +
                ", message=" + message +
                '}';
    }

    public static class ResultBean implements Serializable{
        private String id;
        private Object user_id;
        private int type;
        private Object title;
        private int support_number;
        private int favor_number;
        private int glance_number;
        private String content;
        private String res_url_list;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private String publish_time;
        private Object expert_name;
        private Object expert_portrait;
        private String user_portrait;
        private String reply_num;
        private Object motto;
        private Object telephone_number;
        private Object expert_wechat_number;
        private String nickname;
        private Object is_favored;
        private Object html_url;
        private Object introduction;
        private Object city_code;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public int getSupport_number() {
            return support_number;
        }

        public void setSupport_number(int support_number) {
            this.support_number = support_number;
        }

        public int getFavor_number() {
            return favor_number;
        }

        public void setFavor_number(int favor_number) {
            this.favor_number = favor_number;
        }

        public int getGlance_number() {
            return glance_number;
        }

        public void setGlance_number(int glance_number) {
            this.glance_number = glance_number;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRes_url_list() {
            return res_url_list;
        }

        public void setRes_url_list(String res_url_list) {
            this.res_url_list = res_url_list;
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

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public Object getExpert_name() {
            return expert_name;
        }

        public void setExpert_name(Object expert_name) {
            this.expert_name = expert_name;
        }

        public Object getExpert_portrait() {
            return expert_portrait;
        }

        public void setExpert_portrait(Object expert_portrait) {
            this.expert_portrait = expert_portrait;
        }

        public String getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(String user_portrait) {
            this.user_portrait = user_portrait;
        }

        public String getReply_num() {
            return reply_num;
        }

        public void setReply_num(String reply_num) {
            this.reply_num = reply_num;
        }

        public Object getMotto() {
            return motto;
        }

        public void setMotto(Object motto) {
            this.motto = motto;
        }

        public Object getTelephone_number() {
            return telephone_number;
        }

        public void setTelephone_number(Object telephone_number) {
            this.telephone_number = telephone_number;
        }

        public Object getExpert_wechat_number() {
            return expert_wechat_number;
        }

        public void setExpert_wechat_number(Object expert_wechat_number) {
            this.expert_wechat_number = expert_wechat_number;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(Object is_favored) {
            this.is_favored = is_favored;
        }

        public Object getHtml_url() {
            return html_url;
        }

        public void setHtml_url(Object html_url) {
            this.html_url = html_url;
        }

        public Object getIntroduction() {
            return introduction;
        }

        public void setIntroduction(Object introduction) {
            this.introduction = introduction;
        }

        public Object getCity_code() {
            return city_code;
        }

        public void setCity_code(Object city_code) {
            this.city_code = city_code;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", user_id=" + user_id +
                    ", type=" + type +
                    ", title=" + title +
                    ", support_number=" + support_number +
                    ", favor_number=" + favor_number +
                    ", glance_number=" + glance_number +
                    ", content='" + content + '\'' +
                    ", res_url_list='" + res_url_list + '\'' +
                    ", delete_flag=" + delete_flag +
                    ", insert_time='" + insert_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", publish_time='" + publish_time + '\'' +
                    ", expert_name=" + expert_name +
                    ", expert_portrait=" + expert_portrait +
                    ", user_portrait='" + user_portrait + '\'' +
                    ", reply_num='" + reply_num + '\'' +
                    ", motto=" + motto +
                    ", telephone_number=" + telephone_number +
                    ", expert_wechat_number=" + expert_wechat_number +
                    ", nickname='" + nickname + '\'' +
                    ", is_favored=" + is_favored +
                    ", html_url=" + html_url +
                    ", introduction=" + introduction +
                    ", city_code=" + city_code +
                    '}';
        }
    }
}
