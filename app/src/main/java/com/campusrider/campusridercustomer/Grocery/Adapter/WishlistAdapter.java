package com.campusrider.campusridercustomer.Grocery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Grocery.GroceryMainActivity;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.Grocery.Model.WishlistModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.GroceryItemDesignBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    BottomSheetDialog bottomSheetDialog;
    Context context;
    ArrayList<WishlistModel> list;
    int qty;
    GroceryItemModel currentProduct;
    SharedPrefManager sharedPrefManager;

    public WishlistAdapter(Context context, ArrayList<WishlistModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WishlistModel item=list.get(position);
         int itemId=item.getId();
         int product_id=item.getProduct_id();
         String itemName=item.getName();
         String itemImage=item.getImage();
         int itemPrice=item.getPrice();
         int itemcost=item.getCost();
         String itemUnit=item.getUnit();
         String itemDes=item.getDescription();
         qty=item.getQuantity();

        holder.binding.detailedName.setText(item.getName());
        holder.binding.detailedDes.setText(item.getDescription());
        holder.binding.unit.setText(item.getUnit());
        holder.binding.price.setText("TK "+(item.getCost()));
        holder.binding.quantity.setText(""+item.getQuantity());
        Glide.with(context).load(item.getImage()).into(holder.binding.detailedImg);

        holder.binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("itemid", String.valueOf(itemId));
                Log.e("productid", String.valueOf(product_id));
                removeItem(itemId);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog=new BottomSheetDialog(v.getContext(),R.style.BottomSheetTheme);
                View sheetView=LayoutInflater.from(v.getContext()).inflate(R.layout.grocery_bottomsheet_dialog,null);
                TextView quantity=sheetView.findViewById(R.id.quantity);
                ImageView minus=sheetView.findViewById(R.id.imageMinus);
                ImageView plus=sheetView.findViewById(R.id.imageAddOne);

                ImageView imageView=sheetView.findViewById(R.id.detailed_img);
                TextView name=sheetView.findViewById(R.id.food_name);
                TextView price=sheetView.findViewById(R.id.price);
                TextView description=sheetView.findViewById(R.id.description);
                TextView unit=sheetView.findViewById(R.id.unit);

                Glide.with(context).load(itemImage).into(imageView);
                name.setText(itemName);
                price.setText("Tk "+itemcost);
                description.setText(itemDes);
                unit.setText(itemUnit);
                quantity.setText(""+qty);
                getItemDetail(product_id);

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qty++;
                        quantity.setText(""+qty);
                        price.setText("Tk "+(itemPrice*qty));
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(qty<=1){
                            qty=1;
                        }
                        else {
                            qty--;
                            quantity.setText(""+qty);
                            price.setText("Tk "+(itemPrice*qty));
                        }
                    }
                });

                Cart cart= TinyCartHelper.getCart();
                sheetView.findViewById(R.id.add_to_cart_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cart.addItem(currentProduct,qty);
                        Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(sheetView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        GroceryItemDesignBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=GroceryItemDesignBinding.bind(itemView);

        }
    }
    void getItemDetail(int id){
        RequestQueue queue= Volley.newRequestQueue(context);

        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_GROCERY_ITEM_DETAIL_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")) {
                        JSONArray product_array=object.getJSONArray("Item");
                        JSONObject product=product_array.getJSONObject(0);

                        currentProduct = new GroceryItemModel(
                                product.getInt("id"),
                                product.getInt("grocery_cat"),
                                product.getString("name"),
                                product.getString("description"),
                                product.getString("unit"),
                                product.getInt("price"),
                                Constants.IMAGE_URL + product.getString("image"),
                                product.getInt("status")
                        );

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);

    }
    public void removeItem(int id){
        RequestQueue queue= Volley.newRequestQueue(context);

        StringRequest request=new StringRequest(Request.Method.POST, Constants.REMOVE_ITEM_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")) {
                        Toast.makeText(context,"Removed",Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, GroceryMainActivity.class));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);
    }
}
