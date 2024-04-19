package com.campusrider.campusridercustomer.Food.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.adapters.HomeVerAdapter;
import com.campusrider.campusridercustomer.Food.models.HomeVerModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    String type;
    RecyclerView rec;
    ArrayList<HomeVerModel> homeVerModelList;
    HomeVerAdapter homeVerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        type=getIntent().getStringExtra("type");
        rec=findViewById(R.id.rec);

        homeVerModelList=new ArrayList<>();
        homeVerAdapter=new HomeVerAdapter(this,homeVerModelList);
        rec.setAdapter(homeVerAdapter);
        getVendor(getApplicationContext(),type);
        rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
        rec.setHasFixedSize(true);
        rec.setNestedScrollingEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void getVendor(Context context, String type){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_VENDORS_URL+type, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                homeVerModelList.clear();
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Vendors");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            HomeVerModel vendor=new HomeVerModel(
                                    object.getInt("vendor_id"),
                                    object.getString("vendor_name"),
                                    Constants.IMAGE_URL + object.getString("shop_image"),
                                    object.getString("shop_category"),
                                    object.getString("area"),
                                    object.getString("address"),
                                    object.getString("vendor_phone"),
                                    object.getString("delivery_time"),
                                    object.getString("opening_time"),
                                    object.getString("closing_time"),
                                    object.getInt("vendor_status"),
                                    object.getInt("delivery_fee"),
                                    object.getString("vendor_token")
                            );
                            homeVerModelList.add(vendor);
                        }
                        homeVerAdapter.notifyDataSetChanged();
                    }
                    else {

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);
    }
}