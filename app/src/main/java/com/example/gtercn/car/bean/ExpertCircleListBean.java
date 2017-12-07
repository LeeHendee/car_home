package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/11/17.
 * description:
 * 达人圈列表数据
 */
public class ExpertCircleListBean implements Serializable {

    /**
     * result : [{"id":"2","user_id":null,"type":3,"title":"修车应该注意哪些事项？","support_number":1,"favor_number":1,"glance_number":20,"content":"修车文章内容","res_url_list":"http://192.168.1.71附件1,http://192.168.1.71附件2,http://192.168.1.71附件3","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-16","publish_time":"2016-12-13 15:12","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null},{"id":"3","user_id":null,"type":3,"title":"汽车内饰选购","support_number":1,"favor_number":1,"glance_number":20,"content":"修车文章内容","res_url_list":"http://192.168.1.71附件1,http://192.168.1.71附件2,http://192.168.1.71附件3","delete_flag":0,"insert_time":"2016-12-15","update_time":"2017-01-09","publish_time":"2016-12-15 08:12","expert_name":null,"expert_portrait":null,"reply_num":"0","motto":null,"telephone_number":null,"expert_wechat_number":null,"nickname":"萧炎","is_favored":"0","html_url":"http://192.168.1.71/carhome/htmls/article/article2017-1-9.html"},{"id":"5d09a070091b4fb18a6dba936f71e0c4","user_id":null,"type":3,"title":null,"support_number":null,"favor_number":null,"glance_number":null,"content":"1","res_url_list":null,"delete_flag":0,"insert_time":"2017-01-13","update_time":"2017-01-13","publish_time":"2017-01-13 14:01","expert_name":"李四","expert_portrait":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","reply_num":"0","motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","telephone_number":null,"expert_wechat_number":"135462699","nickname":"那年冬天","is_favored":"0","html_url":null}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 2
     * user_id : null
     * type : 3
     * title : 修车应该注意哪些事项？
     * support_number : 1
     * favor_number : 1
     * glance_number : 20
     * content : 修车文章内容
     * res_url_list : http://192.168.1.71附件1,http://192.168.1.71附件2,http://192.168.1.71附件3
     * delete_flag : 0
     * insert_time : 2016-12-13
     * update_time : 2016-12-16
     * publish_time : 2016-12-13 15:12
     * expert_name : 李四
     * expert_portrait : http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg
     * reply_num : 0
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
        return "ExpertCircleListBean{" +
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
        private String title;
        private int support_number;
        private int favor_number;
        private int glance_number;
        private String content;
        private String res_url_list;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private String publish_time;
        private String expert_name;
        private String expert_portrait;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
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
                    ", title='" + title + '\'' +
                    ", support_number=" + support_number +
                    ", favor_number=" + favor_number +
                    ", glance_number=" + glance_number +
                    ", content='" + content + '\'' +
                    ", res_url_list='" + res_url_list + '\'' +
                    ", delete_flag=" + delete_flag +
                    ", insert_time='" + insert_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", publish_time='" + publish_time + '\'' +
                    ", expert_name='" + expert_name + '\'' +
                    ", expert_portrait='" + expert_portrait + '\'' +
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
