package com.campusrider.campusridercustomer.Grocery.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
import com.campusrider.campusridercustomer.Food.activity.SearchActivity;
import com.campusrider.campusridercustomer.Grocery.Adapter.GroceryCategoryAdapter;
import com.campusrider.campusridercustomer.Grocery.Adapter.GroceryItemAdapter;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryCategoryModel;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.activity.ProfileActivity;
import com.campusrider.campusridercustomer.databinding.FragmentGroceryBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroceryFragment extends Fragment {
    RecyclerView homeHorizontalRec,homeVerticalRec;
    ArrayList<GroceryCategoryModel> homeHorModelList;

    GroceryCategoryAdapter homeHorAdapter;

    ArrayList<GroceryItemModel> homeVerModelList;
    GroceryItemAdapter homeVerAdapter;
    SharedPrefManager sharedPrefManager;
    EditText searchEdit;
    private FragmentGroceryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGroceryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeHorizontalRec=root.findViewById(R.id.home_hor_rec);
        homeVerticalRec=root.findViewById(R.id.home_ver_rec);
        sharedPrefManager=new SharedPrefManager(getContext());

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("type", "grocery");
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

/////////////Horizontal//////////////

        homeHorModelList=new ArrayList<>();
        homeHorAdapter=new GroceryCategoryAdapter(getActivity(),homeHorModelList);
        homeHorizontalRec.setAdapter(homeHorAdapter);
        getFoodCategory(getContext());
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);

/////////////////vertical//////////////////
        homeVerModelList=new ArrayList<>();
        homeVerAdapter=new GroceryItemAdapter(getActivity(),homeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        getItems(getContext());
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        homeVerticalRec.setHasFixedSize(true);
        homeVerticalRec.setNestedScrollingEnabled(false);


        return root;
    }
    public void getItems(Context context){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_GROCERY_ITEM_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                homeVerModelList.clear();
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Grocery List");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            GroceryItemModel item=new GroceryItemModel(
                                    object.getInt("id"),
                                    object.getInt("grocery_cat"),
                                    object.getString("name"),
                                    object.getString("description"),
                                    object.getString("unit"),
                                    object.getInt("price"),
                                    Constants.IMAGE_URL + object.getString("image"),
                                    object.getInt("status")
                            );

                            homeVerModelList.add(item);
                        }
                        homeVerAdapter.notifyDataSetChanged();
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
    public void getFoodCategory(Context context){
        RequestQueue queue=Volley.newRequestQueue(context);

        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                homeHorModelList.clear();
                try {
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray food_catArray=mainObj.getJSONArray("Grocery category List");
                        for(int i=0;i<food_catArray.length();i++){
                            JSONObject object=food_catArray.getJSONObject(i);
                            GroceryCategoryModel food_cat=new GroceryCategoryModel(
                                    object.getInt("id"),
                                    object.getString("name"),
                                    Constants.IMAGE_URL + object.getString("icon"),
                                    object.getInt("grocery_status")
                            );
                            homeHorModelList.add(food_cat);
                        }
                        homeHorAdapter.notifyDataSetChanged();
                    }else{

                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}