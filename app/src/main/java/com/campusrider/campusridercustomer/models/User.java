package com.campusrider.campusridercustomer.models;

public class User {
     int customer_id;
     String customer_name,username,address,customer_phone,customer_email,customer_password,student_id,id_card_front,customer_status,area,customer_token;

    public User(int customer_id, String customer_name, String username, String address, String customer_phone, String customer_email, String customer_password, String student_id, String id_card_front, String customer_status, String area, String customer_token) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.username = username;
        this.address = address;
        this.customer_phone = customer_phone;
        this.customer_email = customer_email;
        this.customer_password = customer_password;
        this.student_id = student_id;
        this.id_card_front = id_card_front;
        this.customer_status = customer_status;
        this.area = area;
        this.customer_token = customer_token;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getId_card_front() {
        return id_card_front;
    }

    public void setId_card_front(String id_card_front) {
        this.id_card_front = id_card_front;
    }

    public String getCustomer_status() {
        return customer_status;
    }

    public void setCustomer_status(String customer_status) {
        this.customer_status = customer_status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCustomer_token() {
        return customer_token;
    }

    public void setCustomer_token(String customer_token) {
        this.customer_token = customer_token;
    }
}
