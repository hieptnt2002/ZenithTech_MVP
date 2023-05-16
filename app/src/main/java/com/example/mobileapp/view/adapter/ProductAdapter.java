package com.example.mobileapp.view.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.model.Cart;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.R;
import com.example.mobileapp.utils.Utils;
import com.example.mobileapp.view.ProductDetails;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> listProduct;
    Context context;
    public interface OnClickAddCartListener {
        void onClickAddToCart();
    }
    public OnClickAddCartListener iClickAddCartListener;
    public void setOnClickAddToCart(OnClickAddCartListener mListener) {
        iClickAddCartListener = mListener;
    }


    public ProductAdapter(List<Product> listProduct, Context context) {
        this.listProduct = listProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.row_sp, parent, false);
        return new ViewHolder(view);
    }
    public void filterNameProduct(String name) {
        name = name.toLowerCase();
        int k = 0;
        for (int i = 0; i < listProduct.size(); i++) {
            Product product = listProduct.get(i);
            String n = product.getTen_sp().toLowerCase();
            if (n.indexOf(name) >= 0) {
                listProduct.set(i, listProduct.get(k));
                listProduct.set(k, product);
                k++;
            }
        }
        notifyDataSetChanged();
    }

    public void filterPriceProductHigh() {
        Comparator<Product> compareByPrice = Comparator.comparing(Product::getGia_km);
        Collections.sort(listProduct, compareByPrice.reversed());
        notifyDataSetChanged();

    }

    public void filterPriceProductLow() {
        Comparator<Product> compareByPrice = Comparator.comparing(Product::getGia_km);
        Collections.sort(listProduct, compareByPrice);
        notifyDataSetChanged();

    }

    public void filterPriceProductPercent() {
        Comparator<Product> compareByPrice = Comparator.comparing(this::calculateRating);
        Collections.sort(listProduct, compareByPrice.reversed());
        notifyDataSetChanged();

    }

    private float calculateRating(Product product) {
        float gia_km = product.getGia_km();
        float gia_sp = product.getGia_sp();
        return ((gia_sp - gia_km) / gia_sp) * 100;
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = listProduct.get(position);

        Glide.with(context).load(product.getAnh()).into(holder.img);
        Locale locale = new Locale("vi", "VN"); // Thiết lập địa phương Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.txtName.setText(product.getTen_sp());
        holder.txtGia_km.setText(currencyFormatter.format(product.getGia_km()));
        holder.txtGia_sp.setText(currencyFormatter.format(product.getGia_sp()));
        holder.txtQuatang.setText(product.getQuatang());
        SpannableString spannableString = new SpannableString(holder.txtGia_sp.getText().toString());
        spannableString.setSpan(new StrikethroughSpan(), 0, holder.txtGia_sp.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtGia_sp.setText(spannableString);

        Glide.with(context).load(product.getAnh()).into(holder.img);
        Glide.with(context).load("https://static.vecteezy.com/system/resources/thumbnails/014/568/310/small/sale-label-promotion-red-tag-banner-label-templates-for-special-offers-free-png.png").into(holder.imgSale);
        int sale = (int) calculateRating(product);
        holder.txtSale.setText("-"+ sale + "%");

        holder.btn_add_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences(Utils.login_success, MODE_PRIVATE);
                String json = preferences.getString("object", null);
                if (json != null) {
                    Gson gson = new Gson();
                    Account account = gson.fromJson(json, Account.class);
                    if (Utils.listCart.size() > 0) {
                        boolean flag = false;
                        int quantity = 1;
                        for (int i = 0; i < Utils.listCart.size(); i++) {
                            if (Utils.listCart.get(i).getName().equals(product.getTen_sp())) {
                                Utils.listCart.get(i).setQuantity(quantity + Utils.listCart.get(i).getQuantity());
                                flag = true;
                                Utils.saveCart(context);
                            }
                        }
                        if (flag == false) {
                            Utils.listCart.add(new Cart(product.getAnh(), product.getTen_sp(), product.getGia_sp(), product.getGia_km(), 1, account.getId()));
                            Utils.saveCart(context);
                        }
                    } else {
                        Utils.listCart.add(new Cart(product.getAnh(), product.getTen_sp(), product.getGia_sp(), product.getGia_km(), 1, account.getId()));
                        Utils.saveCart(context);
                    }
                } else
                    Toast.makeText(context, "Bạn cần đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
                iClickAddCartListener.onClickAddToCart();
            }
        });
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listProduct != null){
            return  listProduct.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, imgSale;
        TextView txtName, txtGia_km, txtGia_sp, txtQuatang, txtSale;
        CardView btn_add_Cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_sp);
            imgSale = itemView.findViewById(R.id.img_sale);
            txtName = itemView.findViewById(R.id.txtName_sp);
            txtGia_km = itemView.findViewById(R.id.txtGia_km);
            txtGia_sp = itemView.findViewById(R.id.txtGia_sp);
            txtQuatang = itemView.findViewById(R.id.txtQuatang);
            txtSale = itemView.findViewById(R.id.textView_sale);
            btn_add_Cart = itemView.findViewById(R.id.btn_addCart_row);
        }
    }

}

