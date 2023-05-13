package com.example.mobileapp.presenter;

import com.example.mobileapp.constants.Constants;
import com.example.mobileapp.constract.ApiService;
import com.example.mobileapp.constract.ProductConstract;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.model.Slider;
import com.example.mobileapp.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmartphonePresenter implements ProductConstract.IPresenter {
    ProductConstract.IView mView;
    private ApiService apiService;

    public SmartphonePresenter(ProductConstract.IView mVIew){
        this.mView = mVIew;
        apiService = RetrofitClient.getClient(Constants.SERVICE_API).create(ApiService.class);
    }
    @Override
    public void getProductList() {
        apiService.getSmartphone().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> mList = response.body();
                mView.setDataToRecyclerViewProduct(mList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getSliderList() {
        apiService.getSliderSmart().enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                List<Slider> sliderList = response.body();
                mView.setDataToViewPagerSlider(sliderList);
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {

            }
        });
    }
}
