package com.campusrider.campusridercustomer.Grocery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.activity.MyOrderDetailsActivity;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryOrderModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.GroceryorderListBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroceryOrderAdapter extends  RecyclerView.Adapter<GroceryOrderAdapter.ViewHolder> {

    Context context;
    ArrayList<GroceryOrderModel> list;
    String itemCount,num=null;

    public GroceryOrderAdapter(Context context, ArrayList<GroceryOrderModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.groceryorder_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GroceryOrderModel order=list.get(position);
        int id=order.getId();
        holder.binding.IdTextView.setText(""+order.getId());
        holder.binding.date.setText(order.getOrder_date());

        holder.binding.status.setText(order.getOrder_status());
        holder.binding.totalBill.setText("TK "+order.getTotal_price());

        holder.binding.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MyOrderDetailsActivity.class);
                String type="grocery";
                intent.putExtra("type",type);
                intent.putExtra("id",order.getId());
                intent.putExtra("address",order.getAddress());
                intent.putExtra("delivery",order.getDelivery_fee());
                intent.putExtra("payment",order.getPayment_type());
                intent.putExtra("cost",order.getCost());
                intent.putExtra("total_price",order.getTotal_price());
                intent.putExtra("status",order.getOrder_status());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });

        RequestQueue queue= Volley.newRequestQueue(context);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, "http://campusriderbd.com/Customer/customer/item_count.php?id=" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("count", String.valueOf(response));
                    if (response.getJSONObject("data").getString("status").equals("success")) {
                        itemCount=response.getJSONObject("data").getString("count");
                        holder.binding.ItemCountTextView.setText(itemCount);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        GroceryorderListBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=GroceryorderListBinding.bind(itemView);
        }
    }

}
