package com.example.digihelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText personName;
    EditText personEmail;
    ProgressBar registerSubmitLoader;
    AppCompatButton registerSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        personName = (EditText) findViewById(R.id.editTextPersonName);
        personEmail = (EditText) findViewById(R.id.editTextPersonEmail);
        registerSubmitLoader = (ProgressBar) findViewById(R.id.registerLoader);
        registerSubmitButton = (AppCompatButton) findViewById(R.id.submitDetailsButton);

        registerSubmitButton.setOnClickListener(view -> {
            String personNameString = personName.getText().toString();
            String personEmailString = personEmail.getText().toString();
            Toast.makeText(getApplicationContext(), "Name is : "+personNameString+" Email is : "+personEmailString, Toast.LENGTH_LONG).show();
        });

    }
}