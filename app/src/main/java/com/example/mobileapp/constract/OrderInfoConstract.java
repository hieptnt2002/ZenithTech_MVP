package com.example.mobileapp.constract;

import android.content.Context;

import com.example.mobileapp.model.OrderInfo;
import com.example.mobileapp.model.Product;

import java.util.List;

public interface OrderInfoConstract {
    interface IView{
        void setDataToRecyclerViewOrder(List<OrderInfo> mList);
    }
    interface IPresenter {
        void getOrderList(String id);
        int getIdAccount(Context context);
    }
}
