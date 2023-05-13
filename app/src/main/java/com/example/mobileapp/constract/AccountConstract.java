package com.example.mobileapp.constract;

import android.content.Context;

import com.example.mobileapp.model.Account;

public interface AccountConstract {
    interface IView{
        void setDataToTextView(Account account);
    }
    interface IPresenter{
        void getData(Context context);
    }
}
