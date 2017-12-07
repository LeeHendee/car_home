package com.example.gtercn.car.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-8-9.
 * 更新 数据结构
 */
public class UpdateBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private int version_code;

    private String version_name;

    private String content;

    private String url;

    private int min_code;

    private String min_version;

    private String min_content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMin_code() {
        return min_code;
    }

    public void setMin_code(int min_code) {
        this.min_code = min_code;
    }

    public String getMin_content() {
        return min_content;
    }

    public void setMin_content(String min_content) {
        this.min_content = min_content;
    }

    public String getMin_version() {
        return min_version;
    }

    public void setMin_version(String min_version) {
        this.min_version = min_version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "content='" + content + '\'' +
                ", version_code=" + version_code +
                ", version_name='" + version_name + '\'' +
                ", url='" + url + '\'' +
                ", min_code=" + min_code +
                ", min_version='" + min_version + '\'' +
                ", min_content='" + min_content + '\'' +
                '}';
    }
}
