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
import com.campusrider.campusridercustomer.AfterLoginActivity;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.models.LoginResponse;
import com.campusrider.campusridercustomer.models.User;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText id,password;
    Button login;
    int customer_id;
    String customer_name,username,address,customer_phone,customer_email,customer_password,student_id,id_card_front,customer_status,area=null,customer_token;
    String strid,strpassword;
    String url= Constants.LOGIN_URL;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       sharedPrefManager=new SharedPrefManager(getApplicationContext());

        id=findViewById(R.id.contact_edit);
        password=findViewById(R.id.password_edit);
        login=findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginResponse loginResponse=null;

                if(id.getText().toString().isEmpty()){
                    id.setError("Field can't be empty");
                    return;
                }
                if(password.getText().toString().isEmpty()){
                    password.setError("Field can't be empty");
                    return;
                }
                else {
                    strid=id.getText().toString().trim();
                    strpassword=password.getText().toString().trim();
                    RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                                Log.e("err",response);
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String result=jsonObject.getString("status");
                                    if(result.equals("Login Success")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject users = jsonArray.getJSONObject(i);
                                            customer_id = users.getInt("customer_id");
                                            customer_name = users.getString("customer_name");
                                            username = users.getString("username");
                                            address = users.getString("address");
                                            customer_phone = users.getString("customer_phone");
                                            customer_email = users.getString("customer_email");
                                            customer_password = users.getString("customer_password");
                                            student_id = users.getString("student_id");
                                            id_card_front = Constants.IMAGE_URL + users.getString("id_card_front");
                                            customer_status = users.getString("customer_status");
                                            customer_token=users.getString("customer_token");
                                            sharedPrefManager.saveUser(new User(customer_id, customer_name, username, address, customer_phone,customer_email, customer_password, student_id,id_card_front,customer_status,area,customer_token));
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(LoginActivity.this, AfterLoginActivity.class);
                                            startActivity(intent);

                                        }
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this,"Login unsuccessful",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }

                                }
                                catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<String, String>();
                            params.put("student_id",strid);
                            params.put("password",strpassword);

                            return params;
                        }
                    };

                    requestQueue.add(request);
                }

            }
        });
    }
    public void reg(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

}