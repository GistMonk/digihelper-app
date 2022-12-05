package com.example.digihelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class OtpActivity extends AppCompatActivity {

    EditText otpText;
    AppCompatButton verifyBtn;
    TextView phoneNumText;
    ProgressBar otpProgressBar;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        verifyBtn = (AppCompatButton) findViewById(R.id.verifyButton);
        otpProgressBar = (ProgressBar) findViewById(R.id.otpLoader);

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

        otpText = (EditText) findViewById(R.id.otpTextField);
        phoneNumText = (TextView) findViewById(R.id.phoneNumText);
        phoneNumText.setText(newString);

        verifyBtn.setOnClickListener(view -> {

            String otpTextString = otpText.getText().toString();

            verifyBtn.setVisibility(view.INVISIBLE);
            otpProgressBar.setVisibility(view.VISIBLE);

            if(otpTextString.equals("123")){
                i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);

                otpText.setText("");
                verifyBtn.setVisibility(view.VISIBLE);
                otpProgressBar.setVisibility(view.INVISIBLE);
            }
            else{
                verifyBtn.setVisibility(view.VISIBLE);
                otpProgressBar.setVisibility(view.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Invalid otp", Toast.LENGTH_LONG).show();
            }
            // Toast.makeText(getApplicationContext(), "otp is "+otpTextString,Toast.LENGTH_LONG).show();
        });

    }
}