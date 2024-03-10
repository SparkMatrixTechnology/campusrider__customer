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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Grocery.GroceryMainActivity;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.GroceryItemListBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder>{

    BottomSheetDialog bottomSheetDialog;
    Context context;
    ArrayList<GroceryItemModel> list;
    int count=1,qty,cost;
    GroceryItemModel currentProduct;
    SharedPrefManager sharedPrefManager;

    public GroceryItemAdapter(Context context, ArrayList<GroceryItemModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroceryItemModel itemModel=list.get(position);

        final int itemId=itemModel.getId();
        final String itemName=itemModel.getName();
        final String itemImage=itemModel.getImage();
        int itemPrice=itemModel.getPrice();
        final String itemUnit=itemModel.getUnit();
        final String itemDes=itemModel.getDescription();

        holder.binding.detailedName.setText(itemModel.getName());
        holder.binding.detailedDes.setText(itemModel.getDescription());
        holder.binding.unit.setText(itemModel.getUnit());
        holder.binding.price.setText("TK "+itemModel.getPrice());
        Glide.with(context).load(itemModel.getImage()).into(holder.binding.detailedImg);
        holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog=new BottomSheetDialog(v.getContext(),R.style.BottomSheetTheme);
                View sheetView=LayoutInflater.from(v.getContext()).inflate(R.layout.grocery_bottomsheet_dialog_wishlist,null);
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
                price.setText("Tk "+itemPrice);
                description.setText(itemDes);
                unit.setText(itemUnit);
                quantity.setText(""+1);
                getItemDetail(itemId);

                qty=1;
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count++;
                        qty=count;
                        quantity.setText(""+count);
                        cost=itemPrice*qty;
                        price.setText("Tk "+(itemPrice*qty));
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(count<=1){
                            count=1;
                            qty=count;
                        }
                        else {
                            count--;
                            qty=count;
                            quantity.setText(""+count);
                            cost=itemPrice*qty;
                            price.setText("Tk "+(itemPrice*qty));
                        }
                    }
                });

                sheetView.findViewById(R.id.wishlist_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPrefManager=new SharedPrefManager(context);
                        int cus_id=sharedPrefManager.getUser().getCustomer_id();
                        addWishlist(cus_id,qty,itemPrice,itemId);
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
        GroceryItemListBinding binding;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=GroceryItemListBinding.bind(itemView);

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
    void addWishlist(int cus_id,int qty,int price,int item_id){

        RequestQueue queue= Volley.newRequestQueue(context);
        int cost=qty+price;

        StringRequest request=new StringRequest(Request.Method.POST, Constants.POST_WISHLIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")) {
                        Toast.makeText(context,"Added to Wishlist",Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, GroceryMainActivity.class));
                    }
                    else if(object.getString("status").equals("failed")) {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
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
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("customer_id", String.valueOf(cus_id));
                params.put("product_id", String.valueOf(item_id));
                params.put("quantity", String.valueOf(qty));
                params.put("price", String.valueOf(price));
                params.put("cost", String.valueOf(cost));
                return params;
            }
        };
        queue.add(request);

    }

}
