package com.example.mobileapp.constract;

import com.example.mobileapp.model.Cart;
import com.example.mobileapp.model.Order;

import java.util.List;

public interface CartConstract {
    interface IView{
        void setDataToRecycleViewCart(List<Cart> mList);
        void onUpdateTotalPriceTextView(int total);
        void onShowBottomSheet(Order order);
        void onCartEmptyError();

    }
    interface IPresenter{
        void getCartList(List<Cart> mList);
        void onClickToOrder(List<Cart> listCart, int totalPay);
    }
}
