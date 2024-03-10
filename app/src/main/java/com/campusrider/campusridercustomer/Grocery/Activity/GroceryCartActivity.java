package com.campusrider.campusridercustomer.Grocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.campusrider.campusridercustomer.Grocery.Adapter.GroceryCartAdapter;
import com.campusrider.campusridercustomer.Grocery.Model.GroceryItemModel;
import com.campusrider.campusridercustomer.databinding.ActivityGroceryCartBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroceryCartActivity extends AppCompatActivity {
    ActivityGroceryCartBinding binding;
    GroceryCartAdapter adapter;
    ArrayList<GroceryItemModel> productModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroceryCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productModels=new ArrayList<>();
        Cart cart = TinyCartHelper.getCart();
        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();

        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            if (item instanceof GroceryItemModel) {
                GroceryItemModel product = (GroceryItemModel) item;
                product.setQuantity(quantity);
                productModels.add(product);
            }
        }


        adapter=new GroceryCartAdapter(this, productModels, new GroceryCartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText("TK "+ cart.getTotalPrice());
            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);
        binding.subtotal.setText("TK "+ cart.getTotalPrice());

mergeCartItems();
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GroceryCartActivity.this, GroceryCheckoutActivity.class));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void mergeCartItems() {
        Cart cart = TinyCartHelper.getCart();
        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();

        // Temporary map to store merged products with quantities
        Map<Integer, GroceryItemModel> mergedProductsMap = new HashMap<>();

        // Merge cart items
        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            GroceryItemModel product = (GroceryItemModel) entry.getKey();
            product.setQuantity(entry.getValue());

            int productId = product.getId();

            if (mergedProductsMap.containsKey(productId)) {
                mergedProductsMap.get(productId).setQuantity(mergedProductsMap.get(productId).getQuantity() + product.getQuantity());
            } else {
                mergedProductsMap.put(productId, product);
            }
        }

        // Update productModels list
        productModels.clear();
        productModels.addAll(mergedProductsMap.values());

        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}