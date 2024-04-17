package com.campusrider.campusridercustomer.Food.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Food.activity.CartActivity;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.Food.models.VariationDetailsModel;
import com.campusrider.campusridercustomer.Food.models.VariationModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.ItemCartBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    int quantity;
    Context context;
    ArrayList<ProductModel> products;
    Cart cart;
    CartListener cartListener;
    ArrayList<VariationDetailsModel> variationModelArrayList;
    VariationListAdapter variationDetailsAdapter;
    SharedPrefManager sharedPrefManager;
    private  RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
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

        sharedPrefManager=new SharedPrefManager(context);
        ProductModel product=products.get(position);
        Glide.with(context).load(product.getImage()).into(holder.binding.detailedImg);
        quantity = product.getQuantity();
        holder.binding.foodName.setText(product.getName());
        holder.binding.quantity.setText(""+product.getQuantity());
        holder.binding.price.setText("TK " +(product.getPrice()*quantity));

        ArrayList<VariationDetailsModel> variations = product.getVariations();
        if (variations == null || variations.isEmpty()) {
           // fetchVariationDetails(product, holder);
        } else {
            setVariationsAdapter(holder, variations);
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://campusriderbd.com/Customer/customer/view_variation_cost.php?product_id=" + product.getId() + "&customer_id=" + sharedPrefManager.getUser().getCustomer_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("errr",response);
                            JSONArray variationsArray = new JSONArray(response);

                            ArrayList<VariationDetailsModel> variationDetails = new ArrayList<>();
                            for (int i = 0; i < variationsArray.length(); i++) {
                                JSONObject variationObj = variationsArray.getJSONObject(i);
                                VariationDetailsModel variationDetail = new VariationDetailsModel(
                                        variationObj.getInt("id"),
                                        variationObj.getInt("variation_id"),
                                        variationObj.getInt("price"),
                                        variationObj.getInt("product_id"),
                                        variationObj.getString("description")
                                );
                                variationDetails.add(variationDetail);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FetchVariationDetails", "Error fetching variation details: " + error.getMessage());
                        // Handle error
                    }
                });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
/*
        if (variations != null && !variations.isEmpty()) {
            LinearLayoutManager variationLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            VariationListAdapter variationAdapter = new VariationListAdapter(context, variations);
            holder.binding.variationrec.setLayoutManager(variationLayoutManager);
            holder.binding.variationrec.setAdapter(variationAdapter);
        }
*/
        holder.binding.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = product.getQuantity();
                quantity++;
                product.setQuantity(quantity);
                holder.binding.quantity.setText(String.valueOf(quantity));
                holder.binding.price.setText("TK "+((product.getPrice())*quantity));
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
                        holder.binding.price.setText("TK "+(product.getPrice()*quantity));
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
    public void setVariationsAdapter(CartViewHolder holder, ArrayList<VariationDetailsModel> variations) {
        LinearLayoutManager variationLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        VariationListAdapter variationAdapter = new VariationListAdapter(context, variations);
        holder.binding.variationrec.setLayoutManager(variationLayoutManager);
        holder.binding.variationrec.setAdapter(variationAdapter);
    }
    public  class CartViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding binding;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemCartBinding.bind(itemView);
        }
    }
}
