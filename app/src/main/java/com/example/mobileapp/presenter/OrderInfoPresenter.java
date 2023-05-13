package com.example.mobileapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.mobileapp.constants.Constants;
import com.example.mobileapp.constract.ApiService;
import com.example.mobileapp.constract.OrderInfoConstract;
import com.example.mobileapp.model.Account;
import com.example.mobileapp.model.OrderInfo;
import com.example.mobileapp.remote.RetrofitClient;
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
        apiService = RetrofitClient.getClient(Constants.SERVICE_API).create(ApiService.class);
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
