package com.example.mobileapp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mobileapp.R;
import com.example.mobileapp.constants.Constants;
import com.example.mobileapp.constract.ApiService;
import com.example.mobileapp.constract.BottomSheetConstract;
import com.example.mobileapp.model.Cart;
import com.example.mobileapp.model.Order;
import com.example.mobileapp.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetPresenter implements BottomSheetConstract.IPresenter {
    private BottomSheetConstract.IView mView;
    private ApiService apiService;

    public BottomSheetPresenter(BottomSheetConstract.IView view) {
        mView = view;
        apiService = RetrofitClient.getClient(Constants.SERVICE_API).create(ApiService.class);
    }
    @Override
    public void setDataOrder(Order mOrder) {
        mView.onSetDataToOrderView(mOrder);

        // Gọi tới các phương thức để cập nhật Spiner và RecyclerView liên quan đến dữ liệu trong đơn hàng
        List<String> list = new ArrayList<>();
        list.add("Thanh toán khi nhận hàng");
        list.add("Chuyển khoản ngân hàng");
        list.add("Thanh toán qua ZaloPay");
        mView.onSetSpinnerAdapter(list);
        mView.onUpdateOrderRecyclerViewHeight();
    }
    public void event(Order mOrder) {
        mView.onEventCancel();
        List<Cart> mCart = mOrder.getmList();
        mView.onEventOrder(mCart);
    }

    @Override
    public void upDataOrder(List<Cart> mList, int position) {
        apiService.postCartData(mList.get(position).getImg(),
                mList.get(position).getName(),
                mList.get(position).getPrice() * mList.get(position).getQuantity()+"",
                mList.get(position).getQuantity()+"",
                "Chưa thanh toán",
                mList.get(position).getAccount_id()+"").enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                Log.d("API", "API up order successfully!");
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.e("API", "Error when up order to server!");
            }
        });
    }
}