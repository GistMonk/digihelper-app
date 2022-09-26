

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


public class OtpScreen extends AppCompatActivity {

    EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        txtName = findViewById(R.id.editTextNumber);

    public void displayMsg()
    {

        String name =  txtName.getText().toString();
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
    }

}