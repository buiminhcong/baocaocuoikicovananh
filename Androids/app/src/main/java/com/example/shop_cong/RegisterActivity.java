package com.example.shop_cong;

import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText ipUserName, ipPassword, ipConfirm;
    private TextView alreadyAccount;
    private Button btnDangKy;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();



//        if(userName.length()>=6 && password.length()>=6 &&)
        btnDangKy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userName = ipUserName.getText().toString();
                String password = ipPassword.getText().toString();
                String confirm = ipConfirm.getText().toString();

                if(userName.length()>=4 && password.length()>=4 && password.equals(confirm)){
                    apiInterface.getUserByName(userName).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            Toast.makeText(RegisterActivity.this, "Tài khoản đã có", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
//                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                            editor.putString("username", userName);
//                            editor.putString("password", password);
//                            editor.putInt("checkLogin", 1);
//                            editor.putString("role", "kh");
//                            editor.apply();
                            Intent intent = new Intent(RegisterActivity.this, InfoActivity.class);
                            intent.putExtra("un", userName);
                            intent.putExtra("ps", password);
                            startActivity(intent);
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "Kiểm tra lại thông tin!", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }

    private void initView() {

        ipUserName = findViewById(R.id.regis_uname);
        ipPassword = findViewById(R.id.resig_password);
        ipConfirm = findViewById(R.id.resgis_confirm);
        alreadyAccount = findViewById(R.id.alreadyHaveAcount);
        btnDangKy = findViewById(R.id.btnRegister);
        apiInterface = ApiClient.getClient().create(APIInterface.class);

    }
}