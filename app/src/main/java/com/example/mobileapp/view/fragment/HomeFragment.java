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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mobileapp.R;
import com.example.mobileapp.view.adapter.BannerAdapter;
import com.example.mobileapp.view.adapter.CategoryAdapter;
import com.example.mobileapp.view.adapter.BrandAdapter;
import com.example.mobileapp.view.adapter.ProductAdapter;
import com.example.mobileapp.data.constract.HomeConstract;
import com.example.mobileapp.data.model.BrandProduct;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;
import com.example.mobileapp.data.model.Category;
import com.example.mobileapp.data.constract.OnItemClickListener;
import com.example.mobileapp.data.presenter.HomePresenter;
import com.example.mobileapp.slider.SliderAuto;
import com.example.mobileapp.view.ZenithActivity;
import com.example.mobileapp.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment implements  HomeConstract.IView {
    //
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ProductAdapter smartAdapter, laptopAdapter;
    BannerAdapter bannerAdapter;
    CategoryAdapter categoryAdapter;
    ProgressBar loading_cate;
    AutoCompleteTextView inputSearch;
    TextInputLayout textInputLayout;
    TextView tvLaptop;
    RecyclerView rvSmart, rvCate, rvLaptop, rvFilter_Smartphone, rvFilter_Laptop;
    public int clickPosition;
    NavigationView mNavigationView;
    BottomNavigationView bottomNavigationView;

    ZenithActivity zenithActivity;
    ArrayAdapter suggestAdapter;
    HomeConstract.IPresenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        zenithActivity = (ZenithActivity) getActivity();

        initGUI();
        loading_cate.setVisibility(View.VISIBLE);
        search();
        mPresenter = new HomePresenter(this);
        mPresenter.getSliderList();
        mPresenter.getCategoryList();
        mPresenter.getBrandSmartList();
        mPresenter.getBrandLapList();
        mPresenter.getSmartphoneList();
        mPresenter.getLaptopList();
        return view;

    }

    private void initGUI() {
        mNavigationView = getActivity().findViewById(R.id.nav_header);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
        viewPager = view.findViewById(R.id.viewPager_home);
        circleIndicator = view.findViewById(R.id.circleIndicator_home);
        rvSmart = view.findViewById(R.id.recyclerView_productHome);
        loading_cate = view.findViewById(R.id.load_categoryHome);
        inputSearch = view.findViewById(R.id.autoCompleteText_search);
        textInputLayout = view.findViewById(R.id.textInputLayout);
        tvLaptop = view.findViewById(R.id.textView_laptopHome);
        rvCate = view.findViewById(R.id.recyclerView_categoryHome);
        rvLaptop = view.findViewById(R.id.recyclerView_laptopHome);
        rvFilter_Smartphone = view.findViewById(R.id.rv_filter_smartphoneHome);
        rvFilter_Laptop = view.findViewById(R.id.rv_filter_laptopHome);
    }

    void search(){
        suggestAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,Utils.suggestSearchList);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setHintEnabled(false);
                inputSearch.setAdapter(suggestAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER){

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


    private void eventClickRvCate() {
        categoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int index = 0;
                switch (position) {
                    case 0:
                        index = 1;
                        mNavigationView.getMenu().findItem(R.id.nav_smart).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.nav_smart).setChecked(true);
                        break;
                    case 1:
                        index = 2;
                        mNavigationView.getMenu().findItem(R.id.nav_lap).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.nav_lap).setChecked(true);
                        break;
                    case 3:
                        index = 1;
                        mNavigationView.getMenu().findItem(R.id.nav_smart).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.nav_smart).setChecked(true);
                        break;
                    default:
                        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                }
                zenithActivity.mIPresenter.eventClickItemNavigation(index);
            }
        });
    }
    @Override
    public void setDataToViewpagerSlider(List<Slider> mList) {
        bannerAdapter = new BannerAdapter(mList);
        viewPager.setAdapter(bannerAdapter);
        circleIndicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        SliderAuto.autoSlideImages(viewPager);
        bannerAdapter.notifyDataSetChanged();
    }


    @Override
    public void setDataToRecyclerViewCategory(List<Category> mList) {
        rvCate.setHasFixedSize(true);
        rvCate.setLayoutManager(new GridLayoutManager(this.getContext(), 5));
        categoryAdapter = new CategoryAdapter(mList, getContext());
        rvCate.setAdapter(categoryAdapter);
        loading_cate.setVisibility(View.INVISIBLE);
        eventClickRvCate();
    }

    @Override
    public void setDataToRecyclerViewBrandSmartphone(List<BrandProduct> mList) {
        rvFilter_Smartphone.setHasFixedSize(true);
        rvFilter_Smartphone.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        BrandAdapter mAdapter = new BrandAdapter(mList);
        rvFilter_Smartphone.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnClickSearchBrand(new BrandAdapter.OnClickSearchBrand() {
            @Override
            public void onClick(String name) {
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                replaceFragment(searchFragment);
            }
        });
    }

    @Override
    public void setDataToRecyclerViewBrandLaptop(List<BrandProduct> mList) {
        rvFilter_Laptop.setHasFixedSize(true);
        rvFilter_Laptop.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        BrandAdapter mAdapter = new BrandAdapter(mList);
        rvFilter_Laptop.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnClickSearchBrand(new BrandAdapter.OnClickSearchBrand() {
            @Override
            public void onClick(String name) {
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                replaceFragment(searchFragment);
            }
        });
    }

    @Override
    public void setDataToRecyclerViewSmartphone(List<Product> mList) {
        rvSmart.setHasFixedSize(true);
        rvSmart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        smartAdapter = new ProductAdapter(mList,getContext());
        rvSmart.setAdapter(smartAdapter);
        smartAdapter.notifyDataSetChanged();
        mPresenter.autoSliderProduct(rvSmart);

        smartAdapter.setOnClickAddToCart(new ProductAdapter.OnClickAddCartListener() {
            @Override
            public void onClickAddToCart() {
                if(Utils.listCart.size() !=0){
                    TextView tvCart = getActivity().findViewById(R.id.num_cart);
                    tvCart.setText(String.valueOf(Utils.listCart.size()));
                }
            }

        });
    }

    @Override
    public void setDataToRecyclerViewLaptop(List<Product> mList) {
        rvLaptop.setHasFixedSize(true);
        rvLaptop.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        laptopAdapter = new ProductAdapter(mList,getContext());
        rvLaptop.setAdapter(laptopAdapter);
        laptopAdapter.notifyDataSetChanged();
        mPresenter.autoSliderProduct(rvLaptop);
        laptopAdapter.setOnClickAddToCart(new ProductAdapter.OnClickAddCartListener() {
            @Override
            public void onClickAddToCart() {
                if(Utils.listCart.size() !=0){
                    TextView tvCart = getActivity().findViewById(R.id.num_cart);
                    tvCart.setText(String.valueOf(Utils.listCart.size()));
                }
            }

        });
    }
}
