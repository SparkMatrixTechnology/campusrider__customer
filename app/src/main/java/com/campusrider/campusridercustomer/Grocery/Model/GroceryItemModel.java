package com.campusrider.campusridercustomer.Grocery.Model;

import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;

public class GroceryItemModel implements Item, Serializable {
    int id,grocery_cat;
    String name,description,unit;
    int price;
    String image;
    int status,quantity;

    public GroceryItemModel(int id, int grocery_cat, String name, String description, String unit, int price, String image, int status) {
        this.id = id;
        this.grocery_cat = grocery_cat;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.price = price;
        this.image = image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    public BigDecimal getItemPrice() {
        return new BigDecimal(price);
    }
    @Override
    public String getItemName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
