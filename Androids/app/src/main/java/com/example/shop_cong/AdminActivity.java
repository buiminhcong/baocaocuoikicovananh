package com.example.shop_cong;

import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shop_cong.adapter.AdminAdapter;
import com.example.shop_cong.adapter.CartRecycleAdapter;
import com.example.shop_cong.entity.Clothes;
import com.example.shop_cong.entity.ClothesItem;
import com.example.shop_cong.entity.Result;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements AdminAdapter.ItemListener,
        AdminAdapter.RemoveClick, AdminAdapter.EditClick{

    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    private APIInterface apiInterface;
    private Button btnLogin, btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recyclerViewAdmin);
        adapter = new AdminAdapter();
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        btnLogin = findViewById(R.id.btnDangXuatAdmin);
        btnAdd = findViewById(R.id.btnAdd_Admin);

        apiInterface.getListClothes().enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                List<Clothes> list = response.body();

                LinearLayoutManager manager = new LinearLayoutManager(AdminActivity.this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                adapter.setList(list);
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("username", null);
                editor.putString("password", null);
                editor.putString("role", null);
                editor.putInt("checkLogin", 0);
                editor.putInt("ID_CART", 0);
                editor.apply();
                startActivity(intent);
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        adapter.setItemListener(this);
        adapter.setRemoveClick(this);
        adapter.setEditClick(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "SP", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveClick(View v, int position) {

        Clothes c2 = adapter.getItem(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Thong Bao Xoa");
        builder.setMessage("Ban Co Chac Muon Xoa " + c2.getName() + " khong?");
        builder.setIcon(R.drawable.ic_remove);
        builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                apiInterface.deleteClothes(c2.getId()).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Result r = response.body();
                        Toast.makeText(AdminActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
                apiInterface.getListClothes().enqueue(new Callback<List<Clothes>>() {
                    @Override
                    public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                        List<Clothes> list = response.body();

                        LinearLayoutManager manager = new LinearLayoutManager(AdminActivity.this);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        adapter.setList(list);
                    }

                    @Override
                    public void onFailure(Call<List<Clothes>> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onEditClick(View v, int position) {
        Clothes c = adapter.getItem(position);
        Intent intent = new Intent(AdminActivity.this, EditActivity.class);
        intent.putExtra("cl", c);
        startActivity(intent);
    }

}