package com.example.digihelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class OtpActivity extends AppCompatActivity {

    EditText optText;
    AppCompatButton verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyBtn = (AppCompatButton) findViewById(R.id.verifyButton);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optText = (EditText) findViewById(R.id.otpTextField);
                String otpTextString = optText.getText().toString();
                Toast.makeText(getApplicationContext(), "otp is "+otpTextString,Toast.LENGTH_LONG).show();
                String value;
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    value = bundle.getString("sample_name");
                }
            }

        });

    }
}