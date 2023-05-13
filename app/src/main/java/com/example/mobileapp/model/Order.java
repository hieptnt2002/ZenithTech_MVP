package com.example.mobileapp.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    List<Cart> mList;
    int total;

    public List<Cart> getmList() {
        return mList;
    }

    public int getTotal() {
        return total;
    }

    public Order(List<Cart> mList, int total) {
        this.mList = mList;
        this.total = total;
    }
}
