package com.example.mobileapp.data.constract;

import com.example.mobileapp.data.model.Account;
import com.example.mobileapp.data.model.BrandProduct;
import com.example.mobileapp.data.model.Cart;
import com.example.mobileapp.data.model.Category;
import com.example.mobileapp.data.model.OrderInfo;
import com.example.mobileapp.data.model.Product;
import com.example.mobileapp.data.model.Slider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("slider__home.php")
    Call<List<Slider>> getImgSlider();
    @GET("show_danhmucc.php")
    Call<List<Category>> getCategory();
    @GET("thuonghieu_dienthoai.php")
    Call<List<BrandProduct>> getBrandSmartphone();
    @GET("thuonghieu_laptop.php")
    Call<List<BrandProduct>> getBrandLaptop();
    @GET("data_smartphone.php")
    Call<List<Product>> getSmartphone();
    @GET("data_laptop.php")
    Call<List<Product>> getLaptop();
    @GET("slider_smart.php")
    Call<List<Slider>> getSliderSmart();
    @GET("slider_laptop.php")
    Call<List<Slider>> getSliderLap();


    @POST("find_result.php")
    @FormUrlEncoded
    Call<List<Product>> getProductSearch(@Field("name") String name);
    @POST("images_phone.php")
    @FormUrlEncoded
    Call<List<Slider>> getImagesSmart(@Field("id") String id);
    @POST("images_phone.php")
    @FormUrlEncoded
    Call<List<Slider>> getImagesLap(@Field("id") String id);
    @POST("order.php")
    @FormUrlEncoded
    Call<Cart> postCartData(@Field("img") String img,
                            @Field("name") String name,
                            @Field("price") String price,
                            @Field("quantity") String quantity,
                            @Field("trangthai") String trangthai,
                            @Field("account_id") String account_id,
                            @Field("receiver") String receiver,
                            @Field("address") String address,
                            @Field("phone") String phone,
                            @Field("date") String date);

    @POST("show_order.php")
    @FormUrlEncoded
    Call<List<OrderInfo>> getOrder(@Field("id") String id);
    @GET("list_account.php")
    Call<List<Account>> getListAccount();
    @POST("sign__up.php")
    @FormUrlEncoded
    Call<Account> createAccount(@Field("account_name") String name,
                            @Field("email") String email,
                            @Field("account_password") String pass);
}
