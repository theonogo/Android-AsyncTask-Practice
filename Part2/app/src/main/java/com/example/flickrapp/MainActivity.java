package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.flickrapp.classes.AsyncBitmapDownloader;
import com.example.flickrapp.classes.GetImageOnClickListener;
import com.example.flickrapp.classes.MyLocationListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        MyLocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates("gps",5000,10, locationListener);

        Button getImageButton = (Button) findViewById(R.id.get_image_button);
        Button listViewButton = (Button) findViewById(R.id.list_button);
        ImageView image = (ImageView) findViewById(R.id.image);

        getImageButton.setOnClickListener( new GetImageOnClickListener(locationManager, image));
        
        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(launch);
            }
        });
    }
}