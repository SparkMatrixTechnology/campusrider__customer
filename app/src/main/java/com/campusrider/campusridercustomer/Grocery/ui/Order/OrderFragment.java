package com.campusrider.campusridercustomer.Grocery.ui.Order;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Grocery.Adapter.GroceryOrderAdapter;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryOrderModel;
import com.campusrider.campusridercustomer.databinding.FragmentOrderBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    ArrayList<GroceryOrderModel> orderModels;
    ArrayList<GroceryOrderModel> activeorderModels;
    GroceryOrderAdapter myOrderAdapter;
    GroceryOrderAdapter activeOrderAdapter;

    private FragmentOrderBinding binding;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPrefManager=new SharedPrefManager(getContext());
        int customer_id=sharedPrefManager.getUser().getCustomer_id();

        orderModels=new ArrayList<>();
        myOrderAdapter=new GroceryOrderAdapter(getActivity(),orderModels);
        binding.pastOrderRec.setAdapter(myOrderAdapter);
        activeorderModels=new ArrayList<>();
        activeOrderAdapter=new GroceryOrderAdapter(getActivity(),activeorderModels);
        binding.activeOrderRec.setAdapter(activeOrderAdapter);
        getDeliverdOrderList(getContext(),customer_id);
        binding.pastOrderRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        binding.pastOrderRec.setHasFixedSize(true);
        binding.pastOrderRec.setNestedScrollingEnabled(false);
        binding.activeOrderRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        binding.activeOrderRec.setHasFixedSize(true);
        binding.activeOrderRec.setNestedScrollingEnabled(false);


        return root;
    }
    public void getDeliverdOrderList(Context context, int id){

        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_GROCERY_ORDER_LIST_URL+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Order_list");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            GroceryOrderModel orders=new GroceryOrderModel(
                                    object.getInt("id"),
                                    object.getInt("customer_id"),
                                    object.getString("address"),
                                    object.getInt("cost"),
                                    object.getInt("delivery_fee"),
                                    object.getInt("total_bill"),
                                    object.getString("payment_type"),
                                    object.getString("payment_status"),
                                    object.getString("order_date"),
                                    object.getString("order_status"),
                                    object.getInt("rider_id")
                            );
                            if(!object.getString("order_status").equals("Delivered")){
                                binding.activeCard.setVisibility(View.VISIBLE);
                                activeorderModels.add(orders);
                            }
                            else {
                                orderModels.add(orders);
                            }

                        }
                        myOrderAdapter.notifyDataSetChanged();
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