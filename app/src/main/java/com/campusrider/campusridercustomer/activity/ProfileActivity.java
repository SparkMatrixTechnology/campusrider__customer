package com.campusrider.campusridercustomer.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.models.User;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    EditText ename,eemail,ephone;
    Button bupdate;
    SharedPrefManager sharedPrefManager;
    int customer_id;
    String customer_name,username,address,customer_phone,customer_email,customer_password,student_id,id_card_front,customer_status,area,customer_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        int customer_id = sharedPrefManager.getUser().getCustomer_id();

        ename=findViewById(R.id.name);
        eemail=findViewById(R.id.email);
        ephone=findViewById(R.id.phone);
        bupdate=findViewById(R.id.update);

        ename.setText(sharedPrefManager.getUser().getCustomer_name());
        eemail.setText(sharedPrefManager.getUser().getCustomer_email());
        ephone.setText(sharedPrefManager.getUser().getCustomer_phone());
        bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_profile(customer_id);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void update_profile(int id){

        StringRequest request=new StringRequest(Request.Method.POST, Constants.UPDATE_USER_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("err",response);
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response);
                    String result=jsonObject.getString("status");
                    if(result.equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject users = jsonArray.getJSONObject(i);
                            customer_id = users.getInt("customer_id");
                            customer_name = users.getString("customer_name");
                            username = users.getString("username");
                            address = sharedPrefManager.getUser().getAddress();
                            customer_phone = users.getString("customer_phone");
                            customer_email = users.getString("customer_email");
                            customer_password = users.getString("customer_password");
                            student_id = users.getString("student_id");
                            area=sharedPrefManager.getUser().getArea();
                            id_card_front = Constants.IMAGE_URL + users.getString("id_card_front");
                            customer_status = users.getString("customer_status");
                            customer_token=users.getString("customer_token");
                            sharedPrefManager.saveUser(new User(customer_id, customer_name, username, address, customer_phone,customer_email, customer_password, student_id,id_card_front, customer_status,area,customer_token));
                            Toast.makeText(ProfileActivity.this, "done", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(ProfileActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("name",ename.getText().toString());
                params.put("email",eemail.getText().toString());
                params.put("phone",ephone.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(request);

    }
}