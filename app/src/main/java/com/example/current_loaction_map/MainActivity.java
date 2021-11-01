package com.example.current_loaction_map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //AIzaSyAcQeC-0uSilsuB-Ptgk9Frx60jLkV7cLo
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Double latitude,longitude;
    private Geocoder geocoder;
    private List<Address>addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      geocoder=new Geocoder(this,Locale.ENGLISH);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);


                if(locationResult!=null){
                    for(Location location:locationResult.getLocations()){
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                        try {

                         addresses=  geocoder.getFromLocation(latitude,longitude,1);
                          Address address = addresses.get(0);
                          String addresss=address.getAddressLine(0)+"\n"+address.getLocality()+"/n"+address.getPostalCode();
                            Toast.makeText(MainActivity.this, addresss, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        Toast.makeText(MainActivity.this, "  "+ latitude+"  "+ longitude, Toast.LENGTH_LONG).show();
                    }
                }
            }

        };


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();;
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},101);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);


    }

    public void Start_Map(View view) {
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("LAT",latitude);
        intent.putExtra("LON",longitude);
        startActivity(intent);
    }
}