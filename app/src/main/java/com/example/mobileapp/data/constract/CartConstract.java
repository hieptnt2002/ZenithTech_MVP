package com.example.mobileapp.data.constract;

import com.example.mobileapp.data.model.Cart;
import com.example.mobileapp.data.model.Order;

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
