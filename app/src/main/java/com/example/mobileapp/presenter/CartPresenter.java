package com.example.mobileapp.presenter;

import com.example.mobileapp.constract.CartConstract;
import com.example.mobileapp.model.Cart;
import com.example.mobileapp.model.Order;

import java.util.List;

public class CartPresenter implements CartConstract.IPresenter {
    private CartConstract.IView mView;

    public CartPresenter(CartConstract.IView mView) {
        this.mView = mView;
    }

    @Override
    public void getCartList(List<Cart> mList) {
        mView.setDataToRecycleViewCart(mList);
    }

    @Override
    public void onClickToOrder(List<Cart> listCart, int totalPay) {
        if (listCart != null && !listCart.isEmpty()) {
            Order order = new Order(listCart, totalPay);
            mView.onShowBottomSheet(order);
        } else {
            mView.onCartEmptyError();
        }
    }
}
