package com.example.gtercn.car.bean;

import java.io.Serializable;

/**
 * author : LiHang.
 * data : 2016/8/26.
 * description:
 */
public class VerificationBean implements Serializable {
    private static final long serialVersionUID = 1L;

    String verify_code;
    String verify_message;
    String verify_expire;


    @Override
    public String toString() {
        return "VerificationBean{" +
                "verify_code='" + verify_code + '\'' +
                ", verify_message='" + verify_message + '\'' +
                ", verify_expire='" + verify_expire + '\'' +
                '}';
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getVerify_message() {
        return verify_message;
    }

    public void setVerify_message(String verify_message) {
        this.verify_message = verify_message;
    }

    public String getVerify_expire() {
        return verify_expire;
    }

    public void setVerify_expire(String verify_expire) {
        this.verify_expire = verify_expire;
    }
}
