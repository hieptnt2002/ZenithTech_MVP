package com.example.mobileapp.data.constract;

import android.content.Context;

import com.example.mobileapp.data.model.Account;

public interface AccountConstract {
    interface IView{
        void setDataToTextView(Account account);
    }
    interface IPresenter{
        void getData(Context context);
    }
}
