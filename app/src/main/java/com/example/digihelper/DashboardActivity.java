package com.example.digihelper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements LocationListener {

    // Text view variables //

    TextView timeText;
    TextView speedText;
    TextView speedKmText;
    TextView crashText;
    TextView latLongText;
    TextView altitudeText;
    TextView baMeasureText;
    TextView realtimeUncertainText;

    // crash image view

    ImageView crashImage;

    // values variable declarations

    double currentSpeed ;
    double prevSpeed;
    double speedMinStamp;
    double speedMaxStamp;
    double timeDiff;
    long time;
    double latitude;
    double longitude;
    double altitude;
    double bearingAccuracy;
    double realtimeUncertainty;

    //  Crash detected sound
    MediaPlayer mediaPlayerAlert;
    // Monitor sound voice at start of app
    MediaPlayer mediaPlayerMonitor;
    // Speeding alert Sound
    MediaPlayer mediaPlayerSpeedAlert;



    public  String timeStampToFormattedTime(long timestamp){

        Date timeD = new Date((long) (timestamp * 1000));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(timeD);
        return  time;
    }


//RESULT this code convert 1633304782 to 05:46:33


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);


        // setting initial values to  0 for all variables //

        currentSpeed = 0;
        prevSpeed =0;
        speedMaxStamp= 0;
        speedMinStamp =0;
        time =0;
        latitude =0;
        longitude =0;
        altitude =0;
        bearingAccuracy =0;
        realtimeUncertainty =0;


        // views initializations //

        speedKmText = findViewById(R.id.speedKmText);
        crashImage = findViewById(R.id.crashImage);
        crashText = findViewById(R.id.crashText);
        speedText = findViewById(R.id.speedText);
        timeText = findViewById(R.id.timeMeasure);
        latLongText = findViewById(R.id.locationText);
        altitudeText = findViewById(R.id.altitudeText);
        baMeasureText = findViewById(R.id.baMeasure);
        realtimeUncertainText = findViewById(R.id.ruMeasure);


        // Setting initial TextViews  //

        speedText.setText("0.0m/s");
        speedKmText.setText("0.0km/h");

        // initializing media sounds  //

        mediaPlayerAlert = MediaPlayer.create(getApplicationContext(), R.raw.alert);
        mediaPlayerMonitor = MediaPlayer.create(getApplicationContext(), R.raw.start);
        mediaPlayerSpeedAlert = MediaPlayer.create(getApplicationContext(),R.raw.speed);


        // play monitoring starting sound at start of application
        mediaPlayerMonitor.start();

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

    @SuppressLint("SetTextI18n")
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
            time = location.getTime();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();
            bearingAccuracy = location.getBearing();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                realtimeUncertainty = location.getElapsedRealtimeUncertaintyNanos();
            }


            Log.i("speed","Current Speed : [location] " + mCurrentSpeed+ "m/s");

            // setting text view values on location change
            timeText.setText(timeStampToFormattedTime(time));
            speedText.setText(mCurrentSpeed+"m/s");
            speedKmText.setText(kmSpeed+"km/h");
            latLongText.setText("Lat: " + latitude +" Long: "+longitude);
            altitudeText.setText(Double.toString(altitude) + 'm');
            baMeasureText.setText(Double.toString(bearingAccuracy));
            realtimeUncertainText.setText(Double.toString(realtimeUncertainty));

            // play sound on over speeding
            if(currentSpeed > 1){
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






