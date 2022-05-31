package com.example.shop_cong.adapter;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shop_cong.fragment.FragmentCart;
import com.example.shop_cong.fragment.FragmentInfo;
import com.example.shop_cong.fragment.FragmentShop;


public class ViewPagerAdaper extends FragmentStatePagerAdapter {


    public ViewPagerAdaper(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // vi tri fragment

        switch (position){
            case 0 :
               return new FragmentShop();
            case 1 :
               return new FragmentCart();
            case 2:
                return new FragmentInfo();
            default:
               return new FragmentShop();
        }
    }

    @Override
    public int getCount() {
        // tra ve 3 fragment
        return 3;
    }
}
