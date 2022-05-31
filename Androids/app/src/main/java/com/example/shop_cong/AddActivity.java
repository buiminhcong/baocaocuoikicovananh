package com.example.shop_cong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shop_cong.entity.Clothes;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    private EditText anh, ten, gia, giamgia, size, mota, chatLieu, diaChi,nguongoc;
    private Spinner spMua, spDanhMuc;
    private Button btnThem;
    private APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();

        spMua.setAdapter(new ArrayAdapter<String>(AddActivity.this, R.layout.item_spriner,
                getResources().getStringArray(R.array.mua)));

        spDanhMuc.setAdapter(new ArrayAdapter<String>(AddActivity.this, R.layout.item_spriner,
                getResources().getStringArray(R.array.danhmuc)));

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String anh1 = anh.getText().toString();
                    String ten1 = ten.getText().toString();
                    String giaT = gia.getText().toString();
                    String giamGiaT = giamgia.getText().toString();
                    String size1 = size.getText().toString();
                    String mota1 = mota.getText().toString();
                    String chatlieu1 = chatLieu.getText().toString();
                    String thuonghieu1 = diaChi.getText().toString();
                    String nguongoc1 = nguongoc.getText().toString();
                    float gia1 = Float.parseFloat(giaT);
                    float giamGia1 = Float.parseFloat(giamGiaT);
                    spMua.setSelection(0);
                    spDanhMuc.setSelection(0);

                    if(!anh1.equals("") && !ten1.equals("") && !giaT.equals("") && !giamGiaT.equals("") &&
                            !size1.equals("") && !mota1.equals("") && !chatlieu1.equals("") &&
                            !thuonghieu1.equals("") && !nguongoc1.equals("")
                    ){
                        Clothes clothes1 = new Clothes(ten1, mota1, nguongoc1,giamGia1,
                                gia1, size1, chatlieu1,thuonghieu1,spMua.getSelectedItem().toString(),
                                1, spDanhMuc.getSelectedItem().toString(),anh1);
                        apiInterface.addClothes(clothes1).enqueue(new Callback<Clothes>() {
                            @Override
                            public void onResponse(Call<Clothes> call, Response<Clothes> response) {
                                Clothes ccc = response.body();
                                Toast.makeText(AddActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AddActivity.this, AdminActivity.class);
                                startActivity(i);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Clothes> call, Throwable t) {

                            }
                        });


                    }else {
                        Toast.makeText(AddActivity.this, "Xem lai thong tin", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    Toast.makeText(AddActivity.this, "Vui lòng kiểm tra lại các trường.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });




    }
    private void initView() {

        anh = findViewById(R.id.ed_anh_add1);
        ten = findViewById(R.id.ed_Ten_add1);
        gia = findViewById(R.id.ed_gia_add1);
        giamgia = findViewById(R.id.ed_giamGia_add1);
        size = findViewById(R.id.ed_size_add1);
        mota = findViewById(R.id.ed_mota_add1);
        chatLieu = findViewById(R.id.ed_chatlieu_add1);
        diaChi = findViewById(R.id.ed_diachi_add1);
        nguongoc = findViewById(R.id.ed_nguongoc_add1);
        spMua = findViewById(R.id.sp_mua_add1);
        spDanhMuc = findViewById(R.id.sp_danhmuc_add1);
        btnThem = findViewById(R.id.btnThem);
        apiInterface = ApiClient.getClient().create(APIInterface.class);

    }
}