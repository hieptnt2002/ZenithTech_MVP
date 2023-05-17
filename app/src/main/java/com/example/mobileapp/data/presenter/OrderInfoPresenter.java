package com.example.mobileapp.data.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobileapp.utils.constants.Constants;
import com.example.mobileapp.data.constract.ApiService;
import com.example.mobileapp.data.constract.OrderInfoConstract;
import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.model.OrderInfo;
import com.example.mobileapp.data.remote.RetrofitClient;
import com.example.mobileapp.utils.Utils;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInfoPresenter implements OrderInfoConstract.IPresenter {
    OrderInfoConstract.IView mView;
    ApiService apiService;

    public OrderInfoPresenter(OrderInfoConstract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getOrderList(String id) {
        apiService.getOrder(id).enqueue(new Callback<List<OrderInfo>>() {
            @Override
            public void onResponse(Call<List<OrderInfo>> call, Response<List<OrderInfo>> response) {
                List<OrderInfo> mList = response.body();
                mView.setDataToRecyclerViewOrder(mList);
            }

            @Override
            public void onFailure(Call<List<OrderInfo>> call, Throwable t) {

            }
        });
    }

    @Override
    public int getIdAccount(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Utils.login_success, Context.MODE_PRIVATE);
        String object = pref.getString("object", null);
        if (object != null && !object.isEmpty()) {
            Gson gson = new Gson();
            Account account = gson.fromJson(object, Account.class);
            return account.getId();
        }
        return 0;
    }
}
