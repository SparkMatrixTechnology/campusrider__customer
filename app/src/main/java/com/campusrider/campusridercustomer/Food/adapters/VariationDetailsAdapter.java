package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.campusrider.campusridercustomer.Food.models.VariationModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.activity.OTPActivity;
import com.campusrider.campusridercustomer.activity.RegistrationActivity;
import com.campusrider.campusridercustomer.activity.StdID_Activity;
import com.campusrider.campusridercustomer.databinding.VariationDescriptionBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VariationDetailsAdapter extends RecyclerView.Adapter<VariationDetailsAdapter.ViewHolder>{
    Context context;
    ArrayList<VariationDetailsModel> list;
    private int selectedPosition = -1;
    SharedPrefManager sharedPrefManager;
    public VariationDetailsModel getSelectedVariation() {
        if (selectedPosition != -1) {
            return list.get(selectedPosition);
        }
        return null;
    }
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
        sharedPrefManager=new SharedPrefManager(context);
        VariationDetailsModel variationDetailsModel=list.get(position);
        holder.binding.radiobutton.setText(variationDetailsModel.getDescription());
        if(variationDetailsModel.getPrice()==0){
            holder.binding.vprice.setText("Free");
        }else {
            holder.binding.vprice.setText("TK "+variationDetailsModel.getPrice());
        }
        holder.binding.radiobutton.setChecked(position == selectedPosition);
        holder.binding.radiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
                System.out.println(variationDetailsModel.getDescription());
                StringRequest request=new StringRequest(Request.Method.POST, "http://campusriderbd.com/Customer/customer/add_temp_variation.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("yes")){
                            System.out.println(response);

                        }else{

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params=new HashMap<String, String>();
                        params.put("customer_id",String.valueOf(sharedPrefManager.getUser().getCustomer_id()));
                        params.put("variation_id",String.valueOf(variationDetailsModel.getVariation_id()));
                        params.put("product_id",String.valueOf(variationDetailsModel.getProduct_id()));
                        params.put("variation_detail_id",String.valueOf(variationDetailsModel.getId()));
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(context);
                requestQueue.add(request);
            }
        });

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
