package com.example.mobileapp.constract;

import android.content.Context;

import com.example.mobileapp.model.Account;

import java.util.List;

public interface SignInConstract {
    interface IView{
        void showProgress();
        void hideProgress();
        void showSuccessMessage(String message);
        void showErrorMessage(String message);
    }
    interface IPresenter{
        void login(String name,String password, Context context);
    }
}
