package com.example.mobileapp.data.constract;

import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;

import java.util.List;

public interface ProductConstract {
    interface IView{
        void setDataToRecyclerViewProduct(List<Product> mList);
        void setDataToViewPagerSlider(List<Slider> mList);
    }
    interface IPresenter{
        void getProductList();
        void getSliderList();
    }
}
