package com.example.mobileapp.slider;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class SliderAuto {
    public static void autoSlideImages(ViewPager viewPager) {
        // Tạo Handler và Runnable  thực hiện slide tự động
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int count = viewPager.getAdapter().getCount();
                viewPager.setCurrentItem((currentItem + 1) % count);

                // Lặp lại chạy lại task sau 5 giây
                handler.postDelayed(this, 6000);
            }
        };
        // Bắt đầu thực hiện slide tự động khi Activity hoặc Fragment được khởi tạo
        handler.postDelayed(runnable, 6000);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    viewPager.removeCallbacks(runnable);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    viewPager.postDelayed(runnable, 6000);
                return false;
            }
        });

    }
}
