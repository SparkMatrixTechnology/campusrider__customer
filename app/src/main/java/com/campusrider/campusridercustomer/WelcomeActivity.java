package com.campusrider.campusridercustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.campusrider.campusridercustomer.activity.LoginActivity;
import com.campusrider.campusridercustomer.activity.RegistrationActivity;
import com.campusrider.campusridercustomer.session.SharedPrefManager;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
    }
    public void register(View view){
        startActivity(new Intent(WelcomeActivity.this, RegistrationActivity.class));
    }
    public void signin(View view){
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
    }


}