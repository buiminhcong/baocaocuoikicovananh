package com.example.shop_cong.entity;

import java.io.Serializable;

public class Result implements Serializable {

    private String kq;

    public Result(String kq) {
        this.kq = kq;
    }

    public Result() {
    }

    public String getKq() {
        return kq;
    }

    public void setKq(String kq) {
        this.kq = kq;
    }
}
