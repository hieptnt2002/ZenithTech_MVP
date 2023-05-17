package com.example.mobileapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.R;
import com.example.mobileapp.data.constract.AccountConstract;
import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.presenter.AccountPresenter;
import com.example.mobileapp.view.OrderInfoActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements AccountConstract.IView {
    TextView tvName, tvGmail;
    CircleImageView civImg;
    LinearLayout layoutOrder, layoutVoucher, layoutIntroduce;
    AccountConstract.IPresenter mPresenter;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        initGUI();
        mPresenter = new AccountPresenter(this);
        mPresenter.getData(getContext());
        return view;
    }

    private void initGUI() {
        tvName = view.findViewById(R.id.textView_nameProfile);
        tvGmail = view.findViewById(R.id.textView_gmailProfile);
        civImg = view.findViewById(R.id.circleImageView_profile);
        layoutOrder = view.findViewById(R.id.layout_order);

        layoutOrder.setOnClickListener(view->
                startActivity(new Intent(getContext(), OrderInfoActivity.class)));
    }

    @Override
    public void setDataToTextView(Account account) {
            tvName.setText(account.getName());
            tvGmail.setText(account.getEmail());
    }
}
