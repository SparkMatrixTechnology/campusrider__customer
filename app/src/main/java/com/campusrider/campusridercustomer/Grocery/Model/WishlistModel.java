package com.campusrider.campusridercustomer.Grocery.Model;

import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class WishlistModel implements Item, Serializable {
    int id,customer_id,product_id,quantity,price,cost,grocery_cat;
    String name,description,unit;
    String image;

    public WishlistModel(int id, int customer_id, int product_id, int quantity, int price, int cost, int grocery_cat, String name, String description, String unit, String image) {
        this.id = id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.cost = cost;
        this.grocery_cat = grocery_cat;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.image = image;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGrocery_cat() {
        return grocery_cat;
    }

    public void setGrocery_cat(int grocery_cat) {
        this.grocery_cat = grocery_cat;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public BigDecimal getItemPrice() {
        return new BigDecimal(price);
    }
    @Override
    public String getItemName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
