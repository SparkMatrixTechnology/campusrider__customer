package com.campusrider.campusridercustomer.Food.models;

public class VariationDetailsModel {
    int variation_id,price;
    String description;

    public VariationDetailsModel(int variation_id, int price, String description) {
        this.variation_id = variation_id;
        this.price = price;
        this.description = description;
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
