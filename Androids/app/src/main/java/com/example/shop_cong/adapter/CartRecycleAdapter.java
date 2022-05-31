package com.example.shop_cong.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop_cong.R;
import com.example.shop_cong.entity.Clothes;
import com.example.shop_cong.entity.ClothesItem;

import java.util.ArrayList;
import java.util.List;

public class CartRecycleAdapter extends RecyclerView.Adapter<CartRecycleAdapter.HomeViewHoler>{

    private List<ClothesItem> list;
    private CartRecycleAdapter.ItemListener itemListener;
    private CartRecycleAdapter.RemoveClick removeClick;


    public void setRemoveClick(CartRecycleAdapter.RemoveClick removeClick) {
        this.removeClick = removeClick;
    }

    public void setItemListener(CartRecycleAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public CartRecycleAdapter() {
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
    public CartRecycleAdapter.HomeViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartRecycleAdapter.HomeViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecycleAdapter.HomeViewHoler holder, int position) {
        ClothesItem item = getItem(position);


        holder.tenSp.setText(item.getName());
        holder.giaSp.setText(String.valueOf(item.getPrice()));
        holder.giaSp.setPaintFlags( holder.giaSp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.giaMoi.setText((item.getPrice() - item.getPrice()*item.getDiscount()/100)+"");
        Glide.with(holder.itemView.getContext()).load(item.getClothes().getImage()).into(holder.img_cart_item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tenSp, giaSp, giaMoi;
        private ImageView img_cart_item, img_remove_item;

        public HomeViewHoler(@NonNull View itemView) {
            super(itemView);
            tenSp= itemView.findViewById(R.id.txtTenSP_cart_item);
            giaSp= itemView.findViewById(R.id.txtGia_cart_item);
            img_cart_item = itemView.findViewById(R.id.img_cart_item);
            img_remove_item = itemView.findViewById(R.id.btnXoaSP_cart_item);
            itemView.setOnClickListener(this);
            img_remove_item.setOnClickListener(this);
            giaMoi = itemView.findViewById(R.id.txtgiamoi_cart_item);

        }

        @Override
        public void onClick(View v) {
            if( v.equals(img_remove_item) ){
                removeClick.onRemoveClick(v, getAdapterPosition());
            }else if(!v.equals(img_remove_item ) ){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }




    // OVeright click item
    public interface ItemListener{
        void onItemClick(View view, int position);
    }

    public interface RemoveClick{
        void onRemoveClick(View v, int position);
    }

}
