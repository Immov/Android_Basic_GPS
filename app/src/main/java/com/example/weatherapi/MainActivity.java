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

        btn_getLoc.setOnClickListener(new View.OnClickListener() { //Get current location

            @Override
            public void onClick(View view) {
                String a_lat = "0";
                String a_lon = "0";
                a_lat = getLocs(1);
                a_lon = getLocs(2);
                t_lat.setText(a_lat);
                t_lon.setText(a_lon);
                String tempResp = "";
                String param = "&lat="+a_lat+"&lon="+a_lon;
                tempResp = getWeather(param);
                System.out.println(tempResp);//Delete later
            }
        });
    }

    public String getLocs(int ID) { //Get Current Lat and Lon 1=lat, 2=lon
        String asd_lat = "";
        String asd_lon = "";
        gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            asd_lat = String.valueOf(latitude);
            asd_lon = String.valueOf(longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
        if (ID == 1) {
            return asd_lat;
        } else if (ID == 2) {
            return asd_lon;
        } else {
            return "0";
        }
    }

    protected String getWeather(String param) {
        String fulllUrl = url1 + param;
        String response = HttpRequest.excuteGet(fulllUrl);
        return response;
    }

}