package com.example.shop_cong.entity;


import java.io.Serializable;

public class Bill implements Serializable {


    private int id;
    private float price;
    private String nameClothes;
    private int isActive;


    private ClothesItem clothesItem;


    private Orderr orderr;

    public Bill() {
    }

    public Bill(float price, String nameClothes, int isActive, ClothesItem clothesItem, Orderr orderr) {
        this.price = price;
        this.nameClothes = nameClothes;
        this.isActive = isActive;
        this.clothesItem = clothesItem;
        this.orderr = orderr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getNameClothes() {
        return nameClothes;
    }

    public void setNameClothes(String nameClothes) {
        this.nameClothes = nameClothes;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public ClothesItem getClothesItem() {
        return clothesItem;
    }

    public void setClothesItem(ClothesItem clothesItem) {
        this.clothesItem = clothesItem;
    }

    public Orderr getOrderr() {
        return orderr;
    }

    public void setOrderr(Orderr orderr) {
        this.orderr = orderr;
    }
}
