package com.campusrider.campusridercustomer.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.campusrider.campusridercustomer.R;

public class RegistrationActivity extends AppCompatActivity {

    EditText studentid_edit,fullName_edit,username_edit,contact_edit,address_edit,password_edit;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        studentid_edit=findViewById(R.id.studentid_edit);
        fullName_edit=findViewById(R.id.fullName_edit);
        username_edit=findViewById(R.id.username_edit);
        contact_edit=findViewById(R.id.contact_edit);
        address_edit=findViewById(R.id.address_edit);
        password_edit=findViewById(R.id.password_edit);
        next=findViewById(R.id.next_btn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String student_id= studentid_edit.getText().toString();
                String name=fullName_edit.getText().toString();
                String contact=contact_edit.getText().toString();
                String address=address_edit.getText().toString();
                String password=password_edit.getText().toString();
                String username=username_edit.getText().toString();
                if(student_id.isEmpty()){
                    studentid_edit.setError("Field can't be empty");
                    return;
                }
                if(name.isEmpty()){
                    fullName_edit.setError("Field can't be empty");
                    return;
                }
                if(contact.isEmpty()){
                    contact_edit.setError("Field can't be empty");
                    return;
                }
                if(address.isEmpty()){
                    address_edit.setError("Field can't be empty");
                    return;
                }
                if(password.isEmpty()){
                    password_edit.setError("Field can't be empty");
                    return;
                }if(username.isEmpty()){
                    username_edit.setError("Field can't be empty");
                    return;
                }

                else {

                    Intent intent = new Intent(RegistrationActivity.this, StdID_Activity.class);
                    intent.putExtra("keyid", student_id);
                    intent.putExtra("keyname", name);
                    intent.putExtra("keycontact", contact);
                    intent.putExtra("keyaddress", address);
                    intent.putExtra("keypassword", password);
                    intent.putExtra("keyusername", username);
                    startActivity(intent);
                }
            }
        });

    }

    public void log(View view){
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
    }

}



