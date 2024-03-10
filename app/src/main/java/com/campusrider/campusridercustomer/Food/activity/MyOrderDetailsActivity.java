package com.campusrider.campusridercustomer.Food.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.adapters.MyOrderDetailsAdapter;
import com.campusrider.campusridercustomer.Food.models.MyOrderDetailsModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrderDetailsActivity extends AppCompatActivity {
    String type,status;
    int order_id,total_price,delivery_fee,subtotal;
    String address,payment,shop_name,shop_address;
    RecyclerView rec;
    LinearLayout progressLayout;
    ProgressBar placedProgress,acceptedProgress,pickedProgress,deliveredProgress;
    TextView orderText,shopNameText,addressText,subtotalText,deliveryText,billText,paymentText,statusText;
    ArrayList<MyOrderDetailsModel> orderDetailsModels;
    MyOrderDetailsAdapter orderDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getStringExtra("type");

        if(type.equals("food")){
            setContentView(R.layout.activity_my_order_details);

            orderText=findViewById(R.id.order_text);
            shopNameText=findViewById(R.id.storeaddress);
            addressText=findViewById(R.id.cusaddress);
            rec=findViewById(R.id.order_rec);
            subtotalText=findViewById(R.id.subtotal);
            deliveryText=findViewById(R.id.delivery);
            billText=findViewById(R.id.totalbill_text);
            paymentText=findViewById(R.id.payment_type);
            placedProgress=findViewById(R.id.placedProgressBar);
            acceptedProgress=findViewById(R.id.acceptedProgressBar);
            pickedProgress=findViewById(R.id.pickedProgressBar);
            deliveredProgress=findViewById(R.id.deliverdProgressBar);
            statusText=findViewById(R.id.statusTextView);
            progressLayout=findViewById(R.id.progresLayout);

            order_id=getIntent().getIntExtra("id",0);
            total_price=getIntent().getIntExtra("total_price",0);
            delivery_fee=getIntent().getIntExtra("delivery",0);
            subtotal=getIntent().getIntExtra("cost",0);;
            address=getIntent().getStringExtra("address");
            payment=getIntent().getStringExtra("payment");
            shop_name=getIntent().getStringExtra("shop_name");
            status=getIntent().getStringExtra("status");

            orderText.setText("#"+order_id);
            shopNameText.setText(shop_name);
            addressText.setText(address);
            subtotalText.setText("TK "+subtotal);
            deliveryText.setText("TK "+delivery_fee);
            billText.setText("TK "+total_price);
            paymentText.setText(payment);

            if(status.equals("Placed")){
                placedProgress.setVisibility(View.VISIBLE);
                placedProgress.incrementProgressBy(100);
                statusText.setText("Your Order Placed Successfully");
            }
            else if(status.equals("Picked")){
                placedProgress.setVisibility(View.VISIBLE);
                placedProgress.incrementProgressBy(100);
                acceptedProgress.setVisibility(View.VISIBLE);
                acceptedProgress.incrementProgressBy(100);
                pickedProgress.setVisibility(View.VISIBLE);
                pickedProgress.incrementProgressBy(100);
                statusText.setText("Your Order Has Been Picked By our Rider");
            }
            else if(status.equals("Delivered")){
                placedProgress.setVisibility(View.VISIBLE);
                placedProgress.incrementProgressBy(100);
                acceptedProgress.setVisibility(View.VISIBLE);
                acceptedProgress.incrementProgressBy(100);
                pickedProgress.setVisibility(View.VISIBLE);
                pickedProgress.incrementProgressBy(100);
                deliveredProgress.setVisibility(View.VISIBLE);
                deliveredProgress.incrementProgressBy(100);
                statusText.setText("Deliverd");
            }
            else if(status.equals("Accepted")){
                placedProgress.setVisibility(View.VISIBLE);
                placedProgress.incrementProgressBy(100);
                acceptedProgress.setVisibility(View.VISIBLE);
                acceptedProgress.incrementProgressBy(100);
                statusText.setText("Your Order Has Been Accepted");
            }
            else if(status.equals("Cancelled")){
                progressLayout.setVisibility(View.GONE);
                statusText.setText("Your Order Has Been Cancelled");
            }

            orderDetailsModels=new ArrayList<>();
            orderDetailsAdapter=new MyOrderDetailsAdapter(getApplicationContext(),orderDetailsModels);
            rec.setAdapter(orderDetailsAdapter);
            getFoodDetails(order_id);
            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
            rec.setHasFixedSize(true);
            rec.setNestedScrollingEnabled(false);

        }
        else if(type.equals("grocery")){

            setContentView(R.layout.activity_grocery_order_details);

            orderText=findViewById(R.id.order_text);
            addressText=findViewById(R.id.cusaddress);
            rec=findViewById(R.id.order_rec);
            subtotalText=findViewById(R.id.subtotal);
            deliveryText=findViewById(R.id.delivery);
            billText=findViewById(R.id.totalbill_text);
            paymentText=findViewById(R.id.payment_type);
            placedProgress=findViewById(R.id.placedProgressBar);
            deliveredProgress=findViewById(R.id.deliveredProgressBar);
            statusText=findViewById(R.id.statusTextView);
            progressLayout=findViewById(R.id.progresLayout);

            order_id=getIntent().getIntExtra("id",0);
            total_price=getIntent().getIntExtra("total_price",0);
            delivery_fee=getIntent().getIntExtra("delivery",0);
            subtotal=getIntent().getIntExtra("cost",0);;
            address=getIntent().getStringExtra("address");
            payment=getIntent().getStringExtra("payment");
            status=getIntent().getStringExtra("status");


            orderText.setText("#"+order_id);
            addressText.setText(address);
            subtotalText.setText("TK "+subtotal);
            deliveryText.setText("TK "+delivery_fee);
            billText.setText("TK "+total_price);
            paymentText.setText(payment);
            if(status.equals("Placed")){
                placedProgress.setVisibility(View.VISIBLE);
                placedProgress.incrementProgressBy(100);
                statusText.setText("Your Order Placed Successfully");
            }

            else if(status.equals("Delivered")){
                placedProgress.setVisibility(View.VISIBLE);
                placedProgress.incrementProgressBy(100);
                deliveredProgress.setVisibility(View.VISIBLE);
                deliveredProgress.incrementProgressBy(100);
                statusText.setText("Deliverd");
            }

            else if(status.equals("Cancelled")){
                progressLayout.setVisibility(View.GONE);
                statusText.setText("Your Order Has Been Cancelled");
            }

            orderDetailsModels=new ArrayList<>();
            orderDetailsAdapter=new MyOrderDetailsAdapter(getApplicationContext(),orderDetailsModels);
            rec.setAdapter(orderDetailsAdapter);
            getGroceryDetails(order_id);
            rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
            rec.setHasFixedSize(true);
            rec.setNestedScrollingEnabled(false);
            getSupportActionBar().setTitle("Placed Orders");

        }


        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void getGroceryDetails(int orderId) {
        RequestQueue queue= Volley.newRequestQueue(MyOrderDetailsActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_GROCERY_ORDER_DETAILS_URL+orderId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray order_array=mainObj.getJSONArray("Order_details");
                        for(int i=0;i<order_array.length();i++){
                            JSONObject object=order_array.getJSONObject(i);
                            MyOrderDetailsModel orderDetails=new MyOrderDetailsModel(
                                    object.getInt("id"),
                                    object.getInt("order_id"),
                                    object.getInt("product_id"),
                                    object.getInt("quantity"),
                                    object.getInt("price"),
                                    object.getString("order_date"),
                                    object.getString("name")
                            );
                            orderDetailsModels.add(orderDetails);
                        }
                        orderDetailsAdapter.notifyDataSetChanged();
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



    public void getFoodDetails(int orderId) {
        RequestQueue queue= Volley.newRequestQueue(MyOrderDetailsActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_FOOD_ORDER_DETAILS_URL+orderId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray order_array=mainObj.getJSONArray("Order_details");
                        for(int i=0;i<order_array.length();i++){
                            JSONObject object=order_array.getJSONObject(i);
                            MyOrderDetailsModel orderDetails=new MyOrderDetailsModel(
                                    object.getInt("id"),
                                    object.getInt("order_id"),
                                    object.getInt("product_id"),
                                    object.getInt("quantity"),
                                    object.getInt("vendor_id"),
                                    object.getInt("price"),
                                    object.getString("order_date"),
                                    object.getString("product_name")
                            );
                            orderDetailsModels.add(orderDetails);
                        }
                        orderDetailsAdapter.notifyDataSetChanged();
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