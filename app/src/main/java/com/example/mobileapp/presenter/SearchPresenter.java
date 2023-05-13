package com.example.mobileapp.presenter;

import com.example.mobileapp.constants.Constants;
import com.example.mobileapp.constract.ApiService;
import com.example.mobileapp.constract.SearchConstract;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements SearchConstract.IPresenter {
    SearchConstract.IView mView;
    ApiService apiService;

    public SearchPresenter(SearchConstract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient(Constants.SERVICE_API).create(ApiService.class);
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
