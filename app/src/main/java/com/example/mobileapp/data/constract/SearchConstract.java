package com.example.mobileapp.data.constract;

import com.example.mobileapp.data.model.Product;

import java.util.List;

public interface SearchConstract {
    interface IView{
        void setDataToRecycleViewToProductFound(List<Product> mList);
    }
    interface IPresenter{
        void getProductFoundList(String key);
    }
}
