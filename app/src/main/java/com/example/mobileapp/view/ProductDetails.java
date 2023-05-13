package com.example.mobileapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.mobileapp.R;
import com.example.mobileapp.view.adapter.SliderImagesAdapter;
import com.example.mobileapp.constract.ProductDetailsConstract;
import com.example.mobileapp.model.Product;
import com.example.mobileapp.model.Slider;
import com.example.mobileapp.presenter.ProductDetailsPresenter;
import com.example.mobileapp.utils.Utils;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import me.relex.circleindicator.CircleIndicator;

public class ProductDetails extends AppCompatActivity implements ProductDetailsConstract.IView {
    ImageView imgBack;
    FrameLayout flCart;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    TextView tvName, tvSalePrice, tvCost, tvColor, tvVoucher, tvInfoProduct, tvAddToCart, tvBuyNow, tvNumberCart;
    ProductDetailsConstract.IPresenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_details);
        initGUI();
        mPresenter = new ProductDetailsPresenter(this);

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");
        if (product != null) {
            mPresenter.getDataIntent(product);
            if (product.getLoaisp_id() == 1) {
                mPresenter.getImagesSmartList(product.getId() + "");
            } else if (product.getLoaisp_id() == 2) {
                mPresenter.getImagesLapList(product.getId() + "");
            }
            eventCart(product);
        }


        tvNumberCart.setText(String.valueOf(Utils.listCart.size()));
        imgBack.setOnClickListener(view ->
                startActivity(new Intent(this, ZenithActivity.class)));
    }


    void initGUI() {
        imgBack = findViewById(R.id.imageView_back);
        flCart = findViewById(R.id.frameLayout_cart);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
        tvName = findViewById(R.id.textView_name_product);
        tvSalePrice = findViewById(R.id.textView_priceSale);
        tvCost = findViewById(R.id.textView_cost);
        tvColor = findViewById(R.id.textView_color);
        tvVoucher = findViewById(R.id.textView_voucher);
        tvInfoProduct = findViewById(R.id.textView_info_product);
        tvAddToCart = findViewById(R.id.textView_addtocart);
        tvBuyNow = findViewById(R.id.textView_buynow);
        tvNumberCart = findViewById(R.id.num_cart);
        flCart.setOnClickListener(view->
                startActivity(new Intent(this,CartActivity.class)));

    }


    private void eventCart(Product product) {
        tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addProductToListCart(product);
                tvNumberCart.setText(String.valueOf(Utils.listCart.size()));
            }
        });
//        flCart.setOnClickListener(view ->
////                startActivity(new Intent(this, ZenithActivity.class))
//                );

        tvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addProductToListCart(product);
                tvNumberCart.setText(String.valueOf(Utils.listCart.size()));
//                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
    }


    @Override
    public void setDataToViewPagerImages(List<Slider> sliderList) {
        SliderImagesAdapter imagesAdapter = new SliderImagesAdapter(sliderList);
        viewPager.setAdapter(imagesAdapter);
        circleIndicator.setViewPager(viewPager);
        imagesAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDataReceived(Product data) {
        tvName.setText(data.getTen_sp());

        Locale locale = new Locale("vi", "VN"); // Thiết lập địa phương Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        String cost = currencyFormatter.format(data.getGia_sp());

        SpannableString spannableString = new SpannableString(cost);
        spannableString.setSpan(new StrikethroughSpan(), 0, cost.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvSalePrice.setText(currencyFormatter.format(data.getGia_km()));
        tvCost.setText(spannableString);
        tvVoucher.setText(data.getQuatang());
        tvInfoProduct.setText(data.getMota() + "");
    }

    @Override
    public void onAddProductSuccess() {
        Toast.makeText(getApplicationContext(), "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddProductFail() {
        Toast.makeText(getApplicationContext(), "Thêm sản phẩm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccountNull() {
        Toast.makeText(getApplicationContext(), "Bạn cần đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
    }
}
