package com.example.shop_cong.entity;


import java.io.Serializable;

public class Orderr implements Serializable {
    private int id;

    private String phoneOrder;
    private String email;
    private String address;
    private int isActive;
    private String firstNameUser;
    private String shipment;
    private String payment;
    private String date;
    private String totalPrice;


    private User user;

    private Cart cart;

    public Orderr(String phoneOrder, String email, String address, int isActive, String firstNameUser,
                  String shipment, String payment, String date,String totalPrice, User user, Cart cart) {
        this.phoneOrder = phoneOrder;
        this.email = email;
        this.address = address;
        this.isActive = isActive;
        this.firstNameUser = firstNameUser;
        this.shipment = shipment;
        this.payment = payment;
        this.date = date;
        this.user = user;
        this.cart = cart;
        this.totalPrice = totalPrice;
    }

    public Orderr() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneOrder() {
        return phoneOrder;
    }

    public void setPhoneOrder(String phoneOrder) {
        this.phoneOrder = phoneOrder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getFirstNameUser() {
        return firstNameUser;
    }

    public void setFirstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
