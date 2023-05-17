package com.example.mobileapp.data.presenter;


import com.example.mobileapp.data.constract.ApiService;
import com.example.mobileapp.data.constract.SearchConstract;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements SearchConstract.IPresenter {
    SearchConstract.IView mView;
    ApiService apiService;

    public SearchPresenter(SearchConstract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @Override
    public void getProductFoundList(String key) {
        apiService.getProductSearch(key).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
               if(response.isSuccessful()){
                   List<Product> productList = response.body();
                   mView.setDataToRecycleViewToProductFound(productList);
               }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}
