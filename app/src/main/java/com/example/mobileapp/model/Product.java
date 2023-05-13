package com.example.mobileapp.model;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String anh,ten_sp;
    int gia_sp,gia_km;
    String quatang,mota;
    int loaisp_id;

    public Product(int id, String anh, String ten_sp, int gia_sp, int gia_km, String quatang, String mota, int loaisp_id) {
        this.id = id;
        this.anh = anh;
        this.ten_sp = ten_sp;
        this.gia_sp = gia_sp;
        this.gia_km = gia_km;
        this.quatang = quatang;
        this.mota = mota;
        this.loaisp_id = loaisp_id;
    }

    public int getId() {
        return id;
    }

    public String getAnh() {
        return anh;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public int getGia_sp() {
        return gia_sp;
    }

    public int getGia_km() {
        return gia_km;
    }

    public String getQuatang() {
        return quatang;
    }

    public String getMota() {
        return mota;
    }

    public int getLoaisp_id() {
        return loaisp_id;
    }
}
