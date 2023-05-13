package com.example.mobileapp.model;

public class OrderInfo {
    int id;
    String img,name;
    int price;
    int quantity;


    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }



    public OrderInfo(int id, String img, String name, int price, int quantity) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;

    }



}
