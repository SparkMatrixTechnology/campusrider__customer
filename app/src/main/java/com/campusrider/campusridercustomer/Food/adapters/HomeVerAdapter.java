package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Food.activity.ShopActivity;
import com.campusrider.campusridercustomer.Food.models.HomeVerModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.HomeVerticalItemBinding;

import java.util.ArrayList;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder>{

    Context context;
    ArrayList<HomeVerModel> list;

    public HomeVerAdapter(Context context, ArrayList<HomeVerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_vertical_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeVerModel vendorModel=list.get(position);
        holder.binding.verName.setText(vendorModel.getVendor_name());
        Glide.with(context).load(vendorModel.getShop_image()).into(holder.binding.verImage);
        holder.binding.area.setText(vendorModel.getArea());
        holder.binding.deliveryFee.setText(" Tk "+vendorModel.getDelivery_fee());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShopActivity.class);
                intent.putExtra("shop_name",vendorModel.getVendor_name());
                intent.putExtra("shop_image",vendorModel.getShop_image());
                intent.putExtra("id",vendorModel.getVendor_id());
                intent.putExtra("vendor_token",vendorModel.getVendor_token());
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
        HomeVerticalItemBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=HomeVerticalItemBinding.bind(itemView);

        }
    }
}
