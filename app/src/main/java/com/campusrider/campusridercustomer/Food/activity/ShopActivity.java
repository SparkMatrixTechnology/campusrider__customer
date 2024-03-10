package com.campusrider.campusridercustomer.Food.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


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
import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Food.adapters.CategoryAdapter;
import com.campusrider.campusridercustomer.Food.models.CategoryModel;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.ActivityShopBinding;
import com.campusrider.campusridercustomer.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    ArrayList<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;
    ActivityShopBinding binding;
    private int cartQuantity=0,count=0;
    String vendor_token;
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name=getIntent().getStringExtra("shop_name");
        String image=getIntent().getStringExtra("shop_image");
        int vendor_id=getIntent().getIntExtra("id",0);
        vendor_token=getIntent().getStringExtra("vendor_token");
        Glide.with(this).load(image).into(binding.shopImage);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        categoryModels=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(getApplicationContext(),categoryModels);
        binding.catRec.setAdapter(categoryAdapter);
        getcat(getApplicationContext(),vendor_id);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.catRec.setLayoutManager(layoutManager);
        binding.catRec.addItemDecoration(itemDecoration);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
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
            Intent intent=new Intent(this,CartActivity.class);

            intent.putExtra("vendor_token",vendor_token);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void getcat(Context context,int id){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_PRODUCT_CAT_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray cat_Array=mainObj.getJSONArray("category");
                        for(int i=0;i<cat_Array.length();i++) {
                            JSONObject object = cat_Array.getJSONObject(i);
                            CategoryModel category = new CategoryModel(
                                    object.getInt("cat_id"),
                                    object.getInt("vendor_id"),
                                    object.getString("cat_name")
                            );
                            JSONArray vendor_Product_Array = mainObj.getJSONArray("Product");
                            ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
                            for (int j = 0; j < vendor_Product_Array.length(); j++) {
                                JSONObject object1 = vendor_Product_Array.getJSONObject(j);
                                if(object.getInt("cat_id")==object1.getInt("product_cat_id")) {
                                    Log.e("catres1", response);
                                    productModelArrayList.add(new ProductModel(
                                            object1.getInt("id"),
                                            object1.getString("product_name"),
                                            object1.getString("product_description"),
                                            object1.getInt("product_price"),
                                            Constants.IMAGE_URL + object1.getString("product_image"),
                                            object1.getInt("vendor_id")
                                    ));

                                }

                            }
                            category.setProductModelArrayList(productModelArrayList);
                            categoryModels.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();
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
    private ArrayList<ProductModel> getItem1(int id,int vid) {

        ArrayList<ProductModel> productModelArrayList=new ArrayList<>();

         productModelArrayList.add(new ProductModel(1,"mname","desc",222,"eee",vid));
         productModelArrayList.add(new ProductModel(1,"mnfhdfame","dgdgdesc",222,"eee",vid));


        return productModelArrayList;
    }
    private ArrayList<ProductModel> getItem(int id,int vid) {

        ArrayList<ProductModel> productModelArrayList=new ArrayList<>();

        RequestQueue queue= Volley.newRequestQueue(ShopActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_VENDOR_PRODUCTS_URL +vid +"&cat_id=" +id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err1",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendor_Product_Array=mainObj.getJSONArray("Product");
                        for(int i=0;i<vendor_Product_Array.length();i++){
                            JSONObject object=vendor_Product_Array.getJSONObject(i);
                            productModelArrayList.add(new ProductModel(
                                    object.getInt("id"),
                                    object.getString("product_name"),
                                    object.getString("product_description"),
                                    object.getInt("product_price"),
                                    Constants.IMAGE_URL+ object.getString("product_image"),
                                    object.getInt("vendor_id")
                            ));
                            Log.e("eproduct",object.getString("product_name"));
                        }
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
        return productModelArrayList;
    }

}