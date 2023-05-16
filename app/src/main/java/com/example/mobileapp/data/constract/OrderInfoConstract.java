package com.example.mobileapp.data.constract;

import android.content.Context;

import com.example.mobileapp.data.model.OrderInfo;

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
