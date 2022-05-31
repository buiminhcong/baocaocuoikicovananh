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

public class EditActivity extends AppCompatActivity {

    private EditText anh, ten, gia, giamgia, size, mota, chatLieu, diaChi,nguongoc;
    private Spinner spMua, spDanhMuc;
    private Button btnEdit;
    private Clothes clothes;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();

        spMua.setAdapter(new ArrayAdapter<String>(EditActivity.this, R.layout.item_spriner,
                getResources().getStringArray(R.array.mua)));

        spDanhMuc.setAdapter(new ArrayAdapter<String>(EditActivity.this, R.layout.item_spriner,
                getResources().getStringArray(R.array.danhmuc)));

        Intent intent = getIntent();
        clothes = (Clothes) intent.getSerializableExtra("cl");
        anh.setText(clothes.getImage());
        ten.setText(clothes.getName());
        gia.setText(String.valueOf(clothes.getPrice()));
        giamgia.setText(String.valueOf(clothes.getDiscount()));
        size.setText(clothes.getSize());
        mota.setText(clothes.getDescription());
        chatLieu.setText(clothes.getMaterial());
        diaChi.setText(clothes.getBrand());
        nguongoc.setText(clothes.getCountryOfOrigin());

        int p = 0, g = 0;
        for(int i=0; i<spMua.getCount(); i++){
            if(spMua.getItemAtPosition(i).toString().equalsIgnoreCase(clothes.getSession())){
                p=i;
                break;
            }
        }
        spMua.setSelection(p);

        for(int i=0; i<spDanhMuc.getCount(); i++){
            if(spDanhMuc.getItemAtPosition(i).toString().equalsIgnoreCase(clothes.getCategory())){
                g=i;
                break;
            }
        }
        spDanhMuc.setSelection(g);

        btnEdit.setOnClickListener(new View.OnClickListener() {
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
                        if(!anh1.equals("") && !ten1.equals("") && !giaT.equals("") && !giamGiaT.equals("") &&
                                !size1.equals("") && !mota1.equals("") && !chatlieu1.equals("") &&
                                !thuonghieu1.equals("") && !nguongoc1.equals("")
                            ){
                            Clothes clothes1 = new Clothes(clothes.getId(),ten1, mota1, nguongoc1,giamGia1,
                                    gia1, size1, chatlieu1,thuonghieu1,spMua.getSelectedItem().toString(),
                                    1, spDanhMuc.getSelectedItem().toString(),anh1);

                            apiInterface.updateClothes(clothes1).enqueue(new Callback<Clothes>() {
                                @Override
                                public void onResponse(Call<Clothes> call, Response<Clothes> response) {
                                    Clothes c1 = response.body();
                                    Toast.makeText(EditActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(EditActivity.this, AdminActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<Clothes> call, Throwable t) {
                                    Log.d("fixloi", t.getMessage());
                                    Toast.makeText(EditActivity.this, "Sai", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(EditActivity.this, "Xem lai thong tin", Toast.LENGTH_SHORT).show();
                        }


                }catch (Exception e){
                    Toast.makeText(EditActivity.this, "Xem lai thong tin", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initView() {

        anh = findViewById(R.id.ed_anh_add);
        ten = findViewById(R.id.ed_Ten_add);
        gia = findViewById(R.id.ed_gia_add);
        giamgia = findViewById(R.id.ed_giamGia_add);
        size = findViewById(R.id.ed_size_add);
        mota = findViewById(R.id.ed_mota_add);
        chatLieu = findViewById(R.id.ed_chatlieu_add);
        diaChi = findViewById(R.id.ed_diachi_add);
        nguongoc = findViewById(R.id.ed_nguongoc_add);
        spMua = findViewById(R.id.sp_mua_add);
        spDanhMuc = findViewById(R.id.sp_danhmuc_add);
        btnEdit = findViewById(R.id.btnCapNhat);
        apiInterface = ApiClient.getClient().create(APIInterface.class);



    }
}