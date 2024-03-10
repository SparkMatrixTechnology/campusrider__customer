package com.campusrider.campusridercustomer.Grocery.ui.Pantry_List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Grocery.Adapter.WishlistAdapter;
import com.campusrider.campusridercustomer.Grocery.Model.WishlistModel;
import com.campusrider.campusridercustomer.databinding.FragmentShoppingListBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PantryListFragment extends Fragment {

    private FragmentShoppingListBinding binding;

    ArrayList<WishlistModel> wishlistModels;
    WishlistAdapter wishlistAdapter;
    SharedPrefManager sharedPrefManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPrefManager=new SharedPrefManager(getContext());
        int customer_id=sharedPrefManager.getUser().getCustomer_id();
        wishlistModels=new ArrayList<>();
        wishlistAdapter=new WishlistAdapter(getActivity(),wishlistModels);
        binding.cartList.setAdapter(wishlistAdapter);
        getItem(customer_id);
        binding.cartList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        binding.cartList.setHasFixedSize(true);
        binding.cartList.setNestedScrollingEnabled(false);

        return root;
    }
    public void getItem(int id){
        RequestQueue queue= Volley.newRequestQueue(getContext());
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_WISHLIST_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Order_list");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            WishlistModel wishlist=new WishlistModel(
                                    object.getInt("id"),
                                    object.getInt("customer_id"),
                                    object.getInt("product_id"),
                                    object.getInt("quantity"),
                                    object.getInt("price"),
                                    object.getInt("cost"),
                                    object.getInt("grocery_cat"),
                                    object.getString("name"),
                                    object.getString("description"),
                                    object.getString("unit"),
                                    Constants.IMAGE_URL+object.getString("image")
                            );
                            wishlistModels.add(wishlist);
                        }
                        wishlistAdapter.notifyDataSetChanged();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}