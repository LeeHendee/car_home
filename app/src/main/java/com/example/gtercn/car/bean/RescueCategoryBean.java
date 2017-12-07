package com.example.gtercn.car.bean;

/**
 * Created by Administrator on 2017-3-9.
 */

public class RescueCategoryBean {

    private String key;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RescueCategoryBean{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
