package com.campusrider.campusridercustomer.Food.models;

import java.util.ArrayList;

public class VariationModel {
    int variation_id,product_id;
    String variation_name,status;
    ArrayList<VariationDetailsModel> variationDetailsModels;

    public VariationModel(int variation_id, int product_id, String variation_name, String status) {
        this.variation_id = variation_id;
        this.product_id = product_id;
        this.variation_name = variation_name;
        this.status = status;
    }

    public int getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(int variation_id) {
        this.variation_id = variation_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getVariation_name() {
        return variation_name;
    }

    public void setVariation_name(String variation_name) {
        this.variation_name = variation_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<VariationDetailsModel> getVariationDetailsModels() {
        return variationDetailsModels;
    }

    public void setVariationDetailsModels(ArrayList<VariationDetailsModel> variationDetailsModels) {
        this.variationDetailsModels = variationDetailsModels;
    }
}
