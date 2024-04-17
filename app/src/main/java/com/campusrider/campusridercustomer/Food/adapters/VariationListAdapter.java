package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.models.VariationDetailsModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.VariationDescriptionBinding;
import com.campusrider.campusridercustomer.databinding.VariationListBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VariationListAdapter extends RecyclerView.Adapter<VariationListAdapter.ViewHolder>{
    Context context;
    ArrayList<VariationDetailsModel> list;

    public VariationListAdapter(Context context, ArrayList<VariationDetailsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.variation_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VariationDetailsModel variationDetailsModel=list.get(position);
        holder.binding.variationName.setText(variationDetailsModel.getDescription()+",");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        VariationListBinding binding;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding= VariationListBinding.bind(itemView);



        }
    }

}
