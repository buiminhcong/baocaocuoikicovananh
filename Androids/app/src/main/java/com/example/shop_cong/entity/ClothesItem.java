package com.example.shop_cong.entity;

import java.io.Serializable;

public class ClothesItem implements Serializable {

    private int id;

    private String name;
    private float discount;
    private float price;

    public ClothesItem(String name, float discount, float price, Clothes clothes, Cart cart) {
        this.name = name;
        this.discount = discount;
        this.price = price;
        this.clothes = clothes;
        this.cart = cart;
    }

    public ClothesItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public void setClothes(Clothes clothes) {
        this.clothes = clothes;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


    private Clothes clothes;


    private Cart cart;

}
