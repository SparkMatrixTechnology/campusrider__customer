package com.campusrider.campusridercustomer.Food.adapters;

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
import com.campusrider.campusridercustomer.Food.activity.CartActivity;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.ItemCartBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    int quantity;
    Context context;
    ArrayList<ProductModel> products;
    Cart cart;
    CartListener cartListener;
    public interface CartListener {
        public void onQuantityChanged();
    }

    public CartAdapter(Context context, ArrayList<ProductModel> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        ProductModel product=products.get(position);
        Glide.with(context).load(product.getImage()).into(holder.binding.detailedImg);
        quantity = product.getQuantity();
        holder.binding.foodName.setText(product.getName());
        holder.binding.price.setText("TK " +(product.getPrice()*quantity));
        holder.binding.quantity.setText(""+product.getQuantity());


        holder.binding.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = product.getQuantity();
                quantity++;
                product.setQuantity(quantity);
                holder.binding.quantity.setText(String.valueOf(quantity));
                holder.binding.price.setText("TK "+(product.getPrice()*quantity));

                notifyDataSetChanged();
                cart.updateItem(product, product.getQuantity());
                cartListener.onQuantityChanged();
            }
        });
        holder.binding.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 quantity = product.getQuantity();
                if(quantity <= 0){
                    quantity = 0;
                    cart.removeItem(product); // Remove the item from the cart if quantity is 0
                    Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    if(quantity > 0){
                        product.setQuantity(quantity);
                        holder.binding.quantity.setText(String.valueOf(quantity));
                        holder.binding.price.setText("TK " + (product.getPrice() * quantity));
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    } else {
                        cart.removeItem(product); // Remove the item from the cart if quantity is 0
                        Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();

                        // Start GroceryCartActivity and finish the current activity
                        Intent intent = new Intent(context, CartActivity.class);
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

    public  class CartViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding binding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemCartBinding.bind(itemView);
        }
    }
}
