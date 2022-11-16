package com.example.digihelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText phoneNumber;
    AppCompatButton continueButton;
    Intent intent;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (getSupportActionBar() != null) {;
            getSupportActionBar().hide();
        }

        phoneNumber = (EditText) findViewById(R.id.phoneNumberTextField);
        continueButton = (AppCompatButton) findViewById(R.id.continueButton);
        progressBar = (ProgressBar) findViewById(R.id.loader);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(view.VISIBLE);
                continueButton.setVisibility(view.INVISIBLE);
                String number = phoneNumber.getText().toString();
                if(number.length()!=10){
                    progressBar.setVisibility(view.INVISIBLE);
                    continueButton.setVisibility(view.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Invalid Number: "+number,Toast.LENGTH_LONG).show();
                }else{
                    intent = new Intent(getApplicationContext(),OtpActivity.class);
                    intent.putExtra("PhoneNumber", phoneNumber.getText().toString());
                    startActivity(intent);


                    //TODO below line May Change Later when connect to backend
                    phoneNumber.setText("");
                    progressBar.setVisibility(view.INVISIBLE);
                    continueButton.setVisibility(view.VISIBLE);

                }
            }
        });



    }
}