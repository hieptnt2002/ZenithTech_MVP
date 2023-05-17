package com.example.mobileapp.data.presenter;

import com.example.mobileapp.utils.constants.Constants;
import com.example.mobileapp.data.constract.ApiService;
import com.example.mobileapp.data.constract.ProductConstract;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;
import com.example.mobileapp.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaptopPresenter implements ProductConstract.IPresenter {
    ProductConstract.IView mView;
    private ApiService apiService;

    public LaptopPresenter(ProductConstract.IView mVIew){
        this.mView = mVIew;
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }
    @Override
    public void getProductList() {
        apiService.getLaptop().enqueue(new Callback<List<Product>>() {
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
        apiService.getSliderLap().enqueue(new Callback<List<Slider>>() {
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
