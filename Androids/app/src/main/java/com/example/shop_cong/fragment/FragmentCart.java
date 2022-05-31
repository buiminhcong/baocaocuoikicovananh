 package com.example.shop_cong.fragment;


 import static android.content.Context.MODE_PRIVATE;
 import static com.example.shop_cong.entity.Constants.MY_PREFS_NAME;

 import android.app.AlertDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.fragment.app.Fragment;

 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;


 import com.example.shop_cong.OrderActivity;
 import com.example.shop_cong.R;

 import com.example.shop_cong.adapter.CartRecycleAdapter;

 import com.example.shop_cong.entity.ClothesItem;

 import com.example.shop_cong.network.APIInterface;
 import com.example.shop_cong.network.ApiClient;


 import java.util.List;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;


 public class FragmentCart extends Fragment implements CartRecycleAdapter.ItemListener,
         CartRecycleAdapter.RemoveClick{

     private RecyclerView recyclerView;
     private CartRecycleAdapter adapter;
     private APIInterface apiInterface;
     private Button btnOrder;
     private int id_cart;
     private List<ClothesItem> list1;
     float totalPrice = 0;
     TextView txtGia;
     private int size = 0;
     TextView priceCart, giaMoi;


     @Nullable
     @Override
     public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_cart,container,  false);
     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);

         //init view
         recyclerView = view.findViewById(R.id.recyclerView_fragment_cart);
         adapter = new CartRecycleAdapter();
         apiInterface = ApiClient.getClient().create(APIInterface.class);
         btnOrder = view.findViewById(R.id.btnCheckout);
         priceCart = view.findViewById(R.id.txtPriceCart);

         adapter.setItemListener(this);
         adapter.setRemoveClick(this);

     }

     @Override
     public void onItemClick(View view, int position) {

         Toast.makeText(getContext(), "Đây là sp", Toast.LENGTH_SHORT).show();

     }

     @Override
     public void onRemoveClick(View v, int position) {
         totalPrice = 0;
         ClothesItem c = adapter.getItem(position);
         AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
         builder.setTitle("Thong Bao Xoa");
         builder.setMessage("Ban Co Chac Muon Xoa " + c.getName() + " khong?");
         builder.setIcon(R.drawable.ic_remove);
         builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 apiInterface.deleteItemInCart(c.getId()).enqueue(new Callback<List<ClothesItem>>() {
                     @Override
                     public void onResponse(Call<List<ClothesItem>> call, Response<List<ClothesItem>> response) {
                         List<ClothesItem> l = response.body();
                         adapter.setList(l);
                         for(ClothesItem clothesItem : l){
                             float gia =clothesItem.getPrice() - (clothesItem.getPrice()*clothesItem.getDiscount()/100);
                             totalPrice+=gia;
                         }
                         priceCart.setText(String.valueOf(totalPrice)+"đ");
                         Toast.makeText(getContext(), "Đã xóa sp "+ c.getName(), Toast.LENGTH_SHORT).show();


                     }

                     @Override
                     public void onFailure(Call<List<ClothesItem>> call, Throwable t) {

                     }
                 });
                 Toast.makeText(getContext(), "Dã xóa sản phẩm" + c.getName(), Toast.LENGTH_SHORT).show();
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
     public void onResume() {
         super.onResume();

         totalPrice = 0;

         SharedPreferences prefs = requireActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         id_cart = prefs.getInt("ID_CART", 0);

         apiInterface.getAllProductInCart(id_cart).enqueue(new Callback<List<ClothesItem>>() {
             @Override
             public void onResponse(Call<List<ClothesItem>> call, Response<List<ClothesItem>> response) {
                 list1 = response.body();
                 for(ClothesItem clothesItem : list1){
                     float gia =clothesItem.getPrice() - (clothesItem.getPrice()*clothesItem.getDiscount()/100);
//                     giaMoi.setText(String.valueOf(gia)+"");
//                     giaMoi.setText(gia+"");
                     totalPrice+=gia;
                 }
                 size = list1.size();
                 adapter.setList(list1);
                 priceCart.setText(String.valueOf(totalPrice)+"đ");

                 if(size==0){
                     btnOrder.setEnabled(false);
                 }else if(size>0) {
                     btnOrder.setEnabled(true);
                     btnOrder.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Intent intent = new Intent(getActivity(), OrderActivity.class);
                             startActivity(intent);
                         }
                     });
                 }



                 LinearLayoutManager manager = new LinearLayoutManager(getContext());
                 recyclerView.setLayoutManager(manager);
                 recyclerView.setAdapter(adapter);

             }

             @Override
             public void onFailure(Call<List<ClothesItem>> call, Throwable t) {

             }
         });

     }







 }
