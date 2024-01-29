package com.epicdeveloper.xcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.res.Resources;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.epicdeveloper.xcar.ui.Chat.fragment_chat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.epicdeveloper.xcar.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class locationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Resources resources;

    String selectedLanguage;

    Context context;

    private final int FINE_PERMISSION_CODE = 1;
    static Location currentLocation;

    public static String add;
    FusedLocationProviderClient fusedLocationProviderClient;

    private ActivityMapsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedLanguage= MainActivity.userlanguage;
        context = LocaleHelper.setLocale(this, selectedLanguage);
        resources = context.getResources();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(locationActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (com.epicdeveloper.xcar.currentLocation.type.equals("C")){
            LatLng latLng = new LatLng(com.epicdeveloper.xcar.currentLocation.latitudfield, com.epicdeveloper.xcar.currentLocation.longitudfield);
            getLocation(com.epicdeveloper.xcar.currentLocation.latitudfield, com.epicdeveloper.xcar.currentLocation.longitudfield);
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }else {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            getLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Locatio permission is denied, allow access the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getLocation(double lat, double lng) {
        Geocoder geocoder = new Geocoder(locationActivity.this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            com.epicdeveloper.xcar.currentLocation.place = add;
            Log.e("IGA", "Address" + add);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        com.epicdeveloper.xcar.currentLocation.longitudLbl.setText(resources.getString(R.string.longitudLbl)+" "+ currentLocation.getLongitude());
        com.epicdeveloper.xcar.currentLocation.latitudLbl.setText(resources.getString(R.string.latitudLbl)+" "+ currentLocation.getLatitude());
        com.epicdeveloper.xcar.currentLocation.placeLbl.setText((resources.getString(R.string.placeLabel))+" "+ add);
    }
}