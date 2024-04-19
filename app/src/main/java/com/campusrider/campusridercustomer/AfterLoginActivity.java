package com.campusrider.campusridercustomer;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.MainActivity;
import com.campusrider.campusridercustomer.Food.activity.SelectAddressActivity;

import com.campusrider.campusridercustomer.Grocery.GroceryMainActivity;
import com.campusrider.campusridercustomer.models.User;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AfterLoginActivity extends AppCompatActivity {
    ImageView food,grocery;
    SharedPrefManager sharedPrefManager;
    String token;
    int customer_id;
    String customer_name,address,customer_phone,customer_email,customer_password,student_id,id_card_front,customer_status,area=null,customer_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        food=findViewById(R.id.food_btn);
        grocery=findViewById(R.id.grocery_btn);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AfterLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AfterLoginActivity.this, GroceryMainActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void onStart() {
        super.onStart();
        if(!sharedPrefManager.isLoggedIn()){
            Intent intent=new Intent(AfterLoginActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            //device id
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            // Get new FCM registration token
                            token = task.getResult();

                            updateRiderToken(token);
                            System.out.println("token"+token);
                            Log.d(TAG, token);
                        }
                    });
        }
    }
    public void updateRiderToken(String token) {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.UPDATE_USER_TOKEN_URL+sharedPrefManager.getUser().getCustomer_id(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray jsonArray = mainObj.getJSONArray("user");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject users=jsonArray.getJSONObject(i);
                            customer_id = users.getInt("customer_id");
                            customer_name = users.getString("customer_name");
                            address = users.getString("address");
                            customer_phone = users.getString("customer_phone");
                            customer_email = users.getString("customer_email");
                            customer_password = users.getString("customer_password");
                            student_id = users.getString("student_id");
                            id_card_front = Constants.IMAGE_URL + users.getString("id_card_front");
                            customer_status = users.getString("customer_status");
                            customer_token=users.getString("customer_token");
                            sharedPrefManager.saveUser(new User(customer_id, customer_name, address, customer_phone,customer_email, customer_password, student_id,id_card_front,customer_status,area,customer_token));


                        }
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AfterLoginActivity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("customer_token",token);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(AfterLoginActivity.this);
        requestQueue.add(request);
    }
}