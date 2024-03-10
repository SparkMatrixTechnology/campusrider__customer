package com.campusrider.campusridercustomer.Food.models;

public class Location {
    String name;
    int delivery_fee;

    public Location(String name, int delivery_fee) {
        this.name = name;
        this.delivery_fee = delivery_fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
    }
}
