package com.example.mobileapp.view.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;
import com.example.mobileapp.view.LoginActivity;

public class StartFragment extends Fragment {
    Button btnSignin, btnSignup;
    View view;
    LoginActivity loginMobileApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start, container, false);
        initGUI();
        eventClickButton();
        return view;
    }

    void initGUI() {
        btnSignin = view.findViewById(R.id.btn_start_signin);
        btnSignup = view.findViewById(R.id.btn_start_signup);
        loginMobileApp = (LoginActivity) getActivity();

    }

    void eventClickButton() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginMobileApp.viewPager2.setCurrentItem(1);


            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginMobileApp.viewPager2.setCurrentItem(2);
            }
        });
    }
}
