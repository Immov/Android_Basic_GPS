package com.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GpsTracker gpsTracker;
    TextView t_lat, t_lon, t_city, t_weather;
    Button btn_getLoc;


    String url1 = "https://api.openweathermap.org/data/2.5/weather?appid=af9001dce8720bbe36288523ce329664";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t_lat = (TextView) findViewById(R.id.txt_lat);
        t_lon = (TextView) findViewById(R.id.txt_long);
        t_city = (TextView) findViewById(R.id.txt_City);
        t_weather = (TextView) findViewById(R.id.txt_weather);
        btn_getLoc = (Button) findViewById(R.id.btn_getLoc);

        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btn_getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation(view);
            }
        });
    }

    public void getLocation(View view) {
        gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            t_lat.setText(String.valueOf(latitude));
            t_lon.setText(String.valueOf(longitude));
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

}