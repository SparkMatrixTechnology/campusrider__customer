package com.campusrider.campusridercustomer.Grocery.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Grocery.Activity.GroceryCartActivity;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.GroceryItemCartBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class GroceryCartAdapter extends RecyclerView.Adapter<GroceryCartAdapter.GroceryCartViewHolder> {

    int quantity;
    Context context;
    ArrayList<GroceryItemModel> products;
    Cart cart;
   CartListener cartListener;

    public interface CartListener {
        public void onQuantityChanged();
    }

    public GroceryCartAdapter(Context context, ArrayList<GroceryItemModel> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public GroceryCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroceryCartViewHolder(LayoutInflater.from(context).inflate(R.layout.grocery_item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryCartViewHolder holder, int position) {

        GroceryItemModel grocery=products.get(position);
        Glide.with(context).load(grocery.getImage()).into(holder.binding.detailedImg);
        quantity = grocery.getQuantity();
        holder.binding.foodName.setText(grocery.getName());
        holder.binding.price.setText("TK " +(grocery.getPrice()*quantity));
        holder.binding.quantity.setText(""+grocery.getQuantity());
        holder.binding.foodUnit.setText(grocery.getUnit());
        holder.binding.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = grocery.getQuantity();
                quantity++;
                grocery.setQuantity(quantity);
                holder.binding.quantity.setText(String.valueOf(quantity));
                holder.binding.price.setText("TK "+(grocery.getPrice()*quantity));
                notifyDataSetChanged();
                cart.updateItem(grocery,grocery.getQuantity());

                cartListener.onQuantityChanged();
            }
        });
        holder.binding.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = grocery.getQuantity();
                if(quantity <= 0){
                    quantity = 0;
                    cart.removeItem(grocery); // Remove the item from the cart if quantity is 0
                    Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    if(quantity > 0){
                        grocery.setQuantity(quantity);
                        holder.binding.quantity.setText(String.valueOf(quantity));
                        holder.binding.price.setText("TK " + (grocery.getPrice() * quantity));
                        notifyDataSetChanged();
                        cart.updateItem(grocery, grocery.getQuantity());
                        cartListener.onQuantityChanged();
                    } else {
                        cart.removeItem(grocery); // Remove the item from the cart if quantity is 0
                        Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();

                        // Start GroceryCartActivity and finish the current activity
                        Intent intent = new Intent(context, GroceryCartActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public  class GroceryCartViewHolder extends RecyclerView.ViewHolder{
        GroceryItemCartBinding binding;
        public GroceryCartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=GroceryItemCartBinding.bind(itemView);
        }

    }
}
