package com.campusrider.campusridercustomer.Food.models;

public class HomeVerModel {

    int vendor_id;
    String vendor_name,shop_image;
    String delivery_time;
String vendor_token;

    public HomeVerModel(int vendor_id, String vendor_name, String shop_image, String delivery_time, String vendor_token) {
        this.vendor_id = vendor_id;
        this.vendor_name = vendor_name;
        this.shop_image = shop_image;
        this.delivery_time = delivery_time;
        this.vendor_token = vendor_token;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getShop_image() {
        return shop_image;
    }

    public void setShop_image(String shop_image) {
        this.shop_image = shop_image;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getVendor_token() {
        return vendor_token;
    }

    public void setVendor_token(String vendor_token) {
        this.vendor_token = vendor_token;
    }
}
