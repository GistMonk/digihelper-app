package com.example.digihelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class OtpActivity extends AppCompatActivity {

    EditText optText;
    AppCompatButton verifyBtn;
    TextView phoneNumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyBtn = (AppCompatButton) findViewById(R.id.verifyButton);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("PhoneNumber");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("PhoneNumber");
        }

        phoneNumText = (TextView) findViewById(R.id.phoneNumText);
        phoneNumText.setText(newString);

        verifyBtn.setOnClickListener(view -> {
            optText = (EditText) findViewById(R.id.otpTextField);
            String otpTextString = optText.getText().toString();
            Toast.makeText(getApplicationContext(), "otp is "+otpTextString,Toast.LENGTH_LONG).show();
        });

    }
}