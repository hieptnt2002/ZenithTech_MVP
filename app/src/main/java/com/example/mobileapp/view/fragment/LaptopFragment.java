package com.example.mobileapp.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mobileapp.R;
import com.example.mobileapp.data.presenter.LaptopPresenter;
import com.example.mobileapp.view.adapter.BannerChildAdapter;
import com.example.mobileapp.view.adapter.ProductAdapter;
import com.example.mobileapp.data.constract.ProductConstract;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;
import com.example.mobileapp.data.presenter.SmartphonePresenter;
import com.example.mobileapp.slider.SliderAuto;
import com.example.mobileapp.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class LaptopFragment extends Fragment implements ProductConstract.IView {
    AutoCompleteTextView inputSearch;
    TextInputLayout inputLayout;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    RecyclerView rvLaptop;
    ProductAdapter lapTopAdapter;
    BannerChildAdapter bannerAdapter;
    View view;
    LinearLayout llHighFilter, llLowFilter, llPercentFilter;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        initGUI();
        progressBar.setVisibility(View.VISIBLE);

       LaptopPresenter mPresenter = new LaptopPresenter(this);
        mPresenter.getProductList();
        mPresenter.getSliderList();
        eventFilter();
        search();
        return view;
    }

    void initGUI() {
        inputSearch = view.findViewById(R.id.autoCompleteText_search);
        inputLayout = view.findViewById(R.id.textInputLayout);
        viewPager = view.findViewById(R.id.slider_smartphone);
        circleIndicator = view.findViewById(R.id.circleIndicator_sm);
        rvLaptop = view.findViewById(R.id.recycleView_smartphone);
        llHighFilter = view.findViewById(R.id.box_caothap);
        llLowFilter = view.findViewById(R.id.box_thapcao);
        llPercentFilter = view.findViewById(R.id.box_percen);
        progressBar = view.findViewById(R.id.load_product);

    }

    void search() {
        ArrayAdapter suggestAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Utils.suggestSearchList);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayout.setHintEnabled(false);
                inputSearch.setAdapter(suggestAdapter);
                if(lapTopAdapter != null) lapTopAdapter.filterNameProduct(inputSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    Bundle bundle = new Bundle();
                    bundle.putString("name", String.valueOf(inputSearch.getText()));
                    SearchFragment searchFragment = new SearchFragment();
                    searchFragment.setArguments(bundle);
                    replaceFragment(searchFragment);

                    return true;
                }
                return false;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void setDataToRecyclerViewProduct(List<Product> mList) {
        rvLaptop.setHasFixedSize(true);
        rvLaptop.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvLaptop.setNestedScrollingEnabled(false);
        lapTopAdapter = new ProductAdapter(mList, getContext());
        rvLaptop.setAdapter(lapTopAdapter);
        progressBar.setVisibility(View.GONE);
        lapTopAdapter.notifyDataSetChanged();
        if (lapTopAdapter != null) {
            //set chieu dai recycleview smartphone
            int itemHeight = getResources().getDimensionPixelSize(R.dimen.item_height_product); // chiều cao của một item
            int numItems = 0;
            if (lapTopAdapter.getItemCount() % 2 == 0) {
                numItems = lapTopAdapter.getItemCount() / 2; // số lượng item trong RecyclerView
            } else
                numItems = (lapTopAdapter.getItemCount() + 1) / 2; // số lượng item trong RecyclerView

            int totalHeight = itemHeight * numItems; // tổng chiều cao của tất cả các item trong RecyclerView
            ViewGroup.LayoutParams params = rvLaptop.getLayoutParams();
            params.height = totalHeight;
            rvLaptop.setLayoutParams(params);//
        }
        lapTopAdapter.setOnClickAddToCart(new ProductAdapter.OnClickAddCartListener() {
            @Override
            public void onClickAddToCart() {
                if (Utils.listCart.size() != 0) {
                    TextView tvCart = getActivity().findViewById(R.id.num_cart);
                    tvCart.setText(String.valueOf(Utils.listCart.size()));
                }
            }

        });
    }

    @Override
    public void setDataToViewPagerSlider(List<Slider> mList) {
        bannerAdapter = new BannerChildAdapter(mList);
        viewPager.setAdapter(bannerAdapter);
        circleIndicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        SliderAuto.autoSlideImages(viewPager);
        bannerAdapter.notifyDataSetChanged();
    }

    public void eventFilter() {
        llHighFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lapTopAdapter != null) lapTopAdapter.filterPriceProductHigh();
                llHighFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_filter_click));
                llLowFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llPercentFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
            }
        });
        llLowFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lapTopAdapter != null) lapTopAdapter.filterPriceProductLow();
                llHighFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llLowFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_filter_click));
                llPercentFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));

            }
        });
        llPercentFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lapTopAdapter != null) lapTopAdapter.filterPriceProductPercent();
                llHighFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llLowFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.border_filter));
                llPercentFilter.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_filter_click));
            }
        });
    }
}
