package com.example.mobileapp.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileapp.R;
import com.example.mobileapp.model.Cart;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<Cart> mList;
    Context context;

    public OrderAdapter(List<Cart> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = mList.get(position);
        Glide.with(context).load(cart.getImg()).into(holder.img);
        holder.tvName.setText(cart.getName());
        Locale locale = new Locale("vi", "VN"); // Thiết lập địa phương Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.tvPrice.setText(currencyFormatter.format(cart.getSaleprice() * cart.getQuantity()));
        holder.tvQuantity.setText(cart.getQuantity() + "");

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvPrice,tvQuantity; ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_order);
            tvName = itemView.findViewById(R.id.textView_NameOrder);
            tvPrice = itemView.findViewById(R.id.textView_price_product_order);
            tvQuantity = itemView.findViewById(R.id.textView_quantity_pr_order);
        }
    }
}
