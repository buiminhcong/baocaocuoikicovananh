package com.example.shop_cong.entity;


import java.io.Serializable;

public class Cart implements Serializable {


    private int id;
    private String quantity;
    private float price;
    private int isActive;

    private User users;

    public Cart() {
    }

    public Cart(String quantity, float price, int isActive, User users) {
        this.quantity = quantity;
        this.price = price;
        this.isActive = isActive;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}
