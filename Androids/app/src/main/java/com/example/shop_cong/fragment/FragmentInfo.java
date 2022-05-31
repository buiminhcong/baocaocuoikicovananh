package com.example.shop_cong.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shop_cong.LoginActivity;
import com.example.shop_cong.R;
import com.example.shop_cong.entity.Constants;
import com.example.shop_cong.entity.User;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentInfo extends Fragment {

    private TextView txtTaiKhoan, txtMatKhau, txtSDT, txtEmail, txtDiaChi;
    private Button btnDangXuat;
    private APIInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTaiKhoan = view.findViewById(R.id.txtTaiKhoan);
        txtMatKhau = view.findViewById(R.id.txtMatKhau);
        txtSDT = view.findViewById(R.id.txtSoDT);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtDiaChi = view.findViewById(R.id.txtDiaChi);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        apiInterface = ApiClient.getClient().create(APIInterface.class);

        SharedPreferences prefs = requireActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString("username", "error");

        apiInterface.getUserByName(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();
                txtTaiKhoan.setText(username);
                txtMatKhau.setText(u.getPassword());
                txtSDT.setText(u.getPhone());
                txtEmail.setText(u.getEmail());
                txtDiaChi.setText(u.getLastName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_SINGLE_TOP);

                SharedPreferences.Editor editor = requireActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("username", null);
                editor.putString("password", null);
                editor.putString("role", null);
                editor.putInt("checkLogin", 0);
                editor.putInt("ID_CART", 0);
                editor.apply();

                startActivity(intent);
                getActivity().finish();

            }
        });



    }
}
