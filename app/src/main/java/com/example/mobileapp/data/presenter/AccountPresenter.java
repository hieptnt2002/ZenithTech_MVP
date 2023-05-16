package com.example.mobileapp.data.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobileapp.data.constract.AccountConstract;
import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.utils.Utils;
import com.google.gson.Gson;

public class AccountPresenter implements AccountConstract.IPresenter {
    AccountConstract.IView mView;

    public AccountPresenter(AccountConstract.IView mView) {
        this.mView = mView;
    }

    @Override
    public void getData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.login_success, Context.MODE_PRIVATE);
        String object = sharedPreferences.getString("object", null);
        Gson gson = new Gson();
        if (object != null) {
            Account account = gson.fromJson(object, Account.class);
            mView.setDataToTextView(account);
        }
    }
}
