package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.Food.models.VariationDetailsModel;
import com.campusrider.campusridercustomer.Food.models.VariationModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.ShopListItemBinding;
import com.campusrider.campusridercustomer.databinding.VariationDetailBinding;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VariationAdapter extends RecyclerView.Adapter<VariationAdapter.ViewHolder> {

    Context context;
    ArrayList<VariationModel> list;
    private  RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
    private int selectedPosition = -1;

    public VariationAdapter(Context context, ArrayList<VariationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.variation_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VariationModel variationModel=list.get(position);
        holder.binding.variationName.setText(variationModel.getVariation_name());
        holder.binding.variationStatus.setText(variationModel.getStatus());

        LinearLayoutManager layoutManager=new LinearLayoutManager(holder.binding.vardetailsrec.getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(variationModel.getVariationDetailsModels().size());
        VariationDetailsAdapter variationDetailsAdapter=new VariationDetailsAdapter(context,variationModel.getVariationDetailsModels());
        holder.binding.vardetailsrec.setLayoutManager(layoutManager);
        holder.binding.vardetailsrec.setAdapter(variationDetailsAdapter);
        holder.binding.vardetailsrec.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        VariationDetailBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=VariationDetailBinding.bind(itemView);
        }
    }
}
