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
import com.example.shop_cong.entity.Clothes;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.HomeViewHoler>{
    private List<Clothes> list;
    private AdminAdapter.ItemListener itemListener;
    private AdminAdapter.RemoveClick removeClick;
    private AdminAdapter.EditClick editClick;

    public void setEditClick(AdminAdapter.EditClick editClick) {
        this.editClick = editClick;
    }

    public void setRemoveClick(AdminAdapter.RemoveClick removeClick) {
        this.removeClick = removeClick;
    }

    public void setItemListener(AdminAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public AdminAdapter() {
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
    public AdminAdapter.HomeViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_admin,
                parent, false);
        return new AdminAdapter.HomeViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.HomeViewHoler holder, int position) {
        Clothes item = getItem(position);

        holder.tenSp.setText(item.getName());
        holder.giaSp.setText(String.valueOf(item.getPrice()));
        Glide.with(holder.itemView.getContext()).load(item.getImage()).into(holder.img_cart_item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tenSp, giaSp;
        private ImageView img_cart_item, img_remove_item, img_edit_item;

        public HomeViewHoler(@NonNull View itemView) {
            super(itemView);
            tenSp= itemView.findViewById(R.id.txt_TenSP_admin);
            giaSp= itemView.findViewById(R.id.txt_gia_admin);
            img_cart_item = itemView.findViewById(R.id.img_clotheAdmin);
            img_remove_item = itemView.findViewById(R.id.btn_xoa_admin);
            img_edit_item = itemView.findViewById(R.id.btn_edit_admin);

            itemView.setOnClickListener(this);
            img_remove_item.setOnClickListener(this);
            img_edit_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( v.equals(img_remove_item) ){
                removeClick.onRemoveClick(v, getAdapterPosition());
            }else if(v.equals(img_edit_item)){
                editClick.onEditClick(v, getAdapterPosition());
            }
           else if(!v.equals(img_remove_item) && !v.equals(img_edit_item) ){
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

    public interface EditClick{
        void onEditClick(View v, int position);
    }

}
