package com.example.mobileapp.data.constract;

public interface MainConstract {
    interface IView{
        void showFragmentHome();
        void showFragmentSmartphone();
        void showFragmentLaptop();
        void showFragmentAccount();
        void showIsLoggedIn(String name, int num);
        void logOut();
    }
    interface IPresenter{
        void eventClickItemNavigation(int index);
        void isLoggedIn();
        void eventLogout();

    }
}
