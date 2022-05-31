package com.example.shop_cong.network;

import com.example.shop_cong.entity.Bill;
import com.example.shop_cong.entity.Cart;
import com.example.shop_cong.entity.Clothes;
import com.example.shop_cong.entity.ClothesItem;
import com.example.shop_cong.entity.Orderr;
import com.example.shop_cong.entity.Result;
import com.example.shop_cong.entity.User;
import com.example.shop_cong.entity.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    String pathUser = "user";
    String pathClothes = "clothes";
    String pathCart = "cart";
    String pathOrder = "order";

    //Kiem Tra Dang Nhao - String User
    @POST(pathUser +"/login")
    Call<User> checkLogin(@Body UserDTO userDTO);

    //Lay 1 user theo id
    @GET(pathUser +"/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET(pathUser +"/get/{name}")
    Call<User> getUserByName(@Path("name") String name);

    //lay tat ca
    @GET(pathUser + "/list-user")
    Call<List<User>> getListUser();

    //them
    @POST(pathUser + "/add")
    Call<User> addUser(@Body User user);

    //sua
    @PUT(pathUser + "/update")
    Call<User> updateUser(@Body User user);

    //Xoa
    @DELETE(pathUser + "/delete/{id}")
    Call<Boolean> deleteUser(@Path("id") int id);

    //  ----------------- Clothes ---------------

    @GET(pathClothes +"/{id}")
    Call<Clothes> getClothesById(@Path("id") int id);

    @GET(pathClothes +"/list-clothes")
    Call<List<Clothes>> getListClothes();

    @POST(pathClothes +"/add")
    Call<Clothes> addClothes(@Body Clothes clothes);

    @POST(pathClothes +"/update")
    Call<Clothes> updateClothes(@Body Clothes clothes);

    @GET(pathClothes +"/delete/{id}")
    Call<Result> deleteClothes(@Path("id") int id);

    @GET(pathClothes +"/search-name")
    Call<List<Clothes>> findClothesByName(@Query("name") String name);

    @GET(pathClothes +"/search-category/{name}")
    Call<List<Clothes>> findClothesByCate(@Path("name") String name);


    // ---------------------- Cart -----------------------
    @GET(pathCart +"/")
    Call<ClothesItem> getAllCart_Clothes();

    @POST(pathCart +"/add")
    Call<ClothesItem> addToCart(@Body ClothesItem clothesItem);

    @GET(pathCart +"/status/{id}")
    Call<Result> checkStatusCart(@Path("id") int id);

    @GET(pathCart +"/get-cart-id-user/{id}")
    Call<Cart> getCartByIdUser(@Path("id") int id);

    @POST(pathCart +"/create")
    Call<Cart> createCart(@Body User user);

    @GET(pathCart +"/get-all-product/{id}")
    Call<List<ClothesItem>> getAllProductInCart(@Path("id") int id);

    @GET(pathCart + "/delete-item/{id}")
    Call<List<ClothesItem>> deleteItemInCart(@Path("id") int id);

    @GET(pathOrder + "/get-list-item/{id}")
    Call<List<Bill>> saveOrder(@Path("id") int id);

    @POST(pathOrder + "/editOrder")
    Call<Orderr> editOrder(@Body Orderr orderr);

    @POST("/notification")
    Call<Void> notification(@Body List<Bill> bills);






}


