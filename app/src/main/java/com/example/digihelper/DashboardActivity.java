package com.example.digihelper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements LocationListener {

    TextView speedText ;
    TextView speedKmText;
    TextView crashText;
    TextView baMeasureText;
    ImageView crashImage;

    double currentSpeed ;
    double prevSpeed;
    double speedMinStamp;
    double speedMaxStamp;
    double timeDiff;


    MediaPlayer mediaPlayerAlert;
    MediaPlayer meadiaPlayerMonitor;
    MediaPlayer mediaPlayerSpeedAlert;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null) {;
            getSupportActionBar().hide();
        }

        speedText = findViewById(R.id.speedText);
        currentSpeed = 0;
        prevSpeed =0;
        speedMaxStamp= 0;
        speedMinStamp =0;
        speedKmText = findViewById(R.id.speedKmText);
        crashImage = findViewById(R.id.crashImage);
        crashText = findViewById(R.id.crashText);
        baMeasureText = findViewById(R.id.baMeasure);
        speedText.setText("0.0m/s");
        speedKmText.setText("0.0km/h");


//        int color=speedText.getCurrentTextColor();
//        String hexColor = String.format("#%06X", (0xFFFFFF & color));
//        if(hexColor.equals("#FFFFFF")){
//            Toast.makeText(getApplicationContext(), "color is : " + hexColor, Toast.LENGTH_LONG).show();
//        }
//        int color=speedText.getCurrentTextColor();
//        String hexColor = String.format("#%06X", (0xFFFFFF & color));
//
//        if (hexColor.equals("#FFFFFF")) {
//            Toast.makeText(getApplicationContext(), "color is : " + hexColor, Toast.LENGTH_LONG).show();
//            speedText.setTextColor(Color.parseColor("#FF0000"));
//        } else {
//            Toast.makeText(getApplicationContext(), "color is : " + hexColor, Toast.LENGTH_LONG).show();
//            speedText.setTextColor(Color.parseColor("#FFFFFF"));
//        }

        mediaPlayerAlert = MediaPlayer.create(getApplicationContext(), R.raw.alert);
        meadiaPlayerMonitor = MediaPlayer.create(getApplicationContext(), R.raw.start);
        mediaPlayerSpeedAlert = MediaPlayer.create(getApplicationContext(),R.raw.speed);

        meadiaPlayerMonitor.start();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("permission","You don't have permission of location");

            return;
        }
                Log.i("permission","You  have permission of location");

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);



    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double speed = 0;

        Log.i("speed","Current Speed : [location null] 0.0m/s");


        if(location == null){
            Log.i("speed","Current Speed : [location null] 0.0m/s");
            speedText.setText("0.0m/s");
            speedKmText.setText("0.0km/h");
        }else{
            float mCurrentSpeed = location.getSpeed();
            prevSpeed = currentSpeed;
            currentSpeed =mCurrentSpeed;
            double kmSpeed = (mCurrentSpeed*3.6);
            speed  = mCurrentSpeed;


            Log.i("speed","Current Speed : [location] " + mCurrentSpeed+ "m/s");
            speedText.setText(mCurrentSpeed+"m/s");

            speedKmText.setText(kmSpeed+"km/h");

            // play sound on over speeding
            if(currentSpeed > 1){
                int color=speedText.getCurrentTextColor();
                String hexColor = String.format("#%06X", (0xFFFFFF & color));

                if (hexColor.equals("#FFFFFF")) {
                    Toast.makeText(getApplicationContext(), "color is : " + hexColor, Toast.LENGTH_LONG).show();
                    speedText.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    Toast.makeText(getApplicationContext(), "color is : " + hexColor, Toast.LENGTH_LONG).show();
                    speedText.setTextColor(Color.parseColor("#FFFFFF"));
                }

                mediaPlayerSpeedAlert.start();
            }

            if(currentSpeed == 0){
                speedMinStamp = location.getTime();
                timeDiff = Math.abs(speedMaxStamp - speedMinStamp);
            }

            if(currentSpeed > 1){
                speedMaxStamp = location.getTime();
            }

            if(currentSpeed == prevSpeed && currentSpeed == 0){

                baMeasureText.setText("Diff : " + 0);


            }

            baMeasureText.setText("Diff : " + timeDiff);

            if(timeDiff !=0 &&  timeDiff < 5001 ) {
                crashText.setText("Crash Detected");
                crashImage.setBackground(getDrawable(R.drawable.cragradient));
                mediaPlayerAlert.start();
            }


        }

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
        Log.i("speed","Current Speed : [location null] 0.0m/s");

    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
        Log.i("speed","Flush Complete");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
        Log.i("speed","status changed");

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
        Log.i("speed","Provider Enabled");

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
        Log.i("speed","Provider Disabled");

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
        Log.i("speed","Pointer Capture");

    }
}






