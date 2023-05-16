package com.example.mobileapp.data.constract;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.data.model.BrandProduct;
import com.example.mobileapp.data.model.Category;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;

import java.util.List;

public interface HomeConstract {
    interface IView{
        void setDataToViewpagerSlider(List<Slider> mList);
        void setDataToRecyclerViewCategory(List<Category> mList);
        void setDataToRecyclerViewBrandSmartphone(List<BrandProduct> mList);
        void setDataToRecyclerViewBrandLaptop(List<BrandProduct> mList);
        void setDataToRecyclerViewSmartphone(List<Product> mList);
        void setDataToRecyclerViewLaptop(List<Product> mList);

    }
    interface IPresenter{
        void getSliderList();
        void getCategoryList();
        void getBrandSmartList();
        void getBrandLapList();
        void getSmartphoneList();
        void getLaptopList();
        void autoSliderProduct(RecyclerView recyclerView);

    }
}
