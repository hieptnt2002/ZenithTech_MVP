package com.example.mobileapp.slider;

import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.RecyclerView;

public class AutoSlider {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable mRunnable;
    private final RecyclerView mRecyclerView;
    private int mCurrentPosition = 0;
    private int mDuration = 5000; // Thời gian tự động chuyển tiếp mặc định là 5 giây

    public AutoSlider(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;

        // Sử dụng Runnable để chuyển đổi vị trí tiếp theo
        mRunnable = new Runnable() {
            @Override
            public void run() {
                int nextPosition = mCurrentPosition + 1;
                if (nextPosition >= mRecyclerView.getAdapter().getItemCount()) {
                    nextPosition = 0;
                }
                mRecyclerView.smoothScrollToPosition(nextPosition);
                mCurrentPosition = nextPosition;
                mHandler.postDelayed(this, mDuration);
            }
        };
    }

    // Bắt đầu chuyển đổi tự động
    public void start() {
        mHandler.postDelayed(mRunnable, mDuration);
    }

    // Dừng chuyển đổi tự động
    public void stop() {
        mHandler.removeCallbacks(mRunnable);
    }

    // Thiết lập thời gian tự động chuyển tiếp (tính bằng mili giây)
    public void setDuration(int duration) {
        mDuration = duration;
    }
}
