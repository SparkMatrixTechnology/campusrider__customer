package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Food.activity.MyOrderDetailsActivity;
import com.campusrider.campusridercustomer.Food.models.MyOrderModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.OrderListBinding;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    Context context;
    ArrayList<MyOrderModel> list;

    public MyOrderAdapter(Context context, ArrayList<MyOrderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyOrderModel order=list.get(position);
        Glide.with(context).load(order.getShop_image()).into(holder.binding.shopImg);
        holder.binding.shopName.setText(order.getVendor_name());
        holder.binding.totalBill.setText("TK "+order.getTotal_price());
        holder.binding.date.setText("Orderd on "+order.getOrder_date());
        holder.binding.orderStatus.setText(order.getOrder_status());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MyOrderDetailsActivity.class);
                String type="food";
                intent.putExtra("type",type);
                intent.putExtra("id",order.getId());
                intent.putExtra("address",order.getAddress());
                intent.putExtra("shop_name",order.getVendor_name());
                intent.putExtra("shop_address",order.getShop_address());
                intent.putExtra("delivery",order.getDelivery_fee());
                intent.putExtra("payment",order.getPayment_type());
                intent.putExtra("cost",order.getCost());
                intent.putExtra("total_price",order.getTotal_price());
                intent.putExtra("status",order.getOrder_status());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        OrderListBinding binding;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=OrderListBinding.bind(itemView);



        }
    }
}
