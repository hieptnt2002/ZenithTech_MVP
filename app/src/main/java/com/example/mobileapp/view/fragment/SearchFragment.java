package com.example.mobileapp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.view.adapter.ProductAdapter;
import com.example.mobileapp.data.constract.SearchConstract;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.presenter.SearchPresenter;
import com.example.mobileapp.utils.Utils;
import com.example.mobileapp.view.ZenithActivity;

import java.util.List;

public class SearchFragment extends Fragment implements SearchConstract.IView {
    View view;
    TextView tvSearchResults;
    RecyclerView rvSearchResults;
    LinearLayout llHighFilter, llLowFilter, llPercentFilter;
    ProductAdapter findAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initGUI();
        Bundle bundle = this.getArguments();
        ZenithActivity z= (ZenithActivity) getActivity();
        z.index = 999;
        if (bundle != null) {
            String key = "%" + bundle.getString("name") + "%";
            SearchConstract.IPresenter mPresenter = new SearchPresenter(this);
            mPresenter.getProductFoundList(key);
        }
        eventFilter();
        return view;
    }

    void initGUI() {
        tvSearchResults = view.findViewById(R.id.textView_searchResults);
        rvSearchResults = view.findViewById(R.id.recyclerView_searchResults);
        rvSearchResults.setHasFixedSize(true);
        rvSearchResults.setLayoutManager(new GridLayoutManager(getContext(), 2));
        llHighFilter = view.findViewById(R.id.box_caothap);
        llLowFilter = view.findViewById(R.id.box_thapcao);
        llPercentFilter = view.findViewById(R.id.box_percen);
    }


    public void eventFilter() {
        llHighFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(findAdapter != null) findAdapter.filterPriceProductHigh();
                llHighFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_filter_click));
                llLowFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llPercentFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
            }
        });
        llLowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findAdapter != null) findAdapter.filterPriceProductLow();
                llHighFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llLowFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_filter_click));
                llPercentFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));

            }
        });
        llPercentFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findAdapter != null) findAdapter.filterPriceProductPercent();
                llHighFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llLowFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llPercentFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_filter_click));
            }
        });
    }

    @Override
    public void setDataToRecycleViewToProductFound(List<Product> mList) {

        findAdapter = new ProductAdapter(mList, getContext());
        rvSearchResults.setAdapter(findAdapter);

        findAdapter.setOnClickAddToCart(new ProductAdapter.OnClickAddCartListener() {
            @Override
            public void onClickAddToCart() {
                if (Utils.listCart.size() != 0) {
                    TextView tvCart = getActivity().findViewById(R.id.num_cart);
                    tvCart.setText(String.valueOf(Utils.listCart.size()));
                }
            }

        });
        //số sản phẩm tìm được
        if(mList != null) tvSearchResults.setText(mList.size()+" sản phẩm");
    }
}
