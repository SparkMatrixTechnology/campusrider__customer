package com.campusrider.campusridercustomer.Grocery.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Grocery.Adapter.GroceryItemAdapter;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {
    RecyclerView homeVerticalRec;
    private int cartQuantity=0,count=0;
    ArrayList<GroceryItemModel> homeVerModelList;
    GroceryItemAdapter homeVerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        homeVerticalRec = findViewById(R.id.home_ver_rec);
        String name = getIntent().getStringExtra("name");
        int cat_id = getIntent().getIntExtra("id", 0);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homeVerModelList = new ArrayList<>();
        homeVerAdapter = new GroceryItemAdapter(getApplicationContext(), homeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        getItem(getApplicationContext(), cat_id);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        homeVerticalRec.setHasFixedSize(true);
        homeVerticalRec.setNestedScrollingEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        MenuItem menuItem=menu.findItem(R.id.cart);
        View actionView=menuItem.getActionView();
        TextView cart_badgeTextView=actionView.findViewById(R.id.count_badge);
        cart_badgeTextView.setText(String.valueOf(cartQuantity));

        Cart cart = TinyCartHelper.getCart();
        if(cart.isCartEmpty()){
            cartQuantity=0;
        }
        else {

        }
        cart_badgeTextView.setVisibility(cartQuantity==0 ? View.GONE : View.VISIBLE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.cart){
            startActivity(new Intent(this, GroceryCartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    public void getItem(Context context,int id){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_CAT_WISE_ITEM_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                homeVerModelList.clear();
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Grocery List");
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

                            homeVerModelList.add(item);
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