package com.campusrider.campusridercustomer.Grocery.Model;

public class GroceryCategoryModel {
    int id;
    String name,icon;
    int status;

    public GroceryCategoryModel(int id, String name, String icon, int status) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.status = status;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
