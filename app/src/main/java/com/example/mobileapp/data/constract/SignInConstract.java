package com.example.mobileapp.data.constract;

import android.content.Context;

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
