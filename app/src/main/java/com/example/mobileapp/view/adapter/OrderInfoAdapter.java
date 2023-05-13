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
import com.example.mobileapp.model.OrderInfo;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.ViewHolder> {
    List<OrderInfo> mList;
    Context context;

    public OrderInfoAdapter(List<OrderInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderInfoAdapter.ViewHolder holder, int position) {
        OrderInfo object = mList.get(position);
        Glide.with(context).load(object.getImg()).into(holder.img);
        holder.tvName.setText(object.getName());
        Locale locale = new Locale("vi", "VN"); // Thiết lập địa phương Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.tvPrice.setText(currencyFormatter.format(object.getPrice()));
        holder.tvQuantity.setText("Số lượng: " + object.getQuantity());

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice, tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewName_row);
            tvPrice = itemView.findViewById(R.id.textView_price_row);
            tvQuantity = itemView.findViewById(R.id.textView_quantity);
            img = itemView.findViewById(R.id.imgorder_row);
        }
    }
}
