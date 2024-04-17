package com.campusrider.campusridercustomer.Food.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
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
import com.campusrider.campusridercustomer.Food.adapters.CartAdapter;
import com.campusrider.campusridercustomer.Food.models.CategoryModel;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.Food.models.VariationDetailsModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.ActivityCartBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    CartAdapter adapter;
    ArrayList<ProductModel> productModels;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        String vendor_token=getIntent().getStringExtra("vendor_token");
        System.out.println("vendor_token"+ vendor_token);
        productModels=new ArrayList<>();
        Cart cart = TinyCartHelper.getCart();
        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();
        // Fetch and update variation details for each product
        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            if (item instanceof ProductModel) {
                ProductModel product = (ProductModel) item;
                product.setQuantity(quantity);
                productModels.add(product);
            }
        }

        // Initialize the adapter and set it to the RecyclerView
        adapter = new CartAdapter(this, productModels, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText("TK " + cart.getTotalPrice());
            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);
        binding.subtotal.setText("TK "+ cart.getTotalPrice());
        mergeCartItems();

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CartActivity.this,CheckoutActivity.class);
                intent.putExtra("vendor_token",vendor_token);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
     public void fetchVariationDetails(ProductModel product,CartAdapter.CartViewHolder holder) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://campusriderbd.com/Customer/customer/view_variation_cost.php?product_id=" + product.getId() + "&customer_id=" + sharedPrefManager.getUser().getCustomer_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("errr",response);
                            JSONArray variationsArray = new JSONArray(response);

                            // Parse variation details and update the product model
                            ArrayList<VariationDetailsModel> variationDetails = new ArrayList<>();
                            for (int i = 0; i < variationsArray.length(); i++) {
                                JSONObject variationObj = variationsArray.getJSONObject(i);
                                VariationDetailsModel variationDetail = new VariationDetailsModel(
                                        variationObj.getInt("id"),
                                        variationObj.getInt("variation_id"),
                                        variationObj.getInt("price"),
                                        variationObj.getInt("product_id"),
                                        variationObj.getString("description")
                                );
                                variationDetails.add(variationDetail);
                            }
                            product.setVariations(variationDetails);
                            adapter.setVariationsAdapter(holder, variationDetails);

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FetchVariationDetails", "Error fetching variation details: " + error.getMessage());
                        // Handle error
                    }
                });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

    public void mergeCartItems() {
        Cart cart = TinyCartHelper.getCart();
        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();

        // Temporary map to store merged products with quantities
        Map<Integer, ProductModel> mergedProductsMap = new HashMap<>();

        // Merge cart items
        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            ProductModel product = (ProductModel) entry.getKey();
            product.setQuantity(entry.getValue());

            int productId = product.getId();

            if (mergedProductsMap.containsKey(productId)) {
                mergedProductsMap.get(productId).setQuantity(mergedProductsMap.get(productId).getQuantity() + product.getQuantity());
            } else {
                mergedProductsMap.put(productId, product);
            }
        }

        // Update productModels list
        productModels.clear();
        productModels.addAll(mergedProductsMap.values());

        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}