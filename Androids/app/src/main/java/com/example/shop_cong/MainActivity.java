package com.example.shop_cong;


import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_cong.adapter.ViewPagerAdaper;
import com.example.shop_cong.entity.ABC;
import com.example.shop_cong.entity.Cart;
import com.example.shop_cong.entity.ClothesItem;
import com.example.shop_cong.entity.User;
import com.example.shop_cong.fragment.FragmentCart;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // MainActivity Customer

    Button btnLogout;
    APIInterface apiInterface;
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private int cc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Intent intent = getIntent();
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString("username", "error");
        String password = prefs.getString("password", "error");
        int checklogin = prefs.getInt("checkLogin", 0);

        String userName="";
        if (intent.hasExtra("user")) {
            User u = (User) intent.getSerializableExtra("user");
            userName = u.getUserName();

        } else {
            userName = intent.getStringExtra("user-name");

        }
        apiInterface.getUserByName(userName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                apiInterface.getCartByIdUser(u.getId()).enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        Cart cart = response.body();
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("ID_CART", cart.getId());
                        editor.apply();
                        initViewPager();
//                        EventBus.getDefault().post(new ABC());

                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mShop:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mCart:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mPerson:
                        viewPager.setCurrentItem(2);
                        break;

                }
                return true;
            }
        });


    }

    private void initView() {

        apiInterface = ApiClient.getClient().create(APIInterface.class);
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);

    }

    private void initViewPager(){

        ViewPagerAdaper adaper = new ViewPagerAdaper(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(adaper);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // lua chon
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.mShop).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.mCart).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.mPerson).setChecked(true);
                        break;
                    default:
                        navigationView.getMenu().findItem(R.id.mShop).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        }


}