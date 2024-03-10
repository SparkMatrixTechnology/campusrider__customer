package com.campusrider.campusridercustomer.Food.ui.My_Orders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.adapters.MyOrderAdapter;
import com.campusrider.campusridercustomer.Food.models.MyOrderModel;
import com.campusrider.campusridercustomer.databinding.FragmentMyOrderBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrderFragment extends Fragment {

    ArrayList<MyOrderModel> orderModels;
    ArrayList<MyOrderModel> activeorderModels;
    MyOrderAdapter myOrderAdapter;
    MyOrderAdapter activeOrderAdapter;
    String url= Constants.GET_ORDER_LIST_URL;
    private FragmentMyOrderBinding binding;
    SharedPrefManager sharedPrefManager;
    int status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPrefManager=new SharedPrefManager(getContext());
        int customer_id=sharedPrefManager.getUser().getCustomer_id();

        orderModels=new ArrayList<>();
        myOrderAdapter=new MyOrderAdapter(getActivity(),orderModels);
        binding.pastOrderRec.setAdapter(myOrderAdapter);
        activeorderModels=new ArrayList<>();
        activeOrderAdapter=new MyOrderAdapter(getActivity(),activeorderModels);
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

    public void getDeliverdOrderList(Context context,int id){

        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, url+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Order_list");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            MyOrderModel orders=new MyOrderModel(
                                    object.getInt("id"),
                                    object.getInt("customer_id"),
                                    object.getInt("vendor_id"),
                                    object.getString("address"),
                                    object.getInt("cost"),
                                    object.getInt("delivery_fee"),
                                    object.getInt("total_price"),
                                    object.getString("comment"),
                                    object.getString("payment_type"),
                                    object.getString("payment_status"),
                                    object.getString("order_status"),
                                    object.getString("order_date"),
                                    object.getInt("rider_id"),
                                    object.getString("vendor_name"),
                                    Constants.IMAGE_URL+object.getString("shop_image"),
                                    object.getString("shop_address")
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}