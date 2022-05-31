package com.example.shop_cong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_cong.R;
import com.example.shop_cong.ViewDetailActivity;
import com.example.shop_cong.adapter.RecycleViewAdapter;
import com.example.shop_cong.entity.Clothes;
import com.example.shop_cong.network.APIInterface;
import com.example.shop_cong.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentShop extends Fragment implements RecycleViewAdapter.ItemListener {

    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private APIInterface apiInterface;
    private SearchView searchView;
    private String keySearch = "";
    private String keyNew = "";
    private Spinner sp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.fg_shop_recycleView);
        adapter = new RecycleViewAdapter();
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        searchView = view.findViewById(R.id.idSearch);
        sp = view.findViewById(R.id.spDanhMuc);
        sp.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spriner,
                getResources().getStringArray(R.array.danhmuc)));




        // Tao luong search
        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                apiInterface.findClothesByName(keyNew).enqueue(new Callback<List<Clothes>>() {
                    @Override
                    public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                        List<Clothes> list = response.body();
                        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        adapter.setList(list);
                    }

                    @Override
                    public void onFailure(Call<List<Clothes>> call, Throwable t) {

                    }
                });
            }
        };

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override

            public boolean onQueryTextChange(String newText) {

                keyNew = searchView.getQuery().toString();
                if (!keyNew.equals(keySearch)) {
                    countDownTimer.cancel();
                    countDownTimer.start();
                    keySearch = keyNew;
                }

                return true;
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cate = sp.getItemAtPosition(position).toString();

                if (cate.equals("All")) {
                    apiInterface.getListClothes().enqueue(new Callback<List<Clothes>>() {
                        @Override
                        public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                            List<Clothes> list1 = response.body();
                            GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                            adapter.setList(list1);

                        }

                        @Override
                        public void onFailure(Call<List<Clothes>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } else {
                    apiInterface.findClothesByCate(cate).enqueue(new Callback<List<Clothes>>() {
                        @Override
                        public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                            List<Clothes> list = response.body();
                            GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                            adapter.setList(list);

                        }

                        @Override
                        public void onFailure(Call<List<Clothes>> call, Throwable t) {

                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {

        Clothes clothes = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ViewDetailActivity.class);
        intent.putExtra("clothes", clothes);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        apiInterface.getListClothes().enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                List<Clothes> list1 = response.body();
                GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                adapter.setList(list1);

            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
