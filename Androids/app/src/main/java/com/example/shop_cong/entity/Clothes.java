package com.example.shop_cong.entity;


import java.io.Serializable;

public class Clothes implements Serializable {


    private int id;

    private String name;
    private String description;
    private String countryOfOrigin;
    private float discount;
    private float price;
    private String size;
    private String material;
    private String brand;
    private String session;
    private int isActive;
    private String category;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Clothes(String name, String description, String countryOfOrigin,
                   float discount, float price, String size, String material,
                   String brand, String session, int isActive, String category, String image) {
        this.name = name;
        this.description = description;
        this.countryOfOrigin = countryOfOrigin;
        this.discount = discount;
        this.price = price;
        this.size = size;
        this.material = material;
        this.brand = brand;
        this.session = session;
        this.isActive = isActive;
        this.category = category;
        this.image = image;
    }

    public Clothes(int id, String name, String description, String countryOfOrigin, float discount, float price, String size, String material, String brand, String session, int isActive, String category, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.countryOfOrigin = countryOfOrigin;
        this.discount = discount;
        this.price = price;
        this.size = size;
        this.material = material;
        this.brand = brand;
        this.session = session;
        this.isActive = isActive;
        this.category = category;
        this.image = image;
    }

    public Clothes() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
