package com.campusrider.campusridercustomer.Grocery.Model;

public class GroceryOrderModel {
    int id,customer_id;
    String address;
    int cost,delivery_fee,total_price;
    String payment_type,payment_status,order_date,order_status;
    int rider_id;

    public GroceryOrderModel(int id, int customer_id, String address, int cost, int delivery_fee, int total_price, String payment_type, String payment_status, String order_date, String order_status, int rider_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.address = address;
        this.cost = cost;
        this.delivery_fee = delivery_fee;
        this.total_price = total_price;
        this.payment_type = payment_type;
        this.payment_status = payment_status;
        this.order_date = order_date;
        this.order_status = order_status;
        this.rider_id = rider_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

}
