package com.campusrider.campusridercustomer.Food.ui.food_delivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.activity.CategoryActivity;
import com.campusrider.campusridercustomer.Food.activity.SearchActivity;
import com.campusrider.campusridercustomer.Food.activity.SelectAddressActivity;
import com.campusrider.campusridercustomer.Food.adapters.HomeHorAdapter;
import com.campusrider.campusridercustomer.Food.adapters.HomeVerAdapter;
import com.campusrider.campusridercustomer.Food.models.HomeHorModel;
import com.campusrider.campusridercustomer.Food.models.HomeVerModel;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.activity.ProfileActivity;
import com.campusrider.campusridercustomer.databinding.FragmentFoodDeliveryBinding;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodDeliveryFragment extends Fragment {
    RecyclerView homeHorizontalRec,homeVerticalRec;
    ArrayList<HomeHorModel> homeHorModelList;

    HomeHorAdapter homeHorAdapter;

    ArrayList<HomeVerModel> homeVerModelList;
    HomeVerAdapter homeVerAdapter;
    String url= Constants.GET_FOOD_CATEGORIES_URL;
    SharedPrefManager sharedPrefManager;
    TextView areaText,addressText;
    FragmentFoodDeliveryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         binding = FragmentFoodDeliveryBinding.inflate(inflater, container, false);
        View root=binding.getRoot();

        binding.meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("type","meal");
                startActivity(intent);
            }
        });
        binding.drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("type","drink");
                startActivity(intent);
            }
        });
        binding.snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("type","snack");
                startActivity(intent);
            }
        });
        binding.sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("type","sweet");
                startActivity(intent);
            }
        });

       /* homeHorizontalRec=root.findViewById(R.id.home_hor_rec);
        homeVerticalRec=root.findViewById(R.id.home_ver_rec);
        areaText=root.findViewById(R.id.areaText);
        addressText=root.findViewById(R.id.addressText);

        eprofile=root.findViewById(R.id.profile);
        sharedPrefManager=new SharedPrefManager(getContext());
        areaText.setText(sharedPrefManager.getUser().getArea());
        addressText.setText(sharedPrefManager.getUser().getAddress());

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("type","food");
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        areaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SelectAddressActivity.class));
            }
        });
        eprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

/////////////Horizontal//////////////

        homeHorModelList=new ArrayList<>();
        homeHorAdapter=new HomeHorAdapter(getActivity(),homeHorModelList);
        homeHorizontalRec.setAdapter(homeHorAdapter);
        getFoodCategory(getContext());
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);




/////////////////vertical//////////////////
        homeVerModelList=new ArrayList<>();
        homeVerAdapter=new HomeVerAdapter(getActivity(),homeVerModelList);
        homeVerticalRec.setAdapter(homeVerAdapter);
        getVendor(getContext(),sharedPrefManager.getUser().getArea());
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        homeVerticalRec.setHasFixedSize(true);
        homeVerticalRec.setNestedScrollingEnabled(false);*/

        return root;
    }
/*
    public void getVendor(Context context,String area){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_VENDORS_URL+area, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                homeVerModelList.clear();
                try {
                    Log.e("err",response);
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray vendorArray=mainObj.getJSONArray("Vendors");
                        for(int i=0;i<vendorArray.length();i++){
                            JSONObject object=vendorArray.getJSONObject(i);
                            HomeVerModel vendor=new HomeVerModel(
                                    object.getInt("vendor_id"),
                                    object.getString("vendor_name"),
                                    Constants.IMAGE_URL + object.getString("shop_image"),
                                    object.getString("shop_category"),
                                    object.getString("area"),
                                    object.getString("address"),
                                    object.getString("vendor_phone"),
                                    object.getString("delivery_time"),
                                    object.getString("opening_time"),
                                    object.getString("closing_time"),
                                    object.getInt("vendor_status"),
                                    object.getInt("delivery_fee"),
                                    object.getString("vendor_token")
                            );
                            homeVerModelList.add(vendor);
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

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                homeHorModelList.clear();
                try {
                    JSONObject mainObj= new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray food_catArray=mainObj.getJSONArray("Food category List");
                        for(int i=0;i<food_catArray.length();i++){
                            JSONObject object=food_catArray.getJSONObject(i);
                            HomeHorModel food_cat=new HomeHorModel(
                                    object.getInt("id"),
                                    object.getString("name"),
                                    Constants.IMAGE_URL + object.getString("icon")
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

*/
}