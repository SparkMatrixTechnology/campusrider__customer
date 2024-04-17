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

public class ShopCategoryActivity extends AppCompatActivity {
    RecyclerView homeVerticalRec;

    ArrayList<HomeVerModel> homeVerModelList;
    HomeVerAdapter homeVerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_category);

        homeVerticalRec=findViewById(R.id.home_ver_rec);
        String name=getIntent().getStringExtra("name");
        int cat_id=getIntent().getIntExtra("id",0);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homeVerModelList=new ArrayList<>();
        homeVerAdapter=new HomeVerAdapter(getApplicationContext(),homeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        getVendor(getApplicationContext(),name);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
        homeVerticalRec.setHasFixedSize(true);
        homeVerticalRec.setNestedScrollingEnabled(false);
    }
    public void getVendor(Context context, String cat){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_CAT_WISE_SHOP_URL+cat, new Response.Listener<String>() {
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
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}