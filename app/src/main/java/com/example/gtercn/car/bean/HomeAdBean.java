package com.example.gtercn.car.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/16.
 */

public class HomeAdBean implements Serializable{

    private static final long serialVersionUID = 8892486013516499425L;

    private String picture_url;

    private String html_url;

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    @Override
    public String toString() {
        return "HomeAdBean{" +
                "html_url='" + html_url + '\'' +
                ", picture_url='" + picture_url + '\'' +
                '}';
    }
}
