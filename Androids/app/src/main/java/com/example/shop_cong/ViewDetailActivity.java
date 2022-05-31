package com.example.shop_cong;

import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shop_cong.entity.Cart;
import com.example.shop_cong.entity.Clothes;
import com.example.shop_cong.entity.ClothesItem;
import com.example.shop_cong.entity.Result;
import com.example.shop_cong.entity.User;
import com.example.shop_cong.fragment.FragmentCart;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDetailActivity extends AppCompatActivity {

    // Detail product
    private ImageView img;
    private TextView txtTenSp, txtGiamGia, txtTien, txtThuognHieu,
            txtXuatSu, txtSize, txtChatLieu, txtMoTa;
    private FloatingActionButton fabAddCart;
    private APIInterface apiInterface;
    private User user;
    //    private ClothesItem clothesItem;
    private Cart cart;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        initView();

        Intent intent = getIntent();

        Clothes clothes = (Clothes) intent.getSerializableExtra("clothes");
        Toast.makeText(this, clothes.getName(), Toast.LENGTH_SHORT).show();

        Log.d("anh", clothes.getImage());
        String url = clothes.getImage();
        Glide.with(ViewDetailActivity.this).load(url).centerCrop()
                .into(img);
        txtTenSp.setText(clothes.getName());
        txtGiamGia.setText(String.valueOf(clothes.getDiscount()));
        txtTien.setText(String.valueOf(clothes.getPrice()));
        txtThuognHieu.setText(clothes.getBrand());
        txtXuatSu.setText(clothes.getCountryOfOrigin());
        txtSize.setText(clothes.getSize());
        txtChatLieu.setText(clothes.getMaterial());
        txtMoTa.setText(clothes.getDescription());

        //check session
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString("username", "error");

        Log.d("session", username);

        //get user by name
        apiInterface.getUserByName(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();

                //check cart by user id

                apiInterface.checkStatusCart(user.getId()).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        result = response.body();

                        // get cart by id user

                        if (result.getKq().equals("check-true")) {

                            apiInterface.getCartByIdUser(user.getId()).enqueue(new Callback<Cart>() {
                                @Override
                                public void onResponse(Call<Cart> call, Response<Cart> response) {
                                    cart = response.body();

                                    ClothesItem clothesItem =
                                            new ClothesItem(clothes.getName(),
                                                    clothes.getDiscount(), clothes.getPrice(), clothes, cart);

                                    // Them vao gio hang
                                    fabAddCart.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            apiInterface.addToCart(clothesItem).enqueue(new Callback<ClothesItem>() {
                                                @Override
                                                public void onResponse(Call<ClothesItem> call, Response<ClothesItem> response) {
                                                    ClothesItem c = response.body();
                                                    Toast.makeText(ViewDetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                                                    Intent i = new Intent(ViewDetailActivity.this, MainActivity.class);
                                                    finish();
                                                }

                                                @Override
                                                public void onFailure(Call<ClothesItem> call, Throwable t) {

                                                }
                                            });

                                        }

                                    });
                                }

                                @Override
                                public void onFailure(Call<Cart> call, Throwable t) {

                                }
                            });
                        } else {

                            // create new cart
                            apiInterface.createCart(user).enqueue(new Callback<Cart>() {
                                @Override
                                public void onResponse(Call<Cart> call, Response<Cart> response) {
                                    cart = response.body();

                                    ClothesItem clothesItem =
                                            new ClothesItem(clothes.getName(),
                                                    clothes.getDiscount(), clothes.getPrice(), clothes, cart);

                                    // Them vao gio hang
                                    fabAddCart.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            apiInterface.addToCart(clothesItem).enqueue(new Callback<ClothesItem>() {
                                                @Override
                                                public void onResponse(Call<ClothesItem> call, Response<ClothesItem> response) {
                                                    ClothesItem c = response.body();
                                                    Toast.makeText(ViewDetailActivity.this, "Thêm thành công "
                                                            + c.getName(), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onFailure(Call<ClothesItem> call, Throwable t) {

                                                }
                                            });

                                        }

                                    });

                                }

                                @Override
                                public void onFailure(Call<Cart> call, Throwable t) {

                                }
                            });

                        }


                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });

                //

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


    }

    private void initView() {

        apiInterface = ApiClient.getClient().create(APIInterface.class);
        img = findViewById(R.id.img_detail_product);
        txtTenSp = findViewById(R.id.txtTensp_detail_product);
        txtGiamGia = findViewById(R.id.txtGiamGia_detail_product);
        txtTien = findViewById(R.id.txtGia_detail_product);
        txtThuognHieu = findViewById(R.id.txtThuongHieu_detail);
        txtXuatSu = findViewById(R.id.txtXuatsu_detail);
        txtSize = findViewById(R.id.txtSize_detail);
        txtChatLieu = findViewById(R.id.txtChatLieu_detail);
        txtMoTa = findViewById(R.id.txtMoTa_detail);
        fabAddCart = findViewById(R.id.fabAddCart);


    }


}