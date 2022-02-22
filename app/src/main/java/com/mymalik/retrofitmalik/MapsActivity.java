package com.mymalik.retrofitmalik;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mymalik.retrofitmalik.databinding.ActivityMapsBinding;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ActivityResultLauncher<String[]> req ;

    ActivityMapsBinding binding ;

    double currentLong =0 ;
    double currentLat =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        req = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result.get(Manifest.permission.ACCESS_COARSE_LOCATION) &&result.get(Manifest.permission.ACCESS_FINE_LOCATION)){
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locationListener = new FMLocationListener();
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500 , 10 , locationListener);
                }
            }
        });

        binding.addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLong==0 || currentLat==0){
                    Toast.makeText(MapsActivity.this, "لم يتم تحديد الموقع", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                Intent intent = new Intent();
                intent.putExtra("LONG" , currentLong);
                intent.putExtra("LAT" , currentLat);
                setResult(1 , intent);
                finish();
            }
        });
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                LatLng sydney = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Position"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney , 14f));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra("LONG" , latLng.longitude);
                        intent.putExtra("LAT" , latLng.latitude);
                        setResult(1 , intent);
                        finish();
                    }
                }, 500);
            }
        });



        req.launch(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION ,Manifest.permission.ACCESS_FINE_LOCATION});

        // Add a marker in Sydney and move the camera
        MyJsonObject object = (MyJsonObject) getIntent().getSerializableExtra("key");

        LatLng sydney = new LatLng(Double.parseDouble(object.getAddress().getGeo().getLat()) ,
                Double.parseDouble(object.getAddress().getGeo().getLng()));
        mMap.addMarker(new MarkerOptions().position(sydney).title(object.getAddress().getGeo().getLat() +"\n"+object.getAddress().getGeo().getLng()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney , 5f));
    }

    private class FMLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
//            mMap.clear();
//            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Position"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney , 14f));
//
//            currentLat = location.getLatitude() ;
//            currentLong = location.getLongitude() ;
        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }
}