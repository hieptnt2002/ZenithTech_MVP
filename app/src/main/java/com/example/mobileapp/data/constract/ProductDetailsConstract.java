package com.example.mobileapp.data.constract;

import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;

import java.util.List;

public interface ProductDetailsConstract {
    interface IView{
        void setDataToViewPagerImages(List<Slider> sliderList);
        void onDataReceived(Product data);
        void onAddProductSuccess();
        void onAddProductFail();
        void onAccountNull();
    }
    interface IPresenter{
        void getImagesSmartList(String id);
        void getImagesLapList(String id);
        void getDataIntent(Product data);
        void addProductToListCart(Product product);

    }
}
