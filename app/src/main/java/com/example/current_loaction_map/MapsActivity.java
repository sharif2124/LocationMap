package com.example.current_loaction_map;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.current_loaction_map.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Double Latitude;
    private  Double Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Latitude=getIntent().getDoubleExtra("LAT",1);
        Longitude=getIntent().getDoubleExtra("LON",1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Bank = new LatLng(23.873127053718566, 90.32031661744043);
        LatLng Daffodil = new LatLng(23.877037801412243, 90.32030851864808);

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(Bank);
        polylineOptions.add(Daffodil);
        polylineOptions.color(Color.GREEN);
        polylineOptions.jointType(JointType.BEVEL);
        polylineOptions.width(20);
        mMap.addPolyline(polylineOptions);



        mMap.addMarker(new MarkerOptions().position(Bank).title("Marker in Dhaka").icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(Bank).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(Daffodil).title("Marker in Dhaka").icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        CameraPosition cameraPosition1 = new CameraPosition.Builder().target(Daffodil).zoom(16).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));

    }
}