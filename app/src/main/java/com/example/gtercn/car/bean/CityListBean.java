package com.example.gtercn.car.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : LiHang.
 * data : 2017/3/3.
 * description:
 */
public class CityListBean implements Serializable {

    /**
     * result : [{"city_name":"大连市","city_code":"210200"},{"city_name":"阜新市","city_code":"210900"}]
     * err_code : 0
     * err_message : OK
     * message : 城市检索成功
     */

    private String err_code;
    private String err_message;
    private String message;
    /**
     * city_name : 大连市
     * city_code : 210200
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

    @Override
    public String toString() {
        return "CityListBean{" +
                "err_code='" + err_code + '\'' +
                ", err_message='" + err_message + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public static class ResultBean implements Serializable {
        private String city_name;
        private String city_code;
        private String city_phoneticize;

        public String getCity_phoneticize() {
            return city_phoneticize;
        }

        public void setCity_phoneticize(String city_phoneticize) {
            this.city_phoneticize = city_phoneticize;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "city_name='" + city_name + '\'' +
                    ", city_code='" + city_code + '\'' +
                    ", city_phoneticize='" + city_phoneticize + '\'' +
                    '}';
        }
    }
}
