package com.campusrider.campusridercustomer.Grocery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Grocery.Activity.ItemActivity;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryCategoryModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.HorizontalItemBinding;

import java.util.ArrayList;

public class GroceryCategoryAdapter extends RecyclerView.Adapter<GroceryCategoryAdapter.ViewHolder>{

    Context context;
    ArrayList<GroceryCategoryModel> groceryCategoryModels;

    public GroceryCategoryAdapter(Context context, ArrayList<GroceryCategoryModel> groceryCategoryModels) {
        this.context = context;
        this.groceryCategoryModels = groceryCategoryModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GroceryCategoryModel categoryModel=groceryCategoryModels.get(position);
        holder.binding.horText.setText(categoryModel.getName());
        Glide.with(context).load(categoryModel.getIcon()).into(holder.binding.horImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ItemActivity.class);
                intent.putExtra("name",categoryModel.getName());
                intent.putExtra("id",categoryModel.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groceryCategoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        HorizontalItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=HorizontalItemBinding.bind(itemView);




        }
    }
}
