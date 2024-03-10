package com.campusrider.campusridercustomer.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.campusrider.campusridercustomer.models.User;


public class SharedPrefManager {

    private static final String SHARED_PREF_NAME="campusRiderBD_customer";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(User user){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt("customer_id",user.getCustomer_id());
        editor.putString("customer_name",user.getCustomer_name());
        editor.putString("username",user.getUsername());
        editor.putString("address",user.getAddress());
        editor.putString("customer_phone",user.getCustomer_phone());
        editor.putString("customer_email",user.getCustomer_email());
        editor.putString("customer_password",user.getCustomer_password());
        editor.putString("student_id",user.getStudent_id());
        editor.putString("id_card_front",user.getId_card_front());
        editor.putString("customer_status",user.getCustomer_status());
        editor.putString("area",user.getArea());
        editor.putString("customer_token",user.getCustomer_token());
        editor.putBoolean("logged",true);
        editor.apply();
    }

    public boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged",false);
    }

    public User getUser(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("customer_id",-1),
                sharedPreferences.getString("customer_name",null),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("address",null),
                sharedPreferences.getString("customer_phone",null),
                sharedPreferences.getString("customer_email",null),
                sharedPreferences.getString("customer_password",null),
                sharedPreferences.getString("student_id",null),
                sharedPreferences.getString("id_card_front",null),
                sharedPreferences.getString("customer_status",null),
                sharedPreferences.getString("area",null),
                sharedPreferences.getString("customer_token",null)
               );
    }
    public void logout(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
