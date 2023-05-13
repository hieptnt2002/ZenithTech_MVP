package com.example.mobileapp.model;

import java.io.Serializable;

public class Account implements Serializable {
    int id;
    String email,name,pass;

    public Account(int id, String email, String name, String pass) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
}
