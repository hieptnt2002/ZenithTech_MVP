package com.example.mobileapp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import com.example.mobileapp.utils.Utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Cart> mList;
    Context context;
    View view;
    Locale locale = new Locale("vi", "VN"); // Thiết lập địa phương Việt Nam
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    public OnClickQuantity onClickQuantity;
    public interface OnClickQuantity{
        void onCick();
    }
    public void setOnClickQuantity(OnClickQuantity mLister){
        onClickQuantity = mLister;
    }

    public CartAdapter(List<Cart> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart mCart = mList.get(position);

        Glide.with(context).load(mCart.getImg()).into(holder.imgProduct);
        holder.tvName.setText(mCart.getName());
        holder.tvSaleprice.setText(currencyFormatter.format(mCart.getSaleprice() * mCart.getQuantity()));
        holder.tvPrice.setText(currencyFormatter.format(mCart.getPrice() * mCart.getQuantity()));
        SpannableString spannableString = new SpannableString(holder.tvPrice.getText().toString());
        spannableString.setSpan(new StrikethroughSpan(), 0, holder.tvPrice.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvPrice.setText(spannableString);
        holder.tvQuantity.setText(mCart.getQuantity()+"");
        eventListCart(holder,position,mCart);

    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct,imgPlus,imgMinus;
        TextView tvName,tvSaleprice,tvPrice,tvQuantity,tvDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgCart_row);
            imgPlus = itemView.findViewById(R.id.imageView_plus);
            imgMinus = itemView.findViewById(R.id.imageView_minus);
            tvDelete = itemView.findViewById(R.id.textView_delete);
            tvName = itemView.findViewById(R.id.textViewName_row);
            tvSaleprice = itemView.findViewById(R.id.textView_saleprice);
            tvPrice = itemView.findViewById(R.id.textView_price);
            tvQuantity = itemView.findViewById(R.id.textView_quantity);
        }
    }
    private void eventListCart(ViewHolder viewHolder,int i,Cart mCart){
        viewHolder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mList.get(i).getQuantity() < 10){
                    mList.get(i).setQuantity(mList.get(i).getQuantity()+1);
                    viewHolder.tvSaleprice.setText(currencyFormatter.format(mCart.getSaleprice() * mCart.getQuantity()));
                    viewHolder.tvPrice.setText(currencyFormatter.format(mCart.getPrice() * mCart.getQuantity()));
                    SpannableString spannableString = new SpannableString(viewHolder.tvPrice.getText().toString());
                    spannableString.setSpan(new StrikethroughSpan(), 0, viewHolder.tvPrice.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.tvPrice.setText(spannableString);
                    viewHolder.tvQuantity.setText(mCart.getQuantity()+"");
                    onClickQuantity.onCick();

                }
            }
        });
        viewHolder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mList.get(i).getQuantity() > 1){
                    mList.get(i).setQuantity(mList.get(i).getQuantity()-1);
                    viewHolder.tvSaleprice.setText(currencyFormatter.format(mCart.getSaleprice() * mCart.getQuantity()));
                    viewHolder.tvPrice.setText(currencyFormatter.format(mCart.getPrice() * mCart.getQuantity()));
                    SpannableString spannableString = new SpannableString(viewHolder.tvPrice.getText().toString());
                    spannableString.setSpan(new StrikethroughSpan(), 0, viewHolder.tvPrice.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    viewHolder.tvPrice.setText(spannableString);
                    viewHolder.tvQuantity.setText(mCart.getQuantity()+"");
                    onClickQuantity.onCick();
                }
            }
        });
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(i);
                Utils.saveCart(view.getContext());
                notifyDataSetChanged();
                onClickQuantity.onCick();
            }
        });
    }
}
