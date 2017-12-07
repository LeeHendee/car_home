package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/8/10.
 * description:
 * 我的收藏 实体类
 */
public class MyFavouriteBean implements Serializable {

    /**
     * result : [{"id":"8326cd153d0b4a65a3f13e9edab62213","user_id":"","favor_id":"2","favor_type":3,"delete_flag":0,"update_time":"2017-01-11 08:59","insert_time":"2017-01-11 08:59","title":"修车应该注意哪些事项？"},{"id":"c8129d27a1af45a08e33dc0684042bcc","user_id":"","favor_id":"3","favor_type":3,"delete_flag":0,"update_time":"2017-01-11 09:00","insert_time":"2017-01-11 09:00","title":"汽车内饰选购"}]
     * err_code : 0
     * err_message : OK
     * message : null
     */

    private String err_code;
    private String err_message;
    private Object message;
    /**
     * id : 8326cd153d0b4a65a3f13e9edab62213
     * user_id :
     * favor_id : 2
     * favor_type : 3
     * delete_flag : 0
     * update_time : 2017-01-11 08:59
     * insert_time : 2017-01-11 08:59
     * title : 修车应该注意哪些事项？
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
        return "MyFavouriteBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message=" + message +
                ", result=" + result +
                '}';
    }

    public static class ResultBean implements Serializable {
        private String id;
        private String user_id;
        private String favor_id;
        private int favor_type;
        private int delete_flag;
        private String update_time;
        private String insert_time;
        private String title;

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

        public String getFavor_id() {
            return favor_id;
        }

        public void setFavor_id(String favor_id) {
            this.favor_id = favor_id;
        }

        public int getFavor_type() {
            return favor_type;
        }

        public void setFavor_type(int favor_type) {
            this.favor_type = favor_type;
        }

        public int getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(int delete_flag) {
            this.delete_flag = delete_flag;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", favor_id='" + favor_id + '\'' +
                    ", favor_type=" + favor_type +
                    ", delete_flag=" + delete_flag +
                    ", update_time='" + update_time + '\'' +
                    ", insert_time='" + insert_time + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
