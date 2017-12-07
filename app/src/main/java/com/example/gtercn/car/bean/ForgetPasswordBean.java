package com.example.gtercn.car.bean;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/21.
 */

public class ForgetPasswordBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String phone;

    private String verify;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    @Override
    public String toString() {
        return "UpdatePasswordBean{" +
                "phone='" + phone + '\'' +
                ", verify='" + verify + '\'' +
                '}';
    }
}
