package com.campusrider.campusridercustomer.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.campusrider.campusridercustomer.R;
import com.hbb20.CountryCodePicker;

public class RegistrationActivity extends AppCompatActivity {

    EditText studentid_edit,fullName_edit,ePhone,address_edit,password_edit;
    Button next;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        studentid_edit=findViewById(R.id.studentid_edit);
        fullName_edit=findViewById(R.id.fullName_edit);
        ePhone=findViewById(R.id.phone);
        address_edit=findViewById(R.id.address_edit);
        password_edit=findViewById(R.id.password_edit);
        next=findViewById(R.id.next_btn);
        countryCodePicker=findViewById(R.id.countryCodePicker);
        countryCodePicker.registerCarrierNumberEditText(ePhone);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String student_id= studentid_edit.getText().toString();
                String name=fullName_edit.getText().toString();
                String contact=ePhone.getText().toString();
                String address=address_edit.getText().toString();
                String password=password_edit.getText().toString();
                if(student_id.isEmpty()){
                    studentid_edit.setError("Field can't be empty");
                    return;
                }
                if(name.isEmpty()){
                    fullName_edit.setError("Field can't be empty");
                    return;
                }
                if(contact.isEmpty()){
                    ePhone.setError("Field can't be empty");
                    return;
                }
                if(address.isEmpty()){
                    address_edit.setError("Field can't be empty");
                    return;
                }
                if(password.isEmpty()){
                    password_edit.setError("Field can't be empty");
                    return;
                }

                else {

                    Intent intent = new Intent(RegistrationActivity.this, StdID_Activity.class);
                    intent.putExtra("keyid", student_id);
                    intent.putExtra("keyname", name);
                    intent.putExtra("keycontact", countryCodePicker.getFullNumberWithPlus());
                    intent.putExtra("keyaddress", address);
                    intent.putExtra("keypassword", password);
                    startActivity(intent);
                }
            }
        });

    }

    public void log(View view){
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
    }

}



