package com.campusrider.campusridercustomer.Food.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Food.activity.ShopCategoryActivity;
import com.campusrider.campusridercustomer.Food.models.HomeHorModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.HorizontalItemBinding;

import java.util.ArrayList;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {


    Context context;
    ArrayList<HomeHorModel> list;

    public HomeHorAdapter(Context context, ArrayList<HomeHorModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HomeHorModel categoryModel=list.get(position);
        holder.binding.horText.setText(categoryModel.getName());
        Glide.with(context).load(categoryModel.getIcon()).into(holder.binding.horImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShopCategoryActivity.class);
                intent.putExtra("name",categoryModel.getName());
                intent.putExtra("id",categoryModel.getId());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        HorizontalItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=HorizontalItemBinding.bind(itemView);




        }
    }
}
