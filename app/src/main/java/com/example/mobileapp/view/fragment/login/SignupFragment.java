package com.example.mobileapp.view.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;
import com.example.mobileapp.data.constract.SignUpConstract;
import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.presenter.SignUpPresenter;
import com.example.mobileapp.view.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class SignupFragment extends Fragment implements SignUpConstract.IView {
    View view;
    EditText edtName, edtEmail, edtPass;
    Button btnSignup;
    String name, email, pass;
    LoginActivity loginMobileApp;
    ProgressBar progressBar;
    SignUpPresenter mPresenter;
    List<Account> accountList = new ArrayList<>();
    ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        initGUI();
        mPresenter = new SignUpPresenter(this);
        mPresenter.getListAccount();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountList != null && !accountList.isEmpty()) {
                    name = edtName.getText().toString();
                    email = edtEmail.getText().toString();
                    pass = edtPass.getText().toString();
                    mPresenter.register(name, pass, email, accountList,loginMobileApp);
                }

            }
        });
        return view;
    }

    void initGUI() {
        edtName = view.findViewById(R.id.signUp_user);
        edtEmail = view.findViewById(R.id.signUP_email);
        edtPass = view.findViewById(R.id.signUP_password);
        btnSignup = view.findViewById(R.id.buttonSignup);
        progressBar = view.findViewById(R.id.progressbar);
        loginMobileApp = (LoginActivity) getActivity();
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

    @Override
    public void setAccountList(List<Account> mList) {
        accountList = mList;
    }


}
