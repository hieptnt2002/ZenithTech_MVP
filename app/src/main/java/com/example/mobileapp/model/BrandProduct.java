package com.example.mobileapp.model;

public class BrandProduct {
    int id ;
    String ten_thuonghieu;

    public int getId() {
        return id;
    }

    public String getTen_thuonghieu() {
        return ten_thuonghieu;
    }

    public BrandProduct(int id, String ten_thuonghieu) {
        this.id = id;
        this.ten_thuonghieu = ten_thuonghieu;
    }
}
