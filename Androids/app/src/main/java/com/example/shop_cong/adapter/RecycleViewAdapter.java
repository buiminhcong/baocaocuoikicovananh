package com.example.shop_cong.adapter;

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

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHoler> {

    private List<Clothes> list;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Clothes> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Clothes getItem(int po){
        return list.get(po);
    }

    @NonNull
    @Override
    public HomeViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new HomeViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHoler holder, int position) {
        Clothes item = getItem(position);

        holder.tenSp.setText(item.getName());
        holder.giamGia.setText( String.valueOf(item.getDiscount()) );
        holder.giaSp.setText(String.valueOf(item.getPrice()));
        Log.d("ptit", item.getName());
        Glide.with(holder.itemView.getContext()).load(item.getImage()).centerCrop().into(holder.imgShop);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tenSp, giamGia, giaSp;
        private ImageView imgShop;

        public HomeViewHoler(@NonNull View itemView) {
            super(itemView);
            tenSp= itemView.findViewById(R.id.txt_tensp_shop);
            giamGia= itemView.findViewById(R.id.txt_giamgia_shop);
            giaSp= itemView.findViewById(R.id.txt_giasp_shop);
            imgShop = itemView.findViewById(R.id.img_shop);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    // OVeright click item
    public interface ItemListener{
        void onItemClick(View view, int position);
    }

}
