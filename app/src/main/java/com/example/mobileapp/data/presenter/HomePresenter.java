package com.example.mobileapp.data.presenter;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.utils.constants.Constants;
import com.example.mobileapp.data.constract.ApiService;
import com.example.mobileapp.data.constract.HomeConstract;
import com.example.mobileapp.data.model.BrandProduct;
import com.example.mobileapp.data.model.Category;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;
import com.example.mobileapp.data.remote.RetrofitClient;
import com.example.mobileapp.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeConstract.IPresenter {
    HomeConstract.IView mView;
    ApiService apiService;

    public HomePresenter(HomeConstract.IView mView) {
        this.mView = mView;
        apiService  = RetrofitClient.getClient().create(ApiService.class);

    }

    @Override
    public void getSliderList() {
        apiService.getImgSlider().enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                List<Slider> sliderList = response.body();
                mView.setDataToViewpagerSlider(sliderList);
            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getCategoryList() {
        apiService.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> mList = response.body();
                mView.setDataToRecyclerViewCategory(mList);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getBrandSmartList() {

        apiService.getBrandSmartphone().enqueue(new Callback<List<BrandProduct>>() {
            @Override
            public void onResponse(Call<List<BrandProduct>> call, Response<List<BrandProduct>> response) {
                List<BrandProduct> mList = response.body();
                mView.setDataToRecyclerViewBrandSmartphone(mList);
            }

            @Override
            public void onFailure(Call<List<BrandProduct>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getBrandLapList() {
        apiService.getBrandLaptop().enqueue(new Callback<List<BrandProduct>>() {
            @Override
            public void onResponse(Call<List<BrandProduct>> call, Response<List<BrandProduct>> response) {
                List<BrandProduct> mList = response.body();
                mView.setDataToRecyclerViewBrandLaptop(mList);
            }

            @Override
            public void onFailure(Call<List<BrandProduct>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getSmartphoneList() {
        apiService.getSmartphone().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> mList = response.body();
                for(int i =0; i < mList.size(); i++){
                    Utils.suggestSearchList.add(mList.get(i).getTen_sp());
                }
                mView.setDataToRecyclerViewSmartphone(mList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getLaptopList() {
        apiService.getLaptop().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> mList = response.body();
                for(int i =0; i < mList.size(); i++){
                    Utils.suggestSearchList.add(mList.get(i).getTen_sp());
                }
                mView.setDataToRecyclerViewLaptop(mList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void autoSliderProduct(RecyclerView recyclerView) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int nextPosition = 0;
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());

            @Override
            public void run() {

                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                if (nextPosition < recyclerView.getAdapter().getItemCount()) {
                    if (lastVisible < recyclerView.getAdapter().getItemCount() - 1) {
                        nextPosition = lastVisible + 1;
                    } else {
                        nextPosition = 0;
                    }
                }
                layoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), nextPosition);
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable,5000);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Tạm dừng trượt tự động khi người dùng bấm xuống
                    handler.removeCallbacks(runnable);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Tiếp tục trượt tự động khi người dùng nhả tay
                    handler.postDelayed(runnable, 5000); // Hoặc thời gian chuyển đổi tự động bạn muốn
                }
                return false;
            }
        });

    }


}
