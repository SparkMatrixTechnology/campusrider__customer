package com.campusrider.campusridercustomer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.campusrider.campusridercustomer.R;

public class QuestionActivity extends AppCompatActivity {

    TextView qs,ans;
    String ques,answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        qs=findViewById(R.id.qs);
        ans=findViewById(R.id.ans);

        ques=getIntent().getStringExtra("ques");
        answer=getIntent().getStringExtra("ans");

        qs.setText(ques);
        ans.setText(answer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}