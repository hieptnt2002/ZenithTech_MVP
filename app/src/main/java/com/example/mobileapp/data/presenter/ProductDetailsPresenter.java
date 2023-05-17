package com.example.mobileapp.data.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mobileapp.data.constract.ApiService;
import com.example.mobileapp.data.constract.ProductDetailsConstract;
import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.model.Cart;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;
import com.example.mobileapp.data.remote.RetrofitClient;
import com.example.mobileapp.utils.Utils;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsPresenter implements ProductDetailsConstract.IPresenter {
    private ProductDetailsConstract.IView mView;
    private ApiService apiService;

    public ProductDetailsPresenter(ProductDetailsConstract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getImagesSmartList(String id) {
        apiService.getImagesSmart(id).enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                List<Slider> mList = response.body();
                mView.setDataToViewPagerImages(mList);
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getImagesLapList(String id) {
        apiService.getImagesLap(id).enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                List<Slider> mList = response.body();
                mView.setDataToViewPagerImages(mList);
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getDataIntent(Product data) {
        mView.onDataReceived(data);
    }

    @Override
    public void addProductToListCart(Product product) {
        Context mContext = (Context) mView;
        SharedPreferences preferences = mContext.getSharedPreferences(Utils.login_success, MODE_PRIVATE);
        String json = preferences.getString("object", "");
        if (json != null) {
            Gson gson = new Gson();
            Account account = gson.fromJson(json, Account.class);
            if (account != null) {
                if (Utils.listCart.size() > 0) {
                    boolean flag = false;
                    int quantity = 1;
                    for (int i = 0; i < Utils.listCart.size(); i++) {
                        if (Utils.listCart.get(i).getName().equals(product.getTen_sp())) {
                            Utils.listCart.get(i).setQuantity(quantity + Utils.listCart.get(i).getQuantity());
                            flag = true;
                            Utils.saveCart(mContext.getApplicationContext());
                        }
                    }
                    if (flag == false) {
                        Utils.listCart.add(new Cart(product.getAnh(), product.getTen_sp(), product.getGia_sp(), product.getGia_km(), 1, account.getId()));
                        Utils.saveCart(mContext.getApplicationContext());
                    }
                } else {
                    Utils.listCart.add(new Cart(product.getAnh(), product.getTen_sp(), product.getGia_sp(), product.getGia_km(), 1, account.getId()));
                    Utils.saveCart(mContext.getApplicationContext());
                }

                mView.onAddProductSuccess();
            } else {
                mView.onAccountNull();
            }
        } else {
            mView.onAccountNull();
        }
    }


}
