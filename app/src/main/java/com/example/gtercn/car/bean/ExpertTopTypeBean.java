package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2016/12/15.
 * description:
 */
public class ExpertTopTypeBean implements Serializable {

    /**
     * result : [{"id":"31c9bedc51984a1995ed5968eb4d77c6","type_value":"汽车买卖","description":"汽车买卖","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"55cabd8f2b4743a0a0122fbd28faf9a3","type_value":"维修保养","description":"维修保养","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"76b5f78080b64342bab434052b947d6b","type_value":"汽车保险","description":"汽车保险","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"a9f5df18c0f211e683f15254006bd52a","type_value":"轮胎","description":"轮胎","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"b67acd36c0f211e683f15254006bd52a","type_value":"电器","description":"电器","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"bde60fee781641d3b7c3fcc8a349ee2f","type_value":"美容装饰","description":"美容装饰","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"c9e21205da43470fa312b8524d7251bb","type_value":"钣金喷漆","description":"钣金喷漆","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"ea3f8fa59c244f269bdd2ac1a61896af","type_value":"改装自驾","description":"改装自驾","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"},{"id":"ee86a0ed76444757b12c3fe74303c781","type_value":"汽车贷款","description":"汽车贷款","delete_flag":0,"insert_time":"2016-12-13","update_time":"2016-12-13"}]
     * err_code : 0
     * err_message : OK
     */

    private String err_code;
    private String err_message;
    /**
     * id : 31c9bedc51984a1995ed5968eb4d77c6
     * type_value : 汽车买卖
     * description : 汽车买卖
     * delete_flag : 0
     * insert_time : 2016-12-13
     * update_time : 2016-12-13
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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        private String id;
        private String type_value;
        private String description;
        private int delete_flag;
        private String insert_time;
        private String update_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType_value() {
            return type_value;
        }

        public void setType_value(String type_value) {
            this.type_value = type_value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
    }

    @Override
    public String toString() {
        return "ExpertTopTypeBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", result=" + result +
                '}';
    }
}
