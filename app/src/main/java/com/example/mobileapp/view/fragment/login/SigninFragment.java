package com.example.mobileapp.view.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;
import com.example.mobileapp.constract.SignInConstract;
import com.example.mobileapp.presenter.SignInPresenter;
import com.example.mobileapp.view.LoginActivity;

public class SigninFragment extends Fragment implements SignInConstract.IView {
    EditText edtUser, edtPassword;
    TextView txtSignup;
    Button btnLogin;
    View view;
    LoginActivity loginMobileApp;
    ProgressBar progressBar;
    SignInPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signin, container, false);
        initGUI();
        mPresenter = new SignInPresenter(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,pass;
                name = edtUser.getText().toString();
                pass = edtPassword.getText().toString();
                mPresenter.login(name,pass,getContext());
            }
        });
        return view;
    }

    private void initGUI() {
        txtSignup = view.findViewById(R.id.textSignup);
        loginMobileApp = (LoginActivity) getActivity();
        edtUser = view.findViewById(R.id.textUser);
        edtPassword = view.findViewById(R.id.text_Pass);
        btnLogin = view.findViewById(R.id.buttonLogin);
        progressBar = view.findViewById(R.id.progressbar);
        txtSignup.setOnClickListener(view->
                loginMobileApp.viewPager2.setCurrentItem(2)
        );
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(loginMobileApp, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(loginMobileApp, message, Toast.LENGTH_SHORT).show();
    }
}
