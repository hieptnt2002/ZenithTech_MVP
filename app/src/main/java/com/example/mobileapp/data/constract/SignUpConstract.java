package com.example.mobileapp.data.constract;

import com.example.mobileapp.data.model.Account;

import java.util.List;

public interface SignUpConstract {
    interface IView{
        void showProgress();
        void hideProgress();
        void showSuccessMessage(String message);
        void showErrorMessage(String message);
        void setAccountList(List<Account> mList);


    }
    interface IPresenter{
        void register(String username, String password, String email,List<Account> mList);
        void getListAccount();
    }
}
