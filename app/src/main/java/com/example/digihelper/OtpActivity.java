package com.example.digihelper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;



public class OtpActivity extends AppCompatActivity {

    TextView phoneNumberTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phoneNumberTxt = (TextView) findViewById(R.id.phoneNumberT);

        phoneNumberTxt.setText(getIntent().getStringExtra("PhoneNumber"));

    }
}