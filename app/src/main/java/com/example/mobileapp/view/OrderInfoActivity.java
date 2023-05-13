package com.example.mobileapp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.constract.OrderInfoConstract;
import com.example.mobileapp.model.OrderInfo;
import com.example.mobileapp.presenter.OrderInfoPresenter;
import com.example.mobileapp.view.adapter.OrderInfoAdapter;

import java.util.List;

public class OrderInfoActivity extends AppCompatActivity implements OrderInfoConstract.IView {
    TextView tvIsOrder,tvBack;
    RecyclerView recyclerView;
    OrderInfoAdapter mAdapter;
    OrderInfoConstract.IPresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order_info);
         initGUI();
         mPresenter = new OrderInfoPresenter(this);
         int id = mPresenter.getIdAccount(this);

         if( id != 0){
             mPresenter.getOrderList(id+"");

         }

    }
    private void initGUI(){
        tvIsOrder = findViewById(R.id.textView_isOrder);
        tvBack = findViewById(R.id.tvOrder_back);
        tvBack.setOnClickListener(view->
                onBackPressed());
        recyclerView = findViewById(R.id.recycleView_HistoryOrder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void setDataToRecyclerViewOrder(List<OrderInfo> mList) {
        mAdapter = new OrderInfoAdapter(mList,this);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        if(mList == null || mList.isEmpty()){
            tvIsOrder.setVisibility(View.VISIBLE);
        }else tvIsOrder.setVisibility(View.GONE);
    }
}
