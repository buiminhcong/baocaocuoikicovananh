package com.example.shop_cong;

import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_cong.entity.Bill;
import com.example.shop_cong.entity.ClothesItem;
import com.example.shop_cong.entity.Orderr;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    private int id_cart;
    private EditText ext_Dia_Chi;
    private EditText ext_Email;
    private EditText ext_STD;
    private Button btnOrder;
    private APIInterface apiInterface;
    private TextView txt_gia_order;
    private float price1;
    private float totalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ext_Dia_Chi = findViewById(R.id.edt_DiaChi);
        ext_Email = findViewById(R.id.edt_email);
        ext_STD = findViewById(R.id.edt_SDT);
        btnOrder = findViewById(R.id.btnOrder);
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        txt_gia_order = findViewById(R.id.txt_gia_order);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        id_cart = prefs.getInt("ID_CART", 0);

        apiInterface.getAllProductInCart(id_cart).enqueue(new Callback<List<ClothesItem>>() {
            @Override
            public void onResponse(Call<List<ClothesItem>> call, Response<List<ClothesItem>> response) {
                List<ClothesItem> list1 = response.body();

                price1 = 0;
                // tinh tien
                for(ClothesItem clothesItem : list1){
                    ext_Dia_Chi.setText(clothesItem.getCart().getUsers().getLastName());
                    ext_Email.setText(clothesItem.getCart().getUsers().getEmail());
                    ext_STD.setText(clothesItem.getCart().getUsers().getPhone());
                    price1 = clothesItem.getPrice() - (clothesItem.getPrice() * clothesItem.getDiscount()/100);
                    totalPrice+=price1;
                }
//  k dc oder
                if(totalPrice==0.0){
                    btnOrder.setEnabled(false);
                }
                else if(totalPrice <= 300.0){
                    totalPrice+=30.0;
                    btnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            apiInterface.saveOrder(id_cart).enqueue(new Callback<List<Bill>>() {
                                @Override
                                public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                                    List<Bill> list = response.body();
                                    for (Bill l : list){
                                        Orderr o = l.getOrderr();
                                        String dc = ext_Dia_Chi.getText().toString().trim();
                                        String em = ext_Email.getText().toString().trim();
                                        String std = ext_STD.getText().toString  ().trim();

                                        if(dc.equals("") || em.equals("") || std.equals("")){
                                            Toast.makeText(OrderActivity.this, "Vui lòng nhập đầy đủ trường", Toast.LENGTH_SHORT).show();
                                        }else {
                                            o.setAddress(ext_Dia_Chi.getText().toString());
                                            o.setEmail(ext_Email.getText().toString());
                                            o.setPhoneOrder(ext_STD.getText().toString  ());
                                            o.setTotalPrice(String.valueOf(totalPrice));
                                            // Luu orrer
                                            apiInterface.editOrder(o).enqueue(new Callback<Orderr>() {
                                                @Override
                                                public void onResponse(Call<Orderr> call, Response<Orderr> response) {
                                                    Orderr o = response.body();
                                                    Toast.makeText(OrderActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(OrderActivity.this, OrderSuccess.class);
                                                    i.putExtra("listClotheItem", (Serializable) list1);
                                                    i.putExtra("bill", (Serializable) list);
                                                    i.putExtra("gia", totalPrice);
                                                    startActivity(i);
                                                    finish();
                                                }

                                                @Override
                                                public void onFailure(Call<Orderr> call, Throwable t) {

                                                }
                                            });
                                        }

                                    }

                                    // notification
                                    apiInterface.notification(list).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Toast.makeText(OrderActivity.this, "Send email!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });

                                }

                                @Override
                                public void onFailure(Call<List<Bill>> call, Throwable t) {

                                }
                            });
                        }

                    });
                }else {
                    btnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            apiInterface.saveOrder(id_cart).enqueue(new Callback<List<Bill>>() {
                                @Override
                                public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                                    List<Bill> list = response.body();
                                    for (Bill l : list){
                                        Orderr o = l.getOrderr();
                                        String dc = ext_Dia_Chi.getText().toString().trim();
                                        String em = ext_Email.getText().toString().trim();
                                        String std = ext_STD.getText().toString  ().trim();

                                        if(dc.equals("") || em.equals("") || std.equals("")){
                                            Toast.makeText(OrderActivity.this, "Vui lòng nhập đầy đủ trường", Toast.LENGTH_SHORT).show();
                                        }else {
                                            o.setAddress(ext_Dia_Chi.getText().toString());
                                            o.setEmail(ext_Email.getText().toString());
                                            o.setPhoneOrder(ext_STD.getText().toString  ());
                                            o.setTotalPrice(String.valueOf(totalPrice));
                                            apiInterface.editOrder(o).enqueue(new Callback<Orderr>() {
                                                @Override
                                                public void onResponse(Call<Orderr> call, Response<Orderr> response) {
                                                    Orderr o = response.body();
                                                    Toast.makeText(OrderActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(OrderActivity.this, OrderSuccess.class);
                                                    i.putExtra("listClotheItem", (Serializable) list1);
                                                    i.putExtra("bill", (Serializable) list);
                                                    i.putExtra("gia", totalPrice);
                                                    startActivity(i);
                                                    finish();
                                                }

                                                @Override
                                                public void onFailure(Call<Orderr> call, Throwable t) {

                                                }
                                            });
                                        }

                                    }

                                    // notification
                                    apiInterface.notification(list).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Toast.makeText(OrderActivity.this, "Send email!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<List<Bill>> call, Throwable t) {

                                }
                            });
                        }





                    });
                }

                txt_gia_order.setText("Tổng thanh toán: "+String.valueOf(totalPrice));

            }
            @Override
            public void onFailure(Call<List<ClothesItem>> call, Throwable t) {

            }
        });


    }
}