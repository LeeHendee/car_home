package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2017/2/22.
 * description:
 */
public class QuestionBean implements Serializable {


    /**
     * result : [{"id":"0f058e4ccb6c4ceb8af2ba9f627f02e3","user_id":"a57348bc5608401fa9f7c11563e7c7b6","type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"福克斯手动1.8L,百公里油耗大概是多少","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-25 15:39:ic_launcher","update_time":"2017-01-25 15:39:ic_launcher","publish_time":null,"expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":null,"motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":null,"html_url":null},{"id":"42d22ddc93cf4d398b188e3a8b83b607","user_id":"a57348bc5608401fa9f7c11563e7c7b6","type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"ssssdddd","res_url_list":null,"delete_flag":0,"insert_time":"2017-02-16 11:54:37","update_time":"2017-02-16 11:54:37","publish_time":null,"expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":null,"motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":null,"html_url":null},{"id":"b70158e45cc44de196f3c00be467b2be","user_id":"a57348bc5608401fa9f7c11563e7c7b6","type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"10万元落地到底能买什么车？！","res_url_list":"http://192.168.1.71/carhome/question/b70158e45cc44de196f3c00be467b2be/1485217838086.JPEG","delete_flag":0,"insert_time":"2017-01-25 08:49:24","update_time":"2017-02-17 09:52:39","publish_time":null,"expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":null,"motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":null,"html_url":null}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 0f058e4ccb6c4ceb8af2ba9f627f02e3
     * user_id : a57348bc5608401fa9f7c11563e7c7b6
     * type : 1
     * title : null
     * support_number : 0
     * favor_number : 0
     * glance_number : 0
     * content : 福克斯手动1.8L,百公里油耗大概是多少
     * res_url_list : null
     * delete_flag : 0
     * insert_time : 2017-01-25 15:39:ic_launcher
     * update_time : 2017-01-25 15:39:ic_launcher
     * publish_time : null
     * expert_name : null
     * expert_portrait : null
     * user_portrait : null
     * reply_num : null
     * motto : null
     * telephone_number : null
     * expert_wechat_number : null
     * nickname : null
     * is_favored : null
     * html_url : null
     */

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

    public static class ResultBean implements Serializable {
        private String id;
        private String user_id;
        private int type;
        private Object title;
        private int support_number;
        private int favor_number;
        private int glance_number;
        private String content;
        private Object res_url_list;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private Object publish_time;
        private Object expert_name;
        private Object expert_portrait;
        private Object user_portrait;
        private Object reply_num;
        private Object motto;
        private Object telephone_number;
        private Object expert_wechat_number;
        private Object nickname;
        private Object is_favored;
        private Object html_url;

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

        public Object getRes_url_list() {
            return res_url_list;
        }

        public void setRes_url_list(Object res_url_list) {
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

        public Object getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(Object publish_time) {
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

        public Object getUser_portrait() {
            return user_portrait;
        }

        public void setUser_portrait(Object user_portrait) {
            this.user_portrait = user_portrait;
        }

        public Object getReply_num() {
            return reply_num;
        }

        public void setReply_num(Object reply_num) {
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

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
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
    }
}
