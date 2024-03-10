package com.campusrider.campusridercustomer.Food.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.campusrider.campusridercustomer.Food.adapters.CartAdapter;
import com.campusrider.campusridercustomer.Food.models.ProductModel;
import com.campusrider.campusridercustomer.databinding.ActivityCartBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    CartAdapter adapter;
    ArrayList<ProductModel> productModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String vendor_token=getIntent().getStringExtra("vendor_token");

        System.out.println("vendor_token"+ vendor_token);
        productModels=new ArrayList<>();
        Cart cart = TinyCartHelper.getCart();
        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();

        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            if (item instanceof ProductModel) {
                ProductModel product = (ProductModel) item;
                product.setQuantity(quantity);
                productModels.add(product);
            }
        }

        adapter=new CartAdapter(this, productModels, new CartAdapter.CartListener() {
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
                Intent intent=new Intent(CartActivity.this,CheckoutActivity.class);
                intent.putExtra("vendor_token",vendor_token);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void mergeCartItems() {
        Cart cart = TinyCartHelper.getCart();
        Map<Item, Integer> allItemsWithQty = cart.getAllItemsWithQty();

        // Temporary map to store merged products with quantities
        Map<Integer, ProductModel> mergedProductsMap = new HashMap<>();

        // Merge cart items
        for (Map.Entry<Item, Integer> entry : allItemsWithQty.entrySet()) {
            ProductModel product = (ProductModel) entry.getKey();
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