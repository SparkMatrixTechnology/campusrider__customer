package com.campusrider.campusridercustomer.Food.adapters;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.campusrider.campusridercustomer.Food.models.CategoryModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.CatWiseProductBinding;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
        Context context;
        private  RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
        ArrayList<CategoryModel> list;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_wise_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel categoryModel=list.get(position);
        holder.binding.catName.setText(categoryModel.getName());

        LinearLayoutManager layoutManager=new LinearLayoutManager(holder.binding.productRec.getContext(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setInitialPrefetchItemCount(categoryModel.getProductModelArrayList().size());
        ProductAdapter productAdapter=new ProductAdapter(context,categoryModel.getProductModelArrayList());
        holder.binding.productRec.setLayoutManager(layoutManager);
        holder.binding.productRec.setAdapter(productAdapter);
        holder.binding.productRec.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder{

            CatWiseProductBinding binding;

            public ViewHolder(@NonNull View itemView){
                super(itemView);
                binding=CatWiseProductBinding.bind(itemView);

            }
        }


}
