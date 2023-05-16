package com.example.mobileapp.data.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import com.example.mobileapp.utils.constants.Constants;
import com.example.mobileapp.data.constract.ApiService;
import com.example.mobileapp.data.constract.SignInConstract;
import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.remote.RetrofitClient;
import com.example.mobileapp.utils.Utils;
import com.example.mobileapp.view.ZenithActivity;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInPresenter implements SignInConstract.IPresenter {
    private SignInConstract.IView mView;
    private ApiService apiService;

    public SignInPresenter(SignInConstract.IView mView) {
        this.mView = mView;
        apiService = RetrofitClient.getClient(Constants.SERVICE_API).create(ApiService.class);
    }

    @Override
    public void login(String name, String password, Context context) {
        String password_256 = Utils.sha256(password);
        mView.showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.hideProgress();
                if (name.isEmpty() || password.isEmpty()) {
                    mView.showErrorMessage("Không được để trống");
                } else {
                    apiService.getListAccount().enqueue(new Callback<List<Account>>() {
                        @Override
                        public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                            List<Account> mList = response.body();
                            if (mList != null && !mList.isEmpty()) {
                                for (int i = 0; i < mList.size(); i++) {
                                    if (name.equals(mList.get(i).getName()) || name.equals(mList.get(i).getEmail()) && password_256.equals(mList.get(i).getPass())) {
                                        mView.showSuccessMessage("Đăng nhập thành công");
                                        //save tam thoi tai khoan tren app
                                        Gson gson = new Gson();
                                        String json = gson.toJson(mList.get(i));
                                        SharedPreferences.Editor editor = context.getSharedPreferences(Utils.login_success, MODE_PRIVATE).edit();
                                        editor.putString("object",json);
                                        editor.putBoolean("isLoggedIn", true);
                                        editor.apply();
                                        //Chuyen màn hình
                                        context.startActivity(new Intent(context, ZenithActivity.class));
                                        return;
                                    }
                                }
                                mView.showErrorMessage("Nhập sai tên hoặc mật khẩu");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Account>> call, Throwable t) {

                        }
                    });

                }
                mView.hideProgress();
            }
        }, 3000);
    }
}
