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

import com.example.shop_cong.entity.User;
import com.example.shop_cong.entity.UserDTO;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    EditText inputUserName, inputPassword;
    TextView creatNewAcount;
    Button btnLogin;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        creatNewAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userName = inputUserName.getText().toString().trim();
                String password = inputPassword.getText().toString();
                UserDTO userDTO = new UserDTO(userName, password);

                apiInterface.checkLogin(userDTO).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();

                        System.out.printf(user.getRole());

                        if (user.getRole().equals("kh")) {
                            apiInterface.getUserByName(userName).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    User user = response.body();

                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("username", user.getUserName());
                                    editor.putString("password", user.getPassword());
                                    editor.putInt("checkLogin", 1);
                                    editor.putString("role", "kh");
                                    editor.apply();
//                                    editor.commit();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user", user);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        if (user.getRole().equals("nv")) {
                            apiInterface.getUserByName(userName).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    User user = response.body();

                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("username", user.getUserName());
                                    editor.putString("password", user.getPassword());
                                    editor.putInt("checkLogin", 1);
                                    editor.putString("role", "nv");
                                    editor.apply();

                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    intent.putExtra("user", user);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, "UserName and password incorrect!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("username", null);
                        editor.putString("password", null);
                        editor.putInt("checkLogin", 0);
                        editor.putInt("ID_CART", 0);
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "UserName and password incorrect!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


    private void initView() {

        inputUserName = findViewById(R.id.inputUserName);
        inputPassword = findViewById(R.id.resig_password);
        btnLogin = findViewById(R.id.btnLogin);
        creatNewAcount = findViewById(R.id.creatNewAcount);
        // khoi tao api
        apiInterface = ApiClient.getClient().create(APIInterface.class);

    }
}