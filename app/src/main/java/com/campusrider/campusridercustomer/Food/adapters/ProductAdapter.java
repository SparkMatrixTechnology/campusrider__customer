package com.campusrider.campusridercustomer.Food.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.campusrider.campusridercustomer.Food.models.CategoryModel;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.Food.models.VariationDetailsModel;
import com.campusrider.campusridercustomer.Food.models.VariationModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.databinding.ShopListItemBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    BottomSheetDialog bottomSheetDialog;
    Context context;
    ArrayList<ProductModel> list;
    ArrayList<VariationModel> variationModelArrayList;
    VariationDetailsAdapter variationDetailsAdapter;
    VariationAdapter variationAdapter;
    int count=1;
    ProductModel currentProduct;
    SharedPrefManager sharedPrefManager;
    int qty;

    public ProductAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductModel product=list.get(position);
        final int itemid=product.getId();
        final String itemname=product.getName();
        int itemprice=product.getPrice();
        final String itemimage=product.getImage();

        holder.binding.detailedName.setText(product.getName());
        holder.binding.detailedDes.setText(product.getDescription());
        holder.binding.price.setText("Tk "+product.getPrice());
        Glide.with(context).load(list.get(position).getImage()).into(holder.binding.detailedImg);

        holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog=new BottomSheetDialog(v.getContext(),R.style.BottomSheetTheme);
                View sheetView=LayoutInflater.from(v.getContext()).inflate(R.layout.bottomsheet_dialog,null);
                TextView quantity=sheetView.findViewById(R.id.quantity);
                ImageView minus=sheetView.findViewById(R.id.imageMinus);
                ImageView plus=sheetView.findViewById(R.id.imageAddOne);
                RecyclerView variationrec=sheetView.findViewById(R.id.variationview);
                ImageView imageView=sheetView.findViewById(R.id.detailed_img);
                TextView name=sheetView.findViewById(R.id.food_name);
                TextView price=sheetView.findViewById(R.id.price);
                Glide.with(context).load(itemimage).into(imageView);
                name.setText(itemname);
                price.setText("Tk "+itemprice);
                quantity.setText(""+1);
                getProductDetail(itemid);
                variationModelArrayList=new ArrayList<>();
                variationAdapter=new VariationAdapter(sheetView.getContext(), variationModelArrayList);
                variationrec.setAdapter(variationAdapter);
                getVariation(context,itemid);
                LinearLayoutManager layoutManager=new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
                variationrec.setLayoutManager(layoutManager);

                 qty=1;
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count++;
                        qty=count;
                        quantity.setText(""+count);
                        price.setText("Tk "+(itemprice*qty));
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
                            price.setText("Tk "+(itemprice*qty));
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

        ShopListItemBinding binding;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            binding=ShopListItemBinding.bind(itemView);



        }
    }
    void getProductDetail(int id){
        RequestQueue queue= Volley.newRequestQueue(context);

        sharedPrefManager=new SharedPrefManager(context);
        StringRequest request=new StringRequest(Request.Method.GET,"http://campusriderbd.com/Customer/customer/product_detail_with_var.php?product_id=" +id +"&customer_id=" +sharedPrefManager.getUser().getCustomer_id() , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("success")) {
                        JSONArray product_array=object.getJSONArray("Product");
                        JSONObject product=product_array.getJSONObject(0);

                        currentProduct = new ProductModel(
                                product.getInt("id"),
                                product.getString("product_name"),
                                product.getString("product_description"),
                                product.getInt("total_price"),
                                Constants.IMAGE_URL + product.getString("product_image"),
                                product.getInt("vendor_id")

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
    public void getVariation(Context context,int id){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_VARIATION_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray variation_Array=mainObj.getJSONArray("Variation List");
                        for(int i=0;i<variation_Array.length();i++) {
                            JSONObject variation = variation_Array.getJSONObject(i);
                            VariationModel variationModel=new VariationModel(
                                    variation.getInt("id"),
                                    variation.getInt("product_id"),
                                    variation.getString("variation_name"),
                                    variation.getString("status")
                            );
                            JSONArray variation_detail_Array = mainObj.getJSONArray("Variation Details List");
                            ArrayList<VariationDetailsModel> variationDetailsModels = new ArrayList<>();
                            for (int j = 0; j < variation_detail_Array.length(); j++) {
                                JSONObject variation_detail = variation_detail_Array.getJSONObject(j);
                                if(variation.getInt("id")==variation_detail.getInt("variation_id")) {
                                    Log.e("catres1", response);

                                    variationDetailsModels.add(new VariationDetailsModel(
                                            variation_detail.getInt("id"),
                                            variation_detail.getInt("variation_id"),
                                            variation_detail.getInt("price"),
                                            variation_detail.getInt("product_id"),
                                            variation_detail.getString("description")
                                    ));

                                }

                            }
                            variationModel.setVariationDetailsModels(variationDetailsModels);
                            variationModelArrayList.add(variationModel);
                        }
                        variationAdapter.notifyDataSetChanged();
                    }
                    else {

                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
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
