package com.campusrider.campusridercustomer.Food.models;

public class VariationDetailsModel {
    int id,variation_id,price,product_id;
    String description;

    public VariationDetailsModel(int id, int variation_id, int price, int product_id, String description) {
        this.id = id;
        this.variation_id = variation_id;
        this.price = price;
        this.product_id = product_id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(int variation_id) {
        this.variation_id = variation_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
