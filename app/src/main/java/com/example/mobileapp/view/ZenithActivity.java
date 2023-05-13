package com.example.mobileapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.R;
import com.example.mobileapp.constract.MainConstract;
import com.example.mobileapp.view.fragment.AccountFragment;
import com.example.mobileapp.view.fragment.SmartphoneFragment;
import com.example.mobileapp.view.fragment.HomeFragment;
import com.example.mobileapp.view.fragment.LaptopFragment;
import com.example.mobileapp.presenter.MainPresenter;
import com.example.mobileapp.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;

public class ZenithActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainConstract.IView {
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;

    NavigationView mNavigationView;
    BottomNavigationView bottomNavigationView;
    TextView tv_num_cart, tvUsername;
    LinearLayout lnCart;
    TextView tvLogOut;
    public MainConstract.IPresenter mIPresenter;
    boolean isLogin = false;
    public int index = 0;

    @Override
    protected void onResume() {
        super.onResume();
        tv_num_cart.setText(String.valueOf(Utils.listCart.size()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zenith);
        initGUI();
        //lấy ngày tháng
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.toString();
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        //
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
       mIPresenter = new MainPresenter(this);

        mIPresenter.eventClickItemNavigation(0);
        mIPresenter.isLoggedIn();
        if(isLogin == false){
            tvLogOut.setText("Login | Register");
        }
        tvLogOut.setOnClickListener(view->mIPresenter.eventLogout());
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(this);
        eventClickBottomNav();
    }

    private void initGUI() {

        mNavigationView = findViewById(R.id.nav_header);
        mDrawerLayout = findViewById(R.id.header_draw);
        mToolbar = findViewById(R.id.header_toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setBackground(null);
        Menu menu = mNavigationView.getMenu();
        MenuItem itemToRemove = menu.findItem(R.id.nav_cart);
        menu.removeItem(itemToRemove.getItemId());
        lnCart = findViewById(R.id.linear_cart);
        tv_num_cart = findViewById(R.id.num_cart);
        View view = mNavigationView.getHeaderView(0);
        tvUsername = view.findViewById(R.id.text_user);
        tvLogOut = findViewById(R.id.textView_logout);
        lnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZenithActivity.this,CartActivity.class));
            }
        });




    }

    public void eventClickBottomNav() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        index = 0;
                        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case R.id.nav_smart:
                        index = 1;
                        mNavigationView.getMenu().findItem(R.id.nav_smart).setChecked(true);
                        break;
                    case R.id.nav_lap:
                        index = 2;
                        mNavigationView.getMenu().findItem(R.id.nav_lap).setChecked(true);
                        break;
                    case R.id.nav_account:
                        index = 3;
                        mNavigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                    default:
                        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                }
                mIPresenter.eventClickItemNavigation(index);
                tv_num_cart.setText(String.valueOf(Utils.listCart.size()));
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                index = 0;
                bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                break;
            case R.id.nav_smart:
                index = 1;;
                bottomNavigationView.getMenu().findItem(R.id.nav_smart).setChecked(true);
                break;
            case R.id.nav_lap:
                index = 2;
                bottomNavigationView.getMenu().findItem(R.id.nav_lap).setChecked(true);
                break;
            case R.id.nav_account:
                index = 3;
                bottomNavigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                break;
            default:
                mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                break;
        }
        mIPresenter.eventClickItemNavigation(index);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        tv_num_cart.setText(String.valueOf(Utils.listCart.size()));
        return true;
    }



    @Override
    public void showFragmentHome() {
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,homeFragment).commit();
    }

    @Override
    public void showFragmentSmartphone() {
        SmartphoneFragment smartphoneFragment = new SmartphoneFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,smartphoneFragment).commit();
    }

    @Override
    public void showFragmentLaptop() {
        LaptopFragment laptopFragment = new LaptopFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,laptopFragment).commit();
    }

    @Override
    public void showFragmentAccount() {
        AccountFragment accountFragment = new AccountFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,accountFragment).commit();
    }

    @Override
    public void showIsLoggedIn(String name, int num) {
        tvUsername.setText(name);
        tv_num_cart.setText(String.valueOf(num));
        isLogin = true;
    }

    @Override
    public void logOut() {
        Utils.listCart.clear();
        SharedPreferences.Editor editor = getSharedPreferences(Utils.login_success, MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", false);
        editor.remove("object");
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra("currentItem", 1);
        startActivity(intent);
    }
}