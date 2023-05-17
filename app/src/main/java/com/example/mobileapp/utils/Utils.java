package com.example.mobileapp.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.model.Cart;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Cart> listCart = new ArrayList<>();
    public static List<String> suggestSearchList = new ArrayList<>();
    public static int total_pay = 0;
    public static final String login_success = "success";

    public static String sha256(String passphrase) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(passphrase.getBytes());
        byte[] hash = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void saveCart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Utils.login_success, MODE_PRIVATE);
        String json = preferences.getString("object", "");
        Gson gson = new Gson();

        if (json != null) {
            Account account = gson.fromJson(json, Account.class);
            if (account != null) {
                String list = gson.toJson(Utils.listCart);
                SharedPreferences.Editor editor = context.getSharedPreferences(account.getName(), MODE_PRIVATE).edit();
                editor.putString(account.getName(), list);
                editor.apply();
            }
        }

    }


}