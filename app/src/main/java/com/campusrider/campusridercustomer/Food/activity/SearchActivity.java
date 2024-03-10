package com.campusrider.campusridercustomer.Food.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.campusrider.campusridercustomer.Grocery.Adapter.GroceryItemAdapter;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rec;
    ArrayList<HomeVerModel> homeVerModelList;
    HomeVerAdapter homeVerAdapter;
    ArrayList<GroceryItemModel> groceryItemModels;
    GroceryItemAdapter groceryItemAdapter;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rec=findViewById(R.id.search_rec);

        String query = getIntent().getStringExtra("query");
        type = getIntent().getStringExtra("type");
        getSupportActionBar().setTitle(query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(type.equals("food")){
            homeVerModelList =new ArrayList<>();
            homeVerAdapter=new HomeVerAdapter(getApplicationContext(),homeVerModelList);

            getProducts(query);

            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
            rec.setHasFixedSize(true);
            rec.setNestedScrollingEnabled(false);
        }
        else if(type.equals("grocery")){
            groceryItemModels=new ArrayList<>();
            groceryItemAdapter=new GroceryItemAdapter(getApplicationContext(),groceryItemModels);
            rec.setAdapter(groceryItemAdapter);
            getItems(query);
            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
            rec.setHasFixedSize(true);
            rec.setNestedScrollingEnabled(false);

        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void getProducts(String query) {
        RequestQueue queue= Volley.newRequestQueue(SearchActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.SEARCH_VENDOR_URL+query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                homeVerModelList.clear();
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Store");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            HomeVerModel vendor=new HomeVerModel(
                                    object.getInt("vendor_id"),
                                    object.getString("vendor_name"),
                                    Constants.IMAGE_URL + object.getString("shop_image"),
                                    object.getString("delivery_time"),
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

    public void getItems(String query){
        RequestQueue queue= Volley.newRequestQueue(SearchActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.SEARCH_ITEM_URL+query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Item");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            GroceryItemModel item=new GroceryItemModel(
                                    object.getInt("id"),
                                    object.getInt("grocery_cat"),
                                    object.getString("name"),
                                    object.getString("description"),
                                    object.getString("unit"),
                                    object.getInt("price"),
                                    Constants.IMAGE_URL + object.getString("image"),
                                    object.getInt("status")
                            );

                            groceryItemModels.add(item);
                        }
                        groceryItemAdapter.notifyDataSetChanged();
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