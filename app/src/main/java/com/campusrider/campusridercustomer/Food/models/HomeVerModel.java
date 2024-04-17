package com.campusrider.campusridercustomer.Food.models;

public class HomeVerModel {

    int vendor_id;
    String vendor_name,shop_image,shop_category,area,address,vendor_phone,
            delivery_time,opening_time,closing_time;
    int vendor_status,delivery_fee;
    String vendor_token;

    public HomeVerModel(int vendor_id, String vendor_name, String shop_image, String shop_category, String area, String address, String vendor_phone, String delivery_time, String opening_time, String closing_time, int vendor_status, int delivery_fee, String vendor_token) {
        this.vendor_id = vendor_id;
        this.vendor_name = vendor_name;
        this.shop_image = shop_image;
        this.shop_category = shop_category;
        this.area = area;
        this.address = address;
        this.vendor_phone = vendor_phone;
        this.delivery_time = delivery_time;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.vendor_status = vendor_status;
        this.delivery_fee = delivery_fee;
        this.vendor_token = vendor_token;
    }

    public int getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
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

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getVendor_token() {
        return vendor_token;
    }

    public void setVendor_token(String vendor_token) {
        this.vendor_token = vendor_token;
    }

    public String getShop_category() {
        return shop_category;
    }

    public void setShop_category(String shop_category) {
        this.shop_category = shop_category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVendor_phone() {
        return vendor_phone;
    }

    public void setVendor_phone(String vendor_phone) {
        this.vendor_phone = vendor_phone;
    }

    public int getVendor_status() {
        return vendor_status;
    }

    public void setVendor_status(int vendor_status) {
        this.vendor_status = vendor_status;
    }
}
