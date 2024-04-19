package com.campusrider.campusridercustomer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.MainActivity;
import com.campusrider.campusridercustomer.activity.ProfileActivity;
import com.campusrider.campusridercustomer.databinding.FragmentFoodDeliveryBinding;
import com.campusrider.campusridercustomer.databinding.FragmentProfileBinding;
import com.campusrider.campusridercustomer.models.User;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profileFragment extends Fragment {
    FragmentProfileBinding binding;
    SharedPrefManager sharedPrefManager;
    int customer_id;
    String customer_name,address,customer_phone,customer_email,customer_password,student_id,id_card_front,customer_status,area,customer_token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root=binding.getRoot();
        sharedPrefManager = new SharedPrefManager(getContext());
        int customer_id = sharedPrefManager.getUser().getCustomer_id();
        binding.name.setText(sharedPrefManager.getUser().getCustomer_name());
        binding.email.setText(sharedPrefManager.getUser().getCustomer_email());
        binding.phone.setText(sharedPrefManager.getUser().getCustomer_phone());
        binding.address.setText(sharedPrefManager.getUser().getAddress());
        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_profile(customer_id);
            }
        });

        return root;
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
                            address = sharedPrefManager.getUser().getAddress();
                            customer_phone = users.getString("customer_phone");
                            customer_email = users.getString("customer_email");
                            customer_password = users.getString("customer_password");
                            student_id = users.getString("student_id");
                            area=sharedPrefManager.getUser().getArea();
                            id_card_front = Constants.IMAGE_URL + users.getString("id_card_front");
                            customer_status = users.getString("customer_status");
                            customer_token=users.getString("customer_token");
                            sharedPrefManager.saveUser(new User(customer_id, customer_name,address, customer_phone,customer_email, customer_password, student_id,id_card_front, customer_status,area,customer_token));

                            Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                           // profileFragment fragment=new profileFragment();
                            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.pfragment,fragment).commit();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("name",binding.name.getText().toString());
                params.put("email",binding.email.getText().toString());
                params.put("phone",binding.phone.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}