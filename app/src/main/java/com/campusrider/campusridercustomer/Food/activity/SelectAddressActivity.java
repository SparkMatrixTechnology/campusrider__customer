package com.campusrider.campusridercustomer.Food.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.campusrider.campusridercustomer.Food.MainActivity;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.models.User;
import com.campusrider.campusridercustomer.session.SharedPrefManager;
import com.campusrider.campusridercustomer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectAddressActivity extends AppCompatActivity {
    ArrayList<String> areaArray;
    ArrayAdapter<String> areaAdapter;
    Spinner areaSpinner;
    EditText addressText;
    Button setAddressBtn;
    String area,address;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        sharedPrefManager=new SharedPrefManager(getApplicationContext());
        areaSpinner=(Spinner) findViewById(R.id.spinner_area);
        addressText=findViewById(R.id.addressEditText);
        setAddressBtn=findViewById(R.id.setButton);


        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                area=item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        areaArray=new ArrayList<>();
        getArea();

        addressText.setText(sharedPrefManager.getUser().getAddress());
        setAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address=addressText.getText().toString();
                sharedPrefManager.saveUser(new User(
                        sharedPrefManager.getUser().getCustomer_id(),
                        sharedPrefManager.getUser().getCustomer_name(),
                        address,
                        sharedPrefManager.getUser().getCustomer_phone(),
                        sharedPrefManager.getUser().getCustomer_email(),
                        sharedPrefManager.getUser().getCustomer_password(),
                        sharedPrefManager.getUser().getStudent_id(),
                        sharedPrefManager.getUser().getId_card_front(),
                        sharedPrefManager.getUser().getCustomer_status(),
                        area,
                        sharedPrefManager.getUser().getCustomer_token()));
                Intent intent=new Intent(SelectAddressActivity.this, MainActivity.class);
                startActivity(intent);
                Log.e("user",address);
            }
        });
        getSupportActionBar().setTitle("Set Delivery Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void getArea(){
        RequestQueue queue= Volley.newRequestQueue(SelectAddressActivity.this);
        StringRequest request =new StringRequest(Request.Method.POST, Constants.GET_LOCATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("err", response);
                    JSONObject mainObj = new JSONObject(response);
                    if(mainObj.getString("status").equals("success")){
                        JSONArray area_Array=mainObj.getJSONArray("Location");
                        for(int i=0;i<area_Array.length();i++){
                            JSONObject object=area_Array.getJSONObject(i);
                            String location=object.getString("name");
                            areaArray.add(location);
                            areaAdapter=new ArrayAdapter<>(SelectAddressActivity.this, android.R.layout.simple_spinner_item,areaArray);
                            areaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            areaSpinner.setAdapter(areaAdapter);
                        }
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