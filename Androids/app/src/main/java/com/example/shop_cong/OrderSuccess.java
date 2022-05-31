package com.example.shop_cong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_cong.adapter.CartRecycleAdapter;
import com.example.shop_cong.adapter.OrderRecycleAdapter;
import com.example.shop_cong.entity.Bill;
import com.example.shop_cong.entity.ClothesItem;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import org.w3c.dom.Text;

import java.util.List;

public class OrderSuccess extends AppCompatActivity implements  OrderRecycleAdapter.ItemListener{

    private List<ClothesItem> clothesItemList;
    private List<Bill> billList;

    private RecyclerView recyclerView;
    private OrderRecycleAdapter adapter;
    private APIInterface apiInterface;
    private TextView txtMaDon, txtTongTien;
    private Button btnQuayLai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        recyclerView = findViewById(R.id.recycleViewOrder);
        adapter = new OrderRecycleAdapter();
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        txtTongTien = findViewById(R.id.txtTongThanhToan);
        txtMaDon = findViewById(R.id.txtMaDon);
        btnQuayLai = findViewById(R.id.btnQuayLai);

        Intent intent = getIntent();
        billList = (List<Bill>) intent.getSerializableExtra("bill");
        clothesItemList = (List<ClothesItem>) intent.getSerializableExtra("listClotheItem");
        float ttl = intent.getFloatExtra("gia", 0);
        Log.d("cong9c",billList.size()+" " + ttl + " " + clothesItemList.size() );

        txtTongTien.setText(String.valueOf(ttl));
        txtMaDon.setText(String.valueOf(billList.get(0).getOrderr().getId()));

        adapter.setList(clothesItemList);
        LinearLayoutManager manager = new LinearLayoutManager(OrderSuccess.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setItemListener(this);

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
    }
}