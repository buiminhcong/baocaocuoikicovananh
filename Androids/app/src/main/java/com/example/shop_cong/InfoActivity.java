package com.example.shop_cong;

import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_cong.entity.Cart;
import com.example.shop_cong.entity.User;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

    private EditText e_ten, e_diachi, e_email, e_sdt;
    private Button btnCapNhat;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        e_ten = findViewById(R.id.ed_ten);
        e_diachi = findViewById(R.id.ed_diachi_info);
        e_email = findViewById(R.id.ed_mail_info);
        e_sdt = findViewById(R.id.ed_sdt_info);
        btnCapNhat = findViewById(R.id.btnLuu_Info);
        apiInterface = ApiClient.getClient().create(APIInterface.class);

        Intent intent = getIntent();
        String username = intent.getStringExtra("un");
        String password = intent.getStringExtra("ps");


        btnCapNhat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String ten = e_ten.getText().toString();
                String diaChi = e_diachi.getText().toString();
                String email = e_email.getText().toString();
                String sdt = e_sdt.getText().toString();

                if (!ten.equals("") && !diaChi.equals("") && email.contains("@gmail.com") && !sdt.equals("")) {

                    User user = new User();
                    user.setFirstName(ten);
                    user.setLastName(diaChi);
                    user.setEmail(email);
                    user.setIsActive(1);
                    user.setUserName(username);
                    user.setPassword(password);
                    user.setPhone(sdt);
                    user.setRole("kh");

                    apiInterface.addUser(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User u = response.body();

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.putInt("checkLogin", 1);
                            editor.putString("role", "kh");
                            editor.apply();

                            apiInterface.createCart(u).enqueue(new Callback<Cart>() {
                                @Override
                                public void onResponse(Call<Cart> call, Response<Cart> response) {
                                    Cart cart = response.body();
                                }

                                @Override
                                public void onFailure(Call<Cart> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });

                            Intent intent1 = new Intent(InfoActivity.this, CheckLogin.class);
                            startActivity(intent1);
                            finish();

                            Toast.makeText(InfoActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

//
                } else {
                    Toast.makeText(InfoActivity.this, "Vui lòng kiểm tra kỹ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}