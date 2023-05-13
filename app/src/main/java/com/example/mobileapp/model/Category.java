package com.example.mobileapp.model;

public class Category {
    int id;
    String tensp,anh;

    public Category(int id, String tensp, String anh) {
        this.id = id;
        this.tensp = tensp;
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public String getAnh() {
        return anh;
    }


}
