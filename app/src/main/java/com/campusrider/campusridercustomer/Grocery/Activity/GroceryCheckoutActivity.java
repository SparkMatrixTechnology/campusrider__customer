package com.campusrider.campusridercustomer.Grocery.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Grocery.Adapter.GroceryCartAdapter;
import com.campusrider.campusridercustomer.Grocery.GroceryMainActivity;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.databinding.ActivityGroceryCheckoutBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GroceryCheckoutActivity extends AppCompatActivity {
    ActivityGroceryCheckoutBinding binding;
    GroceryCartAdapter adapter;
    SharedPrefManager sharedPrefManager;
    //Cart cart;
    int total_price=0,delivery_fee=60,subtotal;
    ArrayList<GroceryItemModel> productModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityGroceryCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        binding.nameText.setText(sharedPrefManager.getUser().getCustomer_name());
        binding.phoneText.setText(sharedPrefManager.getUser().getCustomer_phone());
        binding.addressBox.setText(sharedPrefManager.getUser().getAddress());

        productModels=new ArrayList<>();
        Cart cart = TinyCartHelper.getCart();

        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();

        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            if (item instanceof GroceryItemModel) {
                GroceryItemModel product = (GroceryItemModel) item;
                product.setQuantity(quantity);
                productModels.add(product);
            }
        }
        adapter=new GroceryCartAdapter(this, productModels, new GroceryCartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText("TK "+ cart.getTotalPrice());

            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);
        mergeCartItems();
        binding.subtotal.setText("TK "+ cart.getTotalPrice());
        subtotal=cart.getTotalPrice().intValue();
        total_price=cart.getTotalPrice().intValue()+delivery_fee;

        binding.total.setText("TK "+total_price);

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processOrder();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void processOrder() {

        int customer_id = sharedPrefManager.getUser().getCustomer_id();

        JSONObject productOrder = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {

            productOrder.put("customer_id", customer_id);
            productOrder.put("address", binding.addressBox.getText().toString());
            productOrder.put("cost",subtotal);
            productOrder.put("delivery_fee",delivery_fee);
            productOrder.put("total_price",total_price);
            productOrder.put("payment_type","Cash on Delivery");
            productOrder.put("payment_status","Unpaid");
            productOrder.put("order_date",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date()));
            productOrder.put("order_status","Placed");


            dataObject.put("product_order", productOrder);
            Log.e("err", dataObject.toString());

        } catch (JSONException e) {
        }

        RequestQueue queue = Volley.newRequestQueue(GroceryCheckoutActivity.this);
        Log.e("err1", dataObject.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.POST_GROCERY_ORDER_URL, dataObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getJSONObject("data").getString("status").equals("success")) {
                        String orderId = response.getJSONObject("data").getString("order_id");
                        process_order_details(orderId);

                        new AlertDialog.Builder(GroceryCheckoutActivity.this)
                                .setTitle("Order Successful")
                                .setCancelable(false)
                                .setMessage("Your order number is: " + orderId)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(GroceryCheckoutActivity.this, GroceryMainActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                    else {
                        new AlertDialog.Builder(GroceryCheckoutActivity.this)
                                .setTitle("Order Failed")
                                .setMessage("Something went wrong, please try again.")
                                .setCancelable(false)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();
                        Toast.makeText(GroceryCheckoutActivity.this, "Failed order.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Log.e("res", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;
        queue.add(request);
    }
    public void process_order_details(String orderId){

        JSONObject dataObject = new JSONObject();
        try {

            JSONArray product_order_detail = new JSONArray();
            Cart cart = TinyCartHelper.getCart();
            for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
                GroceryItemModel product = (GroceryItemModel) item.getKey();
                int quantity = item.getValue();
                int price=product.getPrice()*quantity;
                product.setQuantity(quantity);
                JSONObject productObj = new JSONObject();
                productObj.put("order_id",orderId);
                productObj.put("product_id", product.getId());
                productObj.put("quantity", quantity);
                productObj.put("price", price);
                productObj.put("order_date",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date()));

                product_order_detail.put(productObj);
            }

            dataObject.put("product_order_detail", product_order_detail);

        } catch (JSONException e) {
        }

        RequestQueue queue = Volley.newRequestQueue(GroceryCheckoutActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.POST_GROCERY_ORDER_DETAILS_URL, dataObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getJSONObject("data").getString("status").equals("success")) {
                        Toast.makeText(GroceryCheckoutActivity.this, "from product order details", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GroceryCheckoutActivity.this, "Failed order.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;
        queue.add(request);
    }
    public void mergeCartItems() {
        Cart cart = TinyCartHelper.getCart();
        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();

        // Temporary map to store merged products with quantities
        Map<Integer, GroceryItemModel> mergedProductsMap = new HashMap<>();

        // Merge cart items
        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            GroceryItemModel product = (GroceryItemModel) entry.getKey();
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
}