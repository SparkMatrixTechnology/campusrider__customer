package com.campusrider.campusridercustomer.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.campusrider.campusridercustomer.AfterLoginActivity;
import com.campusrider.campusridercustomer.R;
import com.campusrider.campusridercustomer.utils.Constants;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class StdID_Activity extends AppCompatActivity {
    ImageView frontImage;
    Button submit;
    FloatingActionButton addFront;
    String url= Constants.Registration_URL;
    Bitmap bitmap;
    String encodedimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_id);


        frontImage=findViewById(R.id.front_edit);
        addFront=findViewById(R.id.add_front);
        submit=findViewById(R.id.confirm_button);

        //front image upload
        addFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,111);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                            permissionToken.continuePermissionRequest();
                            }
                        }).check();


            }
        });

        //submit button
      submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Register();
          }
      });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111){
            bitmap=(Bitmap) data.getExtras().get("data");
            frontImage.setImageBitmap(bitmap);
            encodebitmap(bitmap);
        }


    }

    private void encodebitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] byteofimages=byteArrayOutputStream.toByteArray();
        encodedimage= Base64.encodeToString(byteofimages, Base64.DEFAULT);
    }
    public void Register(){
            String student_id=getIntent().getStringExtra("keyid");
            String name=getIntent().getStringExtra("keyname");
            String contact=getIntent().getStringExtra("keycontact");
            String address=getIntent().getStringExtra("keyaddress");
            String password=getIntent().getStringExtra("keypassword");
            String username=getIntent().getStringExtra("keyusername");

            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("User Already Exists!")){
                        Toast.makeText(StdID_Activity.this,response,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(StdID_Activity.this,RegistrationActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(StdID_Activity.this,response,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(StdID_Activity.this, AfterLoginActivity.class);
                        startActivity(intent);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(StdID_Activity.this,error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("student_id",student_id);
                    params.put("name",name);
                    params.put("username",username);
                    params.put("contact",contact);
                    params.put("address",address);
                    params.put("password",password);
                    params.put("id_card_front",encodedimage);

                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(StdID_Activity.this);
            requestQueue.add(request);

    }
    
}