package com.example.gtercn.car.bean;


import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/11/17.
 * description:
 * 问题墙列表数据;
 */
public class ExpertQuestionBean implements Serializable {



    /**
     * result : [{"id":"0ab01477d86f44958678b7fa9bb08f68","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"1","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-13 14:11:52","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-13 14:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"39","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"1","user_id":null,"type":1,"title":"洗车应该注意哪些事项？","support_number":0,"favor_number":0,"glance_number":0,"content":"","res_url_list":"http://192.168.1.71附件1,http://192.168.1.71附件2,http://192.168.1.71附件3","delete_flag":0,"insert_time":"2016-12-14 10:56:26","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-14 10:12","expert_name":"张三","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/3ca8cd3b67cb4a518367c9d7700eabf4/063559df52fa4f549af82ee4d4c37bd6.jpg","reply_num":"61","motto":"有智者立长志，无志者长立志","telephone_number":null,"expert_wechat_number":"888923562","nickname":"test","is_favored":"0","html_url":null},{"id":"162a1163792a4874b362a99367d64d12","user_id":null,"type":1,"title":"阿斯顿发圣诞","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":null,"delete_flag":0,"insert_time":"2016-12-28 16:54:57","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 16:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"11","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"16f9bd1065c74270a17129203b9ce224","user_id":null,"type":1,"title":"1","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":null,"delete_flag":0,"insert_time":"2016-12-28 16:19:55","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 16:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"2af828f7053c485cbec343348d82e169","user_id":null,"type":1,"title":"购买国产汽车好吗？","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":"http://192.168.1.71\\carhome\\question\\2af828f7053c485cbec343348d82e169\\1482806525666.jpg","delete_flag":0,"insert_time":"2016-12-27 10:42:54","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-27 10:12","expert_name":null,"expert_portrait":null,"user_portrait":"http://192.168.1.71/carhome/users/avatar/a7810bebc1f74a6fa4af4705440e5cfb/8102570a40664084a8e448fdd91f3112.jpg","reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":"天天","is_favored":"0","html_url":null},{"id":"443812f7bfd041218bffa45edf21d998","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"是对方的发生","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-13 14:13:08","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-13 14:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"446e47c57c6b4b489bf50577a3b74065","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"test","res_url_list":null,"delete_flag":0,"insert_time":"2016-12-29 09:52:49","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-29 09:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"598ada835b1c4dbc8aaa08e37a171886","user_id":null,"type":1,"title":"张飞","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":null,"delete_flag":0,"insert_time":"2016-12-29 09:42:23","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-29 09:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"6a21946bca70493c974b9ae9eecc03a5","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"不不不","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-20 11:28:40","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-20 11:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"6d4f3823157d4ee89644d7529fa14794","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"2017","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-22 15:57:34","update_time":"2017-01-22 15:57:34","publish_time":"1小时前","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"76a3b640698f43268cdcbc8d89264cba","user_id":null,"type":1,"title":"test","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":null,"delete_flag":0,"insert_time":"2016-12-28 16:18:59","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 16:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"83cdb2532a354112a917c9415986503e","user_id":null,"type":1,"title":"test","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":"http://192.168.1.71\\carhome\\question\\83cdb2532a354112a917c9415986503e\\1482912807461.jpg","delete_flag":0,"insert_time":"2016-12-28 16:14:26","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 16:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"85274395ec8b4191b6a0762182aa6a1d","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"海陆空","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-20 11:16:45","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-20 11:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"8808ac46d76e43f78905f27a63aa0e03","user_id":null,"type":1,"title":"test","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":"http://192.168.1.71\\carhome\\question\\8808ac46d76e43f78905f27a63aa0e03\\1482912774999.jpg","delete_flag":0,"insert_time":"2016-12-28 16:13:54","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 16:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"8d9c5f0309d644539c12479631227a79","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"阿斯顿发大大","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-13 14:13:43","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-13 14:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"9e1f0060444040eca783a753581eb982","user_id":null,"type":1,"title":"的说法是打算","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":null,"delete_flag":0,"insert_time":"2016-12-28 17:00:39","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 17:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"a20461ce9f9d4a8b9d8e3b823552f4c6","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"背了","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-20 11:31:27","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-20 11:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"b933c9a111014c36b0ec78cf972a9ef9","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"门店","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-20 11:38:11","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-20 11:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"c15d39d58bb54555b4a2883b4acf0566","user_id":null,"type":1,"title":"test","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":"http://192.168.1.71\\carhome\\question\\c15d39d58bb54555b4a2883b4acf0566\\1482913227187.jpg","delete_flag":0,"insert_time":"2016-12-28 16:22:39","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 16:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null},{"id":"c88dfcd397d843619979933896a22961","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"fhjhfg","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-22 15:56:19","update_time":"2017-01-22 15:56:19","publish_time":"1小时前","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"d5a5fdc4d46d4d449e4e6419944d1cf7","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"1","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-13 14:11:53","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-13 14:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"fae16d482fb04e338eb6920ebe5ea741","user_id":null,"type":1,"title":null,"support_number":0,"favor_number":0,"glance_number":0,"content":"想跑","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-13 15:07:15","update_time":"2017-01-22 15:55:23","publish_time":"2017-01-13 15:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","user_portrait":"http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"fe55bafa85a94adf967c6507c80e5c7d","user_id":null,"type":1,"title":"深刻的叫法是对方就上课的坚实的","support_number":0,"favor_number":0,"glance_number":0,"content":null,"res_url_list":null,"delete_flag":0,"insert_time":"2016-12-28 17:00:00","update_time":"2017-01-22 15:55:23","publish_time":"2016-12-28 17:12","expert_name":null,"expert_portrait":null,"user_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":null,"is_favored":"0","html_url":null}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 0ab01477d86f44958678b7fa9bb08f68
     * user_id : null
     * type : 1
     * title : null
     * support_number : 0
     * favor_number : 0
     * glance_number : 0
     * content : 1
     * res_url_list : null
     * delete_flag : 0
     * insert_time : 2017-01-13 14:11:52
     * update_time : 2017-01-22 15:55:23
     * publish_time : 2017-01-13 14:01
     * expert_name : 李四
     * expert_portrait : http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg
     * user_portrait : http://192.168.1.71/carhome/users/avatar/a57348bc5608401fa9f7c11563e7c7b6/e77f6739a41941afb6aa90b8196a9c13.jpg
     * reply_num : 39
     * motto : 闪电从不打在相同的地方，人不该被相同的方式伤害两次
     * telephone_number : null
     * expert_wechat_number : 135462699
     * nickname : 那年冬天
     * is_favored : 0
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

    @Override
    public String toString() {
        return "ExpertQuestionBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message=" + message +
                ", result=" + result +
                '}';
    }

    public static class ResultBean implements Serializable {
        private String id;
        private Object user_id;
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
        private String publish_time;
        private String expert_name;
        private String expert_portrait;
        private String user_portrait;
        private String reply_num;
        private String motto;
        private Object telephone_number;
        private String expert_wechat_number;
        private String nickname;
        private String is_favored;
        private Object html_url;

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

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getExpert_name() {
            return expert_name;
        }

        public void setExpert_name(String expert_name) {
            this.expert_name = expert_name;
        }

        public String getExpert_portrait() {
            return expert_portrait;
        }

        public void setExpert_portrait(String expert_portrait) {
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

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public Object getTelephone_number() {
            return telephone_number;
        }

        public void setTelephone_number(Object telephone_number) {
            this.telephone_number = telephone_number;
        }

        public String getExpert_wechat_number() {
            return expert_wechat_number;
        }

        public void setExpert_wechat_number(String expert_wechat_number) {
            this.expert_wechat_number = expert_wechat_number;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(String is_favored) {
            this.is_favored = is_favored;
        }

        public Object getHtml_url() {
            return html_url;
        }

        public void setHtml_url(Object html_url) {
            this.html_url = html_url;
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
                    ", res_url_list=" + res_url_list +
                    ", delete_flag=" + delete_flag +
                    ", insert_time='" + insert_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", publish_time='" + publish_time + '\'' +
                    ", expert_name='" + expert_name + '\'' +
                    ", expert_portrait='" + expert_portrait + '\'' +
                    ", user_portrait='" + user_portrait + '\'' +
                    ", reply_num='" + reply_num + '\'' +
                    ", motto='" + motto + '\'' +
                    ", telephone_number=" + telephone_number +
                    ", expert_wechat_number='" + expert_wechat_number + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", is_favored='" + is_favored + '\'' +
                    ", html_url=" + html_url +
                    '}';
        }
    }
}
