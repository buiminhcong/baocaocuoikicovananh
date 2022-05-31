package com.example.shop_cong;


import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shop_cong.entity.Cart;
import com.example.shop_cong.entity.User;
import com.example.shop_cong.fragment.FragmentCart;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;



public class CheckLogin extends AppCompatActivity {

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);
        apiInterface = ApiClient.getClient().create(APIInterface.class);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String  username = prefs.getString("username", "error");
        String  password = prefs.getString("password", "error");
        String  role = prefs.getString("role", "error");
        int checklogin = prefs.getInt("checkLogin", 0);


        Intent intent;
        if(checklogin == 1 && role.equals("kh")){
            intent = new Intent(CheckLogin.this, MainActivity.class);
            intent.putExtra("user-name", username);
            finish();
        }else if(checklogin == 1 && role.equals("nv")){
            intent = new Intent(CheckLogin.this, AdminActivity.class);
            intent.putExtra("user-name", username);
            finish();
        }
        else {
            intent = new Intent(CheckLogin.this, LoginActivity.class);
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("username", null);
            editor.putString("password", null);
            editor.putInt("checkLogin", 0);
            editor.putInt("ID_CART", 0);
            editor.putString("role", null);
            editor.apply();
        }
        startActivity(intent);

    }
}