package com.example.mobileapp.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.view.adapter.CartAdapter;
import com.example.mobileapp.data.constract.CartConstract;
import com.example.mobileapp.view.fragment.BottomSheetFragment;
import com.example.mobileapp.data.model.Cart;
import com.example.mobileapp.data.model.Order;
import com.example.mobileapp.data.presenter.CartPresenter;
import com.example.mobileapp.utils.Utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartConstract.IView {
    RecyclerView rvCart;
    public TextView tvOut, tvTotal,tvIsCart;
    LinearLayout layoutPay;
    Locale locale = new Locale("vi", "VN"); // Thiết lập địa phương Việt Nam
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
     int total = 0;
    public CartAdapter cartAdapter;
    CartConstract.IPresenter mPresenter;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initGUI();

        mPresenter = new CartPresenter(this);
        mPresenter.getCartList(Utils.listCart);
        tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layoutPay.setOnClickListener(view->
                mPresenter.onClickToOrder(Utils.listCart,total));
        Utils.saveCart(this);
    }

    private void initGUI() {
        rvCart = findViewById(R.id.recycleView_cart);
        tvOut = findViewById(R.id.textView_out);
        tvTotal = findViewById(R.id.textView_total);
        tvIsCart = findViewById(R.id.tvCart_null);
        layoutPay = findViewById(R.id.btn_add_pay);
        rvCart.setHasFixedSize(true);
        rvCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setDataToRecycleViewCart(List<Cart> mList) {


        if (mList!= null && !mList.isEmpty()) {
            for (int i = 0; i < mList.size(); i++) {
                total += mList.get(i).getSaleprice() * mList.get(i).getQuantity();
                Utils.total_pay = total;
            }
        }else tvIsCart.setVisibility(View.VISIBLE);
        onUpdateTotalPriceTextView(total);
        cartAdapter = new CartAdapter(mList,getApplicationContext());
        rvCart.setAdapter(cartAdapter);
        cartAdapter.setOnClickQuantity(new CartAdapter.OnClickQuantity() {
            @Override
            public void onCick() {
                int total = 0;
                if (mList != null && !mList.isEmpty()) {
                    for (int i = 0; i < mList.size(); i++) {
                        total += mList.get(i).getSaleprice() * mList.get(i).getQuantity();
                        Utils.total_pay = total;
                    }

                } else tvIsCart.setVisibility(View.VISIBLE);

                onUpdateTotalPriceTextView(total);
                Utils.saveCart(getApplicationContext());

            }
        });
    }

    @Override
    public void onUpdateTotalPriceTextView(int total) {
        tvTotal.setText(currencyFormatter.format(total));
    }


    @Override
    public void onShowBottomSheet(Order order) {
        BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(order);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        bottomSheetFragment.setCancelable(false);
    }



    @Override
    public void onCartEmptyError() {
        Toast.makeText(CartActivity.this, "Giỏ hàng đang trống !!!", Toast.LENGTH_SHORT).show();
    }
}