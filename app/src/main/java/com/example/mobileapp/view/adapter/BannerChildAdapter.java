package com.example.mobileapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mobileapp.data.model.Slider;
import com.example.mobileapp.R;

import java.util.List;

public class BannerChildAdapter extends PagerAdapter {
    List<Slider> imgBannerList;

    public BannerChildAdapter(List<Slider> imgBannerList) {
        this.imgBannerList = imgBannerList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.row_slider,container,false);
        ImageView imageView = view.findViewById(R.id.imageViewBanner);
        Slider mBanner = imgBannerList.get(position);

        Glide.with(container.getContext()).load(mBanner.getImg()).into(imageView);
        // add view to group
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if(imgBannerList != null){
            return imgBannerList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
