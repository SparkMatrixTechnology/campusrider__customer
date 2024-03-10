package com.campusrider.campusridercustomer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.campusrider.campusridercustomer.databinding.FragmentComplainBinding;
import java.util.HashMap;
import java.util.Map;

public class ComplainFragment extends Fragment {

    FragmentComplainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComplainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMessage();
            }
        });



        return root;
    }
    public void submitMessage(){
        StringRequest request=new StringRequest(Request.Method.POST, "http://campusriderbd.com/Customer/query.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("successful")){
                    Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();
                    ComplainFragment fragment=new ComplainFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();

                }else{
                    Toast.makeText(getContext(),"Unsuccessful",Toast.LENGTH_SHORT).show();
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
                params.put("email",binding.emailText.getText().toString().trim());
                params.put("message",binding.messageText.getText().toString().trim());

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }
}