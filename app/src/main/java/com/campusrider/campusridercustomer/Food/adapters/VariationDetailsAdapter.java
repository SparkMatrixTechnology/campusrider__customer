package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrider.campusridercustomer.Food.models.VariationDetailsModel;
import com.campusrider.campusridercustomer.Food.models.VariationModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.VariationDescriptionBinding;

import java.util.ArrayList;

public class VariationDetailsAdapter extends RecyclerView.Adapter<VariationDetailsAdapter.ViewHolder>{
    Context context;
    ArrayList<VariationDetailsModel> list;
    private int selectedPosition = -1;
    public VariationDetailsAdapter(Context context, ArrayList<VariationDetailsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.variation_description,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VariationDetailsModel variationDetailsModel=list.get(position);
        holder.binding.radiobutton.setText(variationDetailsModel.getDescription());
        if(variationDetailsModel.getPrice()==0){
            holder.binding.vprice.setText("Free");
        }else {
            holder.binding.vprice.setText("TK "+variationDetailsModel.getPrice());
        }
        holder.binding.radiobutton.setChecked(position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       VariationDescriptionBinding binding;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding= VariationDescriptionBinding.bind(itemView);



        }
    }
}
