package com.example.mobileapp.data.constract;

import com.example.mobileapp.data.model.Cart;
import com.example.mobileapp.data.model.Order;

import java.util.List;

public interface BottomSheetConstract {
    interface IView{
        void onSetDataToOrderView(Order mOrder);
        void onSetSpinnerAdapter(List<String> list);
        void onUpdateOrderRecyclerViewHeight();
        //event
        void onEventCancel();
        void onEventOrder(List<Cart> mCart);
        void onShowOrderSuccessDialog(String product, String name, String sdt, String address, String method, String totalPay);
    }
    interface IPresenter{
        void setDataOrder(Order mOrder);
         void event(Order mOrder) ;
         void upDataOrder(List<Cart> mList,int position);
    }
}
