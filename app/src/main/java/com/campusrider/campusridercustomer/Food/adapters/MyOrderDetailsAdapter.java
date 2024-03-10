package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrider.campusridercustomer.Food.models.MyOrderDetailsModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.FoodOrderDetailsBinding;

import java.util.ArrayList;


public class MyOrderDetailsAdapter extends RecyclerView.Adapter<MyOrderDetailsAdapter.ViewHolder>{
    Context context;
    ArrayList<MyOrderDetailsModel> list;

    public MyOrderDetailsAdapter(Context context, ArrayList<MyOrderDetailsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_order_details,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyOrderDetailsModel orderDetailsModel =list.get(position);
        holder.binding.quantity.setText(""+orderDetailsModel.getQuantity());
        holder.binding.itemName.setText(orderDetailsModel.getProduct_name());
        holder.binding.cost.setText("TK "+ orderDetailsModel.getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        FoodOrderDetailsBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding= FoodOrderDetailsBinding.bind(itemView);
        }
    }
}