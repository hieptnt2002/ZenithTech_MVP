package com.example.mobileapp.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobileapp.constract.MainConstract;
import com.example.mobileapp.model.Account;
import com.example.mobileapp.model.Cart;
import com.example.mobileapp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainPresenter implements MainConstract.IPresenter {
    private MainConstract.IView mView;
    public MainPresenter(MainConstract.IView mView){
        this.mView = mView;
    }
    @Override
    public void eventClickItemNavigation(int index) {
        switch (index){
            case 0: mView.showFragmentHome();break;
            case 1: mView.showFragmentSmartphone();break;
            case 2: mView.showFragmentLaptop();break;
            case 3: mView.showFragmentAccount();break;
            default: mView.showFragmentHome();break;
        }
    }

    @Override
    public void isLoggedIn() {
        Context context = (Context) mView;
        String name = "";
        int num_cart = 0;
        SharedPreferences prefts = context.getSharedPreferences(Utils.login_success, MODE_PRIVATE);
        String object = prefts.getString("object", null);
        Gson gson = new Gson();
        if (object != null) {
            Account account = gson.fromJson(object, Account.class);
            name = account.getName();
            // Đọc chuỗi JSON từ SharedPreferences
            SharedPreferences list = context.getSharedPreferences(account.getName(), MODE_PRIVATE);
            String json = list.getString(account.getName(), null);
            // Chuyển đổi chuỗi JSON thành ArrayList bằng Gson
            Type type = new TypeToken<List<Cart>>() {
            }.getType();
            if (Utils.listCart.isEmpty() && json != null) {
                Utils.listCart = gson.fromJson(json, type);
                num_cart =Utils.listCart.size();
            }
            mView.showIsLoggedIn(name,num_cart);

        }
    }

    @Override
    public void eventLogout() {
        mView.logOut();
    }
}
