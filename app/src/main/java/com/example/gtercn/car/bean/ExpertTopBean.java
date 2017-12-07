package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/12/15.
 * description:
 */
public class ExpertTopBean implements Serializable {


    /**
     * result : [{"id":"1","user_id":"3ca8cd3b67cb4a518367c9d7700eabf4","top_title":"汽车买卖","expert_name":"张三","expert_discription_short":null,"expert_portrait_url":"http://192.168.1.71/carhome/expert/portrait/2/portrait.jpg","expert_wechat_number":null,"expert_experience":"8","expert_tel_number":null,"motto":"有智者立长志，无志者长立志","expert_display_pic_list":null,"expert_discription_detail":null,"delete_flag":0,"insert_time":"2016-12-14","update_time":"2016-12-15","display_pic_list":null},{"id":"2","user_id":"a57348bc5608401fa9f7c11563e7c7b6","top_title":"汽车买卖","expert_name":"李四","expert_discription_short":null,"expert_portrait_url":"http://192.168.1.71/carhome/expert/portrait/2/portrait_2.jpg","expert_wechat_number":null,"expert_experience":"3","expert_tel_number":null,"motto":"闪电从不打在相同的地方，人不该被相同的方式伤害两次","expert_display_pic_list":"http://192.168.1.71/carhome/expert/details/180.JPEG,http://192.168.1.71/carhome/expert/details/182.JPEG,http://192.168.1.71/carhome/expert/details/183.JPEG,http://192.168.1.71/carhome/expert/details/184.JPEG","expert_discription_detail":null,"delete_flag":0,"insert_time":"2016-02-01","update_time":"2016-12-15","display_pic_list":["http://192.168.1.71/carhome/expert/details/180.JPEG","http://192.168.1.71/carhome/expert/details/182.JPEG","http://192.168.1.71/carhome/expert/details/183.JPEG","http://192.168.1.71/carhome/expert/details/184.JPEG"]}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 1
     * user_id : 3ca8cd3b67cb4a518367c9d7700eabf4
     * top_title : 汽车买卖
     * expert_name : 张三
     * expert_discription_short : null
     * expert_portrait_url : http://192.168.1.71/carhome/expert/portrait/2/portrait.jpg
     * expert_wechat_number : null
     * expert_experience : 8
     * expert_tel_number : null
     * motto : 有智者立长志，无志者长立志
     * expert_display_pic_list : null
     * expert_discription_detail : null
     * delete_flag : 0
     * insert_time : 2016-12-14
     * update_time : 2016-12-15
     * display_pic_list : null
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
        return "ExpertTopBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message=" + message +
                ", result=" + result +
                '}';
    }

    public static class ResultBean implements Serializable {
        private String id;
        private String user_id;
        private String top_title;
        private String expert_name;
        private Object expert_discription_short;
        private String expert_portrait_url;
        private Object expert_wechat_number;
        private String expert_experience;
        private Object expert_tel_number;
        private String motto;
        private Object expert_display_pic_list;
        private Object expert_discription_detail;
        private int delete_flag;
        private String insert_time;
        private String update_time;
        private Object display_pic_list;

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

        public String getTop_title() {
            return top_title;
        }

        public void setTop_title(String top_title) {
            this.top_title = top_title;
        }

        public String getExpert_name() {
            return expert_name;
        }

        public void setExpert_name(String expert_name) {
            this.expert_name = expert_name;
        }

        public Object getExpert_discription_short() {
            return expert_discription_short;
        }

        public void setExpert_discription_short(Object expert_discription_short) {
            this.expert_discription_short = expert_discription_short;
        }

        public String getExpert_portrait_url() {
            return expert_portrait_url;
        }

        public void setExpert_portrait_url(String expert_portrait_url) {
            this.expert_portrait_url = expert_portrait_url;
        }

        public Object getExpert_wechat_number() {
            return expert_wechat_number;
        }

        public void setExpert_wechat_number(Object expert_wechat_number) {
            this.expert_wechat_number = expert_wechat_number;
        }

        public String getExpert_experience() {
            return expert_experience;
        }

        public void setExpert_experience(String expert_experience) {
            this.expert_experience = expert_experience;
        }

        public Object getExpert_tel_number() {
            return expert_tel_number;
        }

        public void setExpert_tel_number(Object expert_tel_number) {
            this.expert_tel_number = expert_tel_number;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public Object getExpert_display_pic_list() {
            return expert_display_pic_list;
        }

        public void setExpert_display_pic_list(Object expert_display_pic_list) {
            this.expert_display_pic_list = expert_display_pic_list;
        }

        public Object getExpert_discription_detail() {
            return expert_discription_detail;
        }

        public void setExpert_discription_detail(Object expert_discription_detail) {
            this.expert_discription_detail = expert_discription_detail;
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

        public Object getDisplay_pic_list() {
            return display_pic_list;
        }

        public void setDisplay_pic_list(Object display_pic_list) {
            this.display_pic_list = display_pic_list;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", top_title='" + top_title + '\'' +
                    ", expert_name='" + expert_name + '\'' +
                    ", expert_discription_short=" + expert_discription_short +
                    ", expert_portrait_url='" + expert_portrait_url + '\'' +
                    ", expert_wechat_number=" + expert_wechat_number +
                    ", expert_experience='" + expert_experience + '\'' +
                    ", expert_tel_number=" + expert_tel_number +
                    ", motto='" + motto + '\'' +
                    ", expert_display_pic_list=" + expert_display_pic_list +
                    ", expert_discription_detail=" + expert_discription_detail +
                    ", delete_flag=" + delete_flag +
                    ", insert_time='" + insert_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", display_pic_list=" + display_pic_list +
                    '}';
        }
    }
}
