package com.example.shop_cong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop_cong.R;
import com.example.shop_cong.entity.ClothesItem;

import java.util.ArrayList;
import java.util.List;

public class OrderRecycleAdapter extends RecyclerView.Adapter<OrderRecycleAdapter.HomeViewHoler>{

    private List<ClothesItem> list;
    private OrderRecycleAdapter.ItemListener itemListener;



    public void setItemListener(OrderRecycleAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public OrderRecycleAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<ClothesItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public ClothesItem getItem(int po){
        return list.get(po);
    }

    @NonNull
    @Override
    public OrderRecycleAdapter.HomeViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderRecycleAdapter.HomeViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecycleAdapter.HomeViewHoler holder, int position) {
        ClothesItem item = getItem(position);

        holder.tenSp.setText(item.getName());
        holder.giaSp.setText(String.valueOf(item.getPrice()));
        Glide.with(holder.itemView.getContext()).load(item.getClothes().getImage()).into(holder.img_cart_item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tenSp, giaSp;
        private ImageView img_cart_item;

        public HomeViewHoler(@NonNull View itemView) {
            super(itemView);
            tenSp= itemView.findViewById(R.id.txtTenSP_cart_item_2);
            giaSp= itemView.findViewById(R.id.txtGia_cart_item_2);
            img_cart_item = itemView.findViewById(R.id.img_cart_item_2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v != null ){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }




    // OVeright click item
    public interface ItemListener{
        void onItemClick(View view, int position);
    }


}
