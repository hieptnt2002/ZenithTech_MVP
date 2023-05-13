package com.example.mobileapp.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.model.Category;
import com.example.mobileapp.R;
import com.example.mobileapp.constract.OnItemClickListener;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<Category> arrCate;
    Context context;
    View v;
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }

    public CategoryAdapter(List<Category> arrCate, Context context) {
        this.arrCate = arrCate;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.row_category,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category danhmuc = arrCate.get(position);
        holder.txtTen.setText(danhmuc.getTensp());
//        Glide.with(context).load(danhmuc.getAnh()).into(holder.img);
        byte[] decode = Base64.decode(danhmuc.getAnh(),Base64.DEFAULT);
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        holder.img.setImageBitmap(imgBitmap);
    }

    @Override
    public int getItemCount() {
        return arrCate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTen;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.cate_ten);
            img = itemView.findViewById(R.id.cate_anh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                            mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
