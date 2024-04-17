package com.campusrider.campusridercustomer.Food.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Food.adapters.VariationAdapter;
import com.campusrider.campusridercustomer.Food.models.HomeVerModel;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.Food.models.VariationDetailsModel;
import com.campusrider.campusridercustomer.Food.models.VariationModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.ActivityProductDetailsBinding;
import com.campusrider.campusridercustomer.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProductDetailsActivity extends AppCompatActivity {

    ActivityProductDetailsBinding binding;
    ArrayList<VariationModel> variationModels;
    VariationAdapter variationAdapter;
    int count=1,qty;
    ProductModel currentProduct;
    HomeVerModel vendorModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name=getIntent().getStringExtra("food_name");
        String image=getIntent().getStringExtra("food_name");
        String desc=getIntent().getStringExtra("food_desc");
        int vendor_id=getIntent().getIntExtra("vendor_id",0);
        int price=getIntent().getIntExtra("food_price",0);
        int product_id=getIntent().getIntExtra("id",0);
        Glide.with(this).load(image).into(binding.foodImage);
        binding.foodName.setText(name);
        binding.price.setText("TK "+price);
        binding.foodDesc.setText(desc);
        binding.quantity.setText(""+1);
        getProductDetail(product_id);
        getVendor(getApplicationContext(),vendor_id);
        getVariation(getApplicationContext(),product_id);
        qty=1;
        binding.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                qty=count;
                binding.quantity.setText(""+count);
                binding.price.setText("Tk "+(price*qty));
            }
        });
        binding.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count<=1){
                    count=1;
                    qty=count;
                }
                else {
                    count--;
                    qty=count;
                    binding.quantity.setText(""+count);
                    binding.price.setText("Tk "+(price*qty));
                }
            }
        });

        Cart cart= TinyCartHelper.getCart();
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.addItem(currentProduct,qty);
                Toast.makeText(ProductDetailsActivity.this,"Added",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ProductDetailsActivity.this, ShopActivity.class);
                intent.putExtra("shop_name",vendorModel.getVendor_name());
                intent.putExtra("shop_image",vendorModel.getShop_image());
                intent.putExtra("id",vendorModel.getVendor_id());
                intent.putExtra("vendor_token",vendorModel.getVendor_token());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void getVendor(Context context,int id){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_Single_VENDORS_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("err",response);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")) {
                        JSONArray product_array=object.getJSONArray("Vendor");
                        JSONObject vendor=product_array.getJSONObject(0);
                        vendorModel=new HomeVerModel(
                                vendor.getInt("vendor_id"),
                                vendor.getString("vendor_name"),
                                Constants.IMAGE_URL + vendor.getString("shop_image"),
                                vendor.getString("shop_category"),
                                vendor.getString("area"),
                                vendor.getString("address"),
                                vendor.getString("vendor_phone"),
                                vendor.getString("delivery_time"),
                                vendor.getString("opening_time"),
                                vendor.getString("closing_time"),
                                vendor.getInt("vendor_status"),
                                vendor.getInt("delivery_fee"),
                                vendor.getString("vendor_token")
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
    void getProductDetail(int id){
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_VENDOR_PRODUCTS_DETAIL_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")) {
                        JSONArray product_array=object.getJSONArray("Product");
                        JSONObject product=product_array.getJSONObject(0);
                        currentProduct = new ProductModel(
                                product.getInt("id"),
                                product.getString("product_name"),
                                product.getString("product_description"),
                                product.getInt("product_price"),
                                Constants.IMAGE_URL + product.getString("product_image"),
                                product.getInt("vendor_id")

                        );

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
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
    public void getVariation(Context context, int id){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_VARIATION_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray variation_Array=mainObj.getJSONArray("Variation List");
                        for(int i=0;i<variation_Array.length();i++) {
                            JSONObject variation = variation_Array.getJSONObject(i);
                            VariationModel variationModel=new VariationModel(
                                    variation.getInt("id"),
                                    variation.getInt("product_id"),
                                    variation.getString("variation_name"),
                                    variation.getString("status")
                            );
                            JSONArray variation_detail_Array = mainObj.getJSONArray("Variation Details List");
                            ArrayList<VariationDetailsModel> variationDetailsModels = new ArrayList<>();
                            for (int j = 0; j < variation_detail_Array.length(); j++) {
                                JSONObject variation_detail = variation_detail_Array.getJSONObject(j);
                                if(variation.getInt("id")==variation_detail.getInt("variation_id")) {
                                    Log.e("catres1", response);

                                    variationDetailsModels.add(new VariationDetailsModel(
                                            variation_detail.getInt("id"),
                                            variation_detail.getInt("variation_id"),
                                            variation_detail.getInt("price"),
                                            variation_detail.getInt("product_id"),
                                            variation_detail.getString("description")
                                    ));

                                }

                            }
                            variationModel.setVariationDetailsModels(variationDetailsModels);
                            variationModels.add(variationModel);
                        }
                        variationAdapter.notifyDataSetChanged();
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
