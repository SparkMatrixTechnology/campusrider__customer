package com.campusrider.campusridercustomer.Food.models;

import java.util.ArrayList;

public class CategoryModel {

    int id,vendor_id;
    String name;

    ArrayList<ProductModel> productModelArrayList;

    public CategoryModel(int id, int vendor_id, String name, ArrayList<ProductModel> productModelArrayList) {
        this.id = id;
        this.vendor_id = vendor_id;
        this.name = name;
        this.productModelArrayList = productModelArrayList;
    }

    public CategoryModel(int id, int vendor_id, String name) {
        this.id = id;
        this.vendor_id = vendor_id;
        this.name = name;
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

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public ArrayList<ProductModel> getProductModelArrayList() {
        return productModelArrayList;
    }

    public void setProductModelArrayList(ArrayList<ProductModel> productModelArrayList) {
        this.productModelArrayList = productModelArrayList;
    }
}
